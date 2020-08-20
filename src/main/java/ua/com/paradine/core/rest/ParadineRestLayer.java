package ua.com.paradine.core.rest;

import static java.util.stream.Collectors.toList;
import static ua.com.paradine.core.Errors.BAD_GEOLOCATION_PARAMS;
import static ua.com.paradine.core.Errors.NOT_FOUND;

import io.github.jhipster.web.util.PaginationUtil;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import ua.com.paradine.core.business.CancelIntendedVisitFlow;
import ua.com.paradine.core.business.SubmitVisitIntentFlow;
import ua.com.paradine.core.business.ViewClassifiedRestaurantsFlow;
import ua.com.paradine.core.business.ViewIntendedVisitsFlow;
import ua.com.paradine.core.business.ViewRestaurantsListCriteria;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.IntendedVisitVO;
import ua.com.paradine.core.business.vo.commands.CancelIntendedVisitCommand;
import ua.com.paradine.core.business.vo.outcomes.CancelIntendedVisitOutcome;
import ua.com.paradine.core.business.vo.outcomes.SubmitVisitIntentOutcome;
import ua.com.paradine.security.SecurityUtils;
import ua.com.paradine.web.api.RestaurantsApiDelegate;
import ua.com.paradine.web.api.model.CreateIntendedVisitRequest;
import ua.com.paradine.web.api.model.IntendedVisitsGetResponse;
import ua.com.paradine.web.api.model.RestaurantsGetResponse;
import ua.com.paradine.web.api.model.UuidResponse;

@Component
public class ParadineRestLayer implements RestaurantsApiDelegate {

    @Value("${paradine.api.version:2.0}")
    private String API_VERSION;

    private final ViewClassifiedRestaurantsFlow viewClassifiedRestaurantsFlow;
    private final SubmitVisitIntentFlow submitVisitIntentFlow;
    private final CancelIntendedVisitFlow cancelIntendedVisitFlow;
    private final ViewIntendedVisitsFlow viewIntendedVisitsFlow;

    private final RestLayerMapper restMapper = Mappers.getMapper(RestLayerMapper.class);

    @Autowired
    public ParadineRestLayer(ViewClassifiedRestaurantsFlow viewClassifiedRestaurantsFlow,
        SubmitVisitIntentFlow submitVisitIntentFlow,
        CancelIntendedVisitFlow cancelIntendedVisitFlow,
        ViewIntendedVisitsFlow viewIntendedVisitsFlow) {
        this.viewClassifiedRestaurantsFlow = viewClassifiedRestaurantsFlow;
        this.submitVisitIntentFlow = submitVisitIntentFlow;
        this.cancelIntendedVisitFlow = cancelIntendedVisitFlow;
        this.viewIntendedVisitsFlow = viewIntendedVisitsFlow;
    }

    @Override
    public ResponseEntity<RestaurantsGetResponse> getRestaurants(Integer page, String city, String q, Float geolat, Float geolng) {
        if (geolat != null && geolng == null) {
            throw Problem.valueOf(Status.BAD_REQUEST, BAD_GEOLOCATION_PARAMS);
        }
        if (geolat == null && geolng != null) {
            throw Problem.valueOf(Status.BAD_REQUEST, BAD_GEOLOCATION_PARAMS);
        }

        ViewRestaurantsListCriteria searchCriteria = ViewRestaurantsListCriteria.Builder.init()
            .withCitySlug(city)
            .withLat(geolat)
            .withLng(geolng)
            .withQuery(q)
            .withPage(page).build();
        Page<ClassifiedRestaurantVO> classifiedRestaurants = viewClassifiedRestaurantsFlow.fetchClassifiedRestaurants(searchCriteria);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), classifiedRestaurants);

        return ResponseEntity.ok()
            .headers(headers)
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

    @Override
    public ResponseEntity<Void> cancelIntendedVisit(String visitId) {

        CancelIntendedVisitOutcome outcome =
            cancelIntendedVisitFlow.cancelVisit(new CancelIntendedVisitCommand(
                SecurityUtils.getCurrentUserLogin().orElseThrow(
                    () -> Problem.valueOf(Status.NOT_FOUND, NOT_FOUND)
                ), visitId
            ));

        if(outcome.getCancelled()) {
            return ResponseEntity.ok().build();
        } else {
            throw outcome.getError();
        }
    }

    @Override
    public ResponseEntity<IntendedVisitsGetResponse> getMyIntendedVisits() {
        List<IntendedVisitVO> visits =
            viewIntendedVisitsFlow.viewMyIntendedVisits(SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> Problem.valueOf(Status.NOT_FOUND, NOT_FOUND)
            ));
        return ResponseEntity.ok()
            .body(new IntendedVisitsGetResponse()
                .version(API_VERSION)
                .todayVisits(
                    visits.stream()
                        .filter(iv -> iv.getKindOfDay().equals("TODAY"))
                        .map(restMapper::mapVisit)
                        .collect(toList())
                )
                .tomorrowVisits(
                    visits.stream()
                        .filter(iv -> iv.getKindOfDay().equals("TOMORROW"))
                        .map(restMapper::mapVisit)
                        .collect(toList())
                )
            );
    }
}
