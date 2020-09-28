package ua.com.paradine.core.dao;

import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.repository.RestaurantRepository;

@Repository
public interface ExtendedRestaurantRepository extends RestaurantRepository {

    @Query(value =
        "FROM Restaurant r where r.city.id = (SELECT c.id from City c where c.slug = :slug)"
    )
    Page<Restaurant> searchByCriteria(@Param("slug") String city, Pageable p);

    @Query(value =
        "SELECT r.id FROM Restaurant r where r.uuid = :uuid"
    )
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Long lockRestaurantRowByUuid(@Param("uuid") String uuid);

    Restaurant findByUuid(@Param("uuid") String uuid);
}
