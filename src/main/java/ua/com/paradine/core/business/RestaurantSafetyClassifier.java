package ua.com.paradine.core.business;

import java.time.LocalDateTime;
import java.util.Set;

public interface RestaurantSafetyClassifier {

    void classifySafety(LocalDateTime at, Set<RestaurantVO> restaurants);

}
