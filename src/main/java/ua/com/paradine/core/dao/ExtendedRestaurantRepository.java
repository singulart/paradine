package ua.com.paradine.core.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.repository.RestaurantRepository;

@Repository
public interface ExtendedRestaurantRepository extends RestaurantRepository {

    @Query(value =
        "FROM Restaurant r"
    )
    Page<Restaurant> searchByCriteria(Pageable p);
}
