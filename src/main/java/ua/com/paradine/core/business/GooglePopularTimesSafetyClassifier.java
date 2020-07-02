package ua.com.paradine.core.business;

import static java.time.temporal.ChronoUnit.DAYS;
import static ua.com.paradine.core.util.DaysOfWeek.DOW;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.HourlyClassifier;
import ua.com.paradine.core.business.vo.PopularTimeVO;
import ua.com.paradine.core.business.vo.RestaurantVO;

/**
 * Paradine 'safety' classification is based on user activity in the app. <br/>
 * However, initially the user activity will be insufficient to classify venues as RED/YELLOW/GREEN correctly.<br/>
 * Therefore, this class is used to build those indicators based purely on Popular Times data from Google Places.<br/>
 */
@Component
public class GooglePopularTimesSafetyClassifier implements RestaurantSafetyClassifier {

    public static final Integer GREEN = 25;
    public static final Integer YELLOW = 75;

    private RestaurantMapperBusiness mapper = Mappers.getMapper(RestaurantMapperBusiness.class);

    @Override
    public ClassifiedRestaurantVO classifySafety(RestaurantVO restaurant) {
        List<HourlyClassifier> today = classify(restaurant, getToday());
        List<HourlyClassifier> tomorrow = classify(restaurant, getTomorrow());
        return mapper.convertToSafety(restaurant, today, tomorrow);
    }

    private List<HourlyClassifier> classify(RestaurantVO restaurant, String dayCode) {
        Optional<PopularTimeVO> popularTimesToday = restaurant.getPopularTimes().stream()
            .filter(pop -> pop.getDayOfWeek().equals(dayCode))
            .findFirst();
        List<HourlyClassifier> classifiers;
        if (popularTimesToday.isPresent()) {
            PopularTimeVO vo = popularTimesToday.get();
            classifiers = Arrays.asList(
                classifyOccupancy(0, vo.getOcc01()),
                classifyOccupancy(1, vo.getOcc02()),
                classifyOccupancy(2, vo.getOcc03()),
                classifyOccupancy(3, vo.getOcc04()),
                classifyOccupancy(4, vo.getOcc05()),
                classifyOccupancy(5, vo.getOcc06()),
                classifyOccupancy(6, vo.getOcc07()),
                classifyOccupancy(7, vo.getOcc08()),
                classifyOccupancy(8, vo.getOcc09()),
                classifyOccupancy(9, vo.getOcc10()),
                classifyOccupancy(10, vo.getOcc11()),
                classifyOccupancy(11, vo.getOcc12()),
                classifyOccupancy(12, vo.getOcc13()),
                classifyOccupancy(13, vo.getOcc14()),
                classifyOccupancy(14, vo.getOcc15()),
                classifyOccupancy(15, vo.getOcc16()),
                classifyOccupancy(16, vo.getOcc17()),
                classifyOccupancy(17, vo.getOcc18()),
                classifyOccupancy(18, vo.getOcc19()),
                classifyOccupancy(19, vo.getOcc20()),
                classifyOccupancy(20, vo.getOcc21()),
                classifyOccupancy(21, vo.getOcc22()),
                classifyOccupancy(22, vo.getOcc23()),
                classifyOccupancy(23, vo.getOcc24())
            );
        } else {
            classifiers = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                classifiers.add(new HourlyClassifier(i, SafetyMarker.CLOSED));
            }
        }
        return classifiers;
    }

    HourlyClassifier classifyOccupancy(Integer hour, Integer occupancyPercentage) {
        if(occupancyPercentage == null) {
            occupancyPercentage = 0;
        }
        if(occupancyPercentage >= 0 && occupancyPercentage < GREEN) {
            return new HourlyClassifier(hour, SafetyMarker.GREEN);
        } else if (occupancyPercentage >= GREEN && occupancyPercentage < YELLOW) {
            return new HourlyClassifier(hour, SafetyMarker.YELLOW);
        } else if (occupancyPercentage >= YELLOW) {
            return new HourlyClassifier(hour, SafetyMarker.RED);
        } else {
            return new HourlyClassifier(hour, SafetyMarker.CLOSED);
        }
    }

    public String getToday() {
        LocalDate date = LocalDate.now();
        return DOW.get(date.getDayOfWeek());
    }

    public String getTomorrow() {
        LocalDate date = LocalDate.now().plus(1, DAYS);
        return DOW.get(date.getDayOfWeek());
    }
}
