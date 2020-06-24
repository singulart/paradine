package ua.com.paradine.service.mapper;


import ua.com.paradine.domain.*;
import ua.com.paradine.service.dto.PopularTimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PopularTime} and its DTO {@link PopularTimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface PopularTimeMapper extends EntityMapper<PopularTimeDTO, PopularTime> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    PopularTimeDTO toDto(PopularTime popularTime);

    @Mapping(source = "restaurantId", target = "restaurant")
    PopularTime toEntity(PopularTimeDTO popularTimeDTO);

    default PopularTime fromId(Long id) {
        if (id == null) {
            return null;
        }
        PopularTime popularTime = new PopularTime();
        popularTime.setId(id);
        return popularTime;
    }
}
