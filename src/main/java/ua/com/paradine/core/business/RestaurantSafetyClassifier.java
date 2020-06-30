package ua.com.paradine.core.business;

public interface RestaurantSafetyClassifier {

    /**
     * Enriches a restaurant with the RED/YELLOW/GREEN/Closed 'safety' classifiers. <br/>
     * A restaurant will receive 24 hourly classifiers for two days: today and tomorrow. <br/>
     * @param restaurant restaurant to classify as RED/YELLOW/GREEN/Closed <br/>
     * @return restaurant classified  <br/>
     */
    ClassifiedRestaurantVO classifySafety(RestaurantVO restaurant);

}
