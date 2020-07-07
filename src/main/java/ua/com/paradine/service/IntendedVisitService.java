package ua.com.paradine.service;

import ua.com.paradine.service.dto.IntendedVisitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ua.com.paradine.domain.IntendedVisit}.
 */
public interface IntendedVisitService {

    /**
     * Save a intendedVisit.
     *
     * @param intendedVisitDTO the entity to save.
     * @return the persisted entity.
     */
    IntendedVisitDTO save(IntendedVisitDTO intendedVisitDTO);

    /**
     * Get all the intendedVisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntendedVisitDTO> findAll(Pageable pageable);


    /**
     * Get the "id" intendedVisit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntendedVisitDTO> findOne(Long id);

    /**
     * Delete the "id" intendedVisit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the intendedVisit corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntendedVisitDTO> search(String query, Pageable pageable);
}
