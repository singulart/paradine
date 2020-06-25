package ua.com.paradine.core.business;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GooglePopularTimesSafetyClassifierTest {

    private RestaurantSafetyClassifier classifier = new GooglePopularTimesSafetyClassifier();

    @Test
    void classifySafety() {
        RestaurantVO restaurant1 = new RestaurantVO();
        restaurant1.setName("Astarta");
        restaurant1.setCapacity(5);
        PopularTimeVO pop1 = new PopularTimeVO();
        pop1.setDayOfWeek("We");
        pop1.setOcc19(70);
        restaurant1.getPopularTimes().add(pop1);

        SafetyVO red = classifier.classifySafety(LocalDateTime.of(2020, Month.JUNE, 17, 19, 0),
            Set.of(restaurant1)).iterator().next();

        assertEquals(SafetyMarker.RED, red.getSafety());
    }
}
