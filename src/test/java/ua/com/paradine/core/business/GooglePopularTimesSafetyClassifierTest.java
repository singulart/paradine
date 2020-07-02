package ua.com.paradine.core.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.PopularTimeVO;
import ua.com.paradine.core.business.vo.RestaurantVO;

@RunWith(MockitoJUnitRunner.class)
class GooglePopularTimesSafetyClassifierTest {

    private GooglePopularTimesSafetyClassifier classifier = spy(new GooglePopularTimesSafetyClassifier());

    @Test
    void classifySafetyNoPopularTimesData() {
        when(classifier.getToday()).thenReturn("We");
        RestaurantVO restaurant1 = new RestaurantVO();
        ClassifiedRestaurantVO classified = classifier.classifySafety(restaurant1);

        for (int i = 0; i < 24; i++) {
            assessSafetyToday(classified, i, SafetyMarker.CLOSED);
            assessSafetyTomorrow(classified, i, SafetyMarker.CLOSED);
        }
    }

    @Test
    void classifySafety() {
        when(classifier.getToday()).thenReturn("We");
        when(classifier.getTomorrow()).thenReturn("Th");

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

        PopularTimeVO pop2 = new PopularTimeVO();
        pop2.setDayOfWeek("Th");
        pop2.setOcc09(25);
        pop2.setOcc10(30);
        pop2.setOcc11(35);
        pop2.setOcc12(40);
        pop2.setOcc13(45);
        pop2.setOcc14(50);
        pop2.setOcc15(55);
        pop2.setOcc16(60);
        pop2.setOcc17(65);
        pop2.setOcc18(70);
        pop2.setOcc19(75);
        pop2.setOcc20(80);
        pop2.setOcc21(65);
        pop2.setOcc22(30);

        restaurant1.getPopularTimes().add(pop1);
        restaurant1.getPopularTimes().add(pop2);

        ClassifiedRestaurantVO classified = classifier.classifySafety(restaurant1);

        assessSafetyToday(classified, 0, SafetyMarker.GREEN);
        assessSafetyToday(classified, 1, SafetyMarker.GREEN);
        assessSafetyToday(classified, 2, SafetyMarker.GREEN);
        assessSafetyToday(classified, 3, SafetyMarker.GREEN);
        assessSafetyToday(classified, 4, SafetyMarker.GREEN);
        assessSafetyToday(classified, 5, SafetyMarker.GREEN);
        assessSafetyToday(classified, 6, SafetyMarker.GREEN);
        assessSafetyToday(classified, 7, SafetyMarker.GREEN);
        assessSafetyToday(classified, 8, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 9, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 10, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 11, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 12, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 13, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 14, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 15, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 16, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 17, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 18, SafetyMarker.RED);
        assessSafetyToday(classified, 19, SafetyMarker.RED);
        assessSafetyToday(classified, 20, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 21, SafetyMarker.YELLOW);
        assessSafetyToday(classified, 22, SafetyMarker.GREEN);
        assessSafetyToday(classified, 23, SafetyMarker.GREEN);

        assessSafetyTomorrow(classified, 0, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 1, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 2, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 3, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 4, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 5, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 6, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 7, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 8, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 9, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 10, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 11, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 12, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 13, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 14, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 15, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 16, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 17, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 18, SafetyMarker.RED);
        assessSafetyTomorrow(classified, 19, SafetyMarker.RED);
        assessSafetyTomorrow(classified, 20, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 21, SafetyMarker.YELLOW);
        assessSafetyTomorrow(classified, 22, SafetyMarker.GREEN);
        assessSafetyTomorrow(classified, 23, SafetyMarker.GREEN);
    }

    private void assessSafetyToday(ClassifiedRestaurantVO classified, Integer hour, SafetyMarker expectedSafety) {
        assertEquals(expectedSafety, classified.getClassifiersToday().stream()
            .filter(p -> p.getHour().equals(hour)).findFirst().get().getMarker());
    }

    private void assessSafetyTomorrow(ClassifiedRestaurantVO classified, Integer hour, SafetyMarker expectedSafety) {
        assertEquals(expectedSafety, classified.getClassifiersTomorrow().stream()
            .filter(p -> p.getHour().equals(hour)).findFirst().get().getMarker());
    }
}
