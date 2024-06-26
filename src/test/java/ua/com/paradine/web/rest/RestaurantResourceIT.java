package ua.com.paradine.web.rest;

import org.junit.Ignore;
import ua.com.paradine.ParadineApp;
import ua.com.paradine.domain.Restaurant;
import ua.com.paradine.domain.City;
import ua.com.paradine.repository.RestaurantRepository;
import ua.com.paradine.service.RestaurantService;
import ua.com.paradine.service.dto.RestaurantDTO;
import ua.com.paradine.service.mapper.RestaurantMapper;
import ua.com.paradine.service.dto.RestaurantCriteria;
import ua.com.paradine.service.RestaurantQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static ua.com.paradine.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RestaurantResource} REST controller.
 */
@SpringBootTest(classes = ParadineApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RestaurantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_ALT_NAME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_EN = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_EN = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_RU = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_RU = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_UA = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_UA = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_PLACES_ID = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_PLACES_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_GEOLAT = 1.0;
    private static final Double UPDATED_GEOLAT = 2.0;
    private static final Double SMALLER_GEOLAT = 1.0 - 1.0;

    private static final Double DEFAULT_GEOLNG = 1.0;
    private static final Double UPDATED_GEOLNG = 2.0;
    private static final Double SMALLER_GEOLNG = 1.0 - 1.0;

    private static final String DEFAULT_PHOTO_URL = "rCKfE@3qs%T9c6vv_sA+G9%XkgKR8Ih(C#DDs7#y6ZuM2=9OLhCBush1OACrZ8bkMXIx2ZJugZ:4sn(_8t7Z(FtQoku57iohYzPQDkTZWF4.dh";
    private static final String UPDATED_PHOTO_URL = "IT#md1g:lzf3RtzZK#L3sKW00KvH)ea8~6Hd8F3zNl~PxFyc?2BYrB8ZbQg%(BfMPGol+RqVwC@5Jvlr0oMbuaE.sbc";

    private static final String DEFAULT_ALT_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_ALT_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_NAME_3 = "AAAAAAAAAA";
    private static final String UPDATED_ALT_NAME_3 = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 3;
    private static final Integer UPDATED_CAPACITY = 4;
    private static final Integer SMALLER_CAPACITY = 3 - 1;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_UUID = "63C5Cdcb-74eE-AAEC-E8B6-ea1FdB393611";
    private static final String UPDATED_UUID = "c4B457Db-FA25-bb2D-DAD5-AFA9ABFb7BEb";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantQueryService restaurantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestaurantMockMvc;

    private Restaurant restaurant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurant createEntity(EntityManager em) {
        Restaurant restaurant = new Restaurant()
            .name(DEFAULT_NAME)
            .altName1(DEFAULT_ALT_NAME_1)
            .addressEn(DEFAULT_ADDRESS_EN)
            .addressRu(DEFAULT_ADDRESS_RU)
            .addressUa(DEFAULT_ADDRESS_UA)
            .googlePlacesId(DEFAULT_GOOGLE_PLACES_ID)
            .geolat(DEFAULT_GEOLAT)
            .geolng(DEFAULT_GEOLNG)
            .photoUrl(DEFAULT_PHOTO_URL)
            .altName2(DEFAULT_ALT_NAME_2)
            .altName3(DEFAULT_ALT_NAME_3)
            .capacity(DEFAULT_CAPACITY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .uuid(DEFAULT_UUID);
        return restaurant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurant createUpdatedEntity(EntityManager em) {
        Restaurant restaurant = new Restaurant()
            .name(UPDATED_NAME)
            .altName1(UPDATED_ALT_NAME_1)
            .addressEn(UPDATED_ADDRESS_EN)
            .addressRu(UPDATED_ADDRESS_RU)
            .addressUa(UPDATED_ADDRESS_UA)
            .googlePlacesId(UPDATED_GOOGLE_PLACES_ID)
            .geolat(UPDATED_GEOLAT)
            .geolng(UPDATED_GEOLNG)
            .photoUrl(UPDATED_PHOTO_URL)
            .altName2(UPDATED_ALT_NAME_2)
            .altName3(UPDATED_ALT_NAME_3)
            .capacity(UPDATED_CAPACITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        return restaurant;
    }

    @BeforeEach
    public void initTest() {
        restaurant = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestaurant() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();
        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);
        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isCreated());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRestaurant.getAltName1()).isEqualTo(DEFAULT_ALT_NAME_1);
        assertThat(testRestaurant.getAddressEn()).isEqualTo(DEFAULT_ADDRESS_EN);
        assertThat(testRestaurant.getAddressRu()).isEqualTo(DEFAULT_ADDRESS_RU);
        assertThat(testRestaurant.getAddressUa()).isEqualTo(DEFAULT_ADDRESS_UA);
        assertThat(testRestaurant.getGooglePlacesId()).isEqualTo(DEFAULT_GOOGLE_PLACES_ID);
        assertThat(testRestaurant.getGeolat()).isEqualTo(DEFAULT_GEOLAT);
        assertThat(testRestaurant.getGeolng()).isEqualTo(DEFAULT_GEOLNG);
        assertThat(testRestaurant.getPhotoUrl()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testRestaurant.getAltName2()).isEqualTo(DEFAULT_ALT_NAME_2);
        assertThat(testRestaurant.getAltName3()).isEqualTo(DEFAULT_ALT_NAME_3);
        assertThat(testRestaurant.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testRestaurant.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRestaurant.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRestaurant.getUuid()).isEqualTo(DEFAULT_UUID);

        // Validate the Restaurant in Elasticsearch
//        verify(mockRestaurantSearchRepository, times(1)).save(testRestaurant);
    }

    @Test
    @Transactional
    public void createRestaurantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // Create the Restaurant with an existing ID
        restaurant.setId(1L);
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate);

        // Validate the Restaurant in Elasticsearch
