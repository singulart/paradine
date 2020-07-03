package ua.com.paradine.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class WorkingHoursDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingHoursDTO.class);
        WorkingHoursDTO workingHoursDTO1 = new WorkingHoursDTO();
        workingHoursDTO1.setId(1L);
        WorkingHoursDTO workingHoursDTO2 = new WorkingHoursDTO();
        assertThat(workingHoursDTO1).isNotEqualTo(workingHoursDTO2);
        workingHoursDTO2.setId(workingHoursDTO1.getId());
        assertThat(workingHoursDTO1).isEqualTo(workingHoursDTO2);
        workingHoursDTO2.setId(2L);
        assertThat(workingHoursDTO1).isNotEqualTo(workingHoursDTO2);
        workingHoursDTO1.setId(null);
        assertThat(workingHoursDTO1).isNotEqualTo(workingHoursDTO2);
    }
}
