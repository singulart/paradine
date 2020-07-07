package ua.com.paradine.core.business;

import static java.time.OffsetDateTime.now;
import static ua.com.paradine.core.Errors.VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED;
import static ua.com.paradine.core.util.DaysOfWeek.DOW;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
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

        OffsetDateTime plannedVisitDate = command.getWhen().truncatedTo(ChronoUnit.HOURS);
        Duration dur = Duration.between(now().truncatedTo(ChronoUnit.HOURS), plannedVisitDate);
        if(dur.isNegative() || dur.toDays() > 1) {
            return new SubmitVisitIntentOutcome(Problem.valueOf(Status.BAD_REQUEST));
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

        IntendedVisit visit = new IntendedVisit();
        visit.setUuid(UUID.randomUUID().toString());
        visit.setVisitingUser(userReference(user.get()));
        visit.setRestaurant(restaurantReference(restaurant.get()));
        visit.setCancelled(Boolean.FALSE);
        visit.setVisitStartDate(plannedVisitDate.toZonedDateTime());
        visit.setVisitEndDate(plannedVisitDate.plusHours(VISIT_DURATION_HOURS).toZonedDateTime());
        visitIntentionRepository.saveAndFlush(visit);
        return new SubmitVisitIntentOutcome(visit.getUuid());
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
