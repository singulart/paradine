package ua.com.paradine.core.business;

import java.time.LocalDateTime;
import java.util.Set;

public interface RestaurantSafetyClassifier {

    Set<SafetyVO> classifySafety(LocalDateTime at, Set<RestaurantVO> restaurants);

}
