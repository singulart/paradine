package ua.com.paradine.core.business;

import java.time.ZonedDateTime;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.RestaurantVO;

public interface RestaurantSafetyClassifier {

    /**
     * Enriches a restaurant with the RED/YELLOW/GREEN/Closed 'safety' classifiers. <br/>
     * A restaurant will receive 24 hourly classifiers for two days: today and tomorrow. <br/>
     * @param restaurant restaurant to classify as RED/YELLOW/GREEN/Closed <br/>
     * @return restaurant classified  <br/>
     */
    ClassifiedRestaurantVO classifySafety(RestaurantVO restaurant);

    SafetyMarker classifySafety(RestaurantVO restaurant, ZonedDateTime at);

}
