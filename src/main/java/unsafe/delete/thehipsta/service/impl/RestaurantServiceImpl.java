package unsafe.delete.thehipsta.service.impl;

import unsafe.delete.thehipsta.service.RestaurantService;
import unsafe.delete.thehipsta.domain.Restaurant;
import unsafe.delete.thehipsta.repository.RestaurantRepository;
import unsafe.delete.thehipsta.repository.search.RestaurantSearchRepository;
import unsafe.delete.thehipsta.service.dto.RestaurantDTO;
import unsafe.delete.thehipsta.service.mapper.RestaurantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Restaurant}.
 */
@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    private final RestaurantSearchRepository restaurantSearchRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, RestaurantSearchRepository restaurantSearchRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.restaurantSearchRepository = restaurantSearchRepository;
    }

    /**
     * Save a restaurant.
     *
     * @param restaurantDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RestaurantDTO save(RestaurantDTO restaurantDTO) {
        log.debug("Request to save Restaurant : {}", restaurantDTO);
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant = restaurantRepository.save(restaurant);
        RestaurantDTO result = restaurantMapper.toDto(restaurant);
        restaurantSearchRepository.save(restaurant);
        return result;
    }

    /**
     * Get all the restaurants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurants");
        return restaurantRepository.findAll(pageable)
            .map(restaurantMapper::toDto);
    }


    /**
     * Get one restaurant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RestaurantDTO> findOne(Long id) {
        log.debug("Request to get Restaurant : {}", id);
        return restaurantRepository.findById(id)
            .map(restaurantMapper::toDto);
    }

    /**
     * Delete the restaurant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Restaurant : {}", id);
        restaurantRepository.deleteById(id);
        restaurantSearchRepository.deleteById(id);
    }

    /**
     * Search for the restaurant corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Restaurants for query {}", query);
        return restaurantSearchRepository.search(queryStringQuery(query), pageable)
            .map(restaurantMapper::toDto);
    }
}
