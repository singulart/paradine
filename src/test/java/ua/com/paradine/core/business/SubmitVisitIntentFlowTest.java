package ua.com.paradine.core.business;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static ua.com.paradine.core.Nowness.getNow;
import static ua.com.paradine.core.Nowness.getNowZoned;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ua.com.paradine.core.Errors;
import ua.com.paradine.core.business.vo.commands.SubmitVisitIntentCommand;
import ua.com.paradine.core.business.vo.outcomes.SubmitVisitIntentOutcome;
import ua.com.paradine.core.dao.ExtendedRestaurantRepository;
import ua.com.paradine.core.dao.ExtendedUserRepository;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;
import ua.com.paradine.core.dao.ExtendedWorkingHoursRepository;
import ua.com.paradine.core.dao.RestaurantDao;
import ua.com.paradine.domain.IntendedVisit;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.domain.WorkingHours;

@ExtendWith({MockitoExtension.class})
class SubmitVisitIntentFlowTest {

    @Mock
    private ExtendedRestaurantRepository restaurantRepository;

    @Mock
    private ExtendedUserRepository userRepository;

    @Mock
    private ExtendedVisitIntentionRepository visitIntentionRepository;

    @Mock
    private ExtendedWorkingHoursRepository workingHoursRepository;

    @Mock
    private RestaurantDao restaurantDao;

    @Spy
    private GooglePopularTimesSafetyClassifier restaurantSafetyClassifier = new GooglePopularTimesSafetyClassifier();

    @Captor
    private ArgumentCaptor<IntendedVisit> intendedVisitCaptor;

    @InjectMocks
    private  SubmitVisitIntentFlow submitVisitIntentFlow;


    @Test
    void visitDateCannotBeInPast() {

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().minusDays(1));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(BAD_REQUEST.value(), outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.VISIT_DATE_OUT_OF_RANGE, outcome.getError().getDetail());

        verifyNoInteractions(visitIntentionRepository, userRepository, restaurantRepository, workingHoursRepository);
    }

    @Test
    void visitDateCannotBeInPast_one_minute_before_now() {

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().minusMinutes(1));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(BAD_REQUEST.value(), outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.VISIT_DATE_OUT_OF_RANGE, outcome.getError().getDetail());

        verifyNoInteractions(visitIntentionRepository, userRepository, restaurantRepository, workingHoursRepository);
    }

    @Test
    void visitDateCannotBeAfterTomorrow() {

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().plusDays(2));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(BAD_REQUEST.value(), outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.VISIT_DATE_OUT_OF_RANGE, outcome.getError().getDetail());

        verifyNoInteractions(visitIntentionRepository, userRepository, restaurantRepository, workingHoursRepository);
    }

    @Test
    void nonExistentRestaurantShouldYield404() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.empty());
        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(new ArrayList<>()));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().plusHours(2));
        cmd.setRestaurantId("123");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(NOT_FOUND.value(), outcome.getError().getStatus().getStatusCode());

        verifyNoInteractions(visitIntentionRepository, userRepository, workingHoursRepository);
    }

    @Test
    void nonExistentUserShouldYield404() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.of(1000L));
        Restaurant rest = new Restaurant();
        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(rest)));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.empty());

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().plusHours(2));
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(NOT_FOUND.value(), outcome.getError().getStatus().getStatusCode());

        verifyNoInteractions(visitIntentionRepository, workingHoursRepository);
    }

    @Test
    void visitInNonBusinessHoursShouldBeRejected_venueClosed() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        Restaurant rest = new Restaurant();
        rest.getWorkingHours().add(
            new WorkingHours()
                .closed(Boolean.TRUE)
                .dayOfWeek(restaurantSafetyClassifier.getToday())
        );
        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(rest)));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().plusHours(2));
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(BAD_REQUEST.value(), outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED, outcome.getError().getDetail());

        verifyNoInteractions(visitIntentionRepository);
    }

    @Test
    void visitInNonBusinessHoursShouldBeRejected_venueEOD() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.of(1000L));

        Restaurant rest = new Restaurant();
        rest.getWorkingHours().add(
            new WorkingHours()
                .closed(Boolean.FALSE)
                .closingHour(21)
                .openingHour(9)
                .dayOfWeek(restaurantSafetyClassifier.getTomorrow())
        );
        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(rest)));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

