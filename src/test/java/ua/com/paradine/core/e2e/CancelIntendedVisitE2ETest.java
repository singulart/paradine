package ua.com.paradine.core.e2e;

import static java.util.UUID.fromString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.web.api.model.CreateIntendedVisitRequest;
import ua.com.paradine.web.api.model.IntendedVisit;
import ua.com.paradine.web.api.model.IntendedVisitsGetResponse;
import ua.com.paradine.web.api.model.UuidResponse;

@SpringBootTest(classes = ParadineApp.class)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
@Sql(scripts = {"/db/test_data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db/cleanup.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@WithMockUser
@ActiveProfiles(profiles = {"testcontainers"})
public class CancelIntendedVisitE2ETest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @Test
    public void testCancelVisitIntent_200OK() throws Exception {
        IntendedVisit intendedVisit = new IntendedVisit()
            .restaurantId(fromString("117c0823-4d31-437d-8d14-e2686fa8c594"))
            .when(OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1).plusHours(14));
        CreateIntendedVisitRequest createIntendedVisitRequest = new CreateIntendedVisitRequest()
            .version("x.y")
            .visit(intendedVisit);

        MvcResult result = mockMvc.perform(post("/api/paradine/v2/restaurants/intended_visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(createIntendedVisitRequest))
        ).andReturn();

        String content = result.getResponse().getContentAsString();
        UuidResponse response = mapper.readValue(content, UuidResponse.class);

        MvcResult getResult = mockMvc.perform(get("/api/paradine/v2/restaurants/intended_visits")
        ).andReturn();
        content = getResult.getResponse().getContentAsString();
        IntendedVisitsGetResponse resp = mapper.readValue(content, IntendedVisitsGetResponse.class);

        mockMvc.perform(delete("/api/paradine/v2/restaurants/intended_visits/{visit}", response.getId()))
            .andExpect(status().isOk());
    }

}
