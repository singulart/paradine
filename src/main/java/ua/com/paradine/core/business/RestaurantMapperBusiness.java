package ua.com.paradine.core.business;

import org.mapstruct.Mapper;
import ua.com.paradine.domain.Restaurant;

@Mapper(componentModel = "spring", uses = {})
public interface RestaurantMapperBusiness {

    RestaurantVO dbEntityToValueObject(Restaurant restaurant);
}
