package ua.com.paradine.service.mapper;


import ua.com.paradine.domain.*;
import ua.com.paradine.service.dto.WorkingHoursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingHours} and its DTO {@link WorkingHoursDTO}.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface WorkingHoursMapper extends EntityMapper<WorkingHoursDTO, WorkingHours> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    WorkingHoursDTO toDto(WorkingHours workingHours);

    @Mapping(source = "restaurantId", target = "restaurant")
    WorkingHours toEntity(WorkingHoursDTO workingHoursDTO);

    default WorkingHours fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkingHours workingHours = new WorkingHours();
        workingHours.setId(id);
        return workingHours;
    }
}
