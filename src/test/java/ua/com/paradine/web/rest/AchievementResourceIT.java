package ua.com.paradine.web.rest;

import ua.com.paradine.ParadineApp;
import ua.com.paradine.domain.Achievement;
import ua.com.paradine.repository.AchievementRepository;
import ua.com.paradine.repository.search.AchievementSearchRepository;
import ua.com.paradine.service.AchievementService;
import ua.com.paradine.service.dto.AchievementDTO;
import ua.com.paradine.service.mapper.AchievementMapper;
import ua.com.paradine.service.dto.AchievementCriteria;
import ua.com.paradine.service.AchievementQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
 * Integration tests for the {@link AchievementResource} REST controller.
 */
@SpringBootTest(classes = ParadineApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AchievementResourceIT {

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_UA = "AAAAAAAAAA";
    private static final String UPDATED_NAME_UA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_RU = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_RU = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_UA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_UA = "BBBBBBBBBB";

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private AchievementService achievementService;

    /**
     * This repository is mocked in the ua.com.paradine.repository.search test package.
     *
     * @see ua.com.paradine.repository.search.AchievementSearchRepositoryMockConfiguration
     */
    @Autowired
    private AchievementSearchRepository mockAchievementSearchRepository;

    @Autowired
    private AchievementQueryService achievementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAchievementMockMvc;

    private Achievement achievement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievement createEntity(EntityManager em) {
        Achievement achievement = new Achievement()
            .slug(DEFAULT_SLUG)
            .nameEn(DEFAULT_NAME_EN)
            .nameRu(DEFAULT_NAME_RU)
            .nameUa(DEFAULT_NAME_UA)
            .descriptionEn(DEFAULT_DESCRIPTION_EN)
            .descriptionRu(DEFAULT_DESCRIPTION_RU)
            .descriptionUa(DEFAULT_DESCRIPTION_UA);
        return achievement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievement createUpdatedEntity(EntityManager em) {
        Achievement achievement = new Achievement()
            .slug(UPDATED_SLUG)
            .nameEn(UPDATED_NAME_EN)
            .nameRu(UPDATED_NAME_RU)
            .nameUa(UPDATED_NAME_UA)
            .descriptionEn(UPDATED_DESCRIPTION_EN)
            .descriptionRu(UPDATED_DESCRIPTION_RU)
            .descriptionUa(UPDATED_DESCRIPTION_UA);
        return achievement;
    }

    @BeforeEach
    public void initTest() {
        achievement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchievement() throws Exception {
        int databaseSizeBeforeCreate = achievementRepository.findAll().size();
        // Create the Achievement
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);
        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isCreated());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeCreate + 1);
        Achievement testAchievement = achievementList.get(achievementList.size() - 1);
        assertThat(testAchievement.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testAchievement.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testAchievement.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testAchievement.getNameUa()).isEqualTo(DEFAULT_NAME_UA);
        assertThat(testAchievement.getDescriptionEn()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testAchievement.getDescriptionRu()).isEqualTo(DEFAULT_DESCRIPTION_RU);
        assertThat(testAchievement.getDescriptionUa()).isEqualTo(DEFAULT_DESCRIPTION_UA);

        // Validate the Achievement in Elasticsearch
        verify(mockAchievementSearchRepository, times(1)).save(testAchievement);
    }

    @Test
    @Transactional
    public void createAchievementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = achievementRepository.findAll().size();

        // Create the Achievement with an existing ID
        achievement.setId(1L);
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeCreate);

        // Validate the Achievement in Elasticsearch
        verify(mockAchievementSearchRepository, times(0)).save(achievement);
    }


    @Test
    @Transactional
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = achievementRepository.findAll().size();
        // set the field null
        achievement.setSlug(null);

        // Create the Achievement, which fails.
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);


        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isBadRequest());

        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = achievementRepository.findAll().size();
        // set the field null
        achievement.setNameEn(null);

        // Create the Achievement, which fails.
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);


        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isBadRequest());

        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAchievements() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList
        restAchievementMockMvc.perform(get("/api/achievements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievement.getId().intValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameUa").value(hasItem(DEFAULT_NAME_UA)))
            .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionRu").value(hasItem(DEFAULT_DESCRIPTION_RU)))
            .andExpect(jsonPath("$.[*].descriptionUa").value(hasItem(DEFAULT_DESCRIPTION_UA)));
    }
    
    @Test
    @Transactional
    public void getAchievement() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get the achievement
        restAchievementMockMvc.perform(get("/api/achievements/{id}", achievement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(achievement.getId().intValue()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameUa").value(DEFAULT_NAME_UA))
            .andExpect(jsonPath("$.descriptionEn").value(DEFAULT_DESCRIPTION_EN))
            .andExpect(jsonPath("$.descriptionRu").value(DEFAULT_DESCRIPTION_RU))
            .andExpect(jsonPath("$.descriptionUa").value(DEFAULT_DESCRIPTION_UA));
    }


    @Test
    @Transactional
    public void getAchievementsByIdFiltering() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        Long id = achievement.getId();

        defaultAchievementShouldBeFound("id.equals=" + id);
        defaultAchievementShouldNotBeFound("id.notEquals=" + id);

        defaultAchievementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAchievementShouldNotBeFound("id.greaterThan=" + id);

        defaultAchievementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAchievementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAchievementsBySlugIsEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where slug equals to DEFAULT_SLUG
        defaultAchievementShouldBeFound("slug.equals=" + DEFAULT_SLUG);

        // Get all the achievementList where slug equals to UPDATED_SLUG
        defaultAchievementShouldNotBeFound("slug.equals=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllAchievementsBySlugIsNotEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where slug not equals to DEFAULT_SLUG
        defaultAchievementShouldNotBeFound("slug.notEquals=" + DEFAULT_SLUG);

        // Get all the achievementList where slug not equals to UPDATED_SLUG
        defaultAchievementShouldBeFound("slug.notEquals=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllAchievementsBySlugIsInShouldWork() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where slug in DEFAULT_SLUG or UPDATED_SLUG
        defaultAchievementShouldBeFound("slug.in=" + DEFAULT_SLUG + "," + UPDATED_SLUG);

        // Get all the achievementList where slug equals to UPDATED_SLUG
        defaultAchievementShouldNotBeFound("slug.in=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllAchievementsBySlugIsNullOrNotNull() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where slug is not null
        defaultAchievementShouldBeFound("slug.specified=true");

        // Get all the achievementList where slug is null
        defaultAchievementShouldNotBeFound("slug.specified=false");
    }
                @Test
    @Transactional
    public void getAllAchievementsBySlugContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where slug contains DEFAULT_SLUG
        defaultAchievementShouldBeFound("slug.contains=" + DEFAULT_SLUG);

        // Get all the achievementList where slug contains UPDATED_SLUG
        defaultAchievementShouldNotBeFound("slug.contains=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void getAllAchievementsBySlugNotContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where slug does not contain DEFAULT_SLUG
        defaultAchievementShouldNotBeFound("slug.doesNotContain=" + DEFAULT_SLUG);

        // Get all the achievementList where slug does not contain UPDATED_SLUG
        defaultAchievementShouldBeFound("slug.doesNotContain=" + UPDATED_SLUG);
    }


    @Test
    @Transactional
    public void getAllAchievementsByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameEn equals to DEFAULT_NAME_EN
        defaultAchievementShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the achievementList where nameEn equals to UPDATED_NAME_EN
        defaultAchievementShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameEn not equals to DEFAULT_NAME_EN
        defaultAchievementShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the achievementList where nameEn not equals to UPDATED_NAME_EN
        defaultAchievementShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultAchievementShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the achievementList where nameEn equals to UPDATED_NAME_EN
        defaultAchievementShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameEn is not null
        defaultAchievementShouldBeFound("nameEn.specified=true");

        // Get all the achievementList where nameEn is null
        defaultAchievementShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllAchievementsByNameEnContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameEn contains DEFAULT_NAME_EN
        defaultAchievementShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the achievementList where nameEn contains UPDATED_NAME_EN
        defaultAchievementShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameEn does not contain DEFAULT_NAME_EN
        defaultAchievementShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the achievementList where nameEn does not contain UPDATED_NAME_EN
        defaultAchievementShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllAchievementsByNameRuIsEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameRu equals to DEFAULT_NAME_RU
        defaultAchievementShouldBeFound("nameRu.equals=" + DEFAULT_NAME_RU);

        // Get all the achievementList where nameRu equals to UPDATED_NAME_RU
        defaultAchievementShouldNotBeFound("nameRu.equals=" + UPDATED_NAME_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameRuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameRu not equals to DEFAULT_NAME_RU
        defaultAchievementShouldNotBeFound("nameRu.notEquals=" + DEFAULT_NAME_RU);

        // Get all the achievementList where nameRu not equals to UPDATED_NAME_RU
        defaultAchievementShouldBeFound("nameRu.notEquals=" + UPDATED_NAME_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameRuIsInShouldWork() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameRu in DEFAULT_NAME_RU or UPDATED_NAME_RU
        defaultAchievementShouldBeFound("nameRu.in=" + DEFAULT_NAME_RU + "," + UPDATED_NAME_RU);

        // Get all the achievementList where nameRu equals to UPDATED_NAME_RU
        defaultAchievementShouldNotBeFound("nameRu.in=" + UPDATED_NAME_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameRuIsNullOrNotNull() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameRu is not null
        defaultAchievementShouldBeFound("nameRu.specified=true");

        // Get all the achievementList where nameRu is null
        defaultAchievementShouldNotBeFound("nameRu.specified=false");
    }
                @Test
    @Transactional
    public void getAllAchievementsByNameRuContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameRu contains DEFAULT_NAME_RU
        defaultAchievementShouldBeFound("nameRu.contains=" + DEFAULT_NAME_RU);

        // Get all the achievementList where nameRu contains UPDATED_NAME_RU
        defaultAchievementShouldNotBeFound("nameRu.contains=" + UPDATED_NAME_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameRuNotContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameRu does not contain DEFAULT_NAME_RU
        defaultAchievementShouldNotBeFound("nameRu.doesNotContain=" + DEFAULT_NAME_RU);

        // Get all the achievementList where nameRu does not contain UPDATED_NAME_RU
        defaultAchievementShouldBeFound("nameRu.doesNotContain=" + UPDATED_NAME_RU);
    }


    @Test
    @Transactional
    public void getAllAchievementsByNameUaIsEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameUa equals to DEFAULT_NAME_UA
        defaultAchievementShouldBeFound("nameUa.equals=" + DEFAULT_NAME_UA);

        // Get all the achievementList where nameUa equals to UPDATED_NAME_UA
        defaultAchievementShouldNotBeFound("nameUa.equals=" + UPDATED_NAME_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameUaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameUa not equals to DEFAULT_NAME_UA
        defaultAchievementShouldNotBeFound("nameUa.notEquals=" + DEFAULT_NAME_UA);

        // Get all the achievementList where nameUa not equals to UPDATED_NAME_UA
        defaultAchievementShouldBeFound("nameUa.notEquals=" + UPDATED_NAME_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameUaIsInShouldWork() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameUa in DEFAULT_NAME_UA or UPDATED_NAME_UA
        defaultAchievementShouldBeFound("nameUa.in=" + DEFAULT_NAME_UA + "," + UPDATED_NAME_UA);

        // Get all the achievementList where nameUa equals to UPDATED_NAME_UA
        defaultAchievementShouldNotBeFound("nameUa.in=" + UPDATED_NAME_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameUaIsNullOrNotNull() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameUa is not null
        defaultAchievementShouldBeFound("nameUa.specified=true");

        // Get all the achievementList where nameUa is null
        defaultAchievementShouldNotBeFound("nameUa.specified=false");
    }
                @Test
    @Transactional
    public void getAllAchievementsByNameUaContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameUa contains DEFAULT_NAME_UA
        defaultAchievementShouldBeFound("nameUa.contains=" + DEFAULT_NAME_UA);

        // Get all the achievementList where nameUa contains UPDATED_NAME_UA
        defaultAchievementShouldNotBeFound("nameUa.contains=" + UPDATED_NAME_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByNameUaNotContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where nameUa does not contain DEFAULT_NAME_UA
        defaultAchievementShouldNotBeFound("nameUa.doesNotContain=" + DEFAULT_NAME_UA);

        // Get all the achievementList where nameUa does not contain UPDATED_NAME_UA
        defaultAchievementShouldBeFound("nameUa.doesNotContain=" + UPDATED_NAME_UA);
    }


    @Test
    @Transactional
    public void getAllAchievementsByDescriptionEnIsEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionEn equals to DEFAULT_DESCRIPTION_EN
        defaultAchievementShouldBeFound("descriptionEn.equals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the achievementList where descriptionEn equals to UPDATED_DESCRIPTION_EN
        defaultAchievementShouldNotBeFound("descriptionEn.equals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionEn not equals to DEFAULT_DESCRIPTION_EN
        defaultAchievementShouldNotBeFound("descriptionEn.notEquals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the achievementList where descriptionEn not equals to UPDATED_DESCRIPTION_EN
        defaultAchievementShouldBeFound("descriptionEn.notEquals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionEnIsInShouldWork() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionEn in DEFAULT_DESCRIPTION_EN or UPDATED_DESCRIPTION_EN
        defaultAchievementShouldBeFound("descriptionEn.in=" + DEFAULT_DESCRIPTION_EN + "," + UPDATED_DESCRIPTION_EN);

        // Get all the achievementList where descriptionEn equals to UPDATED_DESCRIPTION_EN
        defaultAchievementShouldNotBeFound("descriptionEn.in=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionEn is not null
        defaultAchievementShouldBeFound("descriptionEn.specified=true");

        // Get all the achievementList where descriptionEn is null
        defaultAchievementShouldNotBeFound("descriptionEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllAchievementsByDescriptionEnContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionEn contains DEFAULT_DESCRIPTION_EN
        defaultAchievementShouldBeFound("descriptionEn.contains=" + DEFAULT_DESCRIPTION_EN);

        // Get all the achievementList where descriptionEn contains UPDATED_DESCRIPTION_EN
        defaultAchievementShouldNotBeFound("descriptionEn.contains=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionEnNotContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionEn does not contain DEFAULT_DESCRIPTION_EN
        defaultAchievementShouldNotBeFound("descriptionEn.doesNotContain=" + DEFAULT_DESCRIPTION_EN);

        // Get all the achievementList where descriptionEn does not contain UPDATED_DESCRIPTION_EN
        defaultAchievementShouldBeFound("descriptionEn.doesNotContain=" + UPDATED_DESCRIPTION_EN);
    }


    @Test
    @Transactional
    public void getAllAchievementsByDescriptionRuIsEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionRu equals to DEFAULT_DESCRIPTION_RU
        defaultAchievementShouldBeFound("descriptionRu.equals=" + DEFAULT_DESCRIPTION_RU);

        // Get all the achievementList where descriptionRu equals to UPDATED_DESCRIPTION_RU
        defaultAchievementShouldNotBeFound("descriptionRu.equals=" + UPDATED_DESCRIPTION_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionRuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionRu not equals to DEFAULT_DESCRIPTION_RU
        defaultAchievementShouldNotBeFound("descriptionRu.notEquals=" + DEFAULT_DESCRIPTION_RU);

        // Get all the achievementList where descriptionRu not equals to UPDATED_DESCRIPTION_RU
        defaultAchievementShouldBeFound("descriptionRu.notEquals=" + UPDATED_DESCRIPTION_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionRuIsInShouldWork() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionRu in DEFAULT_DESCRIPTION_RU or UPDATED_DESCRIPTION_RU
        defaultAchievementShouldBeFound("descriptionRu.in=" + DEFAULT_DESCRIPTION_RU + "," + UPDATED_DESCRIPTION_RU);

        // Get all the achievementList where descriptionRu equals to UPDATED_DESCRIPTION_RU
        defaultAchievementShouldNotBeFound("descriptionRu.in=" + UPDATED_DESCRIPTION_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionRuIsNullOrNotNull() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionRu is not null
        defaultAchievementShouldBeFound("descriptionRu.specified=true");

        // Get all the achievementList where descriptionRu is null
        defaultAchievementShouldNotBeFound("descriptionRu.specified=false");
    }
                @Test
    @Transactional
    public void getAllAchievementsByDescriptionRuContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionRu contains DEFAULT_DESCRIPTION_RU
        defaultAchievementShouldBeFound("descriptionRu.contains=" + DEFAULT_DESCRIPTION_RU);

        // Get all the achievementList where descriptionRu contains UPDATED_DESCRIPTION_RU
        defaultAchievementShouldNotBeFound("descriptionRu.contains=" + UPDATED_DESCRIPTION_RU);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionRuNotContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionRu does not contain DEFAULT_DESCRIPTION_RU
        defaultAchievementShouldNotBeFound("descriptionRu.doesNotContain=" + DEFAULT_DESCRIPTION_RU);

        // Get all the achievementList where descriptionRu does not contain UPDATED_DESCRIPTION_RU
        defaultAchievementShouldBeFound("descriptionRu.doesNotContain=" + UPDATED_DESCRIPTION_RU);
    }


    @Test
    @Transactional
    public void getAllAchievementsByDescriptionUaIsEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionUa equals to DEFAULT_DESCRIPTION_UA
        defaultAchievementShouldBeFound("descriptionUa.equals=" + DEFAULT_DESCRIPTION_UA);

        // Get all the achievementList where descriptionUa equals to UPDATED_DESCRIPTION_UA
        defaultAchievementShouldNotBeFound("descriptionUa.equals=" + UPDATED_DESCRIPTION_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionUaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionUa not equals to DEFAULT_DESCRIPTION_UA
        defaultAchievementShouldNotBeFound("descriptionUa.notEquals=" + DEFAULT_DESCRIPTION_UA);

        // Get all the achievementList where descriptionUa not equals to UPDATED_DESCRIPTION_UA
        defaultAchievementShouldBeFound("descriptionUa.notEquals=" + UPDATED_DESCRIPTION_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionUaIsInShouldWork() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionUa in DEFAULT_DESCRIPTION_UA or UPDATED_DESCRIPTION_UA
        defaultAchievementShouldBeFound("descriptionUa.in=" + DEFAULT_DESCRIPTION_UA + "," + UPDATED_DESCRIPTION_UA);

        // Get all the achievementList where descriptionUa equals to UPDATED_DESCRIPTION_UA
        defaultAchievementShouldNotBeFound("descriptionUa.in=" + UPDATED_DESCRIPTION_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionUaIsNullOrNotNull() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionUa is not null
        defaultAchievementShouldBeFound("descriptionUa.specified=true");

        // Get all the achievementList where descriptionUa is null
        defaultAchievementShouldNotBeFound("descriptionUa.specified=false");
    }
                @Test
    @Transactional
    public void getAllAchievementsByDescriptionUaContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionUa contains DEFAULT_DESCRIPTION_UA
        defaultAchievementShouldBeFound("descriptionUa.contains=" + DEFAULT_DESCRIPTION_UA);

        // Get all the achievementList where descriptionUa contains UPDATED_DESCRIPTION_UA
        defaultAchievementShouldNotBeFound("descriptionUa.contains=" + UPDATED_DESCRIPTION_UA);
    }

    @Test
    @Transactional
    public void getAllAchievementsByDescriptionUaNotContainsSomething() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList where descriptionUa does not contain DEFAULT_DESCRIPTION_UA
        defaultAchievementShouldNotBeFound("descriptionUa.doesNotContain=" + DEFAULT_DESCRIPTION_UA);

        // Get all the achievementList where descriptionUa does not contain UPDATED_DESCRIPTION_UA
        defaultAchievementShouldBeFound("descriptionUa.doesNotContain=" + UPDATED_DESCRIPTION_UA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAchievementShouldBeFound(String filter) throws Exception {
        restAchievementMockMvc.perform(get("/api/achievements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievement.getId().intValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameUa").value(hasItem(DEFAULT_NAME_UA)))
            .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionRu").value(hasItem(DEFAULT_DESCRIPTION_RU)))
            .andExpect(jsonPath("$.[*].descriptionUa").value(hasItem(DEFAULT_DESCRIPTION_UA)));

        // Check, that the count call also returns 1
        restAchievementMockMvc.perform(get("/api/achievements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAchievementShouldNotBeFound(String filter) throws Exception {
        restAchievementMockMvc.perform(get("/api/achievements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAchievementMockMvc.perform(get("/api/achievements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAchievement() throws Exception {
        // Get the achievement
        restAchievementMockMvc.perform(get("/api/achievements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchievement() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        int databaseSizeBeforeUpdate = achievementRepository.findAll().size();

        // Update the achievement
        Achievement updatedAchievement = achievementRepository.findById(achievement.getId()).get();
        // Disconnect from session so that the updates on updatedAchievement are not directly saved in db
        em.detach(updatedAchievement);
        updatedAchievement
            .slug(UPDATED_SLUG)
            .nameEn(UPDATED_NAME_EN)
            .nameRu(UPDATED_NAME_RU)
            .nameUa(UPDATED_NAME_UA)
            .descriptionEn(UPDATED_DESCRIPTION_EN)
            .descriptionRu(UPDATED_DESCRIPTION_RU)
            .descriptionUa(UPDATED_DESCRIPTION_UA);
        AchievementDTO achievementDTO = achievementMapper.toDto(updatedAchievement);

        restAchievementMockMvc.perform(put("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isOk());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeUpdate);
        Achievement testAchievement = achievementList.get(achievementList.size() - 1);
        assertThat(testAchievement.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testAchievement.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testAchievement.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testAchievement.getNameUa()).isEqualTo(UPDATED_NAME_UA);
        assertThat(testAchievement.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testAchievement.getDescriptionRu()).isEqualTo(UPDATED_DESCRIPTION_RU);
        assertThat(testAchievement.getDescriptionUa()).isEqualTo(UPDATED_DESCRIPTION_UA);

        // Validate the Achievement in Elasticsearch
        verify(mockAchievementSearchRepository, times(1)).save(testAchievement);
    }

    @Test
    @Transactional
    public void updateNonExistingAchievement() throws Exception {
        int databaseSizeBeforeUpdate = achievementRepository.findAll().size();

        // Create the Achievement
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchievementMockMvc.perform(put("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Achievement in Elasticsearch
        verify(mockAchievementSearchRepository, times(0)).save(achievement);
    }

    @Test
    @Transactional
    public void deleteAchievement() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        int databaseSizeBeforeDelete = achievementRepository.findAll().size();

        // Delete the achievement
        restAchievementMockMvc.perform(delete("/api/achievements/{id}", achievement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Achievement in Elasticsearch
        verify(mockAchievementSearchRepository, times(1)).deleteById(achievement.getId());
    }

    @Test
    @Transactional
    public void searchAchievement() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);
        when(mockAchievementSearchRepository.search(queryStringQuery("id:" + achievement.getId())))
            .thenReturn(Collections.singletonList(achievement));

        // Search the achievement
        restAchievementMockMvc.perform(get("/api/_search/achievements?query=id:" + achievement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievement.getId().intValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameUa").value(hasItem(DEFAULT_NAME_UA)))
            .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionRu").value(hasItem(DEFAULT_DESCRIPTION_RU)))
            .andExpect(jsonPath("$.[*].descriptionUa").value(hasItem(DEFAULT_DESCRIPTION_UA)));
    }
}
