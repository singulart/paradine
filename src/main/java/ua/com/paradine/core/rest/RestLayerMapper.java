package ua.com.paradine.core.rest;

import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.HourlyClassifier;
import ua.com.paradine.core.business.vo.IntendedVisitVO;
import ua.com.paradine.core.business.vo.RestaurantVO;
import ua.com.paradine.core.business.vo.commands.SubmitVisitIntentCommand;
import ua.com.paradine.service.dto.CityDTO;
import ua.com.paradine.web.api.model.City;
import ua.com.paradine.web.api.model.CreateIntendedVisitRequest;
import ua.com.paradine.web.api.model.HourlySafety;
import ua.com.paradine.web.api.model.Restaurant;
import ua.com.paradine.web.api.model.RestaurantBasic;
import ua.com.paradine.web.api.model.StoredIntendedVisit;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestLayerMapper {

    List<Restaurant> map(List<ClassifiedRestaurantVO> restaurants);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "image", source = "photoUrl")
    @Mapping(target = "address", expression = "java("
        + "java.util.Map.of("
        + "\"en_US\", restaurant.getAddressEn(),"
        + "\"ru_RU\", restaurant.getAddressRu(),"
        + "\"uk_UA\", restaurant.getAddressUa()"
        + "))")
    @Mapping(target = "safetyToday", source = "classifiersToday")
    @Mapping(target = "safetyTomorrow", source = "classifiersTomorrow")
    Restaurant map(ClassifiedRestaurantVO restaurant);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "image", source = "photoUrl")
    @Mapping(target = "address", expression = "java("
        + "java.util.Map.of("
        + "\"en_US\", restaurant.getAddressEn(),"
        + "\"ru_RU\", restaurant.getAddressRu(),"
        + "\"uk_UA\", restaurant.getAddressUa()"
        + "))")
    RestaurantBasic map(RestaurantVO restaurant);

    @Mapping(target = "h", source = "hour")
    @Mapping(target = "value", source = "marker.indicator")
    HourlySafety map(HourlyClassifier classifier);


    @Mapping(target = "when", source = "request.visit.when")
    @Mapping(target = "restaurantId", source = "request.visit.restaurantId")
    SubmitVisitIntentCommand map(CreateIntendedVisitRequest request, String user);

    @Mapping(target = "visitDate", source = "visitTime")
    StoredIntendedVisit mapVisit(IntendedVisitVO visit);

    default String map(UUID uuid) {
        return uuid.toString();
    }

    default UUID map(String uuid) {
        return UUID.fromString(uuid);
    }

    List<City> mapCities(List<CityDTO> cities);
}
