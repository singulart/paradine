package ua.com.paradine.service.impl;

import java.util.Collections;
import ua.com.paradine.service.AchievementService;
import ua.com.paradine.domain.Achievement;
import ua.com.paradine.repository.AchievementRepository;
import ua.com.paradine.service.dto.AchievementDTO;
import ua.com.paradine.service.mapper.AchievementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Service Implementation for managing {@link Achievement}.
 */
@Service
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private final Logger log = LoggerFactory.getLogger(AchievementServiceImpl.class);

    private final AchievementRepository achievementRepository;

    private final AchievementMapper achievementMapper;

    public AchievementServiceImpl(AchievementRepository achievementRepository, AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
    }

    /**
     * Save a achievement.
     *
     * @param achievementDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AchievementDTO save(AchievementDTO achievementDTO) {
        log.debug("Request to save Achievement : {}", achievementDTO);
        Achievement achievement = achievementMapper.toEntity(achievementDTO);
        achievement = achievementRepository.save(achievement);
        AchievementDTO result = achievementMapper.toDto(achievement);
//        achievementSearchRepository.save(achievement);
        return result;
    }

    /**
     * Get all the achievements.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AchievementDTO> findAll() {
        log.debug("Request to get all Achievements");
        return achievementRepository.findAll().stream()
            .map(achievementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one achievement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AchievementDTO> findOne(Long id) {
        log.debug("Request to get Achievement : {}", id);
        return achievementRepository.findById(id)
            .map(achievementMapper::toDto);
    }

    /**
     * Delete the achievement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Achievement : {}", id);
        achievementRepository.deleteById(id);
//        achievementSearchRepository.deleteById(id);
    }

    /**
     * Search for the achievement corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AchievementDTO> search(String query) {
        log.debug("Request to search Achievements for query {}", query);
//        return StreamSupport
//            .stream(achievementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .map(achievementMapper::toDto)
//        .collect(Collectors.toList());
        return Collections.emptyList();
    }
}
