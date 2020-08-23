package ua.com.paradine.core.e2e;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(scripts = {"/db/test_data.sql"})
public class ViewRestaurantsListE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private GooglePopularTimesSafetyClassifier classifier;

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
}
