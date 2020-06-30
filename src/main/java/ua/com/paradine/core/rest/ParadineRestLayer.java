package ua.com.paradine.core.rest;

import java.util.Collections;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import ua.com.paradine.web.api.RestaurantsApiDelegate;
import ua.com.paradine.web.api.model.RestaurantsGetResponse;

@Component
public class ParadineRestLayer implements RestaurantsApiDelegate {

    @Override
    public ResponseEntity<RestaurantsGetResponse> getRestaurants(Integer page, String q, Float geolat, Float geolng) {
        if (geolat != null && geolng == null) {
            throw Problem.valueOf(Status.BAD_REQUEST, "Missing one of the geolocation parameters");
        }
        if (geolat == null && geolng != null) {
            throw Problem.valueOf(Status.BAD_REQUEST, "Missing one of the geolocation parameters");
        }
        return ResponseEntity.ok(new RestaurantsGetResponse().restaurants(Collections.emptyList()));
    }
}
