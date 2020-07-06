package ua.com.paradine.service.mapper;


import ua.com.paradine.domain.*;
import ua.com.paradine.service.dto.AchievementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Achievement} and its DTO {@link AchievementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AchievementMapper extends EntityMapper<AchievementDTO, Achievement> {



    default Achievement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Achievement achievement = new Achievement();
        achievement.setId(id);
        return achievement;
    }
}
