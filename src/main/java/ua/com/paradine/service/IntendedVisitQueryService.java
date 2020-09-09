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

import ua.com.paradine.domain.IntendedVisit;
import ua.com.paradine.domain.*; // for static metamodels
import ua.com.paradine.repository.IntendedVisitRepository;
import ua.com.paradine.service.dto.IntendedVisitCriteria;
import ua.com.paradine.service.dto.IntendedVisitDTO;
import ua.com.paradine.service.mapper.IntendedVisitMapper;

/**
 * Service for executing complex queries for {@link IntendedVisit} entities in the database.
 * The main input is a {@link IntendedVisitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IntendedVisitDTO} or a {@link Page} of {@link IntendedVisitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntendedVisitQueryService extends QueryService<IntendedVisit> {

    private final Logger log = LoggerFactory.getLogger(IntendedVisitQueryService.class);

    private final IntendedVisitRepository intendedVisitRepository;

    private final IntendedVisitMapper intendedVisitMapper;

    public IntendedVisitQueryService(IntendedVisitRepository intendedVisitRepository, IntendedVisitMapper intendedVisitMapper) {
        this.intendedVisitRepository = intendedVisitRepository;
        this.intendedVisitMapper = intendedVisitMapper;
    }

    /**
     * Return a {@link List} of {@link IntendedVisitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IntendedVisitDTO> findByCriteria(IntendedVisitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IntendedVisit> specification = createSpecification(criteria);
        return intendedVisitMapper.toDto(intendedVisitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IntendedVisitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IntendedVisitDTO> findByCriteria(IntendedVisitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IntendedVisit> specification = createSpecification(criteria);
        return intendedVisitRepository.findAll(specification, page)
            .map(intendedVisitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntendedVisitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IntendedVisit> specification = createSpecification(criteria);
        return intendedVisitRepository.count(specification);
    }

    /**
     * Function to convert {@link IntendedVisitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IntendedVisit> createSpecification(IntendedVisitCriteria criteria) {
        Specification<IntendedVisit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IntendedVisit_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), IntendedVisit_.uuid));
            }
            if (criteria.getVisitStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVisitStartDate(), IntendedVisit_.visitStartDate));
            }
            if (criteria.getVisitEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVisitEndDate(), IntendedVisit_.visitEndDate));
            }
            if (criteria.getCancelled() != null) {
                specification = specification.and(buildSpecification(criteria.getCancelled(), IntendedVisit_.cancelled));
            }
            if (criteria.getSafety() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSafety(), IntendedVisit_.safety));
            }
            if (criteria.getVisitingUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getVisitingUserId(),
                    root -> root.join(IntendedVisit_.visitingUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getRestaurantId() != null) {
                specification = specification.and(buildSpecification(criteria.getRestaurantId(),
                    root -> root.join(IntendedVisit_.restaurant, JoinType.LEFT).get(Restaurant_.id)));
            }
        }
        return specification;
    }
}
