package ua.com.paradine.core.business;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import io.undertow.util.StatusCodes;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.paradine.core.Errors;
import ua.com.paradine.core.business.vo.commands.SubmitVisitIntentCommand;
import ua.com.paradine.core.business.vo.outcomes.SubmitVisitIntentOutcome;
import ua.com.paradine.core.dao.ExtendedRestaurantRepository;
import ua.com.paradine.core.dao.ExtendedUserRepository;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;
import ua.com.paradine.core.dao.ExtendedWorkingHoursRepository;
import ua.com.paradine.domain.IntendedVisit;
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

    @Captor
    private ArgumentCaptor<IntendedVisit> intendedVisitCaptor;

    @InjectMocks
    private  SubmitVisitIntentFlow submitVisitIntentFlow;


    @Test
    void visitDateCannotBeInPast() {

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().minusDays(1));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.BAD_REQUEST, outcome.getError().getStatus().getStatusCode());

        verifyNoInteractions(visitIntentionRepository, userRepository, restaurantRepository, workingHoursRepository);
    }

    @Test
    void visitDateCannotBeAfterTomorrow() {

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().plusDays(2));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.BAD_REQUEST, outcome.getError().getStatus().getStatusCode());

        verifyNoInteractions(visitIntentionRepository, userRepository, restaurantRepository, workingHoursRepository);
    }

    @Test
    void nonExistentRestaurantShouldYield404() {

        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
            .thenReturn(Optional.empty());

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().plusHours(2));
        cmd.setRestaurantId("123");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.NOT_FOUND, outcome.getError().getStatus().getStatusCode());

        verifyNoInteractions(visitIntentionRepository, userRepository, workingHoursRepository);
    }

    @Test
    void nonExistentUserShouldYield404() {

        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.empty());

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().plusHours(2));
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.NOT_FOUND, outcome.getError().getStatus().getStatusCode());

        verifyNoInteractions(visitIntentionRepository, workingHoursRepository);
    }

    @Test
    void visitInNonBusinessHoursShouldBeRejected_venueClosed() {

        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        lenient().when(workingHoursRepository.fetchByRestaurantIdAndDayOfWeek(eq(1000L), anyString()))
            .thenReturn(Optional.of(new WorkingHours().closed(Boolean.TRUE)));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().plusHours(2));
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.BAD_REQUEST, outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED, outcome.getError().getDetail());

        verifyNoInteractions(visitIntentionRepository);
    }

    @Test
    void visitInNonBusinessHoursShouldBeRejected_venueEOD() {

        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        lenient().when(workingHoursRepository.fetchByRestaurantIdAndDayOfWeek(eq(1000L), anyString()))
            .thenReturn(Optional.of(new WorkingHours().closed(Boolean.FALSE).closingHour(21).openingHour(9)));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(22)); //22:00
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.BAD_REQUEST, outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED, outcome.getError().getDetail());

        verifyNoInteractions(visitIntentionRepository);
    }

    @Test
    void usersAllowedToHavePredefinedNumberOfScheduledVisitsEachDay() {

        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(22)); //22:00
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        lenient().when(visitIntentionRepository.findActiveVisitsByUserAndDay(eq(42L), any(), any()))
            .thenReturn(asList(new IntendedVisit(), new IntendedVisit(), new IntendedVisit()));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.BAD_REQUEST, outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.TOO_MANY_INTENDED_VISITS, outcome.getError().getDetail());
    }

    @Test
    void minimalIntervalsBetweenVisitsMustBeValidated() {

        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(22)); //22:00
        cmd.setRestaurantId("123");
        cmd.setUser("hito");

        IntendedVisit intendedVisit = new IntendedVisit();
        intendedVisit.setCancelled(Boolean.FALSE);
        //scheduled visit from 18:00 to 20:00
        intendedVisit.setVisitStartDate(ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(18));
        intendedVisit.setVisitEndDate(ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).plusHours(20));

        lenient().when(visitIntentionRepository.findActiveVisitsByUserAndDay(eq(42L), any(), any()))
            .thenReturn(asList(intendedVisit));

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(cmd);

        assertNotNull(outcome.getError());
        assertEquals(StatusCodes.BAD_REQUEST, outcome.getError().getStatus().getStatusCode());
        assertEquals(Errors.TOO_CLOSE_TO_EXISTING_VISIT, outcome.getError().getDetail());
    }

    @Test
    void fieldsShouldBePopulatedCorrectly() {

        lenient().when(restaurantRepository.findIdByUuid(eq("123")))
            .thenReturn(Optional.of(1000L));

        lenient().when(userRepository.findIdByLogin(eq("hito")))
            .thenReturn(Optional.of(42L));

        SubmitVisitIntentCommand cmd = new SubmitVisitIntentCommand();
        cmd.setWhen(OffsetDateTime.now().plusHours(2));
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