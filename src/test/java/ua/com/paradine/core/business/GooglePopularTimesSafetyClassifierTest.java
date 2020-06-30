package ua.com.paradine.core.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class GooglePopularTimesSafetyClassifierTest {

    private GooglePopularTimesSafetyClassifier classifier = spy(new GooglePopularTimesSafetyClassifier());

    @Test
    void classifySafety() {
        when(classifier.getToday()).thenReturn("We");

        RestaurantVO restaurant1 = new RestaurantVO();
        restaurant1.setName("Astarta");
        restaurant1.setCapacity(5);
        PopularTimeVO pop1 = new PopularTimeVO();
        pop1.setDayOfWeek("We");
        pop1.setOcc01(0);
        pop1.setOcc02(0);
        pop1.setOcc03(0);
        pop1.setOcc04(0);
        pop1.setOcc05(0);
        pop1.setOcc06(0);
        pop1.setOcc07(0);
        pop1.setOcc08(0);
        pop1.setOcc09(25);
        pop1.setOcc10(30);
        pop1.setOcc11(35);
        pop1.setOcc12(40);
        pop1.setOcc13(45);
        pop1.setOcc14(50);
        pop1.setOcc15(55);
        pop1.setOcc16(60);
        pop1.setOcc17(65);
        pop1.setOcc18(70);
        pop1.setOcc19(75);
        pop1.setOcc20(80);
        pop1.setOcc21(65);
        pop1.setOcc22(30);
        pop1.setOcc23(10);
        pop1.setOcc24(10);

        restaurant1.getPopularTimes().add(pop1);

        ClassifiedRestaurantVO red = classifier.classifySafety(restaurant1);

        assessSafety(red, 0, SafetyMarker.GREEN);
        assessSafety(red, 1, SafetyMarker.GREEN);
        assessSafety(red, 2, SafetyMarker.GREEN);
        assessSafety(red, 3, SafetyMarker.GREEN);
        assessSafety(red, 4, SafetyMarker.GREEN);
        assessSafety(red, 5, SafetyMarker.GREEN);
        assessSafety(red, 6, SafetyMarker.GREEN);
        assessSafety(red, 7, SafetyMarker.GREEN);
        assessSafety(red, 8, SafetyMarker.YELLOW);
        assessSafety(red, 9, SafetyMarker.YELLOW);
        assessSafety(red, 10, SafetyMarker.YELLOW);
        assessSafety(red, 11, SafetyMarker.YELLOW);
        assessSafety(red, 12, SafetyMarker.YELLOW);
        assessSafety(red, 13, SafetyMarker.YELLOW);
        assessSafety(red, 14, SafetyMarker.YELLOW);
        assessSafety(red, 15, SafetyMarker.YELLOW);
        assessSafety(red, 16, SafetyMarker.YELLOW);
        assessSafety(red, 17, SafetyMarker.YELLOW);
        assessSafety(red, 18, SafetyMarker.RED);
        assessSafety(red, 19, SafetyMarker.RED);
        assessSafety(red, 20, SafetyMarker.YELLOW);
        assessSafety(red, 21, SafetyMarker.YELLOW);
        assessSafety(red, 22, SafetyMarker.GREEN);
        assessSafety(red, 23, SafetyMarker.GREEN);
    }

    private void assessSafety(ClassifiedRestaurantVO red, Integer hour, SafetyMarker expectedSafety) {
        assertEquals(expectedSafety, red.getClassifiersToday().stream()
            .filter(p -> p.getHour().equals(hour)).findFirst().get().getMarker());
    }
}
