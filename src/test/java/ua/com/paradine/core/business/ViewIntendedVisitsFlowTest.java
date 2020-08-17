package ua.com.paradine.core.business;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static ua.com.paradine.core.Nowness.getNowZoned;
import static ua.com.paradine.core.business.ViewIntendedVisitsFlow.TODAY;
import static ua.com.paradine.core.business.ViewIntendedVisitsFlow.TOMORROW;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.paradine.core.business.vo.IntendedVisitVO;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;
import ua.com.paradine.domain.IntendedVisit;

@ExtendWith({MockitoExtension.class})
class ViewIntendedVisitsFlowTest {

    @Mock
    private ExtendedVisitIntentionRepository visitIntentionRepository;

    @InjectMocks
    private  ViewIntendedVisitsFlow viewIntendedVisitsFlow;

    @Test
    void viewMyIntendedVisits_shouldReturnCorrectKindOfDayValues() {
        ZonedDateTime test1 = getNowZoned().truncatedTo(ChronoUnit.DAYS).plusHours(12);
        ZonedDateTime test2 = test1.plusDays(1);

        lenient().when(visitIntentionRepository.findActiveVisitsByUser(eq("foodie")))
            .thenReturn(asList(new IntendedVisit().visitStartDate(test1),
                new IntendedVisit().visitStartDate(test2)));

        List<String> outcome = viewIntendedVisitsFlow.viewMyIntendedVisits("foodie")
            .stream().map(IntendedVisitVO::getKindOfDay).collect(Collectors.toList());

        assertEquals(asList(TODAY, TOMORROW), outcome);
    }

    @Test
    void viewAtExactStartOfToday_should_be_marked_Today() {
        ZonedDateTime test1 = getNowZoned().truncatedTo(ChronoUnit.DAYS);
        lenient().when(visitIntentionRepository.findActiveVisitsByUser(eq("foodie")))
            .thenReturn(asList(new IntendedVisit().visitStartDate(test1)));

        List<String> outcome = viewIntendedVisitsFlow.viewMyIntendedVisits("foodie")
            .stream().map(IntendedVisitVO::getKindOfDay).collect(Collectors.toList());

        assertEquals(asList(TODAY), outcome);
    }

    @Test
    void viewAtExactEndOfToday_should_be_marked_Tomorrow() {
        ZonedDateTime test1 = getNowZoned().truncatedTo(ChronoUnit.DAYS).plusDays(2);

        lenient().when(visitIntentionRepository.findActiveVisitsByUser(eq("foodie")))
            .thenReturn(asList(new IntendedVisit().visitStartDate(test1)));

        List<String> outcome = viewIntendedVisitsFlow.viewMyIntendedVisits("foodie")
            .stream().map(IntendedVisitVO::getKindOfDay).collect(Collectors.toList());

        assertEquals(asList(TOMORROW), outcome);
    }

    @Test
    void visitAtMidnight_shouldBeMarked_Today_not_Tomorrow() {
        ZonedDateTime test1 = getNowZoned().truncatedTo(ChronoUnit.DAYS).plusHours(24);
        ZonedDateTime test2 = test1.plusMinutes(10);

        lenient().when(visitIntentionRepository.findActiveVisitsByUser(eq("foodie")))
            .thenReturn(asList(new IntendedVisit().visitStartDate(test1),
                new IntendedVisit().visitStartDate(test2)));

        List<String> outcome = viewIntendedVisitsFlow.viewMyIntendedVisits("foodie")
            .stream().map(IntendedVisitVO::getKindOfDay).collect(Collectors.toList());

        assertEquals(asList(TODAY, TOMORROW), outcome);
    }

    @Test
    void visitsWithWeirdVisitDatesShouldBeRemovedFromList() {
        ZonedDateTime test1 = getNowZoned().truncatedTo(ChronoUnit.DAYS).plusHours(24);
        ZonedDateTime test2 = test1.plusMinutes(10);

        lenient().when(visitIntentionRepository.findActiveVisitsByUser(eq("foodie")))
            .thenReturn(asList(
                new IntendedVisit().uuid("uuid1").visitStartDate(test1),
                new IntendedVisit().uuid("uuid2").visitStartDate(test2),
                new IntendedVisit().uuid("uuid-should-be-removed2").visitStartDate(test2.minusDays(2)),
                new IntendedVisit().uuid("uuid-should-be-removed2").visitStartDate(test2.plusDays(10))
                )
            );

        List<String> outcome = viewIntendedVisitsFlow.viewMyIntendedVisits("foodie")
            .stream().map(IntendedVisitVO::getId).collect(Collectors.toList());

        assertEquals(asList("uuid1", "uuid2"), outcome);
    }
}