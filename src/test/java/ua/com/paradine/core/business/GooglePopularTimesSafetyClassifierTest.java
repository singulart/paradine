package ua.com.paradine.core.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class GooglePopularTimesSafetyClassifierTest {

//    @Spy
    private GooglePopularTimesSafetyClassifier classifier = spy(new GooglePopularTimesSafetyClassifier());

    @Test
    void classifySafety() {
        when(classifier.getToday()).thenReturn("We");

        RestaurantVO restaurant1 = new RestaurantVO();
        restaurant1.setName("Astarta");
        restaurant1.setCapacity(5);
        PopularTimeVO pop1 = new PopularTimeVO();
        pop1.setDayOfWeek("We");
        pop1.setOcc19(70);
        restaurant1.getPopularTimes().add(pop1);

        ClassifiedRestaurantVO red = classifier.classifySafety(restaurant1);

        assertEquals(SafetyMarker.YELLOW, red.getClassifiersToday().stream()
            .filter(p -> p.getHour() == 18).findFirst().get().getMarker());
    }
}