//        lenient().when(workingHoursRepository.fetchByRestaurantIdAndDayOfWeek(eq(1000L), anyString()))
//            .thenReturn(Optional.of(new WorkingHours().closed(Boolean.FALSE).closingHour(21).openingHour(9)));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow()
            .truncatedTo(ChronoUnit.DAYS)
            .plusDays(1)
            .plusHours(22)
            .withOffsetSameInstant(ZoneOffset.UTC)); //22:00

        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(BAD_REQUEST.value(), outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED, outcome.getError().getDetail());

        verifyNoInteractions(visitIntentionRepository);
    }

    @Test
    void visitAtExactOpeningHour_should_be_accepted() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        Restaurant rest = new Restaurant();
        rest.getWorkingHours().add(
            new WorkingHours()
                .closed(Boolean.FALSE)
                .closingHour(21)
                .openingHour(9)
                .dayOfWeek(restaurantSafetyClassifier.getTomorrow())
        );
        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(rest)));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow()
            .truncatedTo(ChronoUnit.DAYS)
            .plusDays(1)
            .plusHours(9)
            .withOffsetSameInstant(ZoneOffset.UTC)); //08:00

        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNull(outcome.getError());
    }

    @Test
    void usersAllowedToHavePredefinedNumberOfScheduledVisitsEachDay() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.of(1000L));

        Restaurant rest = new Restaurant();
        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(rest)));


        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().truncatedTo(ChronoUnit.DAYS).plusDays(1).plusHours(22)); //22:00
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        lenient().when(visitIntentionRepository.findActiveVisitsByUserAndDay(eq(42L), any(), any()))
            .thenReturn(asList(new IntendedVisit(), new IntendedVisit(), new IntendedVisit()));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(BAD_REQUEST.value(), outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.TOO_MANY_INTENDED_VISITS, outcome.getError().getDetail());
    }

    @Test
    void minimalIntervalsBetweenVisitsMustBeValidated() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.of(1000L));

        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(new Restaurant())));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().truncatedTo(ChronoUnit.DAYS).plusDays(1).plusHours(22)); //22:00
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        IntendedVisit intendedVisit = new IntendedVisit();
        intendedVisit.setCancelled(Boolean.FALSE);
        //scheduled visit from 18:00 to 20:00
        intendedVisit.setVisitStartDate(getNowZoned().truncatedTo(ChronoUnit.DAYS).plusDays(1).plusHours(18));
        intendedVisit.setVisitEndDate(getNowZoned().truncatedTo(ChronoUnit.DAYS).plusDays(1).plusHours(20));

        lenient().when(visitIntentionRepository.findActiveVisitsByUserAndDay(eq(42L), any(), any()))
            .thenReturn(asList(intendedVisit));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(BAD_REQUEST.value(), outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.TOO_CLOSE_TO_EXISTING_VISIT, outcome.getError().getDetail());
    }

    @Test
    void fieldsShouldBePopulatedCorrectly() {

//        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
//            .thenReturn(Optional.of(1000L));
        Restaurant rest = new Restaurant();
        lenient().when(restaurantDao.loadRestaurants(any()))
            .thenReturn(new PageImpl<>(Collections.singletonList(rest)));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(getNow().plusHours(2));
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getUuid());

        verify(visitIntentionRepository).saveAndFlush(intendedVisitCaptor.capture());
        IntendedVisit capturedVisit = intendedVisitCaptor.getValue();
        assertEquals(SubmitVisitIntentFlow.VISIT_DURATION_HOURS, (int)Duration.between(
            capturedVisit.getVisitStartDate(), capturedVisit.getVisitEndDate()).toHours()
        );
    }
}