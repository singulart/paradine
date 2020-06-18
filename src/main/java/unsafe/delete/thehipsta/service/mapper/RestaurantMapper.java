package unsafe.delete.thehipsta.service.mapper;


import unsafe.delete.thehipsta.domain.*;
import unsafe.delete.thehipsta.service.dto.RestaurantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {



    default Restaurant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }
}
