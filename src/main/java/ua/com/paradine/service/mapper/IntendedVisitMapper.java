package ua.com.paradine.service.mapper;


import ua.com.paradine.domain.*;
import ua.com.paradine.service.dto.IntendedVisitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntendedVisit} and its DTO {@link IntendedVisitDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RestaurantMapper.class})
public interface IntendedVisitMapper extends EntityMapper<IntendedVisitDTO, IntendedVisit> {

    @Mapping(source = "visitingUser.id", target = "visitingUserId")
    @Mapping(source = "visitingUser.login", target = "visitingUserLogin")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    IntendedVisitDTO toDto(IntendedVisit intendedVisit);

    @Mapping(source = "visitingUserId", target = "visitingUser")
    @Mapping(source = "restaurantId", target = "restaurant")
    IntendedVisit toEntity(IntendedVisitDTO intendedVisitDTO);

    default IntendedVisit fromId(Long id) {
        if (id == null) {
            return null;
        }
        IntendedVisit intendedVisit = new IntendedVisit();
        intendedVisit.setId(id);
        return intendedVisit;
    }
}
