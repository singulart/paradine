package ua.com.paradine.core.dao;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.paradine.core.business.ViewRestaurantsListCriteria;
import ua.com.paradine.domain.Restaurant;

@Service
public class JpaRestaurantDao extends RestaurantRelationsBuilder {

    private final ExtendedRestaurantRepository restaurantRepository;

    @Value("${paradine.page.size:10}")
    private Integer pageSize;

    @Autowired
    public JpaRestaurantDao(ExtendedRestaurantRepository restaurantDao,
        ExtendedPopularTimeRepository popularTimeRepository,
        ExtendedWorkingHoursRepository workingHoursRepository) {
        super(popularTimeRepository, workingHoursRepository);
        this.restaurantRepository = restaurantDao;
    }

    @Override
    public Page<Restaurant> loadRestaurants(ViewRestaurantsListCriteria searchCriteria) {
        if(searchCriteria.getId() != null) {
            return new PageImpl<>(
                singletonList(restaurantRepository.findByUuid(searchCriteria.getId()))
            );
        }
        Page<Restaurant> jpaRestaurants = restaurantRepository.searchByCriteria(
            searchCriteria.getCitySlug(),
            PageRequest.of(ofNullable(searchCriteria.getPage()).orElse(0), pageSize));
        super.buildRestaurantRelations(jpaRestaurants.getContent());
        return jpaRestaurants;
    }

}
