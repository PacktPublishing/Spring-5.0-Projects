package com.packt.dal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.packt.model.City;
import com.packt.model.Country;

public class CountryRowMapper implements RowMapper<Country>{

	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Country c = new Country();
		c.setCode(rs.getString("code"));
		c.setName(rs.getString("name"));
		c.setContinent(rs.getString("continent"));
		c.setRegion(rs.getString("region"));
		c.setSurfaceArea(rs.getDouble("surface_area"));
		c.setIndepYear(rs.getShort("indep_year"));
		c.setPopulation(rs.getLong("population"));
		c.setLifeExpectancy(rs.getDouble("life_expectancy"));
		c.setGnp(rs.getDouble("gnp"));
		c.setLocalName(rs.getString("local_name"));
		c.setGovernmentForm(rs.getString("government_form"));
		c.setHeadOfState(rs.getString("head_of_state"));
		c.setCode2(rs.getString("code2"));
		if ( Long.valueOf(rs.getLong("capital")) != null ) {
			City city = new City();
			city.setId(rs.getLong("capital"));
			city.setName(rs.getString("capital_name"));
			c.setCapital(city);
		}
		return c;
	}
	

}
