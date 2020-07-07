package ua.com.paradine.service.impl;

import ua.com.paradine.service.IntendedVisitService;
import ua.com.paradine.domain.IntendedVisit;
import ua.com.paradine.repository.IntendedVisitRepository;
import ua.com.paradine.repository.search.IntendedVisitSearchRepository;
import ua.com.paradine.service.dto.IntendedVisitDTO;
import ua.com.paradine.service.mapper.IntendedVisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link IntendedVisit}.
 */
@Service
@Transactional
public class IntendedVisitServiceImpl implements IntendedVisitService {

    private final Logger log = LoggerFactory.getLogger(IntendedVisitServiceImpl.class);

    private final IntendedVisitRepository intendedVisitRepository;

    private final IntendedVisitMapper intendedVisitMapper;

    private final IntendedVisitSearchRepository intendedVisitSearchRepository;

    public IntendedVisitServiceImpl(IntendedVisitRepository intendedVisitRepository, IntendedVisitMapper intendedVisitMapper, IntendedVisitSearchRepository intendedVisitSearchRepository) {
        this.intendedVisitRepository = intendedVisitRepository;
        this.intendedVisitMapper = intendedVisitMapper;
        this.intendedVisitSearchRepository = intendedVisitSearchRepository;
    }

    /**
     * Save a intendedVisit.
     *
     * @param intendedVisitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IntendedVisitDTO save(IntendedVisitDTO intendedVisitDTO) {
        log.debug("Request to save IntendedVisit : {}", intendedVisitDTO);
        IntendedVisit intendedVisit = intendedVisitMapper.toEntity(intendedVisitDTO);
        intendedVisit = intendedVisitRepository.save(intendedVisit);
        IntendedVisitDTO result = intendedVisitMapper.toDto(intendedVisit);
        intendedVisitSearchRepository.save(intendedVisit);
        return result;
    }

    /**
     * Get all the intendedVisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IntendedVisitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntendedVisits");
        return intendedVisitRepository.findAll(pageable)
            .map(intendedVisitMapper::toDto);
    }


    /**
     * Get one intendedVisit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IntendedVisitDTO> findOne(Long id) {
        log.debug("Request to get IntendedVisit : {}", id);
        return intendedVisitRepository.findById(id)
            .map(intendedVisitMapper::toDto);
    }

    /**
     * Delete the intendedVisit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntendedVisit : {}", id);
        intendedVisitRepository.deleteById(id);
        intendedVisitSearchRepository.deleteById(id);
    }

    /**
     * Search for the intendedVisit corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IntendedVisitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IntendedVisits for query {}", query);
        return intendedVisitSearchRepository.search(queryStringQuery(query), pageable)
            .map(intendedVisitMapper::toDto);
    }
}
