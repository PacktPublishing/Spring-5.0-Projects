package com.nilangpatel.worldgdp.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nilangpatel.worldgdp.model.CountryLanguage;


public class CountryLanguageRowMapper implements RowMapper<CountryLanguage> {

	public CountryLanguage mapRow(ResultSet rs, int rowNum) throws SQLException {
		CountryLanguage countryLng = new CountryLanguage();
		countryLng.setCountryCode(rs.getString("countrycode"));
		countryLng.setIsOfficial(rs.getString("isofficial"));
		countryLng.setLanguage(rs.getString("language"));
		countryLng.setPercentage(rs.getDouble("percentage"));
		return  countryLng;
	}

}
