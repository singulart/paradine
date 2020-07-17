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
import ua.com.paradine.core.business.vo.WorkingHoursVO;

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
        PopularTimeVO popularTimesAtDayCode = restaurant.getPopularTimes().stream()
            .filter(pop -> pop.getDayOfWeek().equals(dayCode))
            .findFirst()
            .orElse(new PopularTimeVO()); // should fallback to GREEN
        WorkingHoursVO workHoursAtDayCode = restaurant.getWorkingHours().stream()
            .filter(wh -> wh.getDayOfWeek().equals(dayCode))
            .findFirst().orElse(new WorkingHoursVO(dayCode, 0, 24));
        List<HourlyClassifier> classifiers = Arrays.asList(
                classifyOccupancy(0, popularTimesAtDayCode.getOcc01(), workHoursAtDayCode),
                classifyOccupancy(1, popularTimesAtDayCode.getOcc02(), workHoursAtDayCode),
                classifyOccupancy(2, popularTimesAtDayCode.getOcc03(), workHoursAtDayCode),
                classifyOccupancy(3, popularTimesAtDayCode.getOcc04(), workHoursAtDayCode),
                classifyOccupancy(4, popularTimesAtDayCode.getOcc05(), workHoursAtDayCode),
                classifyOccupancy(5, popularTimesAtDayCode.getOcc06(), workHoursAtDayCode),
                classifyOccupancy(6, popularTimesAtDayCode.getOcc07(), workHoursAtDayCode),
                classifyOccupancy(7, popularTimesAtDayCode.getOcc08(), workHoursAtDayCode),
                classifyOccupancy(8, popularTimesAtDayCode.getOcc09(), workHoursAtDayCode),
                classifyOccupancy(9, popularTimesAtDayCode.getOcc10(), workHoursAtDayCode),
                classifyOccupancy(10, popularTimesAtDayCode.getOcc11(), workHoursAtDayCode),
                classifyOccupancy(11, popularTimesAtDayCode.getOcc12(), workHoursAtDayCode),
                classifyOccupancy(12, popularTimesAtDayCode.getOcc13(), workHoursAtDayCode),
                classifyOccupancy(13, popularTimesAtDayCode.getOcc14(), workHoursAtDayCode),
                classifyOccupancy(14, popularTimesAtDayCode.getOcc15(), workHoursAtDayCode),
                classifyOccupancy(15, popularTimesAtDayCode.getOcc16(), workHoursAtDayCode),
                classifyOccupancy(16, popularTimesAtDayCode.getOcc17(), workHoursAtDayCode),
                classifyOccupancy(17, popularTimesAtDayCode.getOcc18(), workHoursAtDayCode),
                classifyOccupancy(18, popularTimesAtDayCode.getOcc19(), workHoursAtDayCode),
                classifyOccupancy(19, popularTimesAtDayCode.getOcc20(), workHoursAtDayCode),
                classifyOccupancy(20, popularTimesAtDayCode.getOcc21(), workHoursAtDayCode),
                classifyOccupancy(21, popularTimesAtDayCode.getOcc22(), workHoursAtDayCode),
                classifyOccupancy(22, popularTimesAtDayCode.getOcc23(), workHoursAtDayCode),
                classifyOccupancy(23, popularTimesAtDayCode.getOcc24(), workHoursAtDayCode)
            );
        return classifiers;
    }

    HourlyClassifier classifyOccupancy(Integer hour, Integer occupancyPercentage, WorkingHoursVO workHoursAtDayCode) {
        if( !new WorkingHoursChecker().isDuringWorkingTime(hour, workHoursAtDayCode)) {
            return new HourlyClassifier(hour, SafetyMarker.CLOSED);
        }
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
