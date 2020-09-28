package ua.com.paradine.core.e2e;

import static java.lang.String.format;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.core.business.GooglePopularTimesSafetyClassifier;
import ua.com.paradine.core.business.SafetyMarker;

@SpringBootTest(classes = ParadineApp.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) //TODO problems migrating this to testcontainers
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
            .andExpect(header().string("X-Total-Count", "11"))
            .andExpect(jsonPath("$.version").value("2.0"))
            .andExpect(jsonPath("$.restaurants[?(@.name=='Musafir')].safetyToday[?(@.h=='09')].value")
                .value(SafetyMarker.GREEN.getIndicator()))
            .andExpect(jsonPath("$.restaurants[?(@.name=='Musafir')].safetyTomorrow[?(@.h=='09')].value")
                .value(SafetyMarker.YELLOW.getIndicator()))
        ;
    }

    @ParameterizedTest
    @CsvSource({
        "musafir,6a2f41a3-c54c-fce8-32d2-0324e1c32e22",
        "Мусафир,6a2f41a3-c54c-fce8-32d2-0324e1c32e22",
        "мусаф,6a2f41a3-c54c-fce8-32d2-0324e1c32e22",
        "мусафиr,6a2f41a3-c54c-fce8-32d2-0324e1c32e22",
        "Uусафир,6a2f41a3-c54c-fce8-32d2-0324e1c32e22",
        "Éclair Little Artwork,ea14a6e4-d241-4723-adce-945a47255c33",
        "Little Artwork,ea14a6e4-d241-4723-adce-945a47255c33",
        "little,ea14a6e4-d241-4723-adce-945a47255c33",
        "artwork,ea14a6e4-d241-4723-adce-945a47255c33",
        "art ecler,ea14a6e4-d241-4723-adce-945a47255c33",
        "ohota,6a2f31a3-c54c-fce8-32d2-0324e1c32e22",
        "озота,6a2f31a3-c54c-fce8-32d2-0324e1c32e22",
        "охота,6a2f31a3-c54c-fce8-32d2-0324e1c32e22",
        "Okhota,6a2f31a3-c54c-fce8-32d2-0324e1c32e22",
        "polyu,6a2f31a3-c54c-fce8-32d2-0324e1c32e22",
        "хрещ,1a2f41a3-c54c-fce8-32d2-0324e1c32e22",
        "the,b3f34ec0-010f-dddd-8a1d-9858cbb05b35",
    })
    public void testKeywordSearch(String keyword, String expectedUid) throws Exception {

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("q", keyword)
            .queryParam("city", "kyiv")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath(format("$.restaurants[?(@.id=='%s')]", expectedUid)).exists());
    }

    @Test
    @Sql(
        scripts = {"/db/similar_titles.sql"},
        executionPhase = BEFORE_TEST_METHOD
    )
    public void testKeywordSearch_and_paging() throws Exception {

        String keyword = "Lviv";

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("q", keyword)
            .queryParam("page", "0")
            .queryParam("city", "kyiv")
        )
        .andExpect(status().isOk())
        .andExpect(header().string("X-Total-Count", "13"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.restaurants.length()").value(10));

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("q", keyword)
            .queryParam("page", "1")
            .queryParam("city", "kyiv")
        )
        .andExpect(status().isOk())
        .andExpect(header().string("X-Total-Count", "13"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.restaurants.length()").value(3));
    }

    @ParameterizedTest
    @CsvSource({
        "50.424,30.51,Musafir",
        "50.424,30.51,Turkish House Lounge Grill",
        "50.42459,30.51967,Star Burger",
        "50.439,30.53,Oxota Na Ovets"
    })
    public void testGeolocationSearch(String lat, String lng, String expectedName) throws Exception {

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("lat", lat)
            .queryParam("lng", lng)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath(format("$.restaurants[?(@.name=='%s')]", expectedName)).exists());
    }
}
