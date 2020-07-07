package ua.com.paradine.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IntendedVisitMapperTest {

    private IntendedVisitMapper intendedVisitMapper;

    @BeforeEach
    public void setUp() {
        intendedVisitMapper = new IntendedVisitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(intendedVisitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(intendedVisitMapper.fromId(null)).isNull();
    }
}
