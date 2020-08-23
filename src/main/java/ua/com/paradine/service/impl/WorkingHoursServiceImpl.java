package ua.com.paradine.service.impl;

import ua.com.paradine.service.WorkingHoursService;
import ua.com.paradine.domain.WorkingHours;
import ua.com.paradine.repository.WorkingHoursRepository;
import ua.com.paradine.service.dto.WorkingHoursDTO;
import ua.com.paradine.service.mapper.WorkingHoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkingHours}.
 */
@Service
@Transactional
public class WorkingHoursServiceImpl implements WorkingHoursService {

    private final Logger log = LoggerFactory.getLogger(WorkingHoursServiceImpl.class);

    private final WorkingHoursRepository workingHoursRepository;

    private final WorkingHoursMapper workingHoursMapper;

    public WorkingHoursServiceImpl(WorkingHoursRepository workingHoursRepository, WorkingHoursMapper workingHoursMapper) {
        this.workingHoursRepository = workingHoursRepository;
        this.workingHoursMapper = workingHoursMapper;
    }

    /**
     * Save a workingHours.
     *
     * @param workingHoursDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkingHoursDTO save(WorkingHoursDTO workingHoursDTO) {
        log.debug("Request to save WorkingHours : {}", workingHoursDTO);
        WorkingHours workingHours = workingHoursMapper.toEntity(workingHoursDTO);
        workingHours = workingHoursRepository.save(workingHours);
        WorkingHoursDTO result = workingHoursMapper.toDto(workingHours);
//        workingHoursSearchRepository.save(workingHours);
        return result;
    }

    /**
     * Get all the workingHours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkingHoursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingHours");
        return workingHoursRepository.findAll(pageable)
            .map(workingHoursMapper::toDto);
    }


    /**
     * Get one workingHours by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkingHoursDTO> findOne(Long id) {
        log.debug("Request to get WorkingHours : {}", id);
        return workingHoursRepository.findById(id)
            .map(workingHoursMapper::toDto);
    }

    /**
     * Delete the workingHours by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkingHours : {}", id);
        workingHoursRepository.deleteById(id);
//        workingHoursSearchRepository.deleteById(id);
    }

    /**
     * Search for the workingHours corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkingHoursDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorkingHours for query {}", query);
//        return workingHoursSearchRepository.search(queryStringQuery(query), pageable)
//            .map(workingHoursMapper::toDto);
        return Page.empty();
    }
}
