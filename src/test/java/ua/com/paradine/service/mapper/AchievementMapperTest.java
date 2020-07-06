package ua.com.paradine.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AchievementMapperTest {

    private AchievementMapper achievementMapper;

    @BeforeEach
    public void setUp() {
        achievementMapper = new AchievementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(achievementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(achievementMapper.fromId(null)).isNull();
    }
}
