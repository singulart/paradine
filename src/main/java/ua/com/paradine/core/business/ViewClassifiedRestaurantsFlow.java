package ua.com.paradine.core.business;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.dao.ExtendedPopularTimeRepository;
import ua.com.paradine.core.dao.ExtendedRestaurantRepository;
import ua.com.paradine.domain.PopularTime;
import ua.com.paradine.domain.Restaurant;

@Service
@Transactional
public class ViewClassifiedRestaurantsFlow {

    private final ExtendedRestaurantRepository restaurantDao;
    private final ExtendedPopularTimeRepository popularTimeRepository;
    private final RestaurantSafetyClassifier classifier;
    private final RestaurantMapperBusiness mapper = Mappers.getMapper(RestaurantMapperBusiness.class);
    
    @Value("${paradine.page.size:10}")
    private Integer pageSize;

    @Autowired
    public ViewClassifiedRestaurantsFlow(ExtendedRestaurantRepository restaurantDao,
        ExtendedPopularTimeRepository popularTimeRepository,
        RestaurantSafetyClassifier classifier) {
        this.restaurantDao = restaurantDao;
        this.popularTimeRepository = popularTimeRepository;
        this.classifier = classifier;
    }


    public Page<ClassifiedRestaurantVO> fetchClassifiedRestaurants(
        ViewRestaurantsListCriteria searchCriteria) {
        Page<Restaurant> jpaRestaurants = restaurantDao.searchByCriteria(
            PageRequest.of(ofNullable(searchCriteria.getPage()).orElse(0), pageSize));
        List<Long> ids = jpaRestaurants.stream().map(Restaurant::getId).collect(toList());
        if (!ids.isEmpty()) {
            List<PopularTime> jpaTimes = popularTimeRepository.fetchByRestaurantIdIn(ids);
            bindPopularTimes(jpaRestaurants, jpaTimes);
        }
        return new PageImpl<>(
            jpaRestaurants.stream()
                .map(mapper::dbEntityToValueObject)
                .map(classifier::classifySafety)
                .collect(toList()), jpaRestaurants.getPageable(), jpaRestaurants.getTotalElements()
        );
    }

    private void bindPopularTimes(Page<Restaurant> restaurants, List<PopularTime> popularTimes) {
        restaurants.forEach(r -> {
           List<PopularTime> times = popularTimes.stream()
               .filter(pt -> pt.getRestaurant().getId().equals(r.getId())).collect(toList());
           r.setPopularTimes(times);
        });
    }
}
