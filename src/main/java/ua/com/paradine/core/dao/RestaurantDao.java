package ua.com.paradine.core.dao;

import org.springframework.data.domain.Page;
import ua.com.paradine.core.business.ViewRestaurantsListCriteria;
import ua.com.paradine.domain.Restaurant;

/**
 * DAO which hides complexities of DB-level associations between Restaurant and its
 * WorkingHours / Popular Times / Planned Visits.
 *
 * For instance, the Business Layer ViewClassifiedRestaurantsFlow could not care less that in order to load the list of
 * Restaurants, 4 different repositories have to be used.
 */
public interface RestaurantDao {

    Page<Restaurant> loadRestaurants(ViewRestaurantsListCriteria searchCriteria);

}
