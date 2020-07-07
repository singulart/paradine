package ua.com.paradine.core.rest;

import static ua.com.paradine.core.Errors.BAD_GEOLOCATION_PARAMS;
import static ua.com.paradine.core.Errors.NOT_FOUND;

import java.util.UUID;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;
import ua.com.paradine.core.business.SubmitVisitIntentFlow;
import ua.com.paradine.core.business.ViewClassifiedRestaurantsFlow;
import ua.com.paradine.core.business.ViewRestaurantsListCriteria;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.outcomes.SubmitVisitIntentOutcome;
import ua.com.paradine.security.SecurityUtils;
import ua.com.paradine.web.api.RestaurantsApiDelegate;
import ua.com.paradine.web.api.model.CreateIntendedVisitRequest;
import ua.com.paradine.web.api.model.RestaurantsGetResponse;
import ua.com.paradine.web.api.model.UuidResponse;

@Component
public class ParadineRestLayer implements RestaurantsApiDelegate {

    @Value("${paradine.api.version:2.0}")
    private String API_VERSION;

    private final ViewClassifiedRestaurantsFlow viewClassifiedRestaurantsFlow;
    private final SubmitVisitIntentFlow submitVisitIntentFlow;
    private final RestLayerMapper restMapper = Mappers.getMapper(RestLayerMapper.class);

    @Autowired
    public ParadineRestLayer(ViewClassifiedRestaurantsFlow viewClassifiedRestaurantsFlow,
        SubmitVisitIntentFlow submitVisitIntentFlow) {
        this.viewClassifiedRestaurantsFlow = viewClassifiedRestaurantsFlow;
        this.submitVisitIntentFlow = submitVisitIntentFlow;
    }

    @Override
    public ResponseEntity<RestaurantsGetResponse> getRestaurants(Integer page, String q, Float geolat, Float geolng) {
        if (geolat != null && geolng == null) {
            throw Problem.valueOf(Status.BAD_REQUEST, BAD_GEOLOCATION_PARAMS);
        }
        if (geolat == null && geolng != null) {
            throw Problem.valueOf(Status.BAD_REQUEST, BAD_GEOLOCATION_PARAMS);
        }

        ViewRestaurantsListCriteria searchCriteria = ViewRestaurantsListCriteria.Builder.init()
            .withCitySlug(null) //TODO add this filter
            .withLat(geolat)
            .withLng(geolng)
            .withQuery(q)
            .withPage(page).build();
        Page<ClassifiedRestaurantVO> classifiedRestaurants = viewClassifiedRestaurantsFlow.fetchClassifiedRestaurants(searchCriteria);

        return ResponseEntity.ok()
            .header("X-Total-Count", classifiedRestaurants.getTotalElements() + "")
            .header("X-Total-Pages", classifiedRestaurants.getTotalPages() + "")
            .body(new RestaurantsGetResponse()
            .version(API_VERSION)
            .restaurants(restMapper.map(classifiedRestaurants.getContent())));
    }

    @Override
    public ResponseEntity<UuidResponse> createIntendedVisit(CreateIntendedVisitRequest createIntendedVisitRequest) {

        SubmitVisitIntentOutcome outcome = submitVisitIntentFlow.submitVisitIntent(
            restMapper.map(createIntendedVisitRequest,
                                SecurityUtils.getCurrentUserLogin().orElseThrow(
                                    () -> Problem.valueOf(Status.NOT_FOUND, NOT_FOUND)
                                )
            )
        );

        if (outcome.getError() != null) {
            throw outcome.getError();
        } else {
            return ResponseEntity.ok().body(
                new UuidResponse()
                    .version(API_VERSION)
                    .id(outcome.getUuid())
            );
        }
    }
}
