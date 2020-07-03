package ua.com.paradine.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkingHoursMapperTest {

    private WorkingHoursMapper workingHoursMapper;

    @BeforeEach
    public void setUp() {
        workingHoursMapper = new WorkingHoursMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(workingHoursMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workingHoursMapper.fromId(null)).isNull();
    }
}
