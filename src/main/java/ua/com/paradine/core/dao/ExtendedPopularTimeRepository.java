package ua.com.paradine.core.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.paradine.domain.PopularTime;
import ua.com.paradine.repository.PopularTimeRepository;

public interface ExtendedPopularTimeRepository extends PopularTimeRepository {

    @Query(value = "FROM PopularTime pt WHERE pt.restaurant.id in (:restaurantIds)")
    List<PopularTime> fetchByRestaurantIdIn(@Param("restaurantIds") List<Long> restaurantIds);
}
