package ua.com.paradine.core.business;

import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.dao.ExtendedRestaurantRepository;

@Service
@Transactional
public class ViewClassifiedRestaurantsFlow {

    private final ExtendedRestaurantRepository restaurantDao;
    private final RestaurantSafetyClassifier classifier;

    @Autowired
    public ViewClassifiedRestaurantsFlow(ExtendedRestaurantRepository restaurantDao,
        RestaurantSafetyClassifier classifier) {
        this.restaurantDao = restaurantDao;
        this.classifier = classifier;
    }


    public Set<ClassifiedRestaurantVO> fetchClassifiedRestaurants(
        ViewRestaurantsListCriteria searchCriteria) {
        return Collections.emptySet();
    }
}
