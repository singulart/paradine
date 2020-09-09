package ua.com.paradine.core.business;

import static java.lang.Math.abs;
import static java.time.OffsetDateTime.now;
import static java.util.UUID.fromString;
import static ua.com.paradine.core.Errors.TOO_CLOSE_TO_EXISTING_VISIT;
import static ua.com.paradine.core.Errors.TOO_MANY_INTENDED_VISITS;
import static ua.com.paradine.core.Errors.VISIT_DATE_OUT_OF_RANGE;
import static ua.com.paradine.core.Errors.VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED;
import static ua.com.paradine.core.ParadineConstants.DEFAULT_ZONE;
import static ua.com.paradine.core.util.DaysOfWeek.DOW;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import ua.com.paradine.core.business.vo.WorkingHoursVO;
import ua.com.paradine.core.business.vo.commands.SubmitVisitIntentCommand;
import ua.com.paradine.core.business.vo.outcomes.SubmitVisitIntentOutcome;
import ua.com.paradine.core.dao.ExtendedRestaurantRepository;
import ua.com.paradine.core.dao.ExtendedUserRepository;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;
import ua.com.paradine.core.dao.ExtendedWorkingHoursRepository;
import ua.com.paradine.domain.IntendedVisit;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.domain.User;
import ua.com.paradine.domain.WorkingHours;

@Service
@Transactional
public class SubmitVisitIntentFlow {

    private final ExtendedVisitIntentionRepository visitIntentionRepository;
    private final ExtendedRestaurantRepository restaurantRepository;
    private final ExtendedUserRepository userRepository;
    private final ExtendedWorkingHoursRepository workingHoursRepository;
    private final RestaurantMapperBusiness mapper = Mappers.getMapper(RestaurantMapperBusiness.class);

    public static final Integer VISIT_DURATION_HOURS = 2;
    public static final Integer MAX_VISITS_PER_DAY = 3;
    public static final Integer MINIMAL_INTERVAL_BETWEEN_VISITS_HOURS = 3;

    @Autowired
    public SubmitVisitIntentFlow(ExtendedVisitIntentionRepository visitIntentionRepository,
        ExtendedRestaurantRepository restaurantRepository,
        ExtendedUserRepository userRepository,
        ExtendedWorkingHoursRepository workingHoursRepository) {
        this.visitIntentionRepository = visitIntentionRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.workingHoursRepository = workingHoursRepository;
    }

    public SubmitVisitIntentOutcome submitVisitIntent(SubmitVisitIntentCommand command) {

        ZonedDateTime plannedVisitDate = command.getWhen().truncatedTo(ChronoUnit.HOURS)
            .atZoneSameInstant(DEFAULT_ZONE);
        Duration dur = Duration.between(now(DEFAULT_ZONE).truncatedTo(ChronoUnit.HOURS), plannedVisitDate);
        OffsetDateTime startOfToday = now(DEFAULT_ZONE).truncatedTo(ChronoUnit.DAYS);
        OffsetDateTime endOfTomorrow = startOfToday.plusDays(2);
        Duration tillEndOfTomorrow = Duration.between(now(DEFAULT_ZONE).truncatedTo(ChronoUnit.HOURS), endOfTomorrow);

        if(dur.isZero() || dur.isNegative() || dur.toHours() >= tillEndOfTomorrow.toHours()) {
            return new SubmitVisitIntentOutcome(
                Problem.valueOf(Status.BAD_REQUEST, VISIT_DATE_OUT_OF_RANGE));
        }

        Optional<Long> restaurant = restaurantRepository.findIdByUuid(command.getRestaurantId());
        if(!restaurant.isPresent()) {
            return new SubmitVisitIntentOutcome(Problem.valueOf(Status.NOT_FOUND));
        }

        Optional<Long> user = userRepository.findIdByLogin(command.getUser());
        if(!user.isPresent()) {
            return new SubmitVisitIntentOutcome(Problem.valueOf(Status.NOT_FOUND));
        }

        String dayOfWeek = DOW.get(plannedVisitDate.getDayOfWeek());
        int hour = plannedVisitDate.getHour();
        Optional<WorkingHours> workingHours = workingHoursRepository
            .fetchByRestaurantIdAndDayOfWeek(restaurant.get(), dayOfWeek);
        if(workingHours.isPresent()) {
            WorkingHoursVO workingHoursVO = mapper.dbEntityToValueObject(workingHours.get());
            WorkingHoursChecker workingHoursChecker = new WorkingHoursChecker();
            if(!workingHoursChecker.isDuringWorkingTime(hour, workingHoursVO)) {
                return new SubmitVisitIntentOutcome(
                    Problem.valueOf(Status.BAD_REQUEST, VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED));
            }
        }

        // minus 1 second is a little trick allowing to stay within 'tomorrow', see testSubmitTooManyVisits_400_end_of_tomorrow_edge_case
        ZonedDateTime startOfDay = plannedVisitDate.minusSeconds(1).truncatedTo(ChronoUnit.DAYS);
        List<IntendedVisit> visitsForTargetDay = visitIntentionRepository
            .findActiveVisitsByUserAndDay(user.get(), startOfDay, startOfDay.plusDays(1));
        if(visitsForTargetDay.size() >= MAX_VISITS_PER_DAY) {
            return new SubmitVisitIntentOutcome(
                Problem.valueOf(Status.BAD_REQUEST, TOO_MANY_INTENDED_VISITS));
        }

        Optional<IntendedVisit> tooCloseTo = visitsForTargetDay.stream().filter(iv ->
            abs(Duration.between(iv.getVisitEndDate(),
                plannedVisitDate).toHours()) < MINIMAL_INTERVAL_BETWEEN_VISITS_HOURS
            ||
            abs(Duration.between(iv.getVisitStartDate(),
                plannedVisitDate).toHours()) < MINIMAL_INTERVAL_BETWEEN_VISITS_HOURS
//                plannedVisitDate).toHours()) > MINIMAL_INTERVAL_BETWEEN_VISITS_HOURS
        ).findFirst();
        if(tooCloseTo.isPresent()) {
            return new SubmitVisitIntentOutcome(
                Problem.valueOf(Status.BAD_REQUEST, TOO_CLOSE_TO_EXISTING_VISIT));
        }

        IntendedVisit visit = persistVisitIntent(user.get(), plannedVisitDate, restaurant.get());
        return new SubmitVisitIntentOutcome(fromString(visit.getUuid()));
    }

    private IntendedVisit persistVisitIntent(Long who, ZonedDateTime when, Long where) {
        IntendedVisit visit = new IntendedVisit();
        visit.setUuid(UUID.randomUUID().toString());
        visit.setVisitingUser(userReference(who));
        visit.setRestaurant(restaurantReference(where));
        visit.setCancelled(Boolean.FALSE);
        visit.setVisitStartDate(when);
        visit.setVisitEndDate(when.plusHours(VISIT_DURATION_HOURS));
        // visit.setSafety(); //TODO add safety calculation
        visitIntentionRepository.saveAndFlush(visit);
        return visit;
    }

    private User userReference(Long id) {
        User ref = new User();
        ref.setId(id);
        return ref;
    }

    private Restaurant restaurantReference(Long id) {
        Restaurant ref = new Restaurant();
        ref.setId(id);
        return ref;
    }
}
