package ua.com.paradine.repository.search;

import ua.com.paradine.domain.WorkingHours;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link WorkingHours} entity.
 */
public interface WorkingHoursSearchRepository extends ElasticsearchRepository<WorkingHours, Long> {
}
