package com.nilangpatel.worldgdp.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nilangpatel.worldgdp.model.City;
import com.nilangpatel.worldgdp.model.Country;

public class CountryRowMapper implements RowMapper<Country>{

	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Country country = new Country();
		country.setCode(rs.getString("code"));
		country.setName(rs.getString("name"));
		country.setContinent(rs.getString("continent"));
		country.setRegion(rs.getString("region"));
		country.setSurfaceArea(rs.getDouble("surface_area"));
		country.setIndepYear(rs.getShort("indep_year"));
		country.setPopulation(rs.getLong("population"));
		country.setLifeExpectancy(rs.getDouble("life_expectancy"));
		country.setGnp(rs.getDouble("gnp"));
		country.setLocalName(rs.getString("local_name"));
		country.setGovernmentForm(rs.getString("government_form"));
		country.setHeadOfState(rs.getString("head_of_state"));
		country.setCode2(rs.getString("code2"));
		if ( Long.valueOf(rs.getLong("capital")) != null ) {
			City city = new City();
			city.setId(rs.getLong("capital"));
			city.setName(rs.getString("capital_name"));
			country.setCapital(city);
		}
		return country;
	}
	

}
