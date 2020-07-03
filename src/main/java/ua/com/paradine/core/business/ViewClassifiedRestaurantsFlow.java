package ua.com.paradine.core.business;

import static java.util.stream.Collectors.toList;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.dao.RestaurantDao;
import ua.com.paradine.domain.Restaurant;

@Service
@Transactional
public class ViewClassifiedRestaurantsFlow {

    private final RestaurantDao dao;
    private final RestaurantSafetyClassifier classifier;
    private final RestaurantMapperBusiness mapper = Mappers.getMapper(RestaurantMapperBusiness.class);

    @Autowired
    public ViewClassifiedRestaurantsFlow(RestaurantDao restaurantDao, RestaurantSafetyClassifier classifier) {
        this.dao = restaurantDao;
        this.classifier = classifier;
    }


    public Page<ClassifiedRestaurantVO> fetchClassifiedRestaurants(ViewRestaurantsListCriteria searchCriteria) {
        Page<Restaurant> jpaRestaurants = dao.loadRestaurants(searchCriteria);
        return new PageImpl<>(
            jpaRestaurants.stream()
                .map(mapper::dbEntityToValueObject)
                .map(classifier::classifySafety)
                .collect(toList()), jpaRestaurants.getPageable(), jpaRestaurants.getTotalElements()
        );
    }
}
