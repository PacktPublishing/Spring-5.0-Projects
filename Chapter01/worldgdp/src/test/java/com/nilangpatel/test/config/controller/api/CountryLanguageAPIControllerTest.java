package com.nilangpatel.test.config.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilangpatel.worldgdp.AppConfiguration;
import com.nilangpatel.worldgdp.dao.CountryLanguageDAO;
import com.nilangpatel.worldgdp.model.CountryLanguage;


//@Slf4j
@RunWith(SpringRunner.class)
@SpringJUnitWebConfig(classes = {AppConfiguration.class})
public class CountryLanguageAPIControllerTest {
	
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired CountryLanguageDAO cLanguageDao;
    
    @Autowired
	NamedParameterJdbcTemplate namedParamJdbcTemplate;
	
    @Before
    public void setup() {
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    	cLanguageDao.setNamedParamJdbcTemplate(namedParamJdbcTemplate);
    }
	
	@Test
	public void testGetLanguages() throws Exception {
		String countryCode = "IND";
		this.mockMvc.perform(get("/api/languages/{countryCode}", countryCode)
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()", is(10)))
				.andExpect(jsonPath("$[0].language", is("Hindi")));
	}
	
	@Test
	public void testAddLanguage() throws Exception{
		String countryCode = "IND";
		
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(countryCode);
		cl.setIsOfficial("1");
		cl.setLanguage("TEST");
		cl.setPercentage(100d);
		
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult result = this.mockMvc.perform(
				post("/api/languages/{countryCode}",countryCode)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cl))
			).andExpect(status().isOk())
			.andReturn();
		
		List<CountryLanguage> langs = cLanguageDao.getLanguages(countryCode, 1);
		CountryLanguage first = langs.get(0);
		assertThat(first.getLanguage()).isEqualTo("TEST");
		cLanguageDao.deleteLanguage(countryCode, first.getLanguage());
	}
	
	@Test
	public void testAddLanguage_DuplicateLang() throws Exception{
		String countryCode = "IND";
		
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(countryCode);
		cl.setIsOfficial("1");
		cl.setLanguage("TEST");
		cl.setPercentage(100d);
		
		cLanguageDao.addLanguage(countryCode, cl);
		
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult result = this.mockMvc.perform(
				post("/api/languages/{countryCode}",countryCode)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cl))
			).andExpect(status().isBadRequest())
			.andReturn();
		
		cLanguageDao.deleteLanguage(countryCode, cl.getLanguage());
	}
	
	@Test
	public void testDeleteCity() throws Exception {
		String countryCode = "IND";
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(countryCode);
		cl.setIsOfficial("1");
		cl.setLanguage("TEST");
		cl.setPercentage(100d);
		
		cLanguageDao.addLanguage(countryCode, cl);
		this.mockMvc.perform(
				delete("/api/languages/{countryCode}/language/{language}",
					countryCode, cl.getLanguage())
			).andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());
		
		List<CountryLanguage> langs = cLanguageDao.getLanguages(countryCode, 1);
		CountryLanguage first = langs.get(0);
		assertThat(first.getLanguage()).isEqualTo("Hindi");
	}
}
