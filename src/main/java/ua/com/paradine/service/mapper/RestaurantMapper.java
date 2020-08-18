package ua.com.paradine.service.mapper;


import ua.com.paradine.domain.*;
import ua.com.paradine.service.dto.RestaurantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    RestaurantDTO toDto(Restaurant restaurant);

    @Mapping(source = "cityId", target = "city")
    Restaurant toEntity(RestaurantDTO restaurantDTO);

    default Restaurant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }
}
