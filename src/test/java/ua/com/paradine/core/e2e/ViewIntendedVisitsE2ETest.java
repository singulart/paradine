package ua.com.paradine.core.e2e;

import static java.util.UUID.fromString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.com.paradine.core.Nowness.getNow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.web.api.model.CreateIntendedVisitRequest;
import ua.com.paradine.web.api.model.IntendedVisit;

@SpringBootTest(classes = ParadineApp.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
@Sql(scripts = {"/db/test_data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db/cleanup.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@WithMockUser
@ActiveProfiles(profiles = {"testcontainers"})
public class ViewIntendedVisitsE2ETest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    public void testViewIntendedVisits_200OK() throws Exception {
        IntendedVisit intendedVisit = new IntendedVisit()
            .restaurantId(fromString("117c0823-4d31-437d-8d14-e2686fa8c594"))
            .when(getNow().truncatedTo(ChronoUnit.DAYS).plusDays(1).plusHours(14)); // visit is for tomorrow
        CreateIntendedVisitRequest createIntendedVisitRequest = new CreateIntendedVisitRequest()
            .version("x.y")
            .visit(intendedVisit);

        mockMvc.perform(post("/api/paradine/v2/restaurants/intended_visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(createIntendedVisitRequest)));

        mockMvc.perform(get("/api/paradine/v2/restaurants/intended_visits"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.version").value("2.0"))
            .andExpect(jsonPath("$.todayVisits.length()").value(0))
            .andExpect(jsonPath("$.tomorrowVisits.length()").value(1))
        ;

        intendedVisit.setWhen(getNow().plusHours(1)); // visit is for today. TODO Fails when run between 11pm and midnight);
        mockMvc.perform(post("/api/paradine/v2/restaurants/intended_visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(createIntendedVisitRequest)))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/paradine/v2/restaurants/intended_visits"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.version").value("2.0"))
            .andExpect(jsonPath("$.todayVisits.length()").value(1))
            .andExpect(jsonPath("$.tomorrowVisits.length()").value(1))
        ;
    }

}
