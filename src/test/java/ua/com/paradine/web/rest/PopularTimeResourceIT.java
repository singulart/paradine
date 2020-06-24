package ua.com.paradine.web.rest;

import ua.com.paradine.RedisTestContainerExtension;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.domain.PopularTime;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.repository.PopularTimeRepository;
import ua.com.paradine.repository.search.PopularTimeSearchRepository;
import ua.com.paradine.service.PopularTimeService;
import ua.com.paradine.service.dto.PopularTimeDTO;
import ua.com.paradine.service.mapper.PopularTimeMapper;
import ua.com.paradine.service.PopularTimeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
 * Integration tests for the {@link PopularTimeResource} REST controller.
 */
@SpringBootTest(classes = ParadineApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class PopularTimeResourceIT {

    private static final String DEFAULT_DAY_OF_WEEK = "AA";
    private static final String UPDATED_DAY_OF_WEEK = "BB";

    private static final Integer DEFAULT_OCC_06 = 0;
    private static final Integer UPDATED_OCC_06 = 1;
    private static final Integer SMALLER_OCC_06 = 0 - 1;

    private static final Integer DEFAULT_OCC_07 = 0;
    private static final Integer UPDATED_OCC_07 = 1;
    private static final Integer SMALLER_OCC_07 = 0 - 1;

    private static final Integer DEFAULT_OCC_08 = 0;
    private static final Integer UPDATED_OCC_08 = 1;
    private static final Integer SMALLER_OCC_08 = 0 - 1;

    private static final Integer DEFAULT_OCC_09 = 0;
    private static final Integer UPDATED_OCC_09 = 1;
    private static final Integer SMALLER_OCC_09 = 0 - 1;

    private static final Integer DEFAULT_OCC_10 = 0;
    private static final Integer UPDATED_OCC_10 = 1;
    private static final Integer SMALLER_OCC_10 = 0 - 1;

    private static final Integer DEFAULT_OCC_11 = 0;
    private static final Integer UPDATED_OCC_11 = 1;
    private static final Integer SMALLER_OCC_11 = 0 - 1;

    private static final Integer DEFAULT_OCC_12 = 0;
    private static final Integer UPDATED_OCC_12 = 1;
    private static final Integer SMALLER_OCC_12 = 0 - 1;

    private static final Integer DEFAULT_OCC_13 = 0;
    private static final Integer UPDATED_OCC_13 = 1;
    private static final Integer SMALLER_OCC_13 = 0 - 1;

    private static final Integer DEFAULT_OCC_14 = 0;
    private static final Integer UPDATED_OCC_14 = 1;
    private static final Integer SMALLER_OCC_14 = 0 - 1;

    private static final Integer DEFAULT_OCC_15 = 0;
    private static final Integer UPDATED_OCC_15 = 1;
    private static final Integer SMALLER_OCC_15 = 0 - 1;

    private static final Integer DEFAULT_OCC_16 = 0;
    private static final Integer UPDATED_OCC_16 = 1;
    private static final Integer SMALLER_OCC_16 = 0 - 1;

    private static final Integer DEFAULT_OCC_17 = 0;
    private static final Integer UPDATED_OCC_17 = 1;
    private static final Integer SMALLER_OCC_17 = 0 - 1;

    private static final Integer DEFAULT_OCC_18 = 0;
    private static final Integer UPDATED_OCC_18 = 1;
    private static final Integer SMALLER_OCC_18 = 0 - 1;

    private static final Integer DEFAULT_OCC_19 = 0;
    private static final Integer UPDATED_OCC_19 = 1;
    private static final Integer SMALLER_OCC_19 = 0 - 1;

    private static final Integer DEFAULT_OCC_20 = 0;
    private static final Integer UPDATED_OCC_20 = 1;
    private static final Integer SMALLER_OCC_20 = 0 - 1;

    private static final Integer DEFAULT_OCC_21 = 0;
    private static final Integer UPDATED_OCC_21 = 1;
    private static final Integer SMALLER_OCC_21 = 0 - 1;

    private static final Integer DEFAULT_OCC_22 = 0;
    private static final Integer UPDATED_OCC_22 = 1;
    private static final Integer SMALLER_OCC_22 = 0 - 1;

    private static final Integer DEFAULT_OCC_23 = 0;
    private static final Integer UPDATED_OCC_23 = 1;
    private static final Integer SMALLER_OCC_23 = 0 - 1;

    @Autowired
    private PopularTimeRepository popularTimeRepository;

    @Autowired
    private PopularTimeMapper popularTimeMapper;

    @Autowired
    private PopularTimeService popularTimeService;

    /**
     * This repository is mocked in the ua.com.paradine.repository.search test package.
     *
     * @see ua.com.paradine.repository.search.PopularTimeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PopularTimeSearchRepository mockPopularTimeSearchRepository;

    @Autowired
    private PopularTimeQueryService popularTimeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPopularTimeMockMvc;

    private PopularTime popularTime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopularTime createEntity(EntityManager em) {
        PopularTime popularTime = new PopularTime()
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .occ06(DEFAULT_OCC_06)
            .occ07(DEFAULT_OCC_07)
            .occ08(DEFAULT_OCC_08)
            .occ09(DEFAULT_OCC_09)
            .occ10(DEFAULT_OCC_10)
            .occ11(DEFAULT_OCC_11)
            .occ12(DEFAULT_OCC_12)
            .occ13(DEFAULT_OCC_13)
            .occ14(DEFAULT_OCC_14)
            .occ15(DEFAULT_OCC_15)
            .occ16(DEFAULT_OCC_16)
            .occ17(DEFAULT_OCC_17)
            .occ18(DEFAULT_OCC_18)
            .occ19(DEFAULT_OCC_19)
            .occ20(DEFAULT_OCC_20)
            .occ21(DEFAULT_OCC_21)
            .occ22(DEFAULT_OCC_22)
            .occ23(DEFAULT_OCC_23);
        return popularTime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PopularTime createUpdatedEntity(EntityManager em) {
        PopularTime popularTime = new PopularTime()
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .occ06(UPDATED_OCC_06)
            .occ07(UPDATED_OCC_07)
            .occ08(UPDATED_OCC_08)
            .occ09(UPDATED_OCC_09)
            .occ10(UPDATED_OCC_10)
            .occ11(UPDATED_OCC_11)
            .occ12(UPDATED_OCC_12)
            .occ13(UPDATED_OCC_13)
            .occ14(UPDATED_OCC_14)
            .occ15(UPDATED_OCC_15)
            .occ16(UPDATED_OCC_16)
            .occ17(UPDATED_OCC_17)
            .occ18(UPDATED_OCC_18)
            .occ19(UPDATED_OCC_19)
            .occ20(UPDATED_OCC_20)
            .occ21(UPDATED_OCC_21)
            .occ22(UPDATED_OCC_22)
            .occ23(UPDATED_OCC_23);
        return popularTime;
    }

    @BeforeEach
    public void initTest() {
        popularTime = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopularTime() throws Exception {
        int databaseSizeBeforeCreate = popularTimeRepository.findAll().size();
        // Create the PopularTime
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);
        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isCreated());

        // Validate the PopularTime in the database
        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeCreate + 1);
        PopularTime testPopularTime = popularTimeList.get(popularTimeList.size() - 1);
        assertThat(testPopularTime.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testPopularTime.getOcc06()).isEqualTo(DEFAULT_OCC_06);
        assertThat(testPopularTime.getOcc07()).isEqualTo(DEFAULT_OCC_07);
        assertThat(testPopularTime.getOcc08()).isEqualTo(DEFAULT_OCC_08);
        assertThat(testPopularTime.getOcc09()).isEqualTo(DEFAULT_OCC_09);
        assertThat(testPopularTime.getOcc10()).isEqualTo(DEFAULT_OCC_10);
        assertThat(testPopularTime.getOcc11()).isEqualTo(DEFAULT_OCC_11);
        assertThat(testPopularTime.getOcc12()).isEqualTo(DEFAULT_OCC_12);
        assertThat(testPopularTime.getOcc13()).isEqualTo(DEFAULT_OCC_13);
        assertThat(testPopularTime.getOcc14()).isEqualTo(DEFAULT_OCC_14);
        assertThat(testPopularTime.getOcc15()).isEqualTo(DEFAULT_OCC_15);
        assertThat(testPopularTime.getOcc16()).isEqualTo(DEFAULT_OCC_16);
        assertThat(testPopularTime.getOcc17()).isEqualTo(DEFAULT_OCC_17);
        assertThat(testPopularTime.getOcc18()).isEqualTo(DEFAULT_OCC_18);
        assertThat(testPopularTime.getOcc19()).isEqualTo(DEFAULT_OCC_19);
        assertThat(testPopularTime.getOcc20()).isEqualTo(DEFAULT_OCC_20);
        assertThat(testPopularTime.getOcc21()).isEqualTo(DEFAULT_OCC_21);
        assertThat(testPopularTime.getOcc22()).isEqualTo(DEFAULT_OCC_22);
        assertThat(testPopularTime.getOcc23()).isEqualTo(DEFAULT_OCC_23);

        // Validate the PopularTime in Elasticsearch
        verify(mockPopularTimeSearchRepository, times(1)).save(testPopularTime);
    }

    @Test
    @Transactional
    public void createPopularTimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popularTimeRepository.findAll().size();

        // Create the PopularTime with an existing ID
        popularTime.setId(1L);
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PopularTime in the database
        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PopularTime in Elasticsearch
        verify(mockPopularTimeSearchRepository, times(0)).save(popularTime);
    }


    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setDayOfWeek(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc06IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc06(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc07IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc07(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc08IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc08(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc09IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc09(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc10IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc10(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc11IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc11(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc12IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc12(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc13IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc13(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc14IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc14(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc15IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc15(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc16IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc16(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc17IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc17(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc18IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc18(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc19IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc19(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc20IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc20(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc21IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc21(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc22IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc22(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOcc23IsRequired() throws Exception {
        int databaseSizeBeforeTest = popularTimeRepository.findAll().size();
        // set the field null
        popularTime.setOcc23(null);

        // Create the PopularTime, which fails.
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);


        restPopularTimeMockMvc.perform(post("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPopularTimes() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList
        restPopularTimeMockMvc.perform(get("/api/popular-times?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popularTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].occ06").value(hasItem(DEFAULT_OCC_06)))
            .andExpect(jsonPath("$.[*].occ07").value(hasItem(DEFAULT_OCC_07)))
            .andExpect(jsonPath("$.[*].occ08").value(hasItem(DEFAULT_OCC_08)))
            .andExpect(jsonPath("$.[*].occ09").value(hasItem(DEFAULT_OCC_09)))
            .andExpect(jsonPath("$.[*].occ10").value(hasItem(DEFAULT_OCC_10)))
            .andExpect(jsonPath("$.[*].occ11").value(hasItem(DEFAULT_OCC_11)))
            .andExpect(jsonPath("$.[*].occ12").value(hasItem(DEFAULT_OCC_12)))
            .andExpect(jsonPath("$.[*].occ13").value(hasItem(DEFAULT_OCC_13)))
            .andExpect(jsonPath("$.[*].occ14").value(hasItem(DEFAULT_OCC_14)))
            .andExpect(jsonPath("$.[*].occ15").value(hasItem(DEFAULT_OCC_15)))
            .andExpect(jsonPath("$.[*].occ16").value(hasItem(DEFAULT_OCC_16)))
            .andExpect(jsonPath("$.[*].occ17").value(hasItem(DEFAULT_OCC_17)))
            .andExpect(jsonPath("$.[*].occ18").value(hasItem(DEFAULT_OCC_18)))
            .andExpect(jsonPath("$.[*].occ19").value(hasItem(DEFAULT_OCC_19)))
            .andExpect(jsonPath("$.[*].occ20").value(hasItem(DEFAULT_OCC_20)))
            .andExpect(jsonPath("$.[*].occ21").value(hasItem(DEFAULT_OCC_21)))
            .andExpect(jsonPath("$.[*].occ22").value(hasItem(DEFAULT_OCC_22)))
            .andExpect(jsonPath("$.[*].occ23").value(hasItem(DEFAULT_OCC_23)));
    }

    @Test
    @Transactional
    public void getPopularTime() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get the popularTime
        restPopularTimeMockMvc.perform(get("/api/popular-times/{id}", popularTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(popularTime.getId().intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.occ06").value(DEFAULT_OCC_06))
            .andExpect(jsonPath("$.occ07").value(DEFAULT_OCC_07))
            .andExpect(jsonPath("$.occ08").value(DEFAULT_OCC_08))
            .andExpect(jsonPath("$.occ09").value(DEFAULT_OCC_09))
            .andExpect(jsonPath("$.occ10").value(DEFAULT_OCC_10))
            .andExpect(jsonPath("$.occ11").value(DEFAULT_OCC_11))
            .andExpect(jsonPath("$.occ12").value(DEFAULT_OCC_12))
            .andExpect(jsonPath("$.occ13").value(DEFAULT_OCC_13))
            .andExpect(jsonPath("$.occ14").value(DEFAULT_OCC_14))
            .andExpect(jsonPath("$.occ15").value(DEFAULT_OCC_15))
            .andExpect(jsonPath("$.occ16").value(DEFAULT_OCC_16))
            .andExpect(jsonPath("$.occ17").value(DEFAULT_OCC_17))
            .andExpect(jsonPath("$.occ18").value(DEFAULT_OCC_18))
            .andExpect(jsonPath("$.occ19").value(DEFAULT_OCC_19))
            .andExpect(jsonPath("$.occ20").value(DEFAULT_OCC_20))
            .andExpect(jsonPath("$.occ21").value(DEFAULT_OCC_21))
            .andExpect(jsonPath("$.occ22").value(DEFAULT_OCC_22))
            .andExpect(jsonPath("$.occ23").value(DEFAULT_OCC_23));
    }


    @Test
    @Transactional
    public void getPopularTimesByIdFiltering() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        Long id = popularTime.getId();

        defaultPopularTimeShouldBeFound("id.equals=" + id);
        defaultPopularTimeShouldNotBeFound("id.notEquals=" + id);

        defaultPopularTimeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPopularTimeShouldNotBeFound("id.greaterThan=" + id);

        defaultPopularTimeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPopularTimeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByDayOfWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where dayOfWeek equals to DEFAULT_DAY_OF_WEEK
        defaultPopularTimeShouldBeFound("dayOfWeek.equals=" + DEFAULT_DAY_OF_WEEK);

        // Get all the popularTimeList where dayOfWeek equals to UPDATED_DAY_OF_WEEK
        defaultPopularTimeShouldNotBeFound("dayOfWeek.equals=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByDayOfWeekIsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where dayOfWeek not equals to DEFAULT_DAY_OF_WEEK
        defaultPopularTimeShouldNotBeFound("dayOfWeek.notEquals=" + DEFAULT_DAY_OF_WEEK);

        // Get all the popularTimeList where dayOfWeek not equals to UPDATED_DAY_OF_WEEK
        defaultPopularTimeShouldBeFound("dayOfWeek.notEquals=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByDayOfWeekIsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where dayOfWeek in DEFAULT_DAY_OF_WEEK or UPDATED_DAY_OF_WEEK
        defaultPopularTimeShouldBeFound("dayOfWeek.in=" + DEFAULT_DAY_OF_WEEK + "," + UPDATED_DAY_OF_WEEK);

        // Get all the popularTimeList where dayOfWeek equals to UPDATED_DAY_OF_WEEK
        defaultPopularTimeShouldNotBeFound("dayOfWeek.in=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByDayOfWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where dayOfWeek is not null
        defaultPopularTimeShouldBeFound("dayOfWeek.specified=true");

        // Get all the popularTimeList where dayOfWeek is null
        defaultPopularTimeShouldNotBeFound("dayOfWeek.specified=false");
    }
                @Test
    @Transactional
    public void getAllPopularTimesByDayOfWeekContainsSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where dayOfWeek contains DEFAULT_DAY_OF_WEEK
        defaultPopularTimeShouldBeFound("dayOfWeek.contains=" + DEFAULT_DAY_OF_WEEK);

        // Get all the popularTimeList where dayOfWeek contains UPDATED_DAY_OF_WEEK
        defaultPopularTimeShouldNotBeFound("dayOfWeek.contains=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByDayOfWeekNotContainsSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where dayOfWeek does not contain DEFAULT_DAY_OF_WEEK
        defaultPopularTimeShouldNotBeFound("dayOfWeek.doesNotContain=" + DEFAULT_DAY_OF_WEEK);

        // Get all the popularTimeList where dayOfWeek does not contain UPDATED_DAY_OF_WEEK
        defaultPopularTimeShouldBeFound("dayOfWeek.doesNotContain=" + UPDATED_DAY_OF_WEEK);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 equals to DEFAULT_OCC_06
        defaultPopularTimeShouldBeFound("occ06.equals=" + DEFAULT_OCC_06);

        // Get all the popularTimeList where occ06 equals to UPDATED_OCC_06
        defaultPopularTimeShouldNotBeFound("occ06.equals=" + UPDATED_OCC_06);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 not equals to DEFAULT_OCC_06
        defaultPopularTimeShouldNotBeFound("occ06.notEquals=" + DEFAULT_OCC_06);

        // Get all the popularTimeList where occ06 not equals to UPDATED_OCC_06
        defaultPopularTimeShouldBeFound("occ06.notEquals=" + UPDATED_OCC_06);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 in DEFAULT_OCC_06 or UPDATED_OCC_06
        defaultPopularTimeShouldBeFound("occ06.in=" + DEFAULT_OCC_06 + "," + UPDATED_OCC_06);

        // Get all the popularTimeList where occ06 equals to UPDATED_OCC_06
        defaultPopularTimeShouldNotBeFound("occ06.in=" + UPDATED_OCC_06);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 is not null
        defaultPopularTimeShouldBeFound("occ06.specified=true");

        // Get all the popularTimeList where occ06 is null
        defaultPopularTimeShouldNotBeFound("occ06.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 is greater than or equal to DEFAULT_OCC_06
        defaultPopularTimeShouldBeFound("occ06.greaterThanOrEqual=" + DEFAULT_OCC_06);

        // Get all the popularTimeList where occ06 is greater than or equal to (DEFAULT_OCC_06 + 1)
        defaultPopularTimeShouldNotBeFound("occ06.greaterThanOrEqual=" + (DEFAULT_OCC_06 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 is less than or equal to DEFAULT_OCC_06
        defaultPopularTimeShouldBeFound("occ06.lessThanOrEqual=" + DEFAULT_OCC_06);

        // Get all the popularTimeList where occ06 is less than or equal to SMALLER_OCC_06
        defaultPopularTimeShouldNotBeFound("occ06.lessThanOrEqual=" + SMALLER_OCC_06);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 is less than DEFAULT_OCC_06
        defaultPopularTimeShouldNotBeFound("occ06.lessThan=" + DEFAULT_OCC_06);

        // Get all the popularTimeList where occ06 is less than (DEFAULT_OCC_06 + 1)
        defaultPopularTimeShouldBeFound("occ06.lessThan=" + (DEFAULT_OCC_06 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc06IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ06 is greater than DEFAULT_OCC_06
        defaultPopularTimeShouldNotBeFound("occ06.greaterThan=" + DEFAULT_OCC_06);

        // Get all the popularTimeList where occ06 is greater than SMALLER_OCC_06
        defaultPopularTimeShouldBeFound("occ06.greaterThan=" + SMALLER_OCC_06);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 equals to DEFAULT_OCC_07
        defaultPopularTimeShouldBeFound("occ07.equals=" + DEFAULT_OCC_07);

        // Get all the popularTimeList where occ07 equals to UPDATED_OCC_07
        defaultPopularTimeShouldNotBeFound("occ07.equals=" + UPDATED_OCC_07);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 not equals to DEFAULT_OCC_07
        defaultPopularTimeShouldNotBeFound("occ07.notEquals=" + DEFAULT_OCC_07);

        // Get all the popularTimeList where occ07 not equals to UPDATED_OCC_07
        defaultPopularTimeShouldBeFound("occ07.notEquals=" + UPDATED_OCC_07);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 in DEFAULT_OCC_07 or UPDATED_OCC_07
        defaultPopularTimeShouldBeFound("occ07.in=" + DEFAULT_OCC_07 + "," + UPDATED_OCC_07);

        // Get all the popularTimeList where occ07 equals to UPDATED_OCC_07
        defaultPopularTimeShouldNotBeFound("occ07.in=" + UPDATED_OCC_07);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 is not null
        defaultPopularTimeShouldBeFound("occ07.specified=true");

        // Get all the popularTimeList where occ07 is null
        defaultPopularTimeShouldNotBeFound("occ07.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 is greater than or equal to DEFAULT_OCC_07
        defaultPopularTimeShouldBeFound("occ07.greaterThanOrEqual=" + DEFAULT_OCC_07);

        // Get all the popularTimeList where occ07 is greater than or equal to (DEFAULT_OCC_07 + 1)
        defaultPopularTimeShouldNotBeFound("occ07.greaterThanOrEqual=" + (DEFAULT_OCC_07 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 is less than or equal to DEFAULT_OCC_07
        defaultPopularTimeShouldBeFound("occ07.lessThanOrEqual=" + DEFAULT_OCC_07);

        // Get all the popularTimeList where occ07 is less than or equal to SMALLER_OCC_07
        defaultPopularTimeShouldNotBeFound("occ07.lessThanOrEqual=" + SMALLER_OCC_07);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 is less than DEFAULT_OCC_07
        defaultPopularTimeShouldNotBeFound("occ07.lessThan=" + DEFAULT_OCC_07);

        // Get all the popularTimeList where occ07 is less than (DEFAULT_OCC_07 + 1)
        defaultPopularTimeShouldBeFound("occ07.lessThan=" + (DEFAULT_OCC_07 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc07IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ07 is greater than DEFAULT_OCC_07
        defaultPopularTimeShouldNotBeFound("occ07.greaterThan=" + DEFAULT_OCC_07);

        // Get all the popularTimeList where occ07 is greater than SMALLER_OCC_07
        defaultPopularTimeShouldBeFound("occ07.greaterThan=" + SMALLER_OCC_07);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 equals to DEFAULT_OCC_08
        defaultPopularTimeShouldBeFound("occ08.equals=" + DEFAULT_OCC_08);

        // Get all the popularTimeList where occ08 equals to UPDATED_OCC_08
        defaultPopularTimeShouldNotBeFound("occ08.equals=" + UPDATED_OCC_08);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 not equals to DEFAULT_OCC_08
        defaultPopularTimeShouldNotBeFound("occ08.notEquals=" + DEFAULT_OCC_08);

        // Get all the popularTimeList where occ08 not equals to UPDATED_OCC_08
        defaultPopularTimeShouldBeFound("occ08.notEquals=" + UPDATED_OCC_08);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 in DEFAULT_OCC_08 or UPDATED_OCC_08
        defaultPopularTimeShouldBeFound("occ08.in=" + DEFAULT_OCC_08 + "," + UPDATED_OCC_08);

        // Get all the popularTimeList where occ08 equals to UPDATED_OCC_08
        defaultPopularTimeShouldNotBeFound("occ08.in=" + UPDATED_OCC_08);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 is not null
        defaultPopularTimeShouldBeFound("occ08.specified=true");

        // Get all the popularTimeList where occ08 is null
        defaultPopularTimeShouldNotBeFound("occ08.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 is greater than or equal to DEFAULT_OCC_08
        defaultPopularTimeShouldBeFound("occ08.greaterThanOrEqual=" + DEFAULT_OCC_08);

        // Get all the popularTimeList where occ08 is greater than or equal to (DEFAULT_OCC_08 + 1)
        defaultPopularTimeShouldNotBeFound("occ08.greaterThanOrEqual=" + (DEFAULT_OCC_08 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 is less than or equal to DEFAULT_OCC_08
        defaultPopularTimeShouldBeFound("occ08.lessThanOrEqual=" + DEFAULT_OCC_08);

        // Get all the popularTimeList where occ08 is less than or equal to SMALLER_OCC_08
        defaultPopularTimeShouldNotBeFound("occ08.lessThanOrEqual=" + SMALLER_OCC_08);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 is less than DEFAULT_OCC_08
        defaultPopularTimeShouldNotBeFound("occ08.lessThan=" + DEFAULT_OCC_08);

        // Get all the popularTimeList where occ08 is less than (DEFAULT_OCC_08 + 1)
        defaultPopularTimeShouldBeFound("occ08.lessThan=" + (DEFAULT_OCC_08 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc08IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ08 is greater than DEFAULT_OCC_08
        defaultPopularTimeShouldNotBeFound("occ08.greaterThan=" + DEFAULT_OCC_08);

        // Get all the popularTimeList where occ08 is greater than SMALLER_OCC_08
        defaultPopularTimeShouldBeFound("occ08.greaterThan=" + SMALLER_OCC_08);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 equals to DEFAULT_OCC_09
        defaultPopularTimeShouldBeFound("occ09.equals=" + DEFAULT_OCC_09);

        // Get all the popularTimeList where occ09 equals to UPDATED_OCC_09
        defaultPopularTimeShouldNotBeFound("occ09.equals=" + UPDATED_OCC_09);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 not equals to DEFAULT_OCC_09
        defaultPopularTimeShouldNotBeFound("occ09.notEquals=" + DEFAULT_OCC_09);

        // Get all the popularTimeList where occ09 not equals to UPDATED_OCC_09
        defaultPopularTimeShouldBeFound("occ09.notEquals=" + UPDATED_OCC_09);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 in DEFAULT_OCC_09 or UPDATED_OCC_09
        defaultPopularTimeShouldBeFound("occ09.in=" + DEFAULT_OCC_09 + "," + UPDATED_OCC_09);

        // Get all the popularTimeList where occ09 equals to UPDATED_OCC_09
        defaultPopularTimeShouldNotBeFound("occ09.in=" + UPDATED_OCC_09);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 is not null
        defaultPopularTimeShouldBeFound("occ09.specified=true");

        // Get all the popularTimeList where occ09 is null
        defaultPopularTimeShouldNotBeFound("occ09.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 is greater than or equal to DEFAULT_OCC_09
        defaultPopularTimeShouldBeFound("occ09.greaterThanOrEqual=" + DEFAULT_OCC_09);

        // Get all the popularTimeList where occ09 is greater than or equal to (DEFAULT_OCC_09 + 1)
        defaultPopularTimeShouldNotBeFound("occ09.greaterThanOrEqual=" + (DEFAULT_OCC_09 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 is less than or equal to DEFAULT_OCC_09
        defaultPopularTimeShouldBeFound("occ09.lessThanOrEqual=" + DEFAULT_OCC_09);

        // Get all the popularTimeList where occ09 is less than or equal to SMALLER_OCC_09
        defaultPopularTimeShouldNotBeFound("occ09.lessThanOrEqual=" + SMALLER_OCC_09);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 is less than DEFAULT_OCC_09
        defaultPopularTimeShouldNotBeFound("occ09.lessThan=" + DEFAULT_OCC_09);

        // Get all the popularTimeList where occ09 is less than (DEFAULT_OCC_09 + 1)
        defaultPopularTimeShouldBeFound("occ09.lessThan=" + (DEFAULT_OCC_09 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc09IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ09 is greater than DEFAULT_OCC_09
        defaultPopularTimeShouldNotBeFound("occ09.greaterThan=" + DEFAULT_OCC_09);

        // Get all the popularTimeList where occ09 is greater than SMALLER_OCC_09
        defaultPopularTimeShouldBeFound("occ09.greaterThan=" + SMALLER_OCC_09);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 equals to DEFAULT_OCC_10
        defaultPopularTimeShouldBeFound("occ10.equals=" + DEFAULT_OCC_10);

        // Get all the popularTimeList where occ10 equals to UPDATED_OCC_10
        defaultPopularTimeShouldNotBeFound("occ10.equals=" + UPDATED_OCC_10);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 not equals to DEFAULT_OCC_10
        defaultPopularTimeShouldNotBeFound("occ10.notEquals=" + DEFAULT_OCC_10);

        // Get all the popularTimeList where occ10 not equals to UPDATED_OCC_10
        defaultPopularTimeShouldBeFound("occ10.notEquals=" + UPDATED_OCC_10);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 in DEFAULT_OCC_10 or UPDATED_OCC_10
        defaultPopularTimeShouldBeFound("occ10.in=" + DEFAULT_OCC_10 + "," + UPDATED_OCC_10);

        // Get all the popularTimeList where occ10 equals to UPDATED_OCC_10
        defaultPopularTimeShouldNotBeFound("occ10.in=" + UPDATED_OCC_10);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 is not null
        defaultPopularTimeShouldBeFound("occ10.specified=true");

        // Get all the popularTimeList where occ10 is null
        defaultPopularTimeShouldNotBeFound("occ10.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 is greater than or equal to DEFAULT_OCC_10
        defaultPopularTimeShouldBeFound("occ10.greaterThanOrEqual=" + DEFAULT_OCC_10);

        // Get all the popularTimeList where occ10 is greater than or equal to (DEFAULT_OCC_10 + 1)
        defaultPopularTimeShouldNotBeFound("occ10.greaterThanOrEqual=" + (DEFAULT_OCC_10 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 is less than or equal to DEFAULT_OCC_10
        defaultPopularTimeShouldBeFound("occ10.lessThanOrEqual=" + DEFAULT_OCC_10);

        // Get all the popularTimeList where occ10 is less than or equal to SMALLER_OCC_10
        defaultPopularTimeShouldNotBeFound("occ10.lessThanOrEqual=" + SMALLER_OCC_10);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 is less than DEFAULT_OCC_10
        defaultPopularTimeShouldNotBeFound("occ10.lessThan=" + DEFAULT_OCC_10);

        // Get all the popularTimeList where occ10 is less than (DEFAULT_OCC_10 + 1)
        defaultPopularTimeShouldBeFound("occ10.lessThan=" + (DEFAULT_OCC_10 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc10IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ10 is greater than DEFAULT_OCC_10
        defaultPopularTimeShouldNotBeFound("occ10.greaterThan=" + DEFAULT_OCC_10);

        // Get all the popularTimeList where occ10 is greater than SMALLER_OCC_10
        defaultPopularTimeShouldBeFound("occ10.greaterThan=" + SMALLER_OCC_10);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 equals to DEFAULT_OCC_11
        defaultPopularTimeShouldBeFound("occ11.equals=" + DEFAULT_OCC_11);

        // Get all the popularTimeList where occ11 equals to UPDATED_OCC_11
        defaultPopularTimeShouldNotBeFound("occ11.equals=" + UPDATED_OCC_11);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 not equals to DEFAULT_OCC_11
        defaultPopularTimeShouldNotBeFound("occ11.notEquals=" + DEFAULT_OCC_11);

        // Get all the popularTimeList where occ11 not equals to UPDATED_OCC_11
        defaultPopularTimeShouldBeFound("occ11.notEquals=" + UPDATED_OCC_11);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 in DEFAULT_OCC_11 or UPDATED_OCC_11
        defaultPopularTimeShouldBeFound("occ11.in=" + DEFAULT_OCC_11 + "," + UPDATED_OCC_11);

        // Get all the popularTimeList where occ11 equals to UPDATED_OCC_11
        defaultPopularTimeShouldNotBeFound("occ11.in=" + UPDATED_OCC_11);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 is not null
        defaultPopularTimeShouldBeFound("occ11.specified=true");

        // Get all the popularTimeList where occ11 is null
        defaultPopularTimeShouldNotBeFound("occ11.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 is greater than or equal to DEFAULT_OCC_11
        defaultPopularTimeShouldBeFound("occ11.greaterThanOrEqual=" + DEFAULT_OCC_11);

        // Get all the popularTimeList where occ11 is greater than or equal to (DEFAULT_OCC_11 + 1)
        defaultPopularTimeShouldNotBeFound("occ11.greaterThanOrEqual=" + (DEFAULT_OCC_11 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 is less than or equal to DEFAULT_OCC_11
        defaultPopularTimeShouldBeFound("occ11.lessThanOrEqual=" + DEFAULT_OCC_11);

        // Get all the popularTimeList where occ11 is less than or equal to SMALLER_OCC_11
        defaultPopularTimeShouldNotBeFound("occ11.lessThanOrEqual=" + SMALLER_OCC_11);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 is less than DEFAULT_OCC_11
        defaultPopularTimeShouldNotBeFound("occ11.lessThan=" + DEFAULT_OCC_11);

        // Get all the popularTimeList where occ11 is less than (DEFAULT_OCC_11 + 1)
        defaultPopularTimeShouldBeFound("occ11.lessThan=" + (DEFAULT_OCC_11 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc11IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ11 is greater than DEFAULT_OCC_11
        defaultPopularTimeShouldNotBeFound("occ11.greaterThan=" + DEFAULT_OCC_11);

        // Get all the popularTimeList where occ11 is greater than SMALLER_OCC_11
        defaultPopularTimeShouldBeFound("occ11.greaterThan=" + SMALLER_OCC_11);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 equals to DEFAULT_OCC_12
        defaultPopularTimeShouldBeFound("occ12.equals=" + DEFAULT_OCC_12);

        // Get all the popularTimeList where occ12 equals to UPDATED_OCC_12
        defaultPopularTimeShouldNotBeFound("occ12.equals=" + UPDATED_OCC_12);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 not equals to DEFAULT_OCC_12
        defaultPopularTimeShouldNotBeFound("occ12.notEquals=" + DEFAULT_OCC_12);

        // Get all the popularTimeList where occ12 not equals to UPDATED_OCC_12
        defaultPopularTimeShouldBeFound("occ12.notEquals=" + UPDATED_OCC_12);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 in DEFAULT_OCC_12 or UPDATED_OCC_12
        defaultPopularTimeShouldBeFound("occ12.in=" + DEFAULT_OCC_12 + "," + UPDATED_OCC_12);

        // Get all the popularTimeList where occ12 equals to UPDATED_OCC_12
        defaultPopularTimeShouldNotBeFound("occ12.in=" + UPDATED_OCC_12);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 is not null
        defaultPopularTimeShouldBeFound("occ12.specified=true");

        // Get all the popularTimeList where occ12 is null
        defaultPopularTimeShouldNotBeFound("occ12.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 is greater than or equal to DEFAULT_OCC_12
        defaultPopularTimeShouldBeFound("occ12.greaterThanOrEqual=" + DEFAULT_OCC_12);

        // Get all the popularTimeList where occ12 is greater than or equal to (DEFAULT_OCC_12 + 1)
        defaultPopularTimeShouldNotBeFound("occ12.greaterThanOrEqual=" + (DEFAULT_OCC_12 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 is less than or equal to DEFAULT_OCC_12
        defaultPopularTimeShouldBeFound("occ12.lessThanOrEqual=" + DEFAULT_OCC_12);

        // Get all the popularTimeList where occ12 is less than or equal to SMALLER_OCC_12
        defaultPopularTimeShouldNotBeFound("occ12.lessThanOrEqual=" + SMALLER_OCC_12);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 is less than DEFAULT_OCC_12
        defaultPopularTimeShouldNotBeFound("occ12.lessThan=" + DEFAULT_OCC_12);

        // Get all the popularTimeList where occ12 is less than (DEFAULT_OCC_12 + 1)
        defaultPopularTimeShouldBeFound("occ12.lessThan=" + (DEFAULT_OCC_12 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc12IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ12 is greater than DEFAULT_OCC_12
        defaultPopularTimeShouldNotBeFound("occ12.greaterThan=" + DEFAULT_OCC_12);

        // Get all the popularTimeList where occ12 is greater than SMALLER_OCC_12
        defaultPopularTimeShouldBeFound("occ12.greaterThan=" + SMALLER_OCC_12);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 equals to DEFAULT_OCC_13
        defaultPopularTimeShouldBeFound("occ13.equals=" + DEFAULT_OCC_13);

        // Get all the popularTimeList where occ13 equals to UPDATED_OCC_13
        defaultPopularTimeShouldNotBeFound("occ13.equals=" + UPDATED_OCC_13);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 not equals to DEFAULT_OCC_13
        defaultPopularTimeShouldNotBeFound("occ13.notEquals=" + DEFAULT_OCC_13);

        // Get all the popularTimeList where occ13 not equals to UPDATED_OCC_13
        defaultPopularTimeShouldBeFound("occ13.notEquals=" + UPDATED_OCC_13);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 in DEFAULT_OCC_13 or UPDATED_OCC_13
        defaultPopularTimeShouldBeFound("occ13.in=" + DEFAULT_OCC_13 + "," + UPDATED_OCC_13);

        // Get all the popularTimeList where occ13 equals to UPDATED_OCC_13
        defaultPopularTimeShouldNotBeFound("occ13.in=" + UPDATED_OCC_13);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 is not null
        defaultPopularTimeShouldBeFound("occ13.specified=true");

        // Get all the popularTimeList where occ13 is null
        defaultPopularTimeShouldNotBeFound("occ13.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 is greater than or equal to DEFAULT_OCC_13
        defaultPopularTimeShouldBeFound("occ13.greaterThanOrEqual=" + DEFAULT_OCC_13);

        // Get all the popularTimeList where occ13 is greater than or equal to (DEFAULT_OCC_13 + 1)
        defaultPopularTimeShouldNotBeFound("occ13.greaterThanOrEqual=" + (DEFAULT_OCC_13 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 is less than or equal to DEFAULT_OCC_13
        defaultPopularTimeShouldBeFound("occ13.lessThanOrEqual=" + DEFAULT_OCC_13);

        // Get all the popularTimeList where occ13 is less than or equal to SMALLER_OCC_13
        defaultPopularTimeShouldNotBeFound("occ13.lessThanOrEqual=" + SMALLER_OCC_13);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 is less than DEFAULT_OCC_13
        defaultPopularTimeShouldNotBeFound("occ13.lessThan=" + DEFAULT_OCC_13);

        // Get all the popularTimeList where occ13 is less than (DEFAULT_OCC_13 + 1)
        defaultPopularTimeShouldBeFound("occ13.lessThan=" + (DEFAULT_OCC_13 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc13IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ13 is greater than DEFAULT_OCC_13
        defaultPopularTimeShouldNotBeFound("occ13.greaterThan=" + DEFAULT_OCC_13);

        // Get all the popularTimeList where occ13 is greater than SMALLER_OCC_13
        defaultPopularTimeShouldBeFound("occ13.greaterThan=" + SMALLER_OCC_13);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 equals to DEFAULT_OCC_14
        defaultPopularTimeShouldBeFound("occ14.equals=" + DEFAULT_OCC_14);

        // Get all the popularTimeList where occ14 equals to UPDATED_OCC_14
        defaultPopularTimeShouldNotBeFound("occ14.equals=" + UPDATED_OCC_14);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 not equals to DEFAULT_OCC_14
        defaultPopularTimeShouldNotBeFound("occ14.notEquals=" + DEFAULT_OCC_14);

        // Get all the popularTimeList where occ14 not equals to UPDATED_OCC_14
        defaultPopularTimeShouldBeFound("occ14.notEquals=" + UPDATED_OCC_14);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 in DEFAULT_OCC_14 or UPDATED_OCC_14
        defaultPopularTimeShouldBeFound("occ14.in=" + DEFAULT_OCC_14 + "," + UPDATED_OCC_14);

        // Get all the popularTimeList where occ14 equals to UPDATED_OCC_14
        defaultPopularTimeShouldNotBeFound("occ14.in=" + UPDATED_OCC_14);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 is not null
        defaultPopularTimeShouldBeFound("occ14.specified=true");

        // Get all the popularTimeList where occ14 is null
        defaultPopularTimeShouldNotBeFound("occ14.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 is greater than or equal to DEFAULT_OCC_14
        defaultPopularTimeShouldBeFound("occ14.greaterThanOrEqual=" + DEFAULT_OCC_14);

        // Get all the popularTimeList where occ14 is greater than or equal to (DEFAULT_OCC_14 + 1)
        defaultPopularTimeShouldNotBeFound("occ14.greaterThanOrEqual=" + (DEFAULT_OCC_14 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 is less than or equal to DEFAULT_OCC_14
        defaultPopularTimeShouldBeFound("occ14.lessThanOrEqual=" + DEFAULT_OCC_14);

        // Get all the popularTimeList where occ14 is less than or equal to SMALLER_OCC_14
        defaultPopularTimeShouldNotBeFound("occ14.lessThanOrEqual=" + SMALLER_OCC_14);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 is less than DEFAULT_OCC_14
        defaultPopularTimeShouldNotBeFound("occ14.lessThan=" + DEFAULT_OCC_14);

        // Get all the popularTimeList where occ14 is less than (DEFAULT_OCC_14 + 1)
        defaultPopularTimeShouldBeFound("occ14.lessThan=" + (DEFAULT_OCC_14 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc14IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ14 is greater than DEFAULT_OCC_14
        defaultPopularTimeShouldNotBeFound("occ14.greaterThan=" + DEFAULT_OCC_14);

        // Get all the popularTimeList where occ14 is greater than SMALLER_OCC_14
        defaultPopularTimeShouldBeFound("occ14.greaterThan=" + SMALLER_OCC_14);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 equals to DEFAULT_OCC_15
        defaultPopularTimeShouldBeFound("occ15.equals=" + DEFAULT_OCC_15);

        // Get all the popularTimeList where occ15 equals to UPDATED_OCC_15
        defaultPopularTimeShouldNotBeFound("occ15.equals=" + UPDATED_OCC_15);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 not equals to DEFAULT_OCC_15
        defaultPopularTimeShouldNotBeFound("occ15.notEquals=" + DEFAULT_OCC_15);

        // Get all the popularTimeList where occ15 not equals to UPDATED_OCC_15
        defaultPopularTimeShouldBeFound("occ15.notEquals=" + UPDATED_OCC_15);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 in DEFAULT_OCC_15 or UPDATED_OCC_15
        defaultPopularTimeShouldBeFound("occ15.in=" + DEFAULT_OCC_15 + "," + UPDATED_OCC_15);

        // Get all the popularTimeList where occ15 equals to UPDATED_OCC_15
        defaultPopularTimeShouldNotBeFound("occ15.in=" + UPDATED_OCC_15);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 is not null
        defaultPopularTimeShouldBeFound("occ15.specified=true");

        // Get all the popularTimeList where occ15 is null
        defaultPopularTimeShouldNotBeFound("occ15.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 is greater than or equal to DEFAULT_OCC_15
        defaultPopularTimeShouldBeFound("occ15.greaterThanOrEqual=" + DEFAULT_OCC_15);

        // Get all the popularTimeList where occ15 is greater than or equal to (DEFAULT_OCC_15 + 1)
        defaultPopularTimeShouldNotBeFound("occ15.greaterThanOrEqual=" + (DEFAULT_OCC_15 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 is less than or equal to DEFAULT_OCC_15
        defaultPopularTimeShouldBeFound("occ15.lessThanOrEqual=" + DEFAULT_OCC_15);

        // Get all the popularTimeList where occ15 is less than or equal to SMALLER_OCC_15
        defaultPopularTimeShouldNotBeFound("occ15.lessThanOrEqual=" + SMALLER_OCC_15);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 is less than DEFAULT_OCC_15
        defaultPopularTimeShouldNotBeFound("occ15.lessThan=" + DEFAULT_OCC_15);

        // Get all the popularTimeList where occ15 is less than (DEFAULT_OCC_15 + 1)
        defaultPopularTimeShouldBeFound("occ15.lessThan=" + (DEFAULT_OCC_15 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc15IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ15 is greater than DEFAULT_OCC_15
        defaultPopularTimeShouldNotBeFound("occ15.greaterThan=" + DEFAULT_OCC_15);

        // Get all the popularTimeList where occ15 is greater than SMALLER_OCC_15
        defaultPopularTimeShouldBeFound("occ15.greaterThan=" + SMALLER_OCC_15);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 equals to DEFAULT_OCC_16
        defaultPopularTimeShouldBeFound("occ16.equals=" + DEFAULT_OCC_16);

        // Get all the popularTimeList where occ16 equals to UPDATED_OCC_16
        defaultPopularTimeShouldNotBeFound("occ16.equals=" + UPDATED_OCC_16);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 not equals to DEFAULT_OCC_16
        defaultPopularTimeShouldNotBeFound("occ16.notEquals=" + DEFAULT_OCC_16);

        // Get all the popularTimeList where occ16 not equals to UPDATED_OCC_16
        defaultPopularTimeShouldBeFound("occ16.notEquals=" + UPDATED_OCC_16);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 in DEFAULT_OCC_16 or UPDATED_OCC_16
        defaultPopularTimeShouldBeFound("occ16.in=" + DEFAULT_OCC_16 + "," + UPDATED_OCC_16);

        // Get all the popularTimeList where occ16 equals to UPDATED_OCC_16
        defaultPopularTimeShouldNotBeFound("occ16.in=" + UPDATED_OCC_16);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 is not null
        defaultPopularTimeShouldBeFound("occ16.specified=true");

        // Get all the popularTimeList where occ16 is null
        defaultPopularTimeShouldNotBeFound("occ16.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 is greater than or equal to DEFAULT_OCC_16
        defaultPopularTimeShouldBeFound("occ16.greaterThanOrEqual=" + DEFAULT_OCC_16);

        // Get all the popularTimeList where occ16 is greater than or equal to (DEFAULT_OCC_16 + 1)
        defaultPopularTimeShouldNotBeFound("occ16.greaterThanOrEqual=" + (DEFAULT_OCC_16 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 is less than or equal to DEFAULT_OCC_16
        defaultPopularTimeShouldBeFound("occ16.lessThanOrEqual=" + DEFAULT_OCC_16);

        // Get all the popularTimeList where occ16 is less than or equal to SMALLER_OCC_16
        defaultPopularTimeShouldNotBeFound("occ16.lessThanOrEqual=" + SMALLER_OCC_16);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 is less than DEFAULT_OCC_16
        defaultPopularTimeShouldNotBeFound("occ16.lessThan=" + DEFAULT_OCC_16);

        // Get all the popularTimeList where occ16 is less than (DEFAULT_OCC_16 + 1)
        defaultPopularTimeShouldBeFound("occ16.lessThan=" + (DEFAULT_OCC_16 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc16IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ16 is greater than DEFAULT_OCC_16
        defaultPopularTimeShouldNotBeFound("occ16.greaterThan=" + DEFAULT_OCC_16);

        // Get all the popularTimeList where occ16 is greater than SMALLER_OCC_16
        defaultPopularTimeShouldBeFound("occ16.greaterThan=" + SMALLER_OCC_16);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 equals to DEFAULT_OCC_17
        defaultPopularTimeShouldBeFound("occ17.equals=" + DEFAULT_OCC_17);

        // Get all the popularTimeList where occ17 equals to UPDATED_OCC_17
        defaultPopularTimeShouldNotBeFound("occ17.equals=" + UPDATED_OCC_17);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 not equals to DEFAULT_OCC_17
        defaultPopularTimeShouldNotBeFound("occ17.notEquals=" + DEFAULT_OCC_17);

        // Get all the popularTimeList where occ17 not equals to UPDATED_OCC_17
        defaultPopularTimeShouldBeFound("occ17.notEquals=" + UPDATED_OCC_17);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 in DEFAULT_OCC_17 or UPDATED_OCC_17
        defaultPopularTimeShouldBeFound("occ17.in=" + DEFAULT_OCC_17 + "," + UPDATED_OCC_17);

        // Get all the popularTimeList where occ17 equals to UPDATED_OCC_17
        defaultPopularTimeShouldNotBeFound("occ17.in=" + UPDATED_OCC_17);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 is not null
        defaultPopularTimeShouldBeFound("occ17.specified=true");

        // Get all the popularTimeList where occ17 is null
        defaultPopularTimeShouldNotBeFound("occ17.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 is greater than or equal to DEFAULT_OCC_17
        defaultPopularTimeShouldBeFound("occ17.greaterThanOrEqual=" + DEFAULT_OCC_17);

        // Get all the popularTimeList where occ17 is greater than or equal to (DEFAULT_OCC_17 + 1)
        defaultPopularTimeShouldNotBeFound("occ17.greaterThanOrEqual=" + (DEFAULT_OCC_17 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 is less than or equal to DEFAULT_OCC_17
        defaultPopularTimeShouldBeFound("occ17.lessThanOrEqual=" + DEFAULT_OCC_17);

        // Get all the popularTimeList where occ17 is less than or equal to SMALLER_OCC_17
        defaultPopularTimeShouldNotBeFound("occ17.lessThanOrEqual=" + SMALLER_OCC_17);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 is less than DEFAULT_OCC_17
        defaultPopularTimeShouldNotBeFound("occ17.lessThan=" + DEFAULT_OCC_17);

        // Get all the popularTimeList where occ17 is less than (DEFAULT_OCC_17 + 1)
        defaultPopularTimeShouldBeFound("occ17.lessThan=" + (DEFAULT_OCC_17 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc17IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ17 is greater than DEFAULT_OCC_17
        defaultPopularTimeShouldNotBeFound("occ17.greaterThan=" + DEFAULT_OCC_17);

        // Get all the popularTimeList where occ17 is greater than SMALLER_OCC_17
        defaultPopularTimeShouldBeFound("occ17.greaterThan=" + SMALLER_OCC_17);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 equals to DEFAULT_OCC_18
        defaultPopularTimeShouldBeFound("occ18.equals=" + DEFAULT_OCC_18);

        // Get all the popularTimeList where occ18 equals to UPDATED_OCC_18
        defaultPopularTimeShouldNotBeFound("occ18.equals=" + UPDATED_OCC_18);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 not equals to DEFAULT_OCC_18
        defaultPopularTimeShouldNotBeFound("occ18.notEquals=" + DEFAULT_OCC_18);

        // Get all the popularTimeList where occ18 not equals to UPDATED_OCC_18
        defaultPopularTimeShouldBeFound("occ18.notEquals=" + UPDATED_OCC_18);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 in DEFAULT_OCC_18 or UPDATED_OCC_18
        defaultPopularTimeShouldBeFound("occ18.in=" + DEFAULT_OCC_18 + "," + UPDATED_OCC_18);

        // Get all the popularTimeList where occ18 equals to UPDATED_OCC_18
        defaultPopularTimeShouldNotBeFound("occ18.in=" + UPDATED_OCC_18);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 is not null
        defaultPopularTimeShouldBeFound("occ18.specified=true");

        // Get all the popularTimeList where occ18 is null
        defaultPopularTimeShouldNotBeFound("occ18.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 is greater than or equal to DEFAULT_OCC_18
        defaultPopularTimeShouldBeFound("occ18.greaterThanOrEqual=" + DEFAULT_OCC_18);

        // Get all the popularTimeList where occ18 is greater than or equal to (DEFAULT_OCC_18 + 1)
        defaultPopularTimeShouldNotBeFound("occ18.greaterThanOrEqual=" + (DEFAULT_OCC_18 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 is less than or equal to DEFAULT_OCC_18
        defaultPopularTimeShouldBeFound("occ18.lessThanOrEqual=" + DEFAULT_OCC_18);

        // Get all the popularTimeList where occ18 is less than or equal to SMALLER_OCC_18
        defaultPopularTimeShouldNotBeFound("occ18.lessThanOrEqual=" + SMALLER_OCC_18);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 is less than DEFAULT_OCC_18
        defaultPopularTimeShouldNotBeFound("occ18.lessThan=" + DEFAULT_OCC_18);

        // Get all the popularTimeList where occ18 is less than (DEFAULT_OCC_18 + 1)
        defaultPopularTimeShouldBeFound("occ18.lessThan=" + (DEFAULT_OCC_18 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc18IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ18 is greater than DEFAULT_OCC_18
        defaultPopularTimeShouldNotBeFound("occ18.greaterThan=" + DEFAULT_OCC_18);

        // Get all the popularTimeList where occ18 is greater than SMALLER_OCC_18
        defaultPopularTimeShouldBeFound("occ18.greaterThan=" + SMALLER_OCC_18);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 equals to DEFAULT_OCC_19
        defaultPopularTimeShouldBeFound("occ19.equals=" + DEFAULT_OCC_19);

        // Get all the popularTimeList where occ19 equals to UPDATED_OCC_19
        defaultPopularTimeShouldNotBeFound("occ19.equals=" + UPDATED_OCC_19);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 not equals to DEFAULT_OCC_19
        defaultPopularTimeShouldNotBeFound("occ19.notEquals=" + DEFAULT_OCC_19);

        // Get all the popularTimeList where occ19 not equals to UPDATED_OCC_19
        defaultPopularTimeShouldBeFound("occ19.notEquals=" + UPDATED_OCC_19);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 in DEFAULT_OCC_19 or UPDATED_OCC_19
        defaultPopularTimeShouldBeFound("occ19.in=" + DEFAULT_OCC_19 + "," + UPDATED_OCC_19);

        // Get all the popularTimeList where occ19 equals to UPDATED_OCC_19
        defaultPopularTimeShouldNotBeFound("occ19.in=" + UPDATED_OCC_19);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 is not null
        defaultPopularTimeShouldBeFound("occ19.specified=true");

        // Get all the popularTimeList where occ19 is null
        defaultPopularTimeShouldNotBeFound("occ19.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 is greater than or equal to DEFAULT_OCC_19
        defaultPopularTimeShouldBeFound("occ19.greaterThanOrEqual=" + DEFAULT_OCC_19);

        // Get all the popularTimeList where occ19 is greater than or equal to (DEFAULT_OCC_19 + 1)
        defaultPopularTimeShouldNotBeFound("occ19.greaterThanOrEqual=" + (DEFAULT_OCC_19 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 is less than or equal to DEFAULT_OCC_19
        defaultPopularTimeShouldBeFound("occ19.lessThanOrEqual=" + DEFAULT_OCC_19);

        // Get all the popularTimeList where occ19 is less than or equal to SMALLER_OCC_19
        defaultPopularTimeShouldNotBeFound("occ19.lessThanOrEqual=" + SMALLER_OCC_19);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 is less than DEFAULT_OCC_19
        defaultPopularTimeShouldNotBeFound("occ19.lessThan=" + DEFAULT_OCC_19);

        // Get all the popularTimeList where occ19 is less than (DEFAULT_OCC_19 + 1)
        defaultPopularTimeShouldBeFound("occ19.lessThan=" + (DEFAULT_OCC_19 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc19IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ19 is greater than DEFAULT_OCC_19
        defaultPopularTimeShouldNotBeFound("occ19.greaterThan=" + DEFAULT_OCC_19);

        // Get all the popularTimeList where occ19 is greater than SMALLER_OCC_19
        defaultPopularTimeShouldBeFound("occ19.greaterThan=" + SMALLER_OCC_19);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 equals to DEFAULT_OCC_20
        defaultPopularTimeShouldBeFound("occ20.equals=" + DEFAULT_OCC_20);

        // Get all the popularTimeList where occ20 equals to UPDATED_OCC_20
        defaultPopularTimeShouldNotBeFound("occ20.equals=" + UPDATED_OCC_20);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 not equals to DEFAULT_OCC_20
        defaultPopularTimeShouldNotBeFound("occ20.notEquals=" + DEFAULT_OCC_20);

        // Get all the popularTimeList where occ20 not equals to UPDATED_OCC_20
        defaultPopularTimeShouldBeFound("occ20.notEquals=" + UPDATED_OCC_20);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 in DEFAULT_OCC_20 or UPDATED_OCC_20
        defaultPopularTimeShouldBeFound("occ20.in=" + DEFAULT_OCC_20 + "," + UPDATED_OCC_20);

        // Get all the popularTimeList where occ20 equals to UPDATED_OCC_20
        defaultPopularTimeShouldNotBeFound("occ20.in=" + UPDATED_OCC_20);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 is not null
        defaultPopularTimeShouldBeFound("occ20.specified=true");

        // Get all the popularTimeList where occ20 is null
        defaultPopularTimeShouldNotBeFound("occ20.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 is greater than or equal to DEFAULT_OCC_20
        defaultPopularTimeShouldBeFound("occ20.greaterThanOrEqual=" + DEFAULT_OCC_20);

        // Get all the popularTimeList where occ20 is greater than or equal to (DEFAULT_OCC_20 + 1)
        defaultPopularTimeShouldNotBeFound("occ20.greaterThanOrEqual=" + (DEFAULT_OCC_20 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 is less than or equal to DEFAULT_OCC_20
        defaultPopularTimeShouldBeFound("occ20.lessThanOrEqual=" + DEFAULT_OCC_20);

        // Get all the popularTimeList where occ20 is less than or equal to SMALLER_OCC_20
        defaultPopularTimeShouldNotBeFound("occ20.lessThanOrEqual=" + SMALLER_OCC_20);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 is less than DEFAULT_OCC_20
        defaultPopularTimeShouldNotBeFound("occ20.lessThan=" + DEFAULT_OCC_20);

        // Get all the popularTimeList where occ20 is less than (DEFAULT_OCC_20 + 1)
        defaultPopularTimeShouldBeFound("occ20.lessThan=" + (DEFAULT_OCC_20 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc20IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ20 is greater than DEFAULT_OCC_20
        defaultPopularTimeShouldNotBeFound("occ20.greaterThan=" + DEFAULT_OCC_20);

        // Get all the popularTimeList where occ20 is greater than SMALLER_OCC_20
        defaultPopularTimeShouldBeFound("occ20.greaterThan=" + SMALLER_OCC_20);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 equals to DEFAULT_OCC_21
        defaultPopularTimeShouldBeFound("occ21.equals=" + DEFAULT_OCC_21);

        // Get all the popularTimeList where occ21 equals to UPDATED_OCC_21
        defaultPopularTimeShouldNotBeFound("occ21.equals=" + UPDATED_OCC_21);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 not equals to DEFAULT_OCC_21
        defaultPopularTimeShouldNotBeFound("occ21.notEquals=" + DEFAULT_OCC_21);

        // Get all the popularTimeList where occ21 not equals to UPDATED_OCC_21
        defaultPopularTimeShouldBeFound("occ21.notEquals=" + UPDATED_OCC_21);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 in DEFAULT_OCC_21 or UPDATED_OCC_21
        defaultPopularTimeShouldBeFound("occ21.in=" + DEFAULT_OCC_21 + "," + UPDATED_OCC_21);

        // Get all the popularTimeList where occ21 equals to UPDATED_OCC_21
        defaultPopularTimeShouldNotBeFound("occ21.in=" + UPDATED_OCC_21);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 is not null
        defaultPopularTimeShouldBeFound("occ21.specified=true");

        // Get all the popularTimeList where occ21 is null
        defaultPopularTimeShouldNotBeFound("occ21.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 is greater than or equal to DEFAULT_OCC_21
        defaultPopularTimeShouldBeFound("occ21.greaterThanOrEqual=" + DEFAULT_OCC_21);

        // Get all the popularTimeList where occ21 is greater than or equal to (DEFAULT_OCC_21 + 1)
        defaultPopularTimeShouldNotBeFound("occ21.greaterThanOrEqual=" + (DEFAULT_OCC_21 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 is less than or equal to DEFAULT_OCC_21
        defaultPopularTimeShouldBeFound("occ21.lessThanOrEqual=" + DEFAULT_OCC_21);

        // Get all the popularTimeList where occ21 is less than or equal to SMALLER_OCC_21
        defaultPopularTimeShouldNotBeFound("occ21.lessThanOrEqual=" + SMALLER_OCC_21);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 is less than DEFAULT_OCC_21
        defaultPopularTimeShouldNotBeFound("occ21.lessThan=" + DEFAULT_OCC_21);

        // Get all the popularTimeList where occ21 is less than (DEFAULT_OCC_21 + 1)
        defaultPopularTimeShouldBeFound("occ21.lessThan=" + (DEFAULT_OCC_21 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc21IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ21 is greater than DEFAULT_OCC_21
        defaultPopularTimeShouldNotBeFound("occ21.greaterThan=" + DEFAULT_OCC_21);

        // Get all the popularTimeList where occ21 is greater than SMALLER_OCC_21
        defaultPopularTimeShouldBeFound("occ21.greaterThan=" + SMALLER_OCC_21);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 equals to DEFAULT_OCC_22
        defaultPopularTimeShouldBeFound("occ22.equals=" + DEFAULT_OCC_22);

        // Get all the popularTimeList where occ22 equals to UPDATED_OCC_22
        defaultPopularTimeShouldNotBeFound("occ22.equals=" + UPDATED_OCC_22);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 not equals to DEFAULT_OCC_22
        defaultPopularTimeShouldNotBeFound("occ22.notEquals=" + DEFAULT_OCC_22);

        // Get all the popularTimeList where occ22 not equals to UPDATED_OCC_22
        defaultPopularTimeShouldBeFound("occ22.notEquals=" + UPDATED_OCC_22);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 in DEFAULT_OCC_22 or UPDATED_OCC_22
        defaultPopularTimeShouldBeFound("occ22.in=" + DEFAULT_OCC_22 + "," + UPDATED_OCC_22);

        // Get all the popularTimeList where occ22 equals to UPDATED_OCC_22
        defaultPopularTimeShouldNotBeFound("occ22.in=" + UPDATED_OCC_22);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 is not null
        defaultPopularTimeShouldBeFound("occ22.specified=true");

        // Get all the popularTimeList where occ22 is null
        defaultPopularTimeShouldNotBeFound("occ22.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 is greater than or equal to DEFAULT_OCC_22
        defaultPopularTimeShouldBeFound("occ22.greaterThanOrEqual=" + DEFAULT_OCC_22);

        // Get all the popularTimeList where occ22 is greater than or equal to (DEFAULT_OCC_22 + 1)
        defaultPopularTimeShouldNotBeFound("occ22.greaterThanOrEqual=" + (DEFAULT_OCC_22 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 is less than or equal to DEFAULT_OCC_22
        defaultPopularTimeShouldBeFound("occ22.lessThanOrEqual=" + DEFAULT_OCC_22);

        // Get all the popularTimeList where occ22 is less than or equal to SMALLER_OCC_22
        defaultPopularTimeShouldNotBeFound("occ22.lessThanOrEqual=" + SMALLER_OCC_22);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 is less than DEFAULT_OCC_22
        defaultPopularTimeShouldNotBeFound("occ22.lessThan=" + DEFAULT_OCC_22);

        // Get all the popularTimeList where occ22 is less than (DEFAULT_OCC_22 + 1)
        defaultPopularTimeShouldBeFound("occ22.lessThan=" + (DEFAULT_OCC_22 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc22IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ22 is greater than DEFAULT_OCC_22
        defaultPopularTimeShouldNotBeFound("occ22.greaterThan=" + DEFAULT_OCC_22);

        // Get all the popularTimeList where occ22 is greater than SMALLER_OCC_22
        defaultPopularTimeShouldBeFound("occ22.greaterThan=" + SMALLER_OCC_22);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 equals to DEFAULT_OCC_23
        defaultPopularTimeShouldBeFound("occ23.equals=" + DEFAULT_OCC_23);

        // Get all the popularTimeList where occ23 equals to UPDATED_OCC_23
        defaultPopularTimeShouldNotBeFound("occ23.equals=" + UPDATED_OCC_23);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsNotEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 not equals to DEFAULT_OCC_23
        defaultPopularTimeShouldNotBeFound("occ23.notEquals=" + DEFAULT_OCC_23);

        // Get all the popularTimeList where occ23 not equals to UPDATED_OCC_23
        defaultPopularTimeShouldBeFound("occ23.notEquals=" + UPDATED_OCC_23);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsInShouldWork() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 in DEFAULT_OCC_23 or UPDATED_OCC_23
        defaultPopularTimeShouldBeFound("occ23.in=" + DEFAULT_OCC_23 + "," + UPDATED_OCC_23);

        // Get all the popularTimeList where occ23 equals to UPDATED_OCC_23
        defaultPopularTimeShouldNotBeFound("occ23.in=" + UPDATED_OCC_23);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsNullOrNotNull() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 is not null
        defaultPopularTimeShouldBeFound("occ23.specified=true");

        // Get all the popularTimeList where occ23 is null
        defaultPopularTimeShouldNotBeFound("occ23.specified=false");
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 is greater than or equal to DEFAULT_OCC_23
        defaultPopularTimeShouldBeFound("occ23.greaterThanOrEqual=" + DEFAULT_OCC_23);

        // Get all the popularTimeList where occ23 is greater than or equal to (DEFAULT_OCC_23 + 1)
        defaultPopularTimeShouldNotBeFound("occ23.greaterThanOrEqual=" + (DEFAULT_OCC_23 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 is less than or equal to DEFAULT_OCC_23
        defaultPopularTimeShouldBeFound("occ23.lessThanOrEqual=" + DEFAULT_OCC_23);

        // Get all the popularTimeList where occ23 is less than or equal to SMALLER_OCC_23
        defaultPopularTimeShouldNotBeFound("occ23.lessThanOrEqual=" + SMALLER_OCC_23);
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsLessThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 is less than DEFAULT_OCC_23
        defaultPopularTimeShouldNotBeFound("occ23.lessThan=" + DEFAULT_OCC_23);

        // Get all the popularTimeList where occ23 is less than (DEFAULT_OCC_23 + 1)
        defaultPopularTimeShouldBeFound("occ23.lessThan=" + (DEFAULT_OCC_23 + 1));
    }

    @Test
    @Transactional
    public void getAllPopularTimesByOcc23IsGreaterThanSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        // Get all the popularTimeList where occ23 is greater than DEFAULT_OCC_23
        defaultPopularTimeShouldNotBeFound("occ23.greaterThan=" + DEFAULT_OCC_23);

        // Get all the popularTimeList where occ23 is greater than SMALLER_OCC_23
        defaultPopularTimeShouldBeFound("occ23.greaterThan=" + SMALLER_OCC_23);
    }


    @Test
    @Transactional
    public void getAllPopularTimesByRestaurantIsEqualToSomething() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);
        Restaurant restaurant = RestaurantResourceIT.createEntity(em);
        em.persist(restaurant);
        em.flush();
        popularTime.setRestaurant(restaurant);
        popularTimeRepository.saveAndFlush(popularTime);
        Long restaurantId = restaurant.getId();

        // Get all the popularTimeList where restaurant equals to restaurantId
        defaultPopularTimeShouldBeFound("restaurantId.equals=" + restaurantId);

        // Get all the popularTimeList where restaurant equals to restaurantId + 1
        defaultPopularTimeShouldNotBeFound("restaurantId.equals=" + (restaurantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPopularTimeShouldBeFound(String filter) throws Exception {
        restPopularTimeMockMvc.perform(get("/api/popular-times?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popularTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].occ06").value(hasItem(DEFAULT_OCC_06)))
            .andExpect(jsonPath("$.[*].occ07").value(hasItem(DEFAULT_OCC_07)))
            .andExpect(jsonPath("$.[*].occ08").value(hasItem(DEFAULT_OCC_08)))
            .andExpect(jsonPath("$.[*].occ09").value(hasItem(DEFAULT_OCC_09)))
            .andExpect(jsonPath("$.[*].occ10").value(hasItem(DEFAULT_OCC_10)))
            .andExpect(jsonPath("$.[*].occ11").value(hasItem(DEFAULT_OCC_11)))
            .andExpect(jsonPath("$.[*].occ12").value(hasItem(DEFAULT_OCC_12)))
            .andExpect(jsonPath("$.[*].occ13").value(hasItem(DEFAULT_OCC_13)))
            .andExpect(jsonPath("$.[*].occ14").value(hasItem(DEFAULT_OCC_14)))
            .andExpect(jsonPath("$.[*].occ15").value(hasItem(DEFAULT_OCC_15)))
            .andExpect(jsonPath("$.[*].occ16").value(hasItem(DEFAULT_OCC_16)))
            .andExpect(jsonPath("$.[*].occ17").value(hasItem(DEFAULT_OCC_17)))
            .andExpect(jsonPath("$.[*].occ18").value(hasItem(DEFAULT_OCC_18)))
            .andExpect(jsonPath("$.[*].occ19").value(hasItem(DEFAULT_OCC_19)))
            .andExpect(jsonPath("$.[*].occ20").value(hasItem(DEFAULT_OCC_20)))
            .andExpect(jsonPath("$.[*].occ21").value(hasItem(DEFAULT_OCC_21)))
            .andExpect(jsonPath("$.[*].occ22").value(hasItem(DEFAULT_OCC_22)))
            .andExpect(jsonPath("$.[*].occ23").value(hasItem(DEFAULT_OCC_23)));

        // Check, that the count call also returns 1
        restPopularTimeMockMvc.perform(get("/api/popular-times/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPopularTimeShouldNotBeFound(String filter) throws Exception {
        restPopularTimeMockMvc.perform(get("/api/popular-times?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPopularTimeMockMvc.perform(get("/api/popular-times/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPopularTime() throws Exception {
        // Get the popularTime
        restPopularTimeMockMvc.perform(get("/api/popular-times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopularTime() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        int databaseSizeBeforeUpdate = popularTimeRepository.findAll().size();

        // Update the popularTime
        PopularTime updatedPopularTime = popularTimeRepository.findById(popularTime.getId()).get();
        // Disconnect from session so that the updates on updatedPopularTime are not directly saved in db
        em.detach(updatedPopularTime);
        updatedPopularTime
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .occ06(UPDATED_OCC_06)
            .occ07(UPDATED_OCC_07)
            .occ08(UPDATED_OCC_08)
            .occ09(UPDATED_OCC_09)
            .occ10(UPDATED_OCC_10)
            .occ11(UPDATED_OCC_11)
            .occ12(UPDATED_OCC_12)
            .occ13(UPDATED_OCC_13)
            .occ14(UPDATED_OCC_14)
            .occ15(UPDATED_OCC_15)
            .occ16(UPDATED_OCC_16)
            .occ17(UPDATED_OCC_17)
            .occ18(UPDATED_OCC_18)
            .occ19(UPDATED_OCC_19)
            .occ20(UPDATED_OCC_20)
            .occ21(UPDATED_OCC_21)
            .occ22(UPDATED_OCC_22)
            .occ23(UPDATED_OCC_23);
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(updatedPopularTime);

        restPopularTimeMockMvc.perform(put("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isOk());

        // Validate the PopularTime in the database
        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeUpdate);
        PopularTime testPopularTime = popularTimeList.get(popularTimeList.size() - 1);
        assertThat(testPopularTime.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testPopularTime.getOcc06()).isEqualTo(UPDATED_OCC_06);
        assertThat(testPopularTime.getOcc07()).isEqualTo(UPDATED_OCC_07);
        assertThat(testPopularTime.getOcc08()).isEqualTo(UPDATED_OCC_08);
        assertThat(testPopularTime.getOcc09()).isEqualTo(UPDATED_OCC_09);
        assertThat(testPopularTime.getOcc10()).isEqualTo(UPDATED_OCC_10);
        assertThat(testPopularTime.getOcc11()).isEqualTo(UPDATED_OCC_11);
        assertThat(testPopularTime.getOcc12()).isEqualTo(UPDATED_OCC_12);
        assertThat(testPopularTime.getOcc13()).isEqualTo(UPDATED_OCC_13);
        assertThat(testPopularTime.getOcc14()).isEqualTo(UPDATED_OCC_14);
        assertThat(testPopularTime.getOcc15()).isEqualTo(UPDATED_OCC_15);
        assertThat(testPopularTime.getOcc16()).isEqualTo(UPDATED_OCC_16);
        assertThat(testPopularTime.getOcc17()).isEqualTo(UPDATED_OCC_17);
        assertThat(testPopularTime.getOcc18()).isEqualTo(UPDATED_OCC_18);
        assertThat(testPopularTime.getOcc19()).isEqualTo(UPDATED_OCC_19);
        assertThat(testPopularTime.getOcc20()).isEqualTo(UPDATED_OCC_20);
        assertThat(testPopularTime.getOcc21()).isEqualTo(UPDATED_OCC_21);
        assertThat(testPopularTime.getOcc22()).isEqualTo(UPDATED_OCC_22);
        assertThat(testPopularTime.getOcc23()).isEqualTo(UPDATED_OCC_23);

        // Validate the PopularTime in Elasticsearch
        verify(mockPopularTimeSearchRepository, times(1)).save(testPopularTime);
    }

    @Test
    @Transactional
    public void updateNonExistingPopularTime() throws Exception {
        int databaseSizeBeforeUpdate = popularTimeRepository.findAll().size();

        // Create the PopularTime
        PopularTimeDTO popularTimeDTO = popularTimeMapper.toDto(popularTime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPopularTimeMockMvc.perform(put("/api/popular-times")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PopularTime in the database
        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PopularTime in Elasticsearch
        verify(mockPopularTimeSearchRepository, times(0)).save(popularTime);
    }

    @Test
    @Transactional
    public void deletePopularTime() throws Exception {
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);

        int databaseSizeBeforeDelete = popularTimeRepository.findAll().size();

        // Delete the popularTime
        restPopularTimeMockMvc.perform(delete("/api/popular-times/{id}", popularTime.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PopularTime> popularTimeList = popularTimeRepository.findAll();
        assertThat(popularTimeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PopularTime in Elasticsearch
        verify(mockPopularTimeSearchRepository, times(1)).deleteById(popularTime.getId());
    }

    @Test
    @Transactional
    public void searchPopularTime() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        popularTimeRepository.saveAndFlush(popularTime);
        when(mockPopularTimeSearchRepository.search(queryStringQuery("id:" + popularTime.getId())))
            .thenReturn(Collections.singletonList(popularTime));

        // Search the popularTime
        restPopularTimeMockMvc.perform(get("/api/_search/popular-times?query=id:" + popularTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popularTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].occ06").value(hasItem(DEFAULT_OCC_06)))
            .andExpect(jsonPath("$.[*].occ07").value(hasItem(DEFAULT_OCC_07)))
            .andExpect(jsonPath("$.[*].occ08").value(hasItem(DEFAULT_OCC_08)))
            .andExpect(jsonPath("$.[*].occ09").value(hasItem(DEFAULT_OCC_09)))
            .andExpect(jsonPath("$.[*].occ10").value(hasItem(DEFAULT_OCC_10)))
            .andExpect(jsonPath("$.[*].occ11").value(hasItem(DEFAULT_OCC_11)))
            .andExpect(jsonPath("$.[*].occ12").value(hasItem(DEFAULT_OCC_12)))
            .andExpect(jsonPath("$.[*].occ13").value(hasItem(DEFAULT_OCC_13)))
            .andExpect(jsonPath("$.[*].occ14").value(hasItem(DEFAULT_OCC_14)))
            .andExpect(jsonPath("$.[*].occ15").value(hasItem(DEFAULT_OCC_15)))
            .andExpect(jsonPath("$.[*].occ16").value(hasItem(DEFAULT_OCC_16)))
            .andExpect(jsonPath("$.[*].occ17").value(hasItem(DEFAULT_OCC_17)))
            .andExpect(jsonPath("$.[*].occ18").value(hasItem(DEFAULT_OCC_18)))
            .andExpect(jsonPath("$.[*].occ19").value(hasItem(DEFAULT_OCC_19)))
            .andExpect(jsonPath("$.[*].occ20").value(hasItem(DEFAULT_OCC_20)))
            .andExpect(jsonPath("$.[*].occ21").value(hasItem(DEFAULT_OCC_21)))
            .andExpect(jsonPath("$.[*].occ22").value(hasItem(DEFAULT_OCC_22)))
            .andExpect(jsonPath("$.[*].occ23").value(hasItem(DEFAULT_OCC_23)));
    }
}
