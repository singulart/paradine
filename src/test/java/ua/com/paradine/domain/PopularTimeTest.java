package ua.com.paradine.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class PopularTimeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopularTime.class);
        PopularTime popularTime1 = new PopularTime();
        popularTime1.setId(1L);
        PopularTime popularTime2 = new PopularTime();
        popularTime2.setId(popularTime1.getId());
        assertThat(popularTime1).isEqualTo(popularTime2);
        popularTime2.setId(2L);
        assertThat(popularTime1).isNotEqualTo(popularTime2);
        popularTime1.setId(null);
        assertThat(popularTime1).isNotEqualTo(popularTime2);
    }
}
