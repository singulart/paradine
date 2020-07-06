package ua.com.paradine.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class AchievementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementDTO.class);
        AchievementDTO achievementDTO1 = new AchievementDTO();
        achievementDTO1.setId(1L);
        AchievementDTO achievementDTO2 = new AchievementDTO();
        assertThat(achievementDTO1).isNotEqualTo(achievementDTO2);
        achievementDTO2.setId(achievementDTO1.getId());
        assertThat(achievementDTO1).isEqualTo(achievementDTO2);
        achievementDTO2.setId(2L);
        assertThat(achievementDTO1).isNotEqualTo(achievementDTO2);
        achievementDTO1.setId(null);
        assertThat(achievementDTO1).isNotEqualTo(achievementDTO2);
    }
}
