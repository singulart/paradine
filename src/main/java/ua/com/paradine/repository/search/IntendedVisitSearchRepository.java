package ua.com.paradine.repository.search;

import ua.com.paradine.domain.IntendedVisit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link IntendedVisit} entity.
 */
public interface IntendedVisitSearchRepository extends ElasticsearchRepository<IntendedVisit, Long> {
}
