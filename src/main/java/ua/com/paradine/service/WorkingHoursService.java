package ua.com.paradine.service;

import ua.com.paradine.service.dto.WorkingHoursDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ua.com.paradine.domain.WorkingHours}.
 */
public interface WorkingHoursService {

    /**
     * Save a workingHours.
     *
     * @param workingHoursDTO the entity to save.
     * @return the persisted entity.
     */
    WorkingHoursDTO save(WorkingHoursDTO workingHoursDTO);

    /**
     * Get all the workingHours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingHoursDTO> findAll(Pageable pageable);


    /**
     * Get the "id" workingHours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkingHoursDTO> findOne(Long id);

    /**
     * Delete the "id" workingHours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the workingHours corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingHoursDTO> search(String query, Pageable pageable);
}
