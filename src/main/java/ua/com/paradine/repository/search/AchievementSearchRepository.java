package ua.com.paradine.repository.search;

import ua.com.paradine.domain.Achievement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Achievement} entity.
 */
public interface AchievementSearchRepository extends ElasticsearchRepository<Achievement, Long> {
}
