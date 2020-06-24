package ua.com.paradine.repository.search;

import ua.com.paradine.domain.Restaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Restaurant} entity.
 */
public interface RestaurantSearchRepository extends ElasticsearchRepository<Restaurant, Long> {
}