//        verify(mockRestaurantSearchRepository, times(0)).save(restaurant);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setName(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setAddressEn(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeolatIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setGeolat(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeolngIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setGeolng(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setPhotoUrl(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setCapacity(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setCreatedAt(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setUpdatedAt(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setUuid(null);

        // Create the Restaurant, which fails.
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);


        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRestaurants() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList
        restRestaurantMockMvc.perform(get("/api/restaurants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].altName1").value(hasItem(DEFAULT_ALT_NAME_1)))
            .andExpect(jsonPath("$.[*].addressEn").value(hasItem(DEFAULT_ADDRESS_EN)))
            .andExpect(jsonPath("$.[*].addressRu").value(hasItem(DEFAULT_ADDRESS_RU)))
            .andExpect(jsonPath("$.[*].addressUa").value(hasItem(DEFAULT_ADDRESS_UA)))
            .andExpect(jsonPath("$.[*].googlePlacesId").value(hasItem(DEFAULT_GOOGLE_PLACES_ID)))
            .andExpect(jsonPath("$.[*].geolat").value(hasItem(DEFAULT_GEOLAT.doubleValue())))
            .andExpect(jsonPath("$.[*].geolng").value(hasItem(DEFAULT_GEOLNG.doubleValue())))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].altName2").value(hasItem(DEFAULT_ALT_NAME_2)))
            .andExpect(jsonPath("$.[*].altName3").value(hasItem(DEFAULT_ALT_NAME_3)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }
    
    @Test
    @Transactional
    public void getRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restaurant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.altName1").value(DEFAULT_ALT_NAME_1))
            .andExpect(jsonPath("$.addressEn").value(DEFAULT_ADDRESS_EN))
            .andExpect(jsonPath("$.addressRu").value(DEFAULT_ADDRESS_RU))
            .andExpect(jsonPath("$.addressUa").value(DEFAULT_ADDRESS_UA))
            .andExpect(jsonPath("$.googlePlacesId").value(DEFAULT_GOOGLE_PLACES_ID))
            .andExpect(jsonPath("$.geolat").value(DEFAULT_GEOLAT.doubleValue()))
            .andExpect(jsonPath("$.geolng").value(DEFAULT_GEOLNG.doubleValue()))
            .andExpect(jsonPath("$.photoUrl").value(DEFAULT_PHOTO_URL))
            .andExpect(jsonPath("$.altName2").value(DEFAULT_ALT_NAME_2))
            .andExpect(jsonPath("$.altName3").value(DEFAULT_ALT_NAME_3))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }


    @Test
    @Transactional
    public void getRestaurantsByIdFiltering() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        Long id = restaurant.getId();

        defaultRestaurantShouldBeFound("id.equals=" + id);
        defaultRestaurantShouldNotBeFound("id.notEquals=" + id);

        defaultRestaurantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRestaurantShouldNotBeFound("id.greaterThan=" + id);

        defaultRestaurantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRestaurantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where name equals to DEFAULT_NAME
        defaultRestaurantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the restaurantList where name equals to UPDATED_NAME
        defaultRestaurantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where name not equals to DEFAULT_NAME
        defaultRestaurantShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the restaurantList where name not equals to UPDATED_NAME
        defaultRestaurantShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRestaurantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the restaurantList where name equals to UPDATED_NAME
        defaultRestaurantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where name is not null
        defaultRestaurantShouldBeFound("name.specified=true");

        // Get all the restaurantList where name is null
        defaultRestaurantShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByNameContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where name contains DEFAULT_NAME
        defaultRestaurantShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the restaurantList where name contains UPDATED_NAME
        defaultRestaurantShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where name does not contain DEFAULT_NAME
        defaultRestaurantShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the restaurantList where name does not contain UPDATED_NAME
        defaultRestaurantShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByAltName1IsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName1 equals to DEFAULT_ALT_NAME_1
        defaultRestaurantShouldBeFound("altName1.equals=" + DEFAULT_ALT_NAME_1);

        // Get all the restaurantList where altName1 equals to UPDATED_ALT_NAME_1
        defaultRestaurantShouldNotBeFound("altName1.equals=" + UPDATED_ALT_NAME_1);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName1 not equals to DEFAULT_ALT_NAME_1
        defaultRestaurantShouldNotBeFound("altName1.notEquals=" + DEFAULT_ALT_NAME_1);

        // Get all the restaurantList where altName1 not equals to UPDATED_ALT_NAME_1
        defaultRestaurantShouldBeFound("altName1.notEquals=" + UPDATED_ALT_NAME_1);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName1IsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName1 in DEFAULT_ALT_NAME_1 or UPDATED_ALT_NAME_1
        defaultRestaurantShouldBeFound("altName1.in=" + DEFAULT_ALT_NAME_1 + "," + UPDATED_ALT_NAME_1);

        // Get all the restaurantList where altName1 equals to UPDATED_ALT_NAME_1
        defaultRestaurantShouldNotBeFound("altName1.in=" + UPDATED_ALT_NAME_1);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName1IsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName1 is not null
        defaultRestaurantShouldBeFound("altName1.specified=true");

        // Get all the restaurantList where altName1 is null
        defaultRestaurantShouldNotBeFound("altName1.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByAltName1ContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName1 contains DEFAULT_ALT_NAME_1
        defaultRestaurantShouldBeFound("altName1.contains=" + DEFAULT_ALT_NAME_1);

        // Get all the restaurantList where altName1 contains UPDATED_ALT_NAME_1
        defaultRestaurantShouldNotBeFound("altName1.contains=" + UPDATED_ALT_NAME_1);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName1NotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName1 does not contain DEFAULT_ALT_NAME_1
        defaultRestaurantShouldNotBeFound("altName1.doesNotContain=" + DEFAULT_ALT_NAME_1);

        // Get all the restaurantList where altName1 does not contain UPDATED_ALT_NAME_1
        defaultRestaurantShouldBeFound("altName1.doesNotContain=" + UPDATED_ALT_NAME_1);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByAddressEnIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressEn equals to DEFAULT_ADDRESS_EN
        defaultRestaurantShouldBeFound("addressEn.equals=" + DEFAULT_ADDRESS_EN);

        // Get all the restaurantList where addressEn equals to UPDATED_ADDRESS_EN
        defaultRestaurantShouldNotBeFound("addressEn.equals=" + UPDATED_ADDRESS_EN);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressEn not equals to DEFAULT_ADDRESS_EN
        defaultRestaurantShouldNotBeFound("addressEn.notEquals=" + DEFAULT_ADDRESS_EN);

        // Get all the restaurantList where addressEn not equals to UPDATED_ADDRESS_EN
        defaultRestaurantShouldBeFound("addressEn.notEquals=" + UPDATED_ADDRESS_EN);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressEnIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressEn in DEFAULT_ADDRESS_EN or UPDATED_ADDRESS_EN
        defaultRestaurantShouldBeFound("addressEn.in=" + DEFAULT_ADDRESS_EN + "," + UPDATED_ADDRESS_EN);

        // Get all the restaurantList where addressEn equals to UPDATED_ADDRESS_EN
        defaultRestaurantShouldNotBeFound("addressEn.in=" + UPDATED_ADDRESS_EN);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressEn is not null
        defaultRestaurantShouldBeFound("addressEn.specified=true");

        // Get all the restaurantList where addressEn is null
        defaultRestaurantShouldNotBeFound("addressEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByAddressEnContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressEn contains DEFAULT_ADDRESS_EN
        defaultRestaurantShouldBeFound("addressEn.contains=" + DEFAULT_ADDRESS_EN);

        // Get all the restaurantList where addressEn contains UPDATED_ADDRESS_EN
        defaultRestaurantShouldNotBeFound("addressEn.contains=" + UPDATED_ADDRESS_EN);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressEnNotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressEn does not contain DEFAULT_ADDRESS_EN
        defaultRestaurantShouldNotBeFound("addressEn.doesNotContain=" + DEFAULT_ADDRESS_EN);

        // Get all the restaurantList where addressEn does not contain UPDATED_ADDRESS_EN
        defaultRestaurantShouldBeFound("addressEn.doesNotContain=" + UPDATED_ADDRESS_EN);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByAddressRuIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressRu equals to DEFAULT_ADDRESS_RU
        defaultRestaurantShouldBeFound("addressRu.equals=" + DEFAULT_ADDRESS_RU);

        // Get all the restaurantList where addressRu equals to UPDATED_ADDRESS_RU
        defaultRestaurantShouldNotBeFound("addressRu.equals=" + UPDATED_ADDRESS_RU);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressRuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressRu not equals to DEFAULT_ADDRESS_RU
        defaultRestaurantShouldNotBeFound("addressRu.notEquals=" + DEFAULT_ADDRESS_RU);

        // Get all the restaurantList where addressRu not equals to UPDATED_ADDRESS_RU
        defaultRestaurantShouldBeFound("addressRu.notEquals=" + UPDATED_ADDRESS_RU);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressRuIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressRu in DEFAULT_ADDRESS_RU or UPDATED_ADDRESS_RU
        defaultRestaurantShouldBeFound("addressRu.in=" + DEFAULT_ADDRESS_RU + "," + UPDATED_ADDRESS_RU);

        // Get all the restaurantList where addressRu equals to UPDATED_ADDRESS_RU
        defaultRestaurantShouldNotBeFound("addressRu.in=" + UPDATED_ADDRESS_RU);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressRuIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressRu is not null
        defaultRestaurantShouldBeFound("addressRu.specified=true");

        // Get all the restaurantList where addressRu is null
        defaultRestaurantShouldNotBeFound("addressRu.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByAddressRuContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressRu contains DEFAULT_ADDRESS_RU
        defaultRestaurantShouldBeFound("addressRu.contains=" + DEFAULT_ADDRESS_RU);

        // Get all the restaurantList where addressRu contains UPDATED_ADDRESS_RU
        defaultRestaurantShouldNotBeFound("addressRu.contains=" + UPDATED_ADDRESS_RU);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressRuNotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressRu does not contain DEFAULT_ADDRESS_RU
        defaultRestaurantShouldNotBeFound("addressRu.doesNotContain=" + DEFAULT_ADDRESS_RU);

        // Get all the restaurantList where addressRu does not contain UPDATED_ADDRESS_RU
        defaultRestaurantShouldBeFound("addressRu.doesNotContain=" + UPDATED_ADDRESS_RU);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByAddressUaIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressUa equals to DEFAULT_ADDRESS_UA
        defaultRestaurantShouldBeFound("addressUa.equals=" + DEFAULT_ADDRESS_UA);

        // Get all the restaurantList where addressUa equals to UPDATED_ADDRESS_UA
        defaultRestaurantShouldNotBeFound("addressUa.equals=" + UPDATED_ADDRESS_UA);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressUaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressUa not equals to DEFAULT_ADDRESS_UA
        defaultRestaurantShouldNotBeFound("addressUa.notEquals=" + DEFAULT_ADDRESS_UA);

        // Get all the restaurantList where addressUa not equals to UPDATED_ADDRESS_UA
        defaultRestaurantShouldBeFound("addressUa.notEquals=" + UPDATED_ADDRESS_UA);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressUaIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressUa in DEFAULT_ADDRESS_UA or UPDATED_ADDRESS_UA
        defaultRestaurantShouldBeFound("addressUa.in=" + DEFAULT_ADDRESS_UA + "," + UPDATED_ADDRESS_UA);

        // Get all the restaurantList where addressUa equals to UPDATED_ADDRESS_UA
        defaultRestaurantShouldNotBeFound("addressUa.in=" + UPDATED_ADDRESS_UA);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressUaIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressUa is not null
        defaultRestaurantShouldBeFound("addressUa.specified=true");

        // Get all the restaurantList where addressUa is null
        defaultRestaurantShouldNotBeFound("addressUa.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByAddressUaContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressUa contains DEFAULT_ADDRESS_UA
        defaultRestaurantShouldBeFound("addressUa.contains=" + DEFAULT_ADDRESS_UA);

        // Get all the restaurantList where addressUa contains UPDATED_ADDRESS_UA
        defaultRestaurantShouldNotBeFound("addressUa.contains=" + UPDATED_ADDRESS_UA);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAddressUaNotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where addressUa does not contain DEFAULT_ADDRESS_UA
        defaultRestaurantShouldNotBeFound("addressUa.doesNotContain=" + DEFAULT_ADDRESS_UA);

        // Get all the restaurantList where addressUa does not contain UPDATED_ADDRESS_UA
        defaultRestaurantShouldBeFound("addressUa.doesNotContain=" + UPDATED_ADDRESS_UA);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByGooglePlacesIdIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where googlePlacesId equals to DEFAULT_GOOGLE_PLACES_ID
        defaultRestaurantShouldBeFound("googlePlacesId.equals=" + DEFAULT_GOOGLE_PLACES_ID);

        // Get all the restaurantList where googlePlacesId equals to UPDATED_GOOGLE_PLACES_ID
        defaultRestaurantShouldNotBeFound("googlePlacesId.equals=" + UPDATED_GOOGLE_PLACES_ID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGooglePlacesIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where googlePlacesId not equals to DEFAULT_GOOGLE_PLACES_ID
        defaultRestaurantShouldNotBeFound("googlePlacesId.notEquals=" + DEFAULT_GOOGLE_PLACES_ID);

        // Get all the restaurantList where googlePlacesId not equals to UPDATED_GOOGLE_PLACES_ID
        defaultRestaurantShouldBeFound("googlePlacesId.notEquals=" + UPDATED_GOOGLE_PLACES_ID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGooglePlacesIdIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where googlePlacesId in DEFAULT_GOOGLE_PLACES_ID or UPDATED_GOOGLE_PLACES_ID
        defaultRestaurantShouldBeFound("googlePlacesId.in=" + DEFAULT_GOOGLE_PLACES_ID + "," + UPDATED_GOOGLE_PLACES_ID);

        // Get all the restaurantList where googlePlacesId equals to UPDATED_GOOGLE_PLACES_ID
        defaultRestaurantShouldNotBeFound("googlePlacesId.in=" + UPDATED_GOOGLE_PLACES_ID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGooglePlacesIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where googlePlacesId is not null
        defaultRestaurantShouldBeFound("googlePlacesId.specified=true");

        // Get all the restaurantList where googlePlacesId is null
        defaultRestaurantShouldNotBeFound("googlePlacesId.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByGooglePlacesIdContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where googlePlacesId contains DEFAULT_GOOGLE_PLACES_ID
        defaultRestaurantShouldBeFound("googlePlacesId.contains=" + DEFAULT_GOOGLE_PLACES_ID);

        // Get all the restaurantList where googlePlacesId contains UPDATED_GOOGLE_PLACES_ID
        defaultRestaurantShouldNotBeFound("googlePlacesId.contains=" + UPDATED_GOOGLE_PLACES_ID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGooglePlacesIdNotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where googlePlacesId does not contain DEFAULT_GOOGLE_PLACES_ID
        defaultRestaurantShouldNotBeFound("googlePlacesId.doesNotContain=" + DEFAULT_GOOGLE_PLACES_ID);

        // Get all the restaurantList where googlePlacesId does not contain UPDATED_GOOGLE_PLACES_ID
        defaultRestaurantShouldBeFound("googlePlacesId.doesNotContain=" + UPDATED_GOOGLE_PLACES_ID);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat equals to DEFAULT_GEOLAT
        defaultRestaurantShouldBeFound("geolat.equals=" + DEFAULT_GEOLAT);

        // Get all the restaurantList where geolat equals to UPDATED_GEOLAT
        defaultRestaurantShouldNotBeFound("geolat.equals=" + UPDATED_GEOLAT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat not equals to DEFAULT_GEOLAT
        defaultRestaurantShouldNotBeFound("geolat.notEquals=" + DEFAULT_GEOLAT);

        // Get all the restaurantList where geolat not equals to UPDATED_GEOLAT
        defaultRestaurantShouldBeFound("geolat.notEquals=" + UPDATED_GEOLAT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat in DEFAULT_GEOLAT or UPDATED_GEOLAT
        defaultRestaurantShouldBeFound("geolat.in=" + DEFAULT_GEOLAT + "," + UPDATED_GEOLAT);

        // Get all the restaurantList where geolat equals to UPDATED_GEOLAT
        defaultRestaurantShouldNotBeFound("geolat.in=" + UPDATED_GEOLAT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat is not null
        defaultRestaurantShouldBeFound("geolat.specified=true");

        // Get all the restaurantList where geolat is null
        defaultRestaurantShouldNotBeFound("geolat.specified=false");
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat is greater than or equal to DEFAULT_GEOLAT
        defaultRestaurantShouldBeFound("geolat.greaterThanOrEqual=" + DEFAULT_GEOLAT);

        // Get all the restaurantList where geolat is greater than or equal to UPDATED_GEOLAT
        defaultRestaurantShouldNotBeFound("geolat.greaterThanOrEqual=" + UPDATED_GEOLAT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat is less than or equal to DEFAULT_GEOLAT
        defaultRestaurantShouldBeFound("geolat.lessThanOrEqual=" + DEFAULT_GEOLAT);

        // Get all the restaurantList where geolat is less than or equal to SMALLER_GEOLAT
        defaultRestaurantShouldNotBeFound("geolat.lessThanOrEqual=" + SMALLER_GEOLAT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsLessThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat is less than DEFAULT_GEOLAT
        defaultRestaurantShouldNotBeFound("geolat.lessThan=" + DEFAULT_GEOLAT);

        // Get all the restaurantList where geolat is less than UPDATED_GEOLAT
        defaultRestaurantShouldBeFound("geolat.lessThan=" + UPDATED_GEOLAT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolat is greater than DEFAULT_GEOLAT
        defaultRestaurantShouldNotBeFound("geolat.greaterThan=" + DEFAULT_GEOLAT);

        // Get all the restaurantList where geolat is greater than SMALLER_GEOLAT
        defaultRestaurantShouldBeFound("geolat.greaterThan=" + SMALLER_GEOLAT);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng equals to DEFAULT_GEOLNG
        defaultRestaurantShouldBeFound("geolng.equals=" + DEFAULT_GEOLNG);

        // Get all the restaurantList where geolng equals to UPDATED_GEOLNG
        defaultRestaurantShouldNotBeFound("geolng.equals=" + UPDATED_GEOLNG);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng not equals to DEFAULT_GEOLNG
        defaultRestaurantShouldNotBeFound("geolng.notEquals=" + DEFAULT_GEOLNG);

        // Get all the restaurantList where geolng not equals to UPDATED_GEOLNG
        defaultRestaurantShouldBeFound("geolng.notEquals=" + UPDATED_GEOLNG);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng in DEFAULT_GEOLNG or UPDATED_GEOLNG
        defaultRestaurantShouldBeFound("geolng.in=" + DEFAULT_GEOLNG + "," + UPDATED_GEOLNG);

        // Get all the restaurantList where geolng equals to UPDATED_GEOLNG
        defaultRestaurantShouldNotBeFound("geolng.in=" + UPDATED_GEOLNG);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng is not null
        defaultRestaurantShouldBeFound("geolng.specified=true");

        // Get all the restaurantList where geolng is null
        defaultRestaurantShouldNotBeFound("geolng.specified=false");
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng is greater than or equal to DEFAULT_GEOLNG
        defaultRestaurantShouldBeFound("geolng.greaterThanOrEqual=" + DEFAULT_GEOLNG);

        // Get all the restaurantList where geolng is greater than or equal to UPDATED_GEOLNG
        defaultRestaurantShouldNotBeFound("geolng.greaterThanOrEqual=" + UPDATED_GEOLNG);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng is less than or equal to DEFAULT_GEOLNG
        defaultRestaurantShouldBeFound("geolng.lessThanOrEqual=" + DEFAULT_GEOLNG);

        // Get all the restaurantList where geolng is less than or equal to SMALLER_GEOLNG
        defaultRestaurantShouldNotBeFound("geolng.lessThanOrEqual=" + SMALLER_GEOLNG);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsLessThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng is less than DEFAULT_GEOLNG
        defaultRestaurantShouldNotBeFound("geolng.lessThan=" + DEFAULT_GEOLNG);

        // Get all the restaurantList where geolng is less than UPDATED_GEOLNG
        defaultRestaurantShouldBeFound("geolng.lessThan=" + UPDATED_GEOLNG);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByGeolngIsGreaterThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where geolng is greater than DEFAULT_GEOLNG
        defaultRestaurantShouldNotBeFound("geolng.greaterThan=" + DEFAULT_GEOLNG);

        // Get all the restaurantList where geolng is greater than SMALLER_GEOLNG
        defaultRestaurantShouldBeFound("geolng.greaterThan=" + SMALLER_GEOLNG);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByPhotoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where photoUrl equals to DEFAULT_PHOTO_URL
        defaultRestaurantShouldBeFound("photoUrl.equals=" + DEFAULT_PHOTO_URL);

        // Get all the restaurantList where photoUrl equals to UPDATED_PHOTO_URL
        defaultRestaurantShouldNotBeFound("photoUrl.equals=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByPhotoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where photoUrl not equals to DEFAULT_PHOTO_URL
        defaultRestaurantShouldNotBeFound("photoUrl.notEquals=" + DEFAULT_PHOTO_URL);

        // Get all the restaurantList where photoUrl not equals to UPDATED_PHOTO_URL
        defaultRestaurantShouldBeFound("photoUrl.notEquals=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByPhotoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where photoUrl in DEFAULT_PHOTO_URL or UPDATED_PHOTO_URL
        defaultRestaurantShouldBeFound("photoUrl.in=" + DEFAULT_PHOTO_URL + "," + UPDATED_PHOTO_URL);

        // Get all the restaurantList where photoUrl equals to UPDATED_PHOTO_URL
        defaultRestaurantShouldNotBeFound("photoUrl.in=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByPhotoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where photoUrl is not null
        defaultRestaurantShouldBeFound("photoUrl.specified=true");

        // Get all the restaurantList where photoUrl is null
        defaultRestaurantShouldNotBeFound("photoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByPhotoUrlContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where photoUrl contains DEFAULT_PHOTO_URL
        defaultRestaurantShouldBeFound("photoUrl.contains=" + DEFAULT_PHOTO_URL);

        // Get all the restaurantList where photoUrl contains UPDATED_PHOTO_URL
        defaultRestaurantShouldNotBeFound("photoUrl.contains=" + UPDATED_PHOTO_URL);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByPhotoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where photoUrl does not contain DEFAULT_PHOTO_URL
        defaultRestaurantShouldNotBeFound("photoUrl.doesNotContain=" + DEFAULT_PHOTO_URL);

        // Get all the restaurantList where photoUrl does not contain UPDATED_PHOTO_URL
        defaultRestaurantShouldBeFound("photoUrl.doesNotContain=" + UPDATED_PHOTO_URL);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByAltName2IsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName2 equals to DEFAULT_ALT_NAME_2
        defaultRestaurantShouldBeFound("altName2.equals=" + DEFAULT_ALT_NAME_2);

        // Get all the restaurantList where altName2 equals to UPDATED_ALT_NAME_2
        defaultRestaurantShouldNotBeFound("altName2.equals=" + UPDATED_ALT_NAME_2);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName2 not equals to DEFAULT_ALT_NAME_2
        defaultRestaurantShouldNotBeFound("altName2.notEquals=" + DEFAULT_ALT_NAME_2);

        // Get all the restaurantList where altName2 not equals to UPDATED_ALT_NAME_2
        defaultRestaurantShouldBeFound("altName2.notEquals=" + UPDATED_ALT_NAME_2);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName2IsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName2 in DEFAULT_ALT_NAME_2 or UPDATED_ALT_NAME_2
        defaultRestaurantShouldBeFound("altName2.in=" + DEFAULT_ALT_NAME_2 + "," + UPDATED_ALT_NAME_2);

        // Get all the restaurantList where altName2 equals to UPDATED_ALT_NAME_2
        defaultRestaurantShouldNotBeFound("altName2.in=" + UPDATED_ALT_NAME_2);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName2IsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName2 is not null
        defaultRestaurantShouldBeFound("altName2.specified=true");

        // Get all the restaurantList where altName2 is null
        defaultRestaurantShouldNotBeFound("altName2.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByAltName2ContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName2 contains DEFAULT_ALT_NAME_2
        defaultRestaurantShouldBeFound("altName2.contains=" + DEFAULT_ALT_NAME_2);

        // Get all the restaurantList where altName2 contains UPDATED_ALT_NAME_2
        defaultRestaurantShouldNotBeFound("altName2.contains=" + UPDATED_ALT_NAME_2);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName2NotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName2 does not contain DEFAULT_ALT_NAME_2
        defaultRestaurantShouldNotBeFound("altName2.doesNotContain=" + DEFAULT_ALT_NAME_2);

        // Get all the restaurantList where altName2 does not contain UPDATED_ALT_NAME_2
        defaultRestaurantShouldBeFound("altName2.doesNotContain=" + UPDATED_ALT_NAME_2);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByAltName3IsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName3 equals to DEFAULT_ALT_NAME_3
        defaultRestaurantShouldBeFound("altName3.equals=" + DEFAULT_ALT_NAME_3);

        // Get all the restaurantList where altName3 equals to UPDATED_ALT_NAME_3
        defaultRestaurantShouldNotBeFound("altName3.equals=" + UPDATED_ALT_NAME_3);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName3 not equals to DEFAULT_ALT_NAME_3
        defaultRestaurantShouldNotBeFound("altName3.notEquals=" + DEFAULT_ALT_NAME_3);

        // Get all the restaurantList where altName3 not equals to UPDATED_ALT_NAME_3
        defaultRestaurantShouldBeFound("altName3.notEquals=" + UPDATED_ALT_NAME_3);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName3IsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName3 in DEFAULT_ALT_NAME_3 or UPDATED_ALT_NAME_3
        defaultRestaurantShouldBeFound("altName3.in=" + DEFAULT_ALT_NAME_3 + "," + UPDATED_ALT_NAME_3);

        // Get all the restaurantList where altName3 equals to UPDATED_ALT_NAME_3
        defaultRestaurantShouldNotBeFound("altName3.in=" + UPDATED_ALT_NAME_3);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName3IsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName3 is not null
        defaultRestaurantShouldBeFound("altName3.specified=true");

        // Get all the restaurantList where altName3 is null
        defaultRestaurantShouldNotBeFound("altName3.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByAltName3ContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName3 contains DEFAULT_ALT_NAME_3
        defaultRestaurantShouldBeFound("altName3.contains=" + DEFAULT_ALT_NAME_3);

        // Get all the restaurantList where altName3 contains UPDATED_ALT_NAME_3
        defaultRestaurantShouldNotBeFound("altName3.contains=" + UPDATED_ALT_NAME_3);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByAltName3NotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where altName3 does not contain DEFAULT_ALT_NAME_3
        defaultRestaurantShouldNotBeFound("altName3.doesNotContain=" + DEFAULT_ALT_NAME_3);

        // Get all the restaurantList where altName3 does not contain UPDATED_ALT_NAME_3
        defaultRestaurantShouldBeFound("altName3.doesNotContain=" + UPDATED_ALT_NAME_3);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity equals to DEFAULT_CAPACITY
        defaultRestaurantShouldBeFound("capacity.equals=" + DEFAULT_CAPACITY);

        // Get all the restaurantList where capacity equals to UPDATED_CAPACITY
        defaultRestaurantShouldNotBeFound("capacity.equals=" + UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity not equals to DEFAULT_CAPACITY
        defaultRestaurantShouldNotBeFound("capacity.notEquals=" + DEFAULT_CAPACITY);

        // Get all the restaurantList where capacity not equals to UPDATED_CAPACITY
        defaultRestaurantShouldBeFound("capacity.notEquals=" + UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity in DEFAULT_CAPACITY or UPDATED_CAPACITY
        defaultRestaurantShouldBeFound("capacity.in=" + DEFAULT_CAPACITY + "," + UPDATED_CAPACITY);

        // Get all the restaurantList where capacity equals to UPDATED_CAPACITY
        defaultRestaurantShouldNotBeFound("capacity.in=" + UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity is not null
        defaultRestaurantShouldBeFound("capacity.specified=true");

        // Get all the restaurantList where capacity is null
        defaultRestaurantShouldNotBeFound("capacity.specified=false");
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity is greater than or equal to DEFAULT_CAPACITY
        defaultRestaurantShouldBeFound("capacity.greaterThanOrEqual=" + DEFAULT_CAPACITY);

        // Get all the restaurantList where capacity is greater than or equal to UPDATED_CAPACITY
        defaultRestaurantShouldNotBeFound("capacity.greaterThanOrEqual=" + UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity is less than or equal to DEFAULT_CAPACITY
        defaultRestaurantShouldBeFound("capacity.lessThanOrEqual=" + DEFAULT_CAPACITY);

        // Get all the restaurantList where capacity is less than or equal to SMALLER_CAPACITY
        defaultRestaurantShouldNotBeFound("capacity.lessThanOrEqual=" + SMALLER_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsLessThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity is less than DEFAULT_CAPACITY
        defaultRestaurantShouldNotBeFound("capacity.lessThan=" + DEFAULT_CAPACITY);

        // Get all the restaurantList where capacity is less than UPDATED_CAPACITY
        defaultRestaurantShouldBeFound("capacity.lessThan=" + UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCapacityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where capacity is greater than DEFAULT_CAPACITY
        defaultRestaurantShouldNotBeFound("capacity.greaterThan=" + DEFAULT_CAPACITY);

        // Get all the restaurantList where capacity is greater than SMALLER_CAPACITY
        defaultRestaurantShouldBeFound("capacity.greaterThan=" + SMALLER_CAPACITY);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt equals to DEFAULT_CREATED_AT
        defaultRestaurantShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the restaurantList where createdAt equals to UPDATED_CREATED_AT
        defaultRestaurantShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt not equals to DEFAULT_CREATED_AT
        defaultRestaurantShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the restaurantList where createdAt not equals to UPDATED_CREATED_AT
        defaultRestaurantShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultRestaurantShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the restaurantList where createdAt equals to UPDATED_CREATED_AT
        defaultRestaurantShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt is not null
        defaultRestaurantShouldBeFound("createdAt.specified=true");

        // Get all the restaurantList where createdAt is null
        defaultRestaurantShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt is greater than or equal to DEFAULT_CREATED_AT
        defaultRestaurantShouldBeFound("createdAt.greaterThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the restaurantList where createdAt is greater than or equal to UPDATED_CREATED_AT
        defaultRestaurantShouldNotBeFound("createdAt.greaterThanOrEqual=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt is less than or equal to DEFAULT_CREATED_AT
        defaultRestaurantShouldBeFound("createdAt.lessThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the restaurantList where createdAt is less than or equal to SMALLER_CREATED_AT
        defaultRestaurantShouldNotBeFound("createdAt.lessThanOrEqual=" + SMALLER_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt is less than DEFAULT_CREATED_AT
        defaultRestaurantShouldNotBeFound("createdAt.lessThan=" + DEFAULT_CREATED_AT);

        // Get all the restaurantList where createdAt is less than UPDATED_CREATED_AT
        defaultRestaurantShouldBeFound("createdAt.lessThan=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByCreatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where createdAt is greater than DEFAULT_CREATED_AT
        defaultRestaurantShouldNotBeFound("createdAt.greaterThan=" + DEFAULT_CREATED_AT);

        // Get all the restaurantList where createdAt is greater than SMALLER_CREATED_AT
        defaultRestaurantShouldBeFound("createdAt.greaterThan=" + SMALLER_CREATED_AT);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultRestaurantShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the restaurantList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRestaurantShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultRestaurantShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the restaurantList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultRestaurantShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultRestaurantShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the restaurantList where updatedAt equals to UPDATED_UPDATED_AT
        defaultRestaurantShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt is not null
        defaultRestaurantShouldBeFound("updatedAt.specified=true");

        // Get all the restaurantList where updatedAt is null
        defaultRestaurantShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt is greater than or equal to DEFAULT_UPDATED_AT
        defaultRestaurantShouldBeFound("updatedAt.greaterThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the restaurantList where updatedAt is greater than or equal to UPDATED_UPDATED_AT
        defaultRestaurantShouldNotBeFound("updatedAt.greaterThanOrEqual=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt is less than or equal to DEFAULT_UPDATED_AT
        defaultRestaurantShouldBeFound("updatedAt.lessThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the restaurantList where updatedAt is less than or equal to SMALLER_UPDATED_AT
        defaultRestaurantShouldNotBeFound("updatedAt.lessThanOrEqual=" + SMALLER_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt is less than DEFAULT_UPDATED_AT
        defaultRestaurantShouldNotBeFound("updatedAt.lessThan=" + DEFAULT_UPDATED_AT);

        // Get all the restaurantList where updatedAt is less than UPDATED_UPDATED_AT
        defaultRestaurantShouldBeFound("updatedAt.lessThan=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUpdatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where updatedAt is greater than DEFAULT_UPDATED_AT
        defaultRestaurantShouldNotBeFound("updatedAt.greaterThan=" + DEFAULT_UPDATED_AT);

        // Get all the restaurantList where updatedAt is greater than SMALLER_UPDATED_AT
        defaultRestaurantShouldBeFound("updatedAt.greaterThan=" + SMALLER_UPDATED_AT);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where uuid equals to DEFAULT_UUID
        defaultRestaurantShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the restaurantList where uuid equals to UPDATED_UUID
        defaultRestaurantShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUuidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where uuid not equals to DEFAULT_UUID
        defaultRestaurantShouldNotBeFound("uuid.notEquals=" + DEFAULT_UUID);

        // Get all the restaurantList where uuid not equals to UPDATED_UUID
        defaultRestaurantShouldBeFound("uuid.notEquals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultRestaurantShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the restaurantList where uuid equals to UPDATED_UUID
        defaultRestaurantShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where uuid is not null
        defaultRestaurantShouldBeFound("uuid.specified=true");

        // Get all the restaurantList where uuid is null
        defaultRestaurantShouldNotBeFound("uuid.specified=false");
    }
                @Test
    @Transactional
    public void getAllRestaurantsByUuidContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where uuid contains DEFAULT_UUID
        defaultRestaurantShouldBeFound("uuid.contains=" + DEFAULT_UUID);

        // Get all the restaurantList where uuid contains UPDATED_UUID
        defaultRestaurantShouldNotBeFound("uuid.contains=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllRestaurantsByUuidNotContainsSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList where uuid does not contain DEFAULT_UUID
        defaultRestaurantShouldNotBeFound("uuid.doesNotContain=" + DEFAULT_UUID);

        // Get all the restaurantList where uuid does not contain UPDATED_UUID
        defaultRestaurantShouldBeFound("uuid.doesNotContain=" + UPDATED_UUID);
    }


    @Test
    @Transactional
    public void getAllRestaurantsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);
        City city = CityResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        restaurant.setCity(city);
        restaurantRepository.saveAndFlush(restaurant);
        Long cityId = city.getId();

        // Get all the restaurantList where city equals to cityId
        defaultRestaurantShouldBeFound("cityId.equals=" + cityId);

        // Get all the restaurantList where city equals to cityId + 1
        defaultRestaurantShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRestaurantShouldBeFound(String filter) throws Exception {
        restRestaurantMockMvc.perform(get("/api/restaurants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].altName1").value(hasItem(DEFAULT_ALT_NAME_1)))
            .andExpect(jsonPath("$.[*].addressEn").value(hasItem(DEFAULT_ADDRESS_EN)))
            .andExpect(jsonPath("$.[*].addressRu").value(hasItem(DEFAULT_ADDRESS_RU)))
            .andExpect(jsonPath("$.[*].addressUa").value(hasItem(DEFAULT_ADDRESS_UA)))
            .andExpect(jsonPath("$.[*].googlePlacesId").value(hasItem(DEFAULT_GOOGLE_PLACES_ID)))
            .andExpect(jsonPath("$.[*].geolat").value(hasItem(DEFAULT_GEOLAT.doubleValue())))
            .andExpect(jsonPath("$.[*].geolng").value(hasItem(DEFAULT_GEOLNG.doubleValue())))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].altName2").value(hasItem(DEFAULT_ALT_NAME_2)))
            .andExpect(jsonPath("$.[*].altName3").value(hasItem(DEFAULT_ALT_NAME_3)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));

        // Check, that the count call also returns 1
        restRestaurantMockMvc.perform(get("/api/restaurants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRestaurantShouldNotBeFound(String filter) throws Exception {
        restRestaurantMockMvc.perform(get("/api/restaurants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRestaurantMockMvc.perform(get("/api/restaurants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurant() throws Exception {
        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant
        Restaurant updatedRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurant are not directly saved in db
        em.detach(updatedRestaurant);
        updatedRestaurant
            .name(UPDATED_NAME)
            .altName1(UPDATED_ALT_NAME_1)
            .addressEn(UPDATED_ADDRESS_EN)
            .addressRu(UPDATED_ADDRESS_RU)
            .addressUa(UPDATED_ADDRESS_UA)
            .googlePlacesId(UPDATED_GOOGLE_PLACES_ID)
            .geolat(UPDATED_GEOLAT)
            .geolng(UPDATED_GEOLNG)
            .photoUrl(UPDATED_PHOTO_URL)
            .altName2(UPDATED_ALT_NAME_2)
            .altName3(UPDATED_ALT_NAME_3)
            .capacity(UPDATED_CAPACITY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(updatedRestaurant);

        restRestaurantMockMvc.perform(put("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRestaurant.getAltName1()).isEqualTo(UPDATED_ALT_NAME_1);
        assertThat(testRestaurant.getAddressEn()).isEqualTo(UPDATED_ADDRESS_EN);
        assertThat(testRestaurant.getAddressRu()).isEqualTo(UPDATED_ADDRESS_RU);
        assertThat(testRestaurant.getAddressUa()).isEqualTo(UPDATED_ADDRESS_UA);
        assertThat(testRestaurant.getGooglePlacesId()).isEqualTo(UPDATED_GOOGLE_PLACES_ID);
        assertThat(testRestaurant.getGeolat()).isEqualTo(UPDATED_GEOLAT);
        assertThat(testRestaurant.getGeolng()).isEqualTo(UPDATED_GEOLNG);
        assertThat(testRestaurant.getPhotoUrl()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testRestaurant.getAltName2()).isEqualTo(UPDATED_ALT_NAME_2);
        assertThat(testRestaurant.getAltName3()).isEqualTo(UPDATED_ALT_NAME_3);
        assertThat(testRestaurant.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testRestaurant.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRestaurant.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRestaurant.getUuid()).isEqualTo(UPDATED_UUID);

        // Validate the Restaurant in Elasticsearch
//        verify(mockRestaurantSearchRepository, times(1)).save(testRestaurant);
    }

    @Test
    @Transactional
    public void updateNonExistingRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantMockMvc.perform(put("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Restaurant in Elasticsearch
//        verify(mockRestaurantSearchRepository, times(0)).save(restaurant);
    }

    @Test
    @Transactional
    public void deleteRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeDelete = restaurantRepository.findAll().size();

        // Delete the restaurant
        restRestaurantMockMvc.perform(delete("/api/restaurants/{id}", restaurant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Restaurant in Elasticsearch
//        verify(mockRestaurantSearchRepository, times(1)).deleteById(restaurant.getId());
    }

    @Test
    @Transactional
    @Ignore
    public void searchRestaurant() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);
//        when(mockRestaurantSearchRepository.search(queryStringQuery("id:" + restaurant.getId()), PageRequest.of(0, 20)))
//            .thenReturn(new PageImpl<>(Collections.singletonList(restaurant), PageRequest.of(0, 1), 1));

        // Search the restaurant
        restRestaurantMockMvc.perform(get("/api/_search/restaurants?query=id:" + restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].altName1").value(hasItem(DEFAULT_ALT_NAME_1)))
            .andExpect(jsonPath("$.[*].addressEn").value(hasItem(DEFAULT_ADDRESS_EN)))
            .andExpect(jsonPath("$.[*].addressRu").value(hasItem(DEFAULT_ADDRESS_RU)))
            .andExpect(jsonPath("$.[*].addressUa").value(hasItem(DEFAULT_ADDRESS_UA)))
            .andExpect(jsonPath("$.[*].googlePlacesId").value(hasItem(DEFAULT_GOOGLE_PLACES_ID)))
            .andExpect(jsonPath("$.[*].geolat").value(hasItem(DEFAULT_GEOLAT.doubleValue())))
            .andExpect(jsonPath("$.[*].geolng").value(hasItem(DEFAULT_GEOLNG.doubleValue())))
            .andExpect(jsonPath("$.[*].photoUrl").value(hasItem(DEFAULT_PHOTO_URL)))
            .andExpect(jsonPath("$.[*].altName2").value(hasItem(DEFAULT_ALT_NAME_2)))
            .andExpect(jsonPath("$.[*].altName3").value(hasItem(DEFAULT_ALT_NAME_3)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }
}
