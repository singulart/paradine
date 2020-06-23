package unsafe.delete.thehipsta.service.impl;

import unsafe.delete.thehipsta.service.PopularTimeService;
import unsafe.delete.thehipsta.domain.PopularTime;
import unsafe.delete.thehipsta.repository.PopularTimeRepository;
import unsafe.delete.thehipsta.repository.search.PopularTimeSearchRepository;
import unsafe.delete.thehipsta.service.dto.PopularTimeDTO;
import unsafe.delete.thehipsta.service.mapper.PopularTimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PopularTime}.
 */
@Service
@Transactional
public class PopularTimeServiceImpl implements PopularTimeService {

    private final Logger log = LoggerFactory.getLogger(PopularTimeServiceImpl.class);

    private final PopularTimeRepository popularTimeRepository;

    private final PopularTimeMapper popularTimeMapper;

    private final PopularTimeSearchRepository popularTimeSearchRepository;

    public PopularTimeServiceImpl(PopularTimeRepository popularTimeRepository, PopularTimeMapper popularTimeMapper, PopularTimeSearchRepository popularTimeSearchRepository) {
        this.popularTimeRepository = popularTimeRepository;
        this.popularTimeMapper = popularTimeMapper;
        this.popularTimeSearchRepository = popularTimeSearchRepository;
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
        popularTimeSearchRepository.save(popularTime);
        return result;
    }

    /**
     * Get all the popularTimes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PopularTimeDTO> findAll() {
        log.debug("Request to get all PopularTimes");
        return popularTimeRepository.findAll().stream()
            .map(popularTimeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
        popularTimeSearchRepository.deleteById(id);
    }

    /**
     * Search for the popularTime corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PopularTimeDTO> search(String query) {
        log.debug("Request to search PopularTimes for query {}", query);
        return StreamSupport
            .stream(popularTimeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(popularTimeMapper::toDto)
        .collect(Collectors.toList());
    }
}
