package ua.com.paradine.core.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.web.rest.TestUtil;

@SpringBootTest(classes = ParadineApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParadineRestLayerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getRestaurants() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants"))
            .andExpect(status().isOk());
    }

    @Test
    void getRestaurantsWithValidQueryParams() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("q", "italian")
            .queryParam("page", "2")
            .queryParam("lat", "50.123456")
            .queryParam("lng", "50.123456")
        )
            .andExpect(status().isOk());
    }

    @Test
    void searchQueryMinLengthShouldBeValidated() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants").queryParam("q", "a*"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void pageNumberShouldBeNonNegative() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants").queryParam("page", "-1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void pageNumberShouldBeNumeric() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants").queryParam("page", "asdfg"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void geoLocationParamsShouldBeOfValidRange() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("lat", "-100.0")
            .queryParam("lng", "50.0")
        )
            .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("lat", "100")
            .queryParam("lng", "50.0")
        )
            .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("lat", "50.0")
            .queryParam("lng", "-200")
        )
            .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("lat", "50.0")
            .queryParam("lng", "200")
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    void geoLocationParamsShouldBeBothSet() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("lat", "-10.0")
        )
            .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("lng", "50.0")
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    void searchQueryMaxLengthShouldBeValidated() throws Exception {
        mockMvc.perform(get("/api/paradine/v2/restaurants")
            .queryParam("q", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
            .andExpect(status().isBadRequest());
    }
}