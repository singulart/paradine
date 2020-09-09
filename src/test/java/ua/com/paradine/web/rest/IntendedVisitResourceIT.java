package ua.com.paradine.web.rest;

import ua.com.paradine.ParadineApp;
import ua.com.paradine.domain.IntendedVisit;
import ua.com.paradine.domain.User;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.repository.IntendedVisitRepository;
import ua.com.paradine.service.IntendedVisitService;
import ua.com.paradine.service.dto.IntendedVisitDTO;
import ua.com.paradine.service.mapper.IntendedVisitMapper;
import ua.com.paradine.service.dto.IntendedVisitCriteria;
import ua.com.paradine.service.IntendedVisitQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ua.com.paradine.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IntendedVisitResource} REST controller.
 */
@SpringBootTest(classes = ParadineApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IntendedVisitResourceIT {

    private static final String DEFAULT_UUID = "dA5D4F6a-c5f7-92Af-Fa0D-35fD1eA795fD";
    private static final String UPDATED_UUID = "c58cA680-57c7-673A-E1d4-e8D1F7Fd0bB9";

    private static final ZonedDateTime DEFAULT_VISIT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VISIT_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_VISIT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_VISIT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VISIT_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_VISIT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_CANCELLED = false;
    private static final Boolean UPDATED_CANCELLED = true;

    private static final Integer DEFAULT_SAFETY = 1;
    private static final Integer UPDATED_SAFETY = 2;
    private static final Integer SMALLER_SAFETY = 1 - 1;

    @Autowired
    private IntendedVisitRepository intendedVisitRepository;

    @Autowired
    private IntendedVisitMapper intendedVisitMapper;

    @Autowired
    private IntendedVisitService intendedVisitService;

    @Autowired
    private IntendedVisitQueryService intendedVisitQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntendedVisitMockMvc;

    private IntendedVisit intendedVisit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntendedVisit createEntity(EntityManager em) {
        IntendedVisit intendedVisit = new IntendedVisit()
            .uuid(DEFAULT_UUID)
            .visitStartDate(DEFAULT_VISIT_START_DATE)
            .visitEndDate(DEFAULT_VISIT_END_DATE)
            .cancelled(DEFAULT_CANCELLED)
            .safety(DEFAULT_SAFETY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        intendedVisit.setVisitingUser(user);
        // Add required entity
        Restaurant restaurant;
        if (TestUtil.findAll(em, Restaurant.class).isEmpty()) {
            restaurant = RestaurantResourceIT.createEntity(em);
            em.persist(restaurant);
            em.flush();
        } else {
            restaurant = TestUtil.findAll(em, Restaurant.class).get(0);
        }
        intendedVisit.setRestaurant(restaurant);
        return intendedVisit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntendedVisit createUpdatedEntity(EntityManager em) {
        IntendedVisit intendedVisit = new IntendedVisit()
            .uuid(UPDATED_UUID)
            .visitStartDate(UPDATED_VISIT_START_DATE)
            .visitEndDate(UPDATED_VISIT_END_DATE)
            .cancelled(UPDATED_CANCELLED)
            .safety(UPDATED_SAFETY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        intendedVisit.setVisitingUser(user);
        // Add required entity
        Restaurant restaurant;
        if (TestUtil.findAll(em, Restaurant.class).isEmpty()) {
            restaurant = RestaurantResourceIT.createUpdatedEntity(em);
            em.persist(restaurant);
            em.flush();
        } else {
            restaurant = TestUtil.findAll(em, Restaurant.class).get(0);
        }
        intendedVisit.setRestaurant(restaurant);
        return intendedVisit;
    }

    @BeforeEach
    public void initTest() {
        intendedVisit = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntendedVisit() throws Exception {
        int databaseSizeBeforeCreate = intendedVisitRepository.findAll().size();
        // Create the IntendedVisit
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(intendedVisit);
        restIntendedVisitMockMvc.perform(post("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the IntendedVisit in the database
        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeCreate + 1);
        IntendedVisit testIntendedVisit = intendedVisitList.get(intendedVisitList.size() - 1);
        assertThat(testIntendedVisit.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testIntendedVisit.getVisitStartDate()).isEqualTo(DEFAULT_VISIT_START_DATE);
        assertThat(testIntendedVisit.getVisitEndDate()).isEqualTo(DEFAULT_VISIT_END_DATE);
        assertThat(testIntendedVisit.isCancelled()).isEqualTo(DEFAULT_CANCELLED);
        assertThat(testIntendedVisit.getSafety()).isEqualTo(DEFAULT_SAFETY);
    }

    @Test
    @Transactional
    public void createIntendedVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intendedVisitRepository.findAll().size();

        // Create the IntendedVisit with an existing ID
        intendedVisit.setId(1L);
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(intendedVisit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntendedVisitMockMvc.perform(post("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntendedVisit in the database
        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = intendedVisitRepository.findAll().size();
        // set the field null
        intendedVisit.setUuid(null);

        // Create the IntendedVisit, which fails.
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(intendedVisit);


        restIntendedVisitMockMvc.perform(post("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isBadRequest());

        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisitStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = intendedVisitRepository.findAll().size();
        // set the field null
        intendedVisit.setVisitStartDate(null);

        // Create the IntendedVisit, which fails.
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(intendedVisit);


        restIntendedVisitMockMvc.perform(post("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isBadRequest());

        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisitEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = intendedVisitRepository.findAll().size();
        // set the field null
        intendedVisit.setVisitEndDate(null);

        // Create the IntendedVisit, which fails.
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(intendedVisit);


        restIntendedVisitMockMvc.perform(post("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isBadRequest());

        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCancelledIsRequired() throws Exception {
        int databaseSizeBeforeTest = intendedVisitRepository.findAll().size();
        // set the field null
        intendedVisit.setCancelled(null);

        // Create the IntendedVisit, which fails.
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(intendedVisit);


        restIntendedVisitMockMvc.perform(post("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isBadRequest());

        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntendedVisits() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList
        restIntendedVisitMockMvc.perform(get("/api/intended-visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intendedVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].visitStartDate").value(hasItem(sameInstant(DEFAULT_VISIT_START_DATE))))
            .andExpect(jsonPath("$.[*].visitEndDate").value(hasItem(sameInstant(DEFAULT_VISIT_END_DATE))))
            .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.booleanValue())))
            .andExpect(jsonPath("$.[*].safety").value(hasItem(DEFAULT_SAFETY)));
    }
    
    @Test
    @Transactional
    public void getIntendedVisit() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get the intendedVisit
        restIntendedVisitMockMvc.perform(get("/api/intended-visits/{id}", intendedVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intendedVisit.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.visitStartDate").value(sameInstant(DEFAULT_VISIT_START_DATE)))
            .andExpect(jsonPath("$.visitEndDate").value(sameInstant(DEFAULT_VISIT_END_DATE)))
            .andExpect(jsonPath("$.cancelled").value(DEFAULT_CANCELLED.booleanValue()))
            .andExpect(jsonPath("$.safety").value(DEFAULT_SAFETY));
    }


    @Test
    @Transactional
    public void getIntendedVisitsByIdFiltering() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        Long id = intendedVisit.getId();

        defaultIntendedVisitShouldBeFound("id.equals=" + id);
        defaultIntendedVisitShouldNotBeFound("id.notEquals=" + id);

        defaultIntendedVisitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIntendedVisitShouldNotBeFound("id.greaterThan=" + id);

        defaultIntendedVisitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIntendedVisitShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIntendedVisitsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where uuid equals to DEFAULT_UUID
        defaultIntendedVisitShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the intendedVisitList where uuid equals to UPDATED_UUID
        defaultIntendedVisitShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByUuidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where uuid not equals to DEFAULT_UUID
        defaultIntendedVisitShouldNotBeFound("uuid.notEquals=" + DEFAULT_UUID);

        // Get all the intendedVisitList where uuid not equals to UPDATED_UUID
        defaultIntendedVisitShouldBeFound("uuid.notEquals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultIntendedVisitShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the intendedVisitList where uuid equals to UPDATED_UUID
        defaultIntendedVisitShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where uuid is not null
        defaultIntendedVisitShouldBeFound("uuid.specified=true");

        // Get all the intendedVisitList where uuid is null
        defaultIntendedVisitShouldNotBeFound("uuid.specified=false");
    }
                @Test
    @Transactional
    public void getAllIntendedVisitsByUuidContainsSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where uuid contains DEFAULT_UUID
        defaultIntendedVisitShouldBeFound("uuid.contains=" + DEFAULT_UUID);

        // Get all the intendedVisitList where uuid contains UPDATED_UUID
        defaultIntendedVisitShouldNotBeFound("uuid.contains=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByUuidNotContainsSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where uuid does not contain DEFAULT_UUID
        defaultIntendedVisitShouldNotBeFound("uuid.doesNotContain=" + DEFAULT_UUID);

        // Get all the intendedVisitList where uuid does not contain UPDATED_UUID
        defaultIntendedVisitShouldBeFound("uuid.doesNotContain=" + UPDATED_UUID);
    }


    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate equals to DEFAULT_VISIT_START_DATE
        defaultIntendedVisitShouldBeFound("visitStartDate.equals=" + DEFAULT_VISIT_START_DATE);

        // Get all the intendedVisitList where visitStartDate equals to UPDATED_VISIT_START_DATE
        defaultIntendedVisitShouldNotBeFound("visitStartDate.equals=" + UPDATED_VISIT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate not equals to DEFAULT_VISIT_START_DATE
        defaultIntendedVisitShouldNotBeFound("visitStartDate.notEquals=" + DEFAULT_VISIT_START_DATE);

        // Get all the intendedVisitList where visitStartDate not equals to UPDATED_VISIT_START_DATE
        defaultIntendedVisitShouldBeFound("visitStartDate.notEquals=" + UPDATED_VISIT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate in DEFAULT_VISIT_START_DATE or UPDATED_VISIT_START_DATE
        defaultIntendedVisitShouldBeFound("visitStartDate.in=" + DEFAULT_VISIT_START_DATE + "," + UPDATED_VISIT_START_DATE);

        // Get all the intendedVisitList where visitStartDate equals to UPDATED_VISIT_START_DATE
        defaultIntendedVisitShouldNotBeFound("visitStartDate.in=" + UPDATED_VISIT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate is not null
        defaultIntendedVisitShouldBeFound("visitStartDate.specified=true");

        // Get all the intendedVisitList where visitStartDate is null
        defaultIntendedVisitShouldNotBeFound("visitStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate is greater than or equal to DEFAULT_VISIT_START_DATE
        defaultIntendedVisitShouldBeFound("visitStartDate.greaterThanOrEqual=" + DEFAULT_VISIT_START_DATE);

        // Get all the intendedVisitList where visitStartDate is greater than or equal to UPDATED_VISIT_START_DATE
        defaultIntendedVisitShouldNotBeFound("visitStartDate.greaterThanOrEqual=" + UPDATED_VISIT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate is less than or equal to DEFAULT_VISIT_START_DATE
        defaultIntendedVisitShouldBeFound("visitStartDate.lessThanOrEqual=" + DEFAULT_VISIT_START_DATE);

        // Get all the intendedVisitList where visitStartDate is less than or equal to SMALLER_VISIT_START_DATE
        defaultIntendedVisitShouldNotBeFound("visitStartDate.lessThanOrEqual=" + SMALLER_VISIT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate is less than DEFAULT_VISIT_START_DATE
        defaultIntendedVisitShouldNotBeFound("visitStartDate.lessThan=" + DEFAULT_VISIT_START_DATE);

        // Get all the intendedVisitList where visitStartDate is less than UPDATED_VISIT_START_DATE
        defaultIntendedVisitShouldBeFound("visitStartDate.lessThan=" + UPDATED_VISIT_START_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitStartDate is greater than DEFAULT_VISIT_START_DATE
        defaultIntendedVisitShouldNotBeFound("visitStartDate.greaterThan=" + DEFAULT_VISIT_START_DATE);

        // Get all the intendedVisitList where visitStartDate is greater than SMALLER_VISIT_START_DATE
        defaultIntendedVisitShouldBeFound("visitStartDate.greaterThan=" + SMALLER_VISIT_START_DATE);
    }


    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate equals to DEFAULT_VISIT_END_DATE
        defaultIntendedVisitShouldBeFound("visitEndDate.equals=" + DEFAULT_VISIT_END_DATE);

        // Get all the intendedVisitList where visitEndDate equals to UPDATED_VISIT_END_DATE
        defaultIntendedVisitShouldNotBeFound("visitEndDate.equals=" + UPDATED_VISIT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate not equals to DEFAULT_VISIT_END_DATE
        defaultIntendedVisitShouldNotBeFound("visitEndDate.notEquals=" + DEFAULT_VISIT_END_DATE);

        // Get all the intendedVisitList where visitEndDate not equals to UPDATED_VISIT_END_DATE
        defaultIntendedVisitShouldBeFound("visitEndDate.notEquals=" + UPDATED_VISIT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate in DEFAULT_VISIT_END_DATE or UPDATED_VISIT_END_DATE
        defaultIntendedVisitShouldBeFound("visitEndDate.in=" + DEFAULT_VISIT_END_DATE + "," + UPDATED_VISIT_END_DATE);

        // Get all the intendedVisitList where visitEndDate equals to UPDATED_VISIT_END_DATE
        defaultIntendedVisitShouldNotBeFound("visitEndDate.in=" + UPDATED_VISIT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate is not null
        defaultIntendedVisitShouldBeFound("visitEndDate.specified=true");

        // Get all the intendedVisitList where visitEndDate is null
        defaultIntendedVisitShouldNotBeFound("visitEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate is greater than or equal to DEFAULT_VISIT_END_DATE
        defaultIntendedVisitShouldBeFound("visitEndDate.greaterThanOrEqual=" + DEFAULT_VISIT_END_DATE);

        // Get all the intendedVisitList where visitEndDate is greater than or equal to UPDATED_VISIT_END_DATE
        defaultIntendedVisitShouldNotBeFound("visitEndDate.greaterThanOrEqual=" + UPDATED_VISIT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate is less than or equal to DEFAULT_VISIT_END_DATE
        defaultIntendedVisitShouldBeFound("visitEndDate.lessThanOrEqual=" + DEFAULT_VISIT_END_DATE);

        // Get all the intendedVisitList where visitEndDate is less than or equal to SMALLER_VISIT_END_DATE
        defaultIntendedVisitShouldNotBeFound("visitEndDate.lessThanOrEqual=" + SMALLER_VISIT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate is less than DEFAULT_VISIT_END_DATE
        defaultIntendedVisitShouldNotBeFound("visitEndDate.lessThan=" + DEFAULT_VISIT_END_DATE);

        // Get all the intendedVisitList where visitEndDate is less than UPDATED_VISIT_END_DATE
        defaultIntendedVisitShouldBeFound("visitEndDate.lessThan=" + UPDATED_VISIT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where visitEndDate is greater than DEFAULT_VISIT_END_DATE
        defaultIntendedVisitShouldNotBeFound("visitEndDate.greaterThan=" + DEFAULT_VISIT_END_DATE);

        // Get all the intendedVisitList where visitEndDate is greater than SMALLER_VISIT_END_DATE
        defaultIntendedVisitShouldBeFound("visitEndDate.greaterThan=" + SMALLER_VISIT_END_DATE);
    }


    @Test
    @Transactional
    public void getAllIntendedVisitsByCancelledIsEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where cancelled equals to DEFAULT_CANCELLED
        defaultIntendedVisitShouldBeFound("cancelled.equals=" + DEFAULT_CANCELLED);

        // Get all the intendedVisitList where cancelled equals to UPDATED_CANCELLED
        defaultIntendedVisitShouldNotBeFound("cancelled.equals=" + UPDATED_CANCELLED);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByCancelledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where cancelled not equals to DEFAULT_CANCELLED
        defaultIntendedVisitShouldNotBeFound("cancelled.notEquals=" + DEFAULT_CANCELLED);

        // Get all the intendedVisitList where cancelled not equals to UPDATED_CANCELLED
        defaultIntendedVisitShouldBeFound("cancelled.notEquals=" + UPDATED_CANCELLED);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByCancelledIsInShouldWork() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where cancelled in DEFAULT_CANCELLED or UPDATED_CANCELLED
        defaultIntendedVisitShouldBeFound("cancelled.in=" + DEFAULT_CANCELLED + "," + UPDATED_CANCELLED);

        // Get all the intendedVisitList where cancelled equals to UPDATED_CANCELLED
        defaultIntendedVisitShouldNotBeFound("cancelled.in=" + UPDATED_CANCELLED);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsByCancelledIsNullOrNotNull() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where cancelled is not null
        defaultIntendedVisitShouldBeFound("cancelled.specified=true");

        // Get all the intendedVisitList where cancelled is null
        defaultIntendedVisitShouldNotBeFound("cancelled.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety equals to DEFAULT_SAFETY
        defaultIntendedVisitShouldBeFound("safety.equals=" + DEFAULT_SAFETY);

        // Get all the intendedVisitList where safety equals to UPDATED_SAFETY
        defaultIntendedVisitShouldNotBeFound("safety.equals=" + UPDATED_SAFETY);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety not equals to DEFAULT_SAFETY
        defaultIntendedVisitShouldNotBeFound("safety.notEquals=" + DEFAULT_SAFETY);

        // Get all the intendedVisitList where safety not equals to UPDATED_SAFETY
        defaultIntendedVisitShouldBeFound("safety.notEquals=" + UPDATED_SAFETY);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsInShouldWork() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety in DEFAULT_SAFETY or UPDATED_SAFETY
        defaultIntendedVisitShouldBeFound("safety.in=" + DEFAULT_SAFETY + "," + UPDATED_SAFETY);

        // Get all the intendedVisitList where safety equals to UPDATED_SAFETY
        defaultIntendedVisitShouldNotBeFound("safety.in=" + UPDATED_SAFETY);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsNullOrNotNull() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety is not null
        defaultIntendedVisitShouldBeFound("safety.specified=true");

        // Get all the intendedVisitList where safety is null
        defaultIntendedVisitShouldNotBeFound("safety.specified=false");
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety is greater than or equal to DEFAULT_SAFETY
        defaultIntendedVisitShouldBeFound("safety.greaterThanOrEqual=" + DEFAULT_SAFETY);

        // Get all the intendedVisitList where safety is greater than or equal to UPDATED_SAFETY
        defaultIntendedVisitShouldNotBeFound("safety.greaterThanOrEqual=" + UPDATED_SAFETY);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety is less than or equal to DEFAULT_SAFETY
        defaultIntendedVisitShouldBeFound("safety.lessThanOrEqual=" + DEFAULT_SAFETY);

        // Get all the intendedVisitList where safety is less than or equal to SMALLER_SAFETY
        defaultIntendedVisitShouldNotBeFound("safety.lessThanOrEqual=" + SMALLER_SAFETY);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsLessThanSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety is less than DEFAULT_SAFETY
        defaultIntendedVisitShouldNotBeFound("safety.lessThan=" + DEFAULT_SAFETY);

        // Get all the intendedVisitList where safety is less than UPDATED_SAFETY
        defaultIntendedVisitShouldBeFound("safety.lessThan=" + UPDATED_SAFETY);
    }

    @Test
    @Transactional
    public void getAllIntendedVisitsBySafetyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Get all the intendedVisitList where safety is greater than DEFAULT_SAFETY
        defaultIntendedVisitShouldNotBeFound("safety.greaterThan=" + DEFAULT_SAFETY);

        // Get all the intendedVisitList where safety is greater than SMALLER_SAFETY
        defaultIntendedVisitShouldBeFound("safety.greaterThan=" + SMALLER_SAFETY);
    }


    @Test
    @Transactional
    public void getAllIntendedVisitsByVisitingUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User visitingUser = intendedVisit.getVisitingUser();
        intendedVisitRepository.saveAndFlush(intendedVisit);
        Long visitingUserId = visitingUser.getId();

        // Get all the intendedVisitList where visitingUser equals to visitingUserId
        defaultIntendedVisitShouldBeFound("visitingUserId.equals=" + visitingUserId);

        // Get all the intendedVisitList where visitingUser equals to visitingUserId + 1
        defaultIntendedVisitShouldNotBeFound("visitingUserId.equals=" + (visitingUserId + 1));
    }


    @Test
    @Transactional
    public void getAllIntendedVisitsByRestaurantIsEqualToSomething() throws Exception {
        // Get already existing entity
        Restaurant restaurant = intendedVisit.getRestaurant();
        intendedVisitRepository.saveAndFlush(intendedVisit);
        Long restaurantId = restaurant.getId();

        // Get all the intendedVisitList where restaurant equals to restaurantId
        defaultIntendedVisitShouldBeFound("restaurantId.equals=" + restaurantId);

        // Get all the intendedVisitList where restaurant equals to restaurantId + 1
        defaultIntendedVisitShouldNotBeFound("restaurantId.equals=" + (restaurantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIntendedVisitShouldBeFound(String filter) throws Exception {
        restIntendedVisitMockMvc.perform(get("/api/intended-visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intendedVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].visitStartDate").value(hasItem(sameInstant(DEFAULT_VISIT_START_DATE))))
            .andExpect(jsonPath("$.[*].visitEndDate").value(hasItem(sameInstant(DEFAULT_VISIT_END_DATE))))
            .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.booleanValue())))
            .andExpect(jsonPath("$.[*].safety").value(hasItem(DEFAULT_SAFETY)));

        // Check, that the count call also returns 1
        restIntendedVisitMockMvc.perform(get("/api/intended-visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIntendedVisitShouldNotBeFound(String filter) throws Exception {
        restIntendedVisitMockMvc.perform(get("/api/intended-visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIntendedVisitMockMvc.perform(get("/api/intended-visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingIntendedVisit() throws Exception {
        // Get the intendedVisit
        restIntendedVisitMockMvc.perform(get("/api/intended-visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntendedVisit() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        int databaseSizeBeforeUpdate = intendedVisitRepository.findAll().size();

        // Update the intendedVisit
        IntendedVisit updatedIntendedVisit = intendedVisitRepository.findById(intendedVisit.getId()).get();
        // Disconnect from session so that the updates on updatedIntendedVisit are not directly saved in db
        em.detach(updatedIntendedVisit);
        updatedIntendedVisit
            .uuid(UPDATED_UUID)
            .visitStartDate(UPDATED_VISIT_START_DATE)
            .visitEndDate(UPDATED_VISIT_END_DATE)
            .cancelled(UPDATED_CANCELLED)
            .safety(UPDATED_SAFETY);
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(updatedIntendedVisit);

        restIntendedVisitMockMvc.perform(put("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isOk());

        // Validate the IntendedVisit in the database
        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeUpdate);
        IntendedVisit testIntendedVisit = intendedVisitList.get(intendedVisitList.size() - 1);
        assertThat(testIntendedVisit.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testIntendedVisit.getVisitStartDate()).isEqualTo(UPDATED_VISIT_START_DATE);
        assertThat(testIntendedVisit.getVisitEndDate()).isEqualTo(UPDATED_VISIT_END_DATE);
        assertThat(testIntendedVisit.isCancelled()).isEqualTo(UPDATED_CANCELLED);
        assertThat(testIntendedVisit.getSafety()).isEqualTo(UPDATED_SAFETY);
    }

    @Test
    @Transactional
    public void updateNonExistingIntendedVisit() throws Exception {
        int databaseSizeBeforeUpdate = intendedVisitRepository.findAll().size();

        // Create the IntendedVisit
        IntendedVisitDTO intendedVisitDTO = intendedVisitMapper.toDto(intendedVisit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntendedVisitMockMvc.perform(put("/api/intended-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intendedVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntendedVisit in the database
        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntendedVisit() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        int databaseSizeBeforeDelete = intendedVisitRepository.findAll().size();

        // Delete the intendedVisit
        restIntendedVisitMockMvc.perform(delete("/api/intended-visits/{id}", intendedVisit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntendedVisit> intendedVisitList = intendedVisitRepository.findAll();
        assertThat(intendedVisitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIntendedVisit() throws Exception {
        // Initialize the database
        intendedVisitRepository.saveAndFlush(intendedVisit);

        // Search the intendedVisit
        restIntendedVisitMockMvc.perform(get("/api/_search/intended-visits?query=id:" + intendedVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intendedVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].visitStartDate").value(hasItem(sameInstant(DEFAULT_VISIT_START_DATE))))
            .andExpect(jsonPath("$.[*].visitEndDate").value(hasItem(sameInstant(DEFAULT_VISIT_END_DATE))))
            .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.booleanValue())))
            .andExpect(jsonPath("$.[*].safety").value(hasItem(DEFAULT_SAFETY)));
    }
}
