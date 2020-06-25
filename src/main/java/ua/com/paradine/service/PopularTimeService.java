package ua.com.paradine.service;

import ua.com.paradine.service.dto.PopularTimeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ua.com.paradine.domain.PopularTime}.
 */
public interface PopularTimeService {

    /**
     * Save a popularTime.
     *
     * @param popularTimeDTO the entity to save.
     * @return the persisted entity.
     */
    PopularTimeDTO save(PopularTimeDTO popularTimeDTO);

    /**
     * Get all the popularTimes.
     *
     * @return the list of entities.
     */
    List<PopularTimeDTO> findAll();


    /**
     * Get the "id" popularTime.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PopularTimeDTO> findOne(Long id);

    /**
     * Delete the "id" popularTime.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the popularTime corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PopularTimeDTO> search(String query);
}
