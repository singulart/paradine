package ua.com.paradine.core.dao;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.paradine.core.business.ViewRestaurantsListCriteria;
import ua.com.paradine.domain.PopularTime;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.domain.WorkingHours;

@Service
public class JpaRestaurantDao implements RestaurantDao {

    private final ExtendedRestaurantRepository restaurantRepository;
    private final ExtendedPopularTimeRepository popularTimeRepository;
    private final ExtendedWorkingHoursRepository workingHoursRepository;

    @Value("${paradine.page.size:10}")
    private Integer pageSize;

    @Autowired
    public JpaRestaurantDao(ExtendedRestaurantRepository restaurantDao,
        ExtendedPopularTimeRepository popularTimeRepository,
        ExtendedWorkingHoursRepository workingHoursRepository) {
        this.restaurantRepository = restaurantDao;
        this.popularTimeRepository = popularTimeRepository;
        this.workingHoursRepository = workingHoursRepository;
    }

    @Override
    public Page<Restaurant> loadRestaurants(ViewRestaurantsListCriteria searchCriteria) {
        Page<Restaurant> jpaRestaurants = restaurantRepository.searchByCriteria(
            PageRequest.of(ofNullable(searchCriteria.getPage()).orElse(0), pageSize));
        List<Long> ids = jpaRestaurants.stream().map(Restaurant::getId).collect(toList());
        if (!ids.isEmpty()) {
            List<PopularTime> jpaTimes = popularTimeRepository.fetchByRestaurantIdIn(ids);
            bindPopularTimes(jpaRestaurants, jpaTimes);
            List<WorkingHours> jpaWorkingHours = workingHoursRepository.fetchByRestaurantIdIn(ids);
            bindWorkingHours(jpaRestaurants, jpaWorkingHours);
        }
        return jpaRestaurants;
    }

    private void bindPopularTimes(Page<Restaurant> restaurants, List<PopularTime> popularTimes) {
        restaurants.forEach(r -> {
            List<PopularTime> times = popularTimes.stream()
                .filter(pt -> pt.getRestaurant().getId().equals(r.getId())).collect(toList());
            r.setPopularTimes(times);
        });
    }

    private void bindWorkingHours(Page<Restaurant> restaurants, List<WorkingHours> workingHours) {
        restaurants.forEach(r -> {
            List<WorkingHours> times = workingHours.stream()
                .filter(pt -> pt.getRestaurant().getId().equals(r.getId())).collect(toList());
            r.setWorkingHours(times);
        });
    }

}
