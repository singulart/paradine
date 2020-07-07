package ua.com.paradine.core.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.core.business.SafetyMarker;
import ua.com.paradine.web.api.model.CreateIntendedVisitRequest;
import ua.com.paradine.web.api.model.IntendedVisit;

@SpringBootTest(classes = ParadineApp.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(scripts = {"/db/test_data.sql"})
@WithMockUser
public class SubmitVisitIntentionE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  JestElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testSubmitVisitIntent() throws Exception {
        IntendedVisit intendedVisit = new IntendedVisit()
            .restaurantId("117c0823-4d31-437d-8d14-e2686fa8c594")
            .when(OffsetDateTime.now());
        CreateIntendedVisitRequest createIntendedVisitRequest = new CreateIntendedVisitRequest()
            .version("x.y")
            .visit(intendedVisit);

        ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(post("/api/paradine/v2/restaurants/intended_visits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createIntendedVisitRequest))
            )
            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(header().string("X-Total-Count", "10"))
//            .andExpect(header().string("X-Total-Pages", "1"))
//            .andExpect(jsonPath("$.version").value("2.0"))
//            .andExpect(jsonPath("$.restaurants[?(@.name=='Musafir')].safetyToday[?(@.h=='09')].value")
//                .value(SafetyMarker.GREEN.getIndicator()))
//            .andExpect(jsonPath("$.restaurants[?(@.name=='Musafir')].safetyTomorrow[?(@.h=='09')].value")
//                .value(SafetyMarker.YELLOW.getIndicator()))
        ;

    }

}
