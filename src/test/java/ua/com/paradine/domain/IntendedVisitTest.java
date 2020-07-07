package ua.com.paradine.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class IntendedVisitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntendedVisit.class);
        IntendedVisit intendedVisit1 = new IntendedVisit();
        intendedVisit1.setId(1L);
        IntendedVisit intendedVisit2 = new IntendedVisit();
        intendedVisit2.setId(intendedVisit1.getId());
        assertThat(intendedVisit1).isEqualTo(intendedVisit2);
        intendedVisit2.setId(2L);
        assertThat(intendedVisit1).isNotEqualTo(intendedVisit2);
        intendedVisit1.setId(null);
        assertThat(intendedVisit1).isNotEqualTo(intendedVisit2);
    }
}
