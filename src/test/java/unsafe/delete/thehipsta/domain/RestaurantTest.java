package unsafe.delete.thehipsta.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import unsafe.delete.thehipsta.web.rest.TestUtil;

public class RestaurantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Restaurant.class);
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(restaurant1.getId());
        assertThat(restaurant1).isEqualTo(restaurant2);
        restaurant2.setId(2L);
        assertThat(restaurant1).isNotEqualTo(restaurant2);
        restaurant1.setId(null);
        assertThat(restaurant1).isNotEqualTo(restaurant2);
    }
}
