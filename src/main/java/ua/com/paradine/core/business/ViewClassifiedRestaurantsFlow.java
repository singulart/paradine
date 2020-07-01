package ua.com.paradine.core.business;

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
import ua.com.paradine.core.dao.ExtendedRestaurantRepository;
import ua.com.paradine.domain.Restaurant;

@Service
@Transactional
public class ViewClassifiedRestaurantsFlow {

    private final ExtendedRestaurantRepository restaurantDao;
    private final RestaurantSafetyClassifier classifier;
    private final RestaurantMapperBusiness mapper = Mappers.getMapper(RestaurantMapperBusiness.class);
    
    @Value("${paradine.page.size:10}")
    private Integer pageSize;

    @Autowired
    public ViewClassifiedRestaurantsFlow(ExtendedRestaurantRepository restaurantDao,
        RestaurantSafetyClassifier classifier) {
        this.restaurantDao = restaurantDao;
        this.classifier = classifier;
    }


    public Page<ClassifiedRestaurantVO> fetchClassifiedRestaurants(
        ViewRestaurantsListCriteria searchCriteria) {
        Page<Restaurant> jpaRestaurants = restaurantDao.findAll(PageRequest.of(searchCriteria.getPage(), pageSize));
        return new PageImpl<>(
            jpaRestaurants.stream()
                .map(mapper::dbEntityToValueObject)
                .map(classifier::classifySafety)
                .collect(Collectors.toList()), jpaRestaurants.getPageable(), jpaRestaurants.getTotalElements()
        );
    }
}
