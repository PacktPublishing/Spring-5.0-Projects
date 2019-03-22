package com.nilangpatel.test.config.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilangpatel.worldgdp.AppConfiguration;
import com.nilangpatel.worldgdp.dao.CountryDAO;
import com.nilangpatel.worldgdp.model.Country;


@RunWith(SpringRunner.class)
@SpringJUnitWebConfig(classes = {AppConfiguration.class})
public class CountryAPIControllerTest {
	
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired CountryDAO countryDao;
    
    @Autowired
    NamedParameterJdbcTemplate namedParamJdbcTemplate;
	
    @Before
    public void setup() {
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    	countryDao.setNamedParamJdbcTemplate(namedParamJdbcTemplate);
    }
	
	@Test
	public void testGetCountries() throws Exception {
		this.mockMvc.perform(get("/api/countries")
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$.list").isNotEmpty())
				.andExpect(jsonPath("$.count", is(239)));
	}
	
	@Test
	public void testEditCountry() throws Exception{
		String countryCode = "IND";
		Country country = countryDao.getCountryDetail(countryCode);
		country.setHeadOfState("Ram Nath Kovind");
		country.setLifeExpectancy(70d);
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult result = this.mockMvc.perform(
				post("/api/countries/" + countryCode)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(country))
			).andExpect(status().isOk())
			.andReturn();
		
		country = objectMapper.readValue(result.getResponse().getContentAsString(), Country.class);
		assertThat(country.getHeadOfState()).isEqualTo("Ram Nath Kovind");
	}
}
