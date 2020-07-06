package ua.com.paradine.service;

import ua.com.paradine.service.dto.AchievementDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ua.com.paradine.domain.Achievement}.
 */
public interface AchievementService {

    /**
     * Save a achievement.
     *
     * @param achievementDTO the entity to save.
     * @return the persisted entity.
     */
    AchievementDTO save(AchievementDTO achievementDTO);

    /**
     * Get all the achievements.
     *
     * @return the list of entities.
     */
    List<AchievementDTO> findAll();


    /**
     * Get the "id" achievement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AchievementDTO> findOne(Long id);

    /**
     * Delete the "id" achievement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the achievement corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<AchievementDTO> search(String query);
}
