package ua.com.paradine.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ua.com.paradine.domain.Achievement;
import ua.com.paradine.domain.*; // for static metamodels
import ua.com.paradine.repository.AchievementRepository;
import ua.com.paradine.repository.search.AchievementSearchRepository;
import ua.com.paradine.service.dto.AchievementCriteria;
import ua.com.paradine.service.dto.AchievementDTO;
import ua.com.paradine.service.mapper.AchievementMapper;

/**
 * Service for executing complex queries for {@link Achievement} entities in the database.
 * The main input is a {@link AchievementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AchievementDTO} or a {@link Page} of {@link AchievementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AchievementQueryService extends QueryService<Achievement> {

    private final Logger log = LoggerFactory.getLogger(AchievementQueryService.class);

    private final AchievementRepository achievementRepository;

    private final AchievementMapper achievementMapper;

    private final AchievementSearchRepository achievementSearchRepository;

    public AchievementQueryService(AchievementRepository achievementRepository, AchievementMapper achievementMapper, AchievementSearchRepository achievementSearchRepository) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
        this.achievementSearchRepository = achievementSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AchievementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AchievementDTO> findByCriteria(AchievementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Achievement> specification = createSpecification(criteria);
        return achievementMapper.toDto(achievementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AchievementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AchievementDTO> findByCriteria(AchievementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Achievement> specification = createSpecification(criteria);
        return achievementRepository.findAll(specification, page)
            .map(achievementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AchievementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Achievement> specification = createSpecification(criteria);
        return achievementRepository.count(specification);
    }

    /**
     * Function to convert {@link AchievementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Achievement> createSpecification(AchievementCriteria criteria) {
        Specification<Achievement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Achievement_.id));
            }
            if (criteria.getSlug() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlug(), Achievement_.slug));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), Achievement_.nameEn));
            }
            if (criteria.getNameRu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameRu(), Achievement_.nameRu));
            }
            if (criteria.getNameUa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameUa(), Achievement_.nameUa));
            }
            if (criteria.getDescriptionEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionEn(), Achievement_.descriptionEn));
            }
            if (criteria.getDescriptionRu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionRu(), Achievement_.descriptionRu));
            }
            if (criteria.getDescriptionUa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionUa(), Achievement_.descriptionUa));
            }
        }
        return specification;
    }
}
