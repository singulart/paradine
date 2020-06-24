package ua.com.paradine.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantMapperTest {

    private RestaurantMapper restaurantMapper;

    @BeforeEach
    public void setUp() {
        restaurantMapper = new RestaurantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(restaurantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(restaurantMapper.fromId(null)).isNull();
    }
}
