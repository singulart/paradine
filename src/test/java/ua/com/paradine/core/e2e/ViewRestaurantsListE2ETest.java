package ua.com.paradine.core.e2e;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.core.business.GooglePopularTimesSafetyClassifier;
import ua.com.paradine.core.business.SafetyMarker;

@SpringBootTest(classes = ParadineApp.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(
    scripts = {"/db/test_data.sql"},
    executionPhase = BEFORE_TEST_METHOD
)
@Sql(
    scripts = {"/db/cleanup.sql"},
    executionPhase = AFTER_TEST_METHOD
)
public class ViewRestaurantsListE2ETest extends SearchIndexTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private GooglePopularTimesSafetyClassifier classifier;

    @BeforeEach // TODO indexing before each test method is very time-consuming and ineffective
    public void setup() {
        super.rebuildIndex();
    }

    @Test
    public void testLoadPage() throws Exception {

        doReturn("We").when(classifier).getToday();
        doReturn("Th").when(classifier).getTomorrow();

        mockMvc.perform(get("/api/paradine/v2/restaurants").queryParam("city", "kyiv"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("X-Total-Count", "10"))
            .andExpect(jsonPath("$.version").value("2.0"))
            .andExpect(jsonPath("$.restaurants[?(@.name=='Musafir')].safetyToday[?(@.h=='09')].value")
                .value(SafetyMarker.GREEN.getIndicator()))
            .andExpect(jsonPath("$.restaurants[?(@.name=='Musafir')].safetyTomorrow[?(@.h=='09')].value")
                .value(SafetyMarker.YELLOW.getIndicator()))
        ;
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "musafir",
        "Мусафир",
        "мусаф",
        "мусафиr",
        "Uусафир",
    })
    public void testKeywordSearch(String keyword) throws Exception {

        String expectedUid = "6a2f41a3-c54c-fce8-32d2-0324e1c32e22";

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("q", keyword)
            .queryParam("city", "kyiv")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(header().string("X-Total-Count", "1"))
        .andExpect(jsonPath("$.restaurants[0].id")
            .value(expectedUid));
    }
}
