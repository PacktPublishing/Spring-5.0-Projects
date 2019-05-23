package com.nilangpatel.web.rest;

import com.nilangpatel.GdpApp;

import com.nilangpatel.domain.Country;
import com.nilangpatel.domain.City;
import com.nilangpatel.domain.CountryLanguage;
import com.nilangpatel.repository.CountryRepository;
import com.nilangpatel.service.CountryService;
import com.nilangpatel.service.dto.CountryDTO;
import com.nilangpatel.service.mapper.CountryMapper;
import com.nilangpatel.web.rest.errors.ExceptionTranslator;
import com.nilangpatel.service.dto.CountryCriteria;
import com.nilangpatel.service.CountryQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.nilangpatel.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nilangpatel.domain.enumeration.Continent;
/**
 * Test class for the CountryResource REST controller.
 *
 * @see CountryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdpApp.class)
public class CountryResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Continent DEFAULT_CONTINENT = Continent.ASIA;
    private static final Continent UPDATED_CONTINENT = Continent.EUROPE;

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final Float DEFAULT_SURFACE_AREA = 1F;
    private static final Float UPDATED_SURFACE_AREA = 2F;

    private static final Integer DEFAULT_POPULATION = 1;
    private static final Integer UPDATED_POPULATION = 2;

    private static final Float DEFAULT_LIFE_EXPECTANCY = 1F;
    private static final Float UPDATED_LIFE_EXPECTANCY = 2F;

    private static final String DEFAULT_LOCAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GOVERNMENT_FORM = "AAAAAAAAAA";
    private static final String UPDATED_GOVERNMENT_FORM = "BBBBBBBBBB";

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryQueryService countryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCountryMockMvc;

    private Country country;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountryResource countryResource = new CountryResource(countryService, countryQueryService);
        this.restCountryMockMvc = MockMvcBuilders.standaloneSetup(countryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .continent(DEFAULT_CONTINENT)
            .region(DEFAULT_REGION)
            .surfaceArea(DEFAULT_SURFACE_AREA)
            .population(DEFAULT_POPULATION)
            .lifeExpectancy(DEFAULT_LIFE_EXPECTANCY)
            .localName(DEFAULT_LOCAL_NAME)
            .governmentForm(DEFAULT_GOVERNMENT_FORM);
        return country;
    }

    @Before
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testCountry.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCountry.getSurfaceArea()).isEqualTo(DEFAULT_SURFACE_AREA);
        assertThat(testCountry.getPopulation()).isEqualTo(DEFAULT_POPULATION);
        assertThat(testCountry.getLifeExpectancy()).isEqualTo(DEFAULT_LIFE_EXPECTANCY);
        assertThat(testCountry.getLocalName()).isEqualTo(DEFAULT_LOCAL_NAME);
        assertThat(testCountry.getGovernmentForm()).isEqualTo(DEFAULT_GOVERNMENT_FORM);
    }

    @Test
    @Transactional
    public void createCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country with an existing ID
        country.setId(1L);
        CountryDTO countryDTO = countryMapper.toDto(country);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCode(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setName(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContinentIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setContinent(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setRegion(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurfaceAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setSurfaceArea(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPopulationIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setPopulation(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setLocalName(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGovernmentFormIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setGovernmentForm(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].surfaceArea").value(hasItem(DEFAULT_SURFACE_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].population").value(hasItem(DEFAULT_POPULATION)))
            .andExpect(jsonPath("$.[*].lifeExpectancy").value(hasItem(DEFAULT_LIFE_EXPECTANCY.doubleValue())))
            .andExpect(jsonPath("$.[*].localName").value(hasItem(DEFAULT_LOCAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].governmentForm").value(hasItem(DEFAULT_GOVERNMENT_FORM.toString())));
    }
    
    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.surfaceArea").value(DEFAULT_SURFACE_AREA.doubleValue()))
            .andExpect(jsonPath("$.population").value(DEFAULT_POPULATION))
            .andExpect(jsonPath("$.lifeExpectancy").value(DEFAULT_LIFE_EXPECTANCY.doubleValue()))
            .andExpect(jsonPath("$.localName").value(DEFAULT_LOCAL_NAME.toString()))
            .andExpect(jsonPath("$.governmentForm").value(DEFAULT_GOVERNMENT_FORM.toString()));
    }

    @Test
    @Transactional
    public void getAllCountriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code equals to DEFAULT_CODE
        defaultCountryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the countryList where code equals to UPDATED_CODE
        defaultCountryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCountryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the countryList where code equals to UPDATED_CODE
        defaultCountryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where code is not null
        defaultCountryShouldBeFound("code.specified=true");

        // Get all the countryList where code is null
        defaultCountryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name equals to DEFAULT_NAME
        defaultCountryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name is not null
        defaultCountryShouldBeFound("name.specified=true");

        // Get all the countryList where name is null
        defaultCountryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByContinentIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where continent equals to DEFAULT_CONTINENT
        defaultCountryShouldBeFound("continent.equals=" + DEFAULT_CONTINENT);

        // Get all the countryList where continent equals to UPDATED_CONTINENT
        defaultCountryShouldNotBeFound("continent.equals=" + UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void getAllCountriesByContinentIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where continent in DEFAULT_CONTINENT or UPDATED_CONTINENT
        defaultCountryShouldBeFound("continent.in=" + DEFAULT_CONTINENT + "," + UPDATED_CONTINENT);

        // Get all the countryList where continent equals to UPDATED_CONTINENT
        defaultCountryShouldNotBeFound("continent.in=" + UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void getAllCountriesByContinentIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where continent is not null
        defaultCountryShouldBeFound("continent.specified=true");

        // Get all the countryList where continent is null
        defaultCountryShouldNotBeFound("continent.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where region equals to DEFAULT_REGION
        defaultCountryShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the countryList where region equals to UPDATED_REGION
        defaultCountryShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllCountriesByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where region in DEFAULT_REGION or UPDATED_REGION
        defaultCountryShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the countryList where region equals to UPDATED_REGION
        defaultCountryShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllCountriesByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where region is not null
        defaultCountryShouldBeFound("region.specified=true");

        // Get all the countryList where region is null
        defaultCountryShouldNotBeFound("region.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesBySurfaceAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where surfaceArea equals to DEFAULT_SURFACE_AREA
        defaultCountryShouldBeFound("surfaceArea.equals=" + DEFAULT_SURFACE_AREA);

        // Get all the countryList where surfaceArea equals to UPDATED_SURFACE_AREA
        defaultCountryShouldNotBeFound("surfaceArea.equals=" + UPDATED_SURFACE_AREA);
    }

    @Test
    @Transactional
    public void getAllCountriesBySurfaceAreaIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where surfaceArea in DEFAULT_SURFACE_AREA or UPDATED_SURFACE_AREA
        defaultCountryShouldBeFound("surfaceArea.in=" + DEFAULT_SURFACE_AREA + "," + UPDATED_SURFACE_AREA);

        // Get all the countryList where surfaceArea equals to UPDATED_SURFACE_AREA
        defaultCountryShouldNotBeFound("surfaceArea.in=" + UPDATED_SURFACE_AREA);
    }

    @Test
    @Transactional
    public void getAllCountriesBySurfaceAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where surfaceArea is not null
        defaultCountryShouldBeFound("surfaceArea.specified=true");

        // Get all the countryList where surfaceArea is null
        defaultCountryShouldNotBeFound("surfaceArea.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByPopulationIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where population equals to DEFAULT_POPULATION
        defaultCountryShouldBeFound("population.equals=" + DEFAULT_POPULATION);

        // Get all the countryList where population equals to UPDATED_POPULATION
        defaultCountryShouldNotBeFound("population.equals=" + UPDATED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByPopulationIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where population in DEFAULT_POPULATION or UPDATED_POPULATION
        defaultCountryShouldBeFound("population.in=" + DEFAULT_POPULATION + "," + UPDATED_POPULATION);

        // Get all the countryList where population equals to UPDATED_POPULATION
        defaultCountryShouldNotBeFound("population.in=" + UPDATED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByPopulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where population is not null
        defaultCountryShouldBeFound("population.specified=true");

        // Get all the countryList where population is null
        defaultCountryShouldNotBeFound("population.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByPopulationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where population greater than or equals to DEFAULT_POPULATION
        defaultCountryShouldBeFound("population.greaterOrEqualThan=" + DEFAULT_POPULATION);

        // Get all the countryList where population greater than or equals to UPDATED_POPULATION
        defaultCountryShouldNotBeFound("population.greaterOrEqualThan=" + UPDATED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByPopulationIsLessThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where population less than or equals to DEFAULT_POPULATION
        defaultCountryShouldNotBeFound("population.lessThan=" + DEFAULT_POPULATION);

        // Get all the countryList where population less than or equals to UPDATED_POPULATION
        defaultCountryShouldBeFound("population.lessThan=" + UPDATED_POPULATION);
    }


    @Test
    @Transactional
    public void getAllCountriesByLifeExpectancyIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lifeExpectancy equals to DEFAULT_LIFE_EXPECTANCY
        defaultCountryShouldBeFound("lifeExpectancy.equals=" + DEFAULT_LIFE_EXPECTANCY);

        // Get all the countryList where lifeExpectancy equals to UPDATED_LIFE_EXPECTANCY
        defaultCountryShouldNotBeFound("lifeExpectancy.equals=" + UPDATED_LIFE_EXPECTANCY);
    }

    @Test
    @Transactional
    public void getAllCountriesByLifeExpectancyIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lifeExpectancy in DEFAULT_LIFE_EXPECTANCY or UPDATED_LIFE_EXPECTANCY
        defaultCountryShouldBeFound("lifeExpectancy.in=" + DEFAULT_LIFE_EXPECTANCY + "," + UPDATED_LIFE_EXPECTANCY);

        // Get all the countryList where lifeExpectancy equals to UPDATED_LIFE_EXPECTANCY
        defaultCountryShouldNotBeFound("lifeExpectancy.in=" + UPDATED_LIFE_EXPECTANCY);
    }

    @Test
    @Transactional
    public void getAllCountriesByLifeExpectancyIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where lifeExpectancy is not null
        defaultCountryShouldBeFound("lifeExpectancy.specified=true");

        // Get all the countryList where lifeExpectancy is null
        defaultCountryShouldNotBeFound("lifeExpectancy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByLocalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where localName equals to DEFAULT_LOCAL_NAME
        defaultCountryShouldBeFound("localName.equals=" + DEFAULT_LOCAL_NAME);

        // Get all the countryList where localName equals to UPDATED_LOCAL_NAME
        defaultCountryShouldNotBeFound("localName.equals=" + UPDATED_LOCAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByLocalNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where localName in DEFAULT_LOCAL_NAME or UPDATED_LOCAL_NAME
        defaultCountryShouldBeFound("localName.in=" + DEFAULT_LOCAL_NAME + "," + UPDATED_LOCAL_NAME);

        // Get all the countryList where localName equals to UPDATED_LOCAL_NAME
        defaultCountryShouldNotBeFound("localName.in=" + UPDATED_LOCAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByLocalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where localName is not null
        defaultCountryShouldBeFound("localName.specified=true");

        // Get all the countryList where localName is null
        defaultCountryShouldNotBeFound("localName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByGovernmentFormIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where governmentForm equals to DEFAULT_GOVERNMENT_FORM
        defaultCountryShouldBeFound("governmentForm.equals=" + DEFAULT_GOVERNMENT_FORM);

        // Get all the countryList where governmentForm equals to UPDATED_GOVERNMENT_FORM
        defaultCountryShouldNotBeFound("governmentForm.equals=" + UPDATED_GOVERNMENT_FORM);
    }

    @Test
    @Transactional
    public void getAllCountriesByGovernmentFormIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where governmentForm in DEFAULT_GOVERNMENT_FORM or UPDATED_GOVERNMENT_FORM
        defaultCountryShouldBeFound("governmentForm.in=" + DEFAULT_GOVERNMENT_FORM + "," + UPDATED_GOVERNMENT_FORM);

        // Get all the countryList where governmentForm equals to UPDATED_GOVERNMENT_FORM
        defaultCountryShouldNotBeFound("governmentForm.in=" + UPDATED_GOVERNMENT_FORM);
    }

    @Test
    @Transactional
    public void getAllCountriesByGovernmentFormIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where governmentForm is not null
        defaultCountryShouldBeFound("governmentForm.specified=true");

        // Get all the countryList where governmentForm is null
        defaultCountryShouldNotBeFound("governmentForm.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        City city = CityResourceIntTest.createEntity(em);
        em.persist(city);
        em.flush();
        country.addCity(city);
        countryRepository.saveAndFlush(country);
        Long cityId = city.getId();

        // Get all the countryList where city equals to cityId
        defaultCountryShouldBeFound("cityId.equals=" + cityId);

        // Get all the countryList where city equals to cityId + 1
        defaultCountryShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }


    @Test
    @Transactional
    public void getAllCountriesByCountryLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        CountryLanguage countryLanguage = CountryLanguageResourceIntTest.createEntity(em);
        em.persist(countryLanguage);
        em.flush();
        country.addCountryLanguage(countryLanguage);
        countryRepository.saveAndFlush(country);
        Long countryLanguageId = countryLanguage.getId();

        // Get all the countryList where countryLanguage equals to countryLanguageId
        defaultCountryShouldBeFound("countryLanguageId.equals=" + countryLanguageId);

        // Get all the countryList where countryLanguage equals to countryLanguageId + 1
        defaultCountryShouldNotBeFound("countryLanguageId.equals=" + (countryLanguageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCountryShouldBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].surfaceArea").value(hasItem(DEFAULT_SURFACE_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].population").value(hasItem(DEFAULT_POPULATION)))
            .andExpect(jsonPath("$.[*].lifeExpectancy").value(hasItem(DEFAULT_LIFE_EXPECTANCY.doubleValue())))
            .andExpect(jsonPath("$.[*].localName").value(hasItem(DEFAULT_LOCAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].governmentForm").value(hasItem(DEFAULT_GOVERNMENT_FORM.toString())));

        // Check, that the count call also returns 1
        restCountryMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCountryShouldNotBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .continent(UPDATED_CONTINENT)
            .region(UPDATED_REGION)
            .surfaceArea(UPDATED_SURFACE_AREA)
            .population(UPDATED_POPULATION)
            .lifeExpectancy(UPDATED_LIFE_EXPECTANCY)
            .localName(UPDATED_LOCAL_NAME)
            .governmentForm(UPDATED_GOVERNMENT_FORM);
        CountryDTO countryDTO = countryMapper.toDto(updatedCountry);

        restCountryMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testCountry.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCountry.getSurfaceArea()).isEqualTo(UPDATED_SURFACE_AREA);
        assertThat(testCountry.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testCountry.getLifeExpectancy()).isEqualTo(UPDATED_LIFE_EXPECTANCY);
        assertThat(testCountry.getLocalName()).isEqualTo(UPDATED_LOCAL_NAME);
        assertThat(testCountry.getGovernmentForm()).isEqualTo(UPDATED_GOVERNMENT_FORM);
    }

    @Test
    @Transactional
    public void updateNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Get the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = new Country();
        country1.setId(1L);
        Country country2 = new Country();
        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);
        country2.setId(2L);
        assertThat(country1).isNotEqualTo(country2);
        country1.setId(null);
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryDTO.class);
        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setId(1L);
        CountryDTO countryDTO2 = new CountryDTO();
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO2.setId(countryDTO1.getId());
        assertThat(countryDTO1).isEqualTo(countryDTO2);
        countryDTO2.setId(2L);
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO1.setId(null);
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(countryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(countryMapper.fromId(null)).isNull();
    }
}
