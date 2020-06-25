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

import ua.com.paradine.domain.PopularTime;
import ua.com.paradine.domain.*; // for static metamodels
import ua.com.paradine.repository.PopularTimeRepository;
import ua.com.paradine.repository.search.PopularTimeSearchRepository;
import ua.com.paradine.service.dto.PopularTimeCriteria;
import ua.com.paradine.service.dto.PopularTimeDTO;
import ua.com.paradine.service.mapper.PopularTimeMapper;

/**
 * Service for executing complex queries for {@link PopularTime} entities in the database.
 * The main input is a {@link PopularTimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PopularTimeDTO} or a {@link Page} of {@link PopularTimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PopularTimeQueryService extends QueryService<PopularTime> {

    private final Logger log = LoggerFactory.getLogger(PopularTimeQueryService.class);

    private final PopularTimeRepository popularTimeRepository;

    private final PopularTimeMapper popularTimeMapper;

    private final PopularTimeSearchRepository popularTimeSearchRepository;

    public PopularTimeQueryService(PopularTimeRepository popularTimeRepository, PopularTimeMapper popularTimeMapper, PopularTimeSearchRepository popularTimeSearchRepository) {
        this.popularTimeRepository = popularTimeRepository;
        this.popularTimeMapper = popularTimeMapper;
        this.popularTimeSearchRepository = popularTimeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PopularTimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PopularTimeDTO> findByCriteria(PopularTimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PopularTime> specification = createSpecification(criteria);
        return popularTimeMapper.toDto(popularTimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PopularTimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PopularTimeDTO> findByCriteria(PopularTimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PopularTime> specification = createSpecification(criteria);
        return popularTimeRepository.findAll(specification, page)
            .map(popularTimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PopularTimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PopularTime> specification = createSpecification(criteria);
        return popularTimeRepository.count(specification);
    }

    /**
     * Function to convert {@link PopularTimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PopularTime> createSpecification(PopularTimeCriteria criteria) {
        Specification<PopularTime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PopularTime_.id));
            }
            if (criteria.getDayOfWeek() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDayOfWeek(), PopularTime_.dayOfWeek));
            }
            if (criteria.getOcc01() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc01(), PopularTime_.occ01));
            }
            if (criteria.getOcc02() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc02(), PopularTime_.occ02));
            }
            if (criteria.getOcc03() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc03(), PopularTime_.occ03));
            }
            if (criteria.getOcc04() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc04(), PopularTime_.occ04));
            }
            if (criteria.getOcc05() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc05(), PopularTime_.occ05));
            }
            if (criteria.getOcc06() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc06(), PopularTime_.occ06));
            }
            if (criteria.getOcc07() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc07(), PopularTime_.occ07));
            }
            if (criteria.getOcc08() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc08(), PopularTime_.occ08));
            }
            if (criteria.getOcc09() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc09(), PopularTime_.occ09));
            }
            if (criteria.getOcc10() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc10(), PopularTime_.occ10));
            }
            if (criteria.getOcc11() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc11(), PopularTime_.occ11));
            }
            if (criteria.getOcc12() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc12(), PopularTime_.occ12));
            }
            if (criteria.getOcc13() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc13(), PopularTime_.occ13));
            }
            if (criteria.getOcc14() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc14(), PopularTime_.occ14));
            }
            if (criteria.getOcc15() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc15(), PopularTime_.occ15));
            }
            if (criteria.getOcc16() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc16(), PopularTime_.occ16));
            }
            if (criteria.getOcc17() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc17(), PopularTime_.occ17));
            }
            if (criteria.getOcc18() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc18(), PopularTime_.occ18));
            }
            if (criteria.getOcc19() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc19(), PopularTime_.occ19));
            }
            if (criteria.getOcc20() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc20(), PopularTime_.occ20));
            }
            if (criteria.getOcc21() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc21(), PopularTime_.occ21));
            }
            if (criteria.getOcc22() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc22(), PopularTime_.occ22));
            }
            if (criteria.getOcc23() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc23(), PopularTime_.occ23));
            }
            if (criteria.getOcc24() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOcc24(), PopularTime_.occ24));
            }
            if (criteria.getRestaurantId() != null) {
                specification = specification.and(buildSpecification(criteria.getRestaurantId(),
                    root -> root.join(PopularTime_.restaurant, JoinType.LEFT).get(Restaurant_.id)));
            }
        }
        return specification;
    }
}
