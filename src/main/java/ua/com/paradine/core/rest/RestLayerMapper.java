package ua.com.paradine.core.rest;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.HourlyClassifier;
import ua.com.paradine.web.api.model.HourlySafety;
import ua.com.paradine.web.api.model.Restaurant;

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

    @Mapping(target = "h", source = "hour")
    @Mapping(target = "value", source = "marker.indicator")
    HourlySafety map(HourlyClassifier classifier);

}
