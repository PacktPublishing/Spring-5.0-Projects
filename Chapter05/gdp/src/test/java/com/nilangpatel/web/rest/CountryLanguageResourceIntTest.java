package com.nilangpatel.web.rest;

import com.nilangpatel.GdpApp;

import com.nilangpatel.domain.CountryLanguage;
import com.nilangpatel.domain.Country;
import com.nilangpatel.repository.CountryLanguageRepository;
import com.nilangpatel.service.CountryLanguageService;
import com.nilangpatel.service.dto.CountryLanguageDTO;
import com.nilangpatel.service.mapper.CountryLanguageMapper;
import com.nilangpatel.web.rest.errors.ExceptionTranslator;

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

import com.nilangpatel.domain.enumeration.TrueFalse;
/**
 * Test class for the CountryLanguageResource REST controller.
 *
 * @see CountryLanguageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GdpApp.class)
public class CountryLanguageResourceIntTest {

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final TrueFalse DEFAULT_IS_OFFICIAL = TrueFalse.T;
    private static final TrueFalse UPDATED_IS_OFFICIAL = TrueFalse.F;

    private static final Float DEFAULT_PERCENTAGE = 1F;
    private static final Float UPDATED_PERCENTAGE = 2F;

    @Autowired
    private CountryLanguageRepository countryLanguageRepository;

    @Autowired
    private CountryLanguageMapper countryLanguageMapper;

    @Autowired
    private CountryLanguageService countryLanguageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCountryLanguageMockMvc;

    private CountryLanguage countryLanguage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountryLanguageResource countryLanguageResource = new CountryLanguageResource(countryLanguageService);
        this.restCountryLanguageMockMvc = MockMvcBuilders.standaloneSetup(countryLanguageResource)
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
    public static CountryLanguage createEntity(EntityManager em) {
        CountryLanguage countryLanguage = new CountryLanguage()
            .language(DEFAULT_LANGUAGE)
            .isOfficial(DEFAULT_IS_OFFICIAL)
            .percentage(DEFAULT_PERCENTAGE);
        // Add required entity
        Country country = CountryResourceIntTest.createEntity(em);
        em.persist(country);
        em.flush();
        countryLanguage.setCountry(country);
        return countryLanguage;
    }

    @Before
    public void initTest() {
        countryLanguage = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountryLanguage() throws Exception {
        int databaseSizeBeforeCreate = countryLanguageRepository.findAll().size();

        // Create the CountryLanguage
        CountryLanguageDTO countryLanguageDTO = countryLanguageMapper.toDto(countryLanguage);
        restCountryLanguageMockMvc.perform(post("/api/country-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryLanguageDTO)))
            .andExpect(status().isCreated());

        // Validate the CountryLanguage in the database
        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeCreate + 1);
        CountryLanguage testCountryLanguage = countryLanguageList.get(countryLanguageList.size() - 1);
        assertThat(testCountryLanguage.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testCountryLanguage.getIsOfficial()).isEqualTo(DEFAULT_IS_OFFICIAL);
        assertThat(testCountryLanguage.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createCountryLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryLanguageRepository.findAll().size();

        // Create the CountryLanguage with an existing ID
        countryLanguage.setId(1L);
        CountryLanguageDTO countryLanguageDTO = countryLanguageMapper.toDto(countryLanguage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryLanguageMockMvc.perform(post("/api/country-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryLanguage in the database
        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryLanguageRepository.findAll().size();
        // set the field null
        countryLanguage.setLanguage(null);

        // Create the CountryLanguage, which fails.
        CountryLanguageDTO countryLanguageDTO = countryLanguageMapper.toDto(countryLanguage);

        restCountryLanguageMockMvc.perform(post("/api/country-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryLanguageDTO)))
            .andExpect(status().isBadRequest());

        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsOfficialIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryLanguageRepository.findAll().size();
        // set the field null
        countryLanguage.setIsOfficial(null);

        // Create the CountryLanguage, which fails.
        CountryLanguageDTO countryLanguageDTO = countryLanguageMapper.toDto(countryLanguage);

        restCountryLanguageMockMvc.perform(post("/api/country-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryLanguageDTO)))
            .andExpect(status().isBadRequest());

        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryLanguageRepository.findAll().size();
        // set the field null
        countryLanguage.setPercentage(null);

        // Create the CountryLanguage, which fails.
        CountryLanguageDTO countryLanguageDTO = countryLanguageMapper.toDto(countryLanguage);

        restCountryLanguageMockMvc.perform(post("/api/country-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryLanguageDTO)))
            .andExpect(status().isBadRequest());

        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountryLanguages() throws Exception {
        // Initialize the database
        countryLanguageRepository.saveAndFlush(countryLanguage);

        // Get all the countryLanguageList
        restCountryLanguageMockMvc.perform(get("/api/country-languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryLanguage.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].isOfficial").value(hasItem(DEFAULT_IS_OFFICIAL.toString())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCountryLanguage() throws Exception {
        // Initialize the database
        countryLanguageRepository.saveAndFlush(countryLanguage);

        // Get the countryLanguage
        restCountryLanguageMockMvc.perform(get("/api/country-languages/{id}", countryLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countryLanguage.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.isOfficial").value(DEFAULT_IS_OFFICIAL.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCountryLanguage() throws Exception {
        // Get the countryLanguage
        restCountryLanguageMockMvc.perform(get("/api/country-languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountryLanguage() throws Exception {
        // Initialize the database
        countryLanguageRepository.saveAndFlush(countryLanguage);

        int databaseSizeBeforeUpdate = countryLanguageRepository.findAll().size();

        // Update the countryLanguage
        CountryLanguage updatedCountryLanguage = countryLanguageRepository.findById(countryLanguage.getId()).get();
        // Disconnect from session so that the updates on updatedCountryLanguage are not directly saved in db
        em.detach(updatedCountryLanguage);
        updatedCountryLanguage
            .language(UPDATED_LANGUAGE)
            .isOfficial(UPDATED_IS_OFFICIAL)
            .percentage(UPDATED_PERCENTAGE);
        CountryLanguageDTO countryLanguageDTO = countryLanguageMapper.toDto(updatedCountryLanguage);

        restCountryLanguageMockMvc.perform(put("/api/country-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryLanguageDTO)))
            .andExpect(status().isOk());

        // Validate the CountryLanguage in the database
        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeUpdate);
        CountryLanguage testCountryLanguage = countryLanguageList.get(countryLanguageList.size() - 1);
        assertThat(testCountryLanguage.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testCountryLanguage.getIsOfficial()).isEqualTo(UPDATED_IS_OFFICIAL);
        assertThat(testCountryLanguage.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCountryLanguage() throws Exception {
        int databaseSizeBeforeUpdate = countryLanguageRepository.findAll().size();

        // Create the CountryLanguage
        CountryLanguageDTO countryLanguageDTO = countryLanguageMapper.toDto(countryLanguage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryLanguageMockMvc.perform(put("/api/country-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryLanguage in the database
        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountryLanguage() throws Exception {
        // Initialize the database
        countryLanguageRepository.saveAndFlush(countryLanguage);

        int databaseSizeBeforeDelete = countryLanguageRepository.findAll().size();

        // Get the countryLanguage
        restCountryLanguageMockMvc.perform(delete("/api/country-languages/{id}", countryLanguage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CountryLanguage> countryLanguageList = countryLanguageRepository.findAll();
        assertThat(countryLanguageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryLanguage.class);
        CountryLanguage countryLanguage1 = new CountryLanguage();
        countryLanguage1.setId(1L);
        CountryLanguage countryLanguage2 = new CountryLanguage();
        countryLanguage2.setId(countryLanguage1.getId());
        assertThat(countryLanguage1).isEqualTo(countryLanguage2);
        countryLanguage2.setId(2L);
        assertThat(countryLanguage1).isNotEqualTo(countryLanguage2);
        countryLanguage1.setId(null);
        assertThat(countryLanguage1).isNotEqualTo(countryLanguage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryLanguageDTO.class);
        CountryLanguageDTO countryLanguageDTO1 = new CountryLanguageDTO();
        countryLanguageDTO1.setId(1L);
        CountryLanguageDTO countryLanguageDTO2 = new CountryLanguageDTO();
        assertThat(countryLanguageDTO1).isNotEqualTo(countryLanguageDTO2);
        countryLanguageDTO2.setId(countryLanguageDTO1.getId());
        assertThat(countryLanguageDTO1).isEqualTo(countryLanguageDTO2);
        countryLanguageDTO2.setId(2L);
        assertThat(countryLanguageDTO1).isNotEqualTo(countryLanguageDTO2);
        countryLanguageDTO1.setId(null);
        assertThat(countryLanguageDTO1).isNotEqualTo(countryLanguageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(countryLanguageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(countryLanguageMapper.fromId(null)).isNull();
    }
}
