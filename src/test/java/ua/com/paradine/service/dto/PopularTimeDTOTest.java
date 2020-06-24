package ua.com.paradine.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class PopularTimeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopularTimeDTO.class);
        PopularTimeDTO popularTimeDTO1 = new PopularTimeDTO();
        popularTimeDTO1.setId(1L);
        PopularTimeDTO popularTimeDTO2 = new PopularTimeDTO();
        assertThat(popularTimeDTO1).isNotEqualTo(popularTimeDTO2);
        popularTimeDTO2.setId(popularTimeDTO1.getId());
        assertThat(popularTimeDTO1).isEqualTo(popularTimeDTO2);
        popularTimeDTO2.setId(2L);
        assertThat(popularTimeDTO1).isNotEqualTo(popularTimeDTO2);
        popularTimeDTO1.setId(null);
        assertThat(popularTimeDTO1).isNotEqualTo(popularTimeDTO2);
    }
}
