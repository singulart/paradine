package ua.com.paradine.core.rest;

import java.util.Collections;
import java.util.Set;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import ua.com.paradine.core.business.ViewClassifiedRestaurantsFlow;
import ua.com.paradine.core.business.ViewRestaurantsListCriteria;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.web.api.RestaurantsApiDelegate;
import ua.com.paradine.web.api.model.RestaurantsGetResponse;

@Component
public class ParadineRestLayer implements RestaurantsApiDelegate {

    @Value("${paradine.api.version}:2.0.0")
    private String API_VERSION;

    private ViewClassifiedRestaurantsFlow viewListFlow;
    private RestLayerMapper restMapper = Mappers.getMapper(RestLayerMapper.class);

    @Autowired
    public ParadineRestLayer(ViewClassifiedRestaurantsFlow viewListFlow) {
        this.viewListFlow = viewListFlow;
    }

    @Override
    public ResponseEntity<RestaurantsGetResponse> getRestaurants(Integer page, String q, Float geolat, Float geolng) {
        if (geolat != null && geolng == null) {
            throw Problem.valueOf(Status.BAD_REQUEST, "Missing one of the geolocation parameters");
        }
        if (geolat == null && geolng != null) {
            throw Problem.valueOf(Status.BAD_REQUEST, "Missing one of the geolocation parameters");
        }

        ViewRestaurantsListCriteria searchCriteria = ViewRestaurantsListCriteria.Builder.init()
            .withCitySlug(null) //TODO add this filter
            .withLat(geolat)
            .withLng(geolng)
            .withQuery(q)
            .withPage(page).build();
        Set<ClassifiedRestaurantVO> classifiedRestaurants = viewListFlow.fetchClassifiedRestaurants(searchCriteria);

        return ResponseEntity.ok(new RestaurantsGetResponse()
            .version(API_VERSION)
            .restaurants(restMapper.map(classifiedRestaurants)));
    }
}
