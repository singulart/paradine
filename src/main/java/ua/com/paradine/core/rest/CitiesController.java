package ua.com.paradine.core.rest;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ua.com.paradine.service.CityService;
import ua.com.paradine.web.api.CitiesApiDelegate;
import ua.com.paradine.web.api.model.CitiesGetResponse;

@Component
public class CitiesController implements CitiesApiDelegate {

    @Value("${paradine.api.version:2.0}")
    private String API_VERSION;

    private final CityService cityService;
    private final RestLayerMapper restMapper = Mappers.getMapper(RestLayerMapper.class);

    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public ResponseEntity<CitiesGetResponse> getCities() {
        return ResponseEntity.ok()
            .body(new CitiesGetResponse()
                .version(API_VERSION)
                .cities(restMapper.mapCities(cityService.findAll())));

    }

}
