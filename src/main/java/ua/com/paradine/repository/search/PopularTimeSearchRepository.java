package ua.com.paradine.repository.search;

import ua.com.paradine.domain.PopularTime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PopularTime} entity.
 */
public interface PopularTimeSearchRepository extends ElasticsearchRepository<PopularTime, Long> {
}
