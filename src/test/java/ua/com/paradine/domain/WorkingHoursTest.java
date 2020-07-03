package ua.com.paradine.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class WorkingHoursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingHours.class);
        WorkingHours workingHours1 = new WorkingHours();
        workingHours1.setId(1L);
        WorkingHours workingHours2 = new WorkingHours();
        workingHours2.setId(workingHours1.getId());
        assertThat(workingHours1).isEqualTo(workingHours2);
        workingHours2.setId(2L);
        assertThat(workingHours1).isNotEqualTo(workingHours2);
        workingHours1.setId(null);
        assertThat(workingHours1).isNotEqualTo(workingHours2);
    }
}
