package ua.com.paradine.web.rest;

import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.domain.WorkingHours;
import ua.com.paradine.repository.WorkingHoursRepository;
import ua.com.paradine.repository.search.WorkingHoursSearchRepository;
import ua.com.paradine.service.WorkingHoursService;
import ua.com.paradine.service.dto.WorkingHoursDTO;
import ua.com.paradine.service.mapper.WorkingHoursMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkingHoursResource} REST controller.
 */
@SpringBootTest(classes = ParadineApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkingHoursResourceIT {

    private static final String DEFAULT_DAY_OF_WEEK = "AA";
    private static final String UPDATED_DAY_OF_WEEK = "BB";

    private static final Boolean DEFAULT_CLOSED = false;
    private static final Boolean UPDATED_CLOSED = true;

    private static final Integer DEFAULT_OPENING_HOUR = 0;
    private static final Integer UPDATED_OPENING_HOUR = 1;

    private static final Integer DEFAULT_CLOSING_HOUR = 0;
    private static final Integer UPDATED_CLOSING_HOUR = 1;

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private WorkingHoursMapper workingHoursMapper;

    @Autowired
    private WorkingHoursService workingHoursService;

    @MockBean
    private JestElasticsearchTemplate jestElasticsearchTemplate;

    /**
     * This repository is mocked in the ua.com.paradine.repository.search test package.
     *
     * @see ua.com.paradine.repository.search.WorkingHoursSearchRepositoryMockConfiguration
     */
    @Autowired
    private WorkingHoursSearchRepository mockWorkingHoursSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkingHoursMockMvc;

    private WorkingHours workingHours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingHours createEntity(EntityManager em) {
        WorkingHours workingHours = new WorkingHours()
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .closed(DEFAULT_CLOSED)
            .openingHour(DEFAULT_OPENING_HOUR)
            .closingHour(DEFAULT_CLOSING_HOUR);
        return workingHours;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingHours createUpdatedEntity(EntityManager em) {
        WorkingHours workingHours = new WorkingHours()
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .closed(UPDATED_CLOSED)
            .openingHour(UPDATED_OPENING_HOUR)
            .closingHour(UPDATED_CLOSING_HOUR);
        return workingHours;
    }

    @BeforeEach
    public void initTest() {
        workingHours = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkingHours() throws Exception {
        int databaseSizeBeforeCreate = workingHoursRepository.findAll().size();
        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);
        restWorkingHoursMockMvc.perform(post("/api/working-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingHours testWorkingHours = workingHoursList.get(workingHoursList.size() - 1);
        assertThat(testWorkingHours.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testWorkingHours.isClosed()).isEqualTo(DEFAULT_CLOSED);
        assertThat(testWorkingHours.getOpeningHour()).isEqualTo(DEFAULT_OPENING_HOUR);
        assertThat(testWorkingHours.getClosingHour()).isEqualTo(DEFAULT_CLOSING_HOUR);

        // Validate the WorkingHours in Elasticsearch
        verify(mockWorkingHoursSearchRepository, times(1)).save(testWorkingHours);
    }

    @Test
    @Transactional
    public void createWorkingHoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workingHoursRepository.findAll().size();

        // Create the WorkingHours with an existing ID
        workingHours.setId(1L);
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingHoursMockMvc.perform(post("/api/working-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeCreate);

        // Validate the WorkingHours in Elasticsearch
        verify(mockWorkingHoursSearchRepository, times(0)).save(workingHours);
    }


    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingHoursRepository.findAll().size();
        // set the field null
        workingHours.setDayOfWeek(null);

        // Create the WorkingHours, which fails.
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);


        restWorkingHoursMockMvc.perform(post("/api/working-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO)))
            .andExpect(status().isBadRequest());

        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClosedIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingHoursRepository.findAll().size();
        // set the field null
        workingHours.setClosed(null);

        // Create the WorkingHours, which fails.
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);


        restWorkingHoursMockMvc.perform(post("/api/working-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO)))
            .andExpect(status().isBadRequest());

        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList
        restWorkingHoursMockMvc.perform(get("/api/working-hours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].closed").value(hasItem(DEFAULT_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].openingHour").value(hasItem(DEFAULT_OPENING_HOUR)))
            .andExpect(jsonPath("$.[*].closingHour").value(hasItem(DEFAULT_CLOSING_HOUR)));
    }
    
    @Test
    @Transactional
    public void getWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get the workingHours
        restWorkingHoursMockMvc.perform(get("/api/working-hours/{id}", workingHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workingHours.getId().intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.closed").value(DEFAULT_CLOSED.booleanValue()))
            .andExpect(jsonPath("$.openingHour").value(DEFAULT_OPENING_HOUR))
            .andExpect(jsonPath("$.closingHour").value(DEFAULT_CLOSING_HOUR));
    }
    @Test
    @Transactional
    public void getNonExistingWorkingHours() throws Exception {
        // Get the workingHours
        restWorkingHoursMockMvc.perform(get("/api/working-hours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();

        // Update the workingHours
        WorkingHours updatedWorkingHours = workingHoursRepository.findById(workingHours.getId()).get();
        // Disconnect from session so that the updates on updatedWorkingHours are not directly saved in db
        em.detach(updatedWorkingHours);
        updatedWorkingHours
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .closed(UPDATED_CLOSED)
            .openingHour(UPDATED_OPENING_HOUR)
            .closingHour(UPDATED_CLOSING_HOUR);
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(updatedWorkingHours);

        restWorkingHoursMockMvc.perform(put("/api/working-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO)))
            .andExpect(status().isOk());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
        WorkingHours testWorkingHours = workingHoursList.get(workingHoursList.size() - 1);
        assertThat(testWorkingHours.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testWorkingHours.isClosed()).isEqualTo(UPDATED_CLOSED);
        assertThat(testWorkingHours.getOpeningHour()).isEqualTo(UPDATED_OPENING_HOUR);
        assertThat(testWorkingHours.getClosingHour()).isEqualTo(UPDATED_CLOSING_HOUR);

        // Validate the WorkingHours in Elasticsearch
        verify(mockWorkingHoursSearchRepository, times(1)).save(testWorkingHours);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkingHours() throws Exception {
        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();

        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingHoursMockMvc.perform(put("/api/working-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WorkingHours in Elasticsearch
        verify(mockWorkingHoursSearchRepository, times(0)).save(workingHours);
    }

    @Test
    @Transactional
    public void deleteWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        int databaseSizeBeforeDelete = workingHoursRepository.findAll().size();

        // Delete the workingHours
        restWorkingHoursMockMvc.perform(delete("/api/working-hours/{id}", workingHours.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WorkingHours in Elasticsearch
        verify(mockWorkingHoursSearchRepository, times(1)).deleteById(workingHours.getId());
    }

    @Test
    @Transactional
    public void searchWorkingHours() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);
        when(mockWorkingHoursSearchRepository.search(queryStringQuery("id:" + workingHours.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(workingHours), PageRequest.of(0, 1), 1));

        // Search the workingHours
        restWorkingHoursMockMvc.perform(get("/api/_search/working-hours?query=id:" + workingHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].closed").value(hasItem(DEFAULT_CLOSED.booleanValue())))
            .andExpect(jsonPath("$.[*].openingHour").value(hasItem(DEFAULT_OPENING_HOUR)))
            .andExpect(jsonPath("$.[*].closingHour").value(hasItem(DEFAULT_CLOSING_HOUR)));
    }
}
