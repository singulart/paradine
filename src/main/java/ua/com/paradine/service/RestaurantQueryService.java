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

import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.domain.*; // for static metamodels
import ua.com.paradine.repository.RestaurantRepository;
import ua.com.paradine.repository.search.RestaurantSearchRepository;
import ua.com.paradine.service.dto.RestaurantCriteria;
import ua.com.paradine.service.dto.RestaurantDTO;
import ua.com.paradine.service.mapper.RestaurantMapper;

/**
 * Service for executing complex queries for {@link Restaurant} entities in the database.
 * The main input is a {@link RestaurantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RestaurantDTO} or a {@link Page} of {@link RestaurantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RestaurantQueryService extends QueryService<Restaurant> {

    private final Logger log = LoggerFactory.getLogger(RestaurantQueryService.class);

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    private final RestaurantSearchRepository restaurantSearchRepository;

    public RestaurantQueryService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, RestaurantSearchRepository restaurantSearchRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.restaurantSearchRepository = restaurantSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RestaurantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RestaurantDTO> findByCriteria(RestaurantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Restaurant> specification = createSpecification(criteria);
        return restaurantMapper.toDto(restaurantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RestaurantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RestaurantDTO> findByCriteria(RestaurantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Restaurant> specification = createSpecification(criteria);
        return restaurantRepository.findAll(specification, page)
            .map(restaurantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RestaurantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Restaurant> specification = createSpecification(criteria);
        return restaurantRepository.count(specification);
    }

    /**
     * Function to convert {@link RestaurantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Restaurant> createSpecification(RestaurantCriteria criteria) {
        Specification<Restaurant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Restaurant_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Restaurant_.name));
            }
            if (criteria.getAltName1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltName1(), Restaurant_.altName1));
            }
            if (criteria.getAddressEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressEn(), Restaurant_.addressEn));
            }
            if (criteria.getAddressRu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressRu(), Restaurant_.addressRu));
            }
            if (criteria.getAddressUa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressUa(), Restaurant_.addressUa));
            }
            if (criteria.getGooglePlacesId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGooglePlacesId(), Restaurant_.googlePlacesId));
            }
            if (criteria.getGeolat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeolat(), Restaurant_.geolat));
            }
            if (criteria.getGeolng() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeolng(), Restaurant_.geolng));
            }
            if (criteria.getPhotoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhotoUrl(), Restaurant_.photoUrl));
            }
            if (criteria.getAltName2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltName2(), Restaurant_.altName2));
            }
            if (criteria.getAltName3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltName3(), Restaurant_.altName3));
            }
            if (criteria.getCapacity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapacity(), Restaurant_.capacity));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Restaurant_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Restaurant_.updatedAt));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), Restaurant_.uuid));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(Restaurant_.city, JoinType.LEFT).get(City_.id)));
            }
        }
        return specification;
    }
}
