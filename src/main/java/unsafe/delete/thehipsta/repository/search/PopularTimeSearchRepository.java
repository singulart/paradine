package unsafe.delete.thehipsta.repository.search;

import unsafe.delete.thehipsta.domain.PopularTime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PopularTime} entity.
 */
public interface PopularTimeSearchRepository extends ElasticsearchRepository<PopularTime, Long> {
}
