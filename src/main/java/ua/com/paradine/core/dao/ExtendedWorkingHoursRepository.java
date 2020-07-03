package ua.com.paradine.core.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.paradine.domain.WorkingHours;
import ua.com.paradine.repository.WorkingHoursRepository;

public interface ExtendedWorkingHoursRepository extends WorkingHoursRepository {

    @Query(value = "FROM WorkingHours pt WHERE pt.restaurant.id in (:restaurantIds)")
    List<WorkingHours> fetchByRestaurantIdIn(@Param("restaurantIds") List<Long> restaurantIds);

}
