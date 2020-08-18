package ua.com.paradine.repository.search;

import ua.com.paradine.domain.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link City} entity.
 */
public interface CitySearchRepository extends ElasticsearchRepository<City, Long> {
}
