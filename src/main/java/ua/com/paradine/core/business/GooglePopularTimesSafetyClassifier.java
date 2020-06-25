package ua.com.paradine.core.business;

import java.time.LocalDateTime;
import java.util.Set;
import org.mapstruct.factory.Mappers;

/**
 * Paradine safety indicators are based on user activity in the app. <br/>
 * However, initially the user activity will be insufficient to build RED/YELLOW/GREEN indicators correctly.<br/>
 * Therefore, this class is used to build those indicators based purely on Popular Times data from Google Places.<br/>
 */
public class GooglePopularTimesSafetyClassifier implements RestaurantSafetyClassifier {

    private RestaurantMapperBusiness mapper = Mappers.getMapper(RestaurantMapperBusiness.class);

    @Override
    public Set<SafetyVO> classifySafety(LocalDateTime at, Set<RestaurantVO> restaurants) {
        return mapper.convertToSafety(restaurants, SafetyMarker.RED);
    }
}
