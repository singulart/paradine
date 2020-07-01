package ua.com.paradine.core.rest;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.core.business.SafetyMarker;
import ua.com.paradine.core.business.ViewClassifiedRestaurantsFlow;
import ua.com.paradine.core.business.vo.ClassifiedRestaurantVO;
import ua.com.paradine.core.business.vo.HourlyClassifier;

@SpringBootTest(classes = ParadineApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ParadineRestLayerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ViewClassifiedRestaurantsFlow viewListFlow;

    @Test
    void getRestaurants() throws Exception {
        when(viewListFlow.fetchClassifiedRestaurants(any())).thenReturn(
            new PageImpl<>(asList(new ClassifiedRestaurantVO()), PageRequest.of(0, 1), 10)
        );
        mockMvc.perform(get("/api/paradine/v2/restaurants"))
            .andExpect(status().isOk());
    }

    @Test
    void getRestaurantsWithValidQueryParams() throws Exception {
        when(viewListFlow.fetchClassifiedRestaurants(any())).thenReturn(
            new PageImpl<>(asList(new ClassifiedRestaurantVO()), PageRequest.of(0, 1), 10)
        );
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

    @Test
    void verifyViewRestaurantsListOutput() throws Exception {
        ClassifiedRestaurantVO rest1 = new ClassifiedRestaurantVO();
        rest1.setName("Big Bang");
        rest1.setPhotoUrl("https://paradine.com/static/assets/000011/87843874.jpg");
        rest1.setUuid(UUID.randomUUID().toString());
        rest1.setClassifiersToday(Set.of(
            new HourlyClassifier(10, SafetyMarker.RED),
            new HourlyClassifier(15, SafetyMarker.YELLOW),
            new HourlyClassifier(20, SafetyMarker.GREEN),
            new HourlyClassifier(23, SafetyMarker.CLOSED))
            .stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new))
        );
        rest1.setClassifiersTomorrow(Set.of(
            new HourlyClassifier(11, SafetyMarker.RED),
            new HourlyClassifier(16, SafetyMarker.YELLOW),
            new HourlyClassifier(21, SafetyMarker.GREEN),
            new HourlyClassifier(22, SafetyMarker.CLOSED))
            .stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new))
        );
        when(viewListFlow.fetchClassifiedRestaurants(any())).thenReturn(
            new PageImpl<>(asList(rest1), PageRequest.of(0, 2), 100)
        );

        mockMvc.perform(get("/api/paradine/v2/restaurants"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("X-Total-Count", "100"))
            .andExpect(header().string("X-Total-Pages", "50"))
            .andExpect(jsonPath("$.version").value("2.0"))
            .andExpect(jsonPath("$.restaurants[0].id").value(rest1.getUuid()))
            .andExpect(jsonPath("$.restaurants[0].name").value(rest1.getName()))
            .andExpect(jsonPath("$.restaurants[0].image").value(rest1.getPhotoUrl()))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[0].h").value(10))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[0].value").value(SafetyMarker.RED.getIndicator()))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[1].h").value(15))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[1].value").value(SafetyMarker.YELLOW.getIndicator()))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[2].h").value(20))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[2].value").value(SafetyMarker.GREEN.getIndicator()))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[3].h").value(23))
            .andExpect(jsonPath("$.restaurants[0].safetyToday[3].value").value(SafetyMarker.CLOSED.getIndicator()))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[0].h").value(11))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[0].value").value(SafetyMarker.RED.getIndicator()))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[1].h").value(16))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[1].value").value(SafetyMarker.YELLOW.getIndicator()))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[2].h").value(21))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[2].value").value(SafetyMarker.GREEN.getIndicator()))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[3].h").value(22))
            .andExpect(jsonPath("$.restaurants[0].safetyTomorrow[3].value").value(SafetyMarker.CLOSED.getIndicator()))
            ;

    }
}