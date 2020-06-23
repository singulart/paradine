package unsafe.delete.thehipsta.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PopularTimeMapperTest {

    private PopularTimeMapper popularTimeMapper;

    @BeforeEach
    public void setUp() {
        popularTimeMapper = new PopularTimeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(popularTimeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(popularTimeMapper.fromId(null)).isNull();
    }
}
