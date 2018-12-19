package com.packt.dal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.packt.model.CountryLanguage;

public class CountryLanguageRowMapper implements RowMapper<CountryLanguage> {

	@Override
	public CountryLanguage mapRow(ResultSet rs, int rowNum) throws SQLException {
		CountryLanguage cl = new CountryLanguage();
		cl.setCountryCode(rs.getString("countrycode"));
		cl.setIsOfficial(rs.getString("isofficial"));
		cl.setLanguage(rs.getString("language"));
		cl.setPercentage(rs.getDouble("percentage"));
		return  cl;
	}

}
