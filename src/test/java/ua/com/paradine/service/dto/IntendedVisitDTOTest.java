package ua.com.paradine.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class IntendedVisitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntendedVisitDTO.class);
        IntendedVisitDTO intendedVisitDTO1 = new IntendedVisitDTO();
        intendedVisitDTO1.setId(1L);
        IntendedVisitDTO intendedVisitDTO2 = new IntendedVisitDTO();
        assertThat(intendedVisitDTO1).isNotEqualTo(intendedVisitDTO2);
        intendedVisitDTO2.setId(intendedVisitDTO1.getId());
        assertThat(intendedVisitDTO1).isEqualTo(intendedVisitDTO2);
        intendedVisitDTO2.setId(2L);
        assertThat(intendedVisitDTO1).isNotEqualTo(intendedVisitDTO2);
        intendedVisitDTO1.setId(null);
        assertThat(intendedVisitDTO1).isNotEqualTo(intendedVisitDTO2);
    }
}
