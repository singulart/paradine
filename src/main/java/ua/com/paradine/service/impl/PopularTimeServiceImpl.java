package ua.com.paradine.service.impl;

import ua.com.paradine.service.PopularTimeService;
import ua.com.paradine.domain.PopularTime;
import ua.com.paradine.repository.PopularTimeRepository;
import ua.com.paradine.service.dto.PopularTimeDTO;
import ua.com.paradine.service.mapper.PopularTimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PopularTime}.
 */
@Service
@Transactional
public class PopularTimeServiceImpl implements PopularTimeService {

    private final Logger log = LoggerFactory.getLogger(PopularTimeServiceImpl.class);

    private final PopularTimeRepository popularTimeRepository;

    private final PopularTimeMapper popularTimeMapper;

    public PopularTimeServiceImpl(PopularTimeRepository popularTimeRepository, PopularTimeMapper popularTimeMapper) {
        this.popularTimeRepository = popularTimeRepository;
        this.popularTimeMapper = popularTimeMapper;
    }

    /**
     * Save a popularTime.
     *
     * @param popularTimeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PopularTimeDTO save(PopularTimeDTO popularTimeDTO) {
        log.debug("Request to save PopularTime : {}", popularTimeDTO);
        PopularTime popularTime = popularTimeMapper.toEntity(popularTimeDTO);
        popularTime = popularTimeRepository.save(popularTime);
        PopularTimeDTO result = popularTimeMapper.toDto(popularTime);
//        popularTimeSearchRepository.save(popularTime);
        return result;
    }

    /**
     * Get all the popularTimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PopularTimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PopularTimes");
        return popularTimeRepository.findAll(pageable)
            .map(popularTimeMapper::toDto);
    }


    /**
     * Get one popularTime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PopularTimeDTO> findOne(Long id) {
        log.debug("Request to get PopularTime : {}", id);
        return popularTimeRepository.findById(id)
            .map(popularTimeMapper::toDto);
    }

    /**
     * Delete the popularTime by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PopularTime : {}", id);
        popularTimeRepository.deleteById(id);
//        popularTimeSearchRepository.deleteById(id);
    }

    /**
     * Search for the popularTime corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PopularTimeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PopularTimes for query {}", query);
//        return popularTimeSearchRepository.search(queryStringQuery(query), pageable)
//            .map(popularTimeMapper::toDto);
        return Page.empty();
    }
}
