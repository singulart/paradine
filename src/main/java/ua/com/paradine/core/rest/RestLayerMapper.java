package ua.com.paradine.core.rest;

import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.com.paradine.core.business.SafetyMarker;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.HourlyClassifier;
import ua.com.paradine.web.api.model.HourlySafety;
import ua.com.paradine.web.api.model.Restaurant;
import ua.com.paradine.web.api.model.RestaurantsGetResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestLayerMapper {

//    @Mapping(target = "version", source = API_VERSION)
//    @Mapping(target = "restaurants", source = "restaurants")
//    RestaurantsGetResponse mapViewRestaurantsListResponse(Set<ClassifiedRestaurantVO> restaurants);

    List<Restaurant> map(List<ClassifiedRestaurantVO> restaurants);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "image", source = "photoUrl")
    @Mapping(target = "address", ignore = true) // TODO FIXME
    @Mapping(target = "safetyToday", source = "classifiersToday")
    @Mapping(target = "safetyTomorrow", source = "classifiersTomorrow")
    Restaurant map(ClassifiedRestaurantVO restaurants);

    @Mapping(target = "h", source = "hour")
    @Mapping(target = "value", source = "marker.indicator")
    HourlySafety map(HourlyClassifier classifier);

}
