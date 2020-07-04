package ua.com.paradine.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link WorkingHoursSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WorkingHoursSearchRepositoryMockConfiguration {

    @MockBean
    private WorkingHoursSearchRepository mockWorkingHoursSearchRepository;

}