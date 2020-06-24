package ua.com.paradine.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ua.com.paradine.web.rest.TestUtil;

public class RestaurantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestaurantDTO.class);
        RestaurantDTO restaurantDTO1 = new RestaurantDTO();
        restaurantDTO1.setId(1L);
        RestaurantDTO restaurantDTO2 = new RestaurantDTO();
        assertThat(restaurantDTO1).isNotEqualTo(restaurantDTO2);
        restaurantDTO2.setId(restaurantDTO1.getId());
        assertThat(restaurantDTO1).isEqualTo(restaurantDTO2);
        restaurantDTO2.setId(2L);
        assertThat(restaurantDTO1).isNotEqualTo(restaurantDTO2);
        restaurantDTO1.setId(null);
        assertThat(restaurantDTO1).isNotEqualTo(restaurantDTO2);
    }
}
