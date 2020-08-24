package ua.com.paradine.core.dao;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.data.domain.Page;
import ua.com.paradine.domain.PopularTime;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.domain.WorkingHours;

public abstract class RestaurantRelationsBuilder implements RestaurantDao {

    private final ExtendedPopularTimeRepository popularTimeRepository;
    private final ExtendedWorkingHoursRepository workingHoursRepository;

    public RestaurantRelationsBuilder(ExtendedPopularTimeRepository popularTimeRepository,
        ExtendedWorkingHoursRepository workingHoursRepository) {
        this.popularTimeRepository = popularTimeRepository;
        this.workingHoursRepository = workingHoursRepository;
    }

    protected void buildRestaurantRelations(List<Restaurant> restaurants) {
        List<Long> ids = restaurants.stream().map(Restaurant::getId).collect(toList());
        if (!ids.isEmpty()) {
            List<PopularTime> jpaTimes = popularTimeRepository.fetchByRestaurantIdIn(ids);
            bindPopularTimes(restaurants, jpaTimes);
            List<WorkingHours> jpaWorkingHours = workingHoursRepository.fetchByRestaurantIdIn(ids);
            bindWorkingHours(restaurants, jpaWorkingHours);
        }
    }

    private void bindPopularTimes(List<Restaurant> restaurants, List<PopularTime> popularTimes) {
        restaurants.forEach(r -> {
            List<PopularTime> times = popularTimes.stream()
                .filter(pt -> pt.getRestaurant().getId().equals(r.getId())).collect(toList());
            r.setPopularTimes(times);
        });
    }

    private void bindWorkingHours(List<Restaurant> restaurants, List<WorkingHours> workingHours) {
        restaurants.forEach(r -> {
            List<WorkingHours> times = workingHours.stream()
                .filter(wh -> wh.getRestaurant().getId().equals(r.getId())).collect(toList());
            r.setWorkingHours(times);
        });
    }

}
