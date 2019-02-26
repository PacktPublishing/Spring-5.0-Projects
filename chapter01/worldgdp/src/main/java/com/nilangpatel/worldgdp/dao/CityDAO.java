package com.nilangpatel.worldgdp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.nilangpatel.worldgdp.dao.mapper.CityRowMapper;
import com.nilangpatel.worldgdp.model.City;

import lombok.Setter;

//@Slf4j
@Service
@Setter
public class CityDAO {

	@Autowired NamedParameterJdbcTemplate namedParamJdbcTemplate;
	private static final Integer PAGE_SIZE = 10;
	
	public List<City> getCities(String countryCode){
		return getCities(countryCode, null);
	}
	
	public List<City> getCities(String countryCode, Integer pageNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", countryCode);
		if ( pageNo != null ) {
			Integer offset = (pageNo - 1) * PAGE_SIZE;
			params.put("offset", offset);
			params.put("size", PAGE_SIZE);
		}
		
		return namedParamJdbcTemplate.query("SELECT "
				+ " id, name, countrycode country_code, district, population "
				+ " FROM city WHERE countrycode = :code"
				+ " ORDER BY Population DESC"
				+ ((pageNo != null) ? " LIMIT :offset , :size " : ""),
				params, new CityRowMapper());
	}
	
	public City getCityDetail(Long cityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", cityId);
		return namedParamJdbcTemplate.queryForObject("SELECT id, "
				+ " name, countrycode country_code, "
				+ " district, population  "
				+ "	FROM city WHERE id = :id", 
				params, new CityRowMapper());
	}
	
	public Long addCity(String countryCode, City city) {
		
		SqlParameterSource paramSource = new MapSqlParameterSource(
				getMapForCity(countryCode, city));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParamJdbcTemplate.update("INSERT INTO city("
				+ " name, countrycode, "
				+ " district, population) "
				+ " VALUES (:name, :country_code, "
				+ " :district, :population )", 
				paramSource, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	public void deleteCity(Long cityId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", cityId);
		System.out.println("Deleting : {}"+ params);
		namedParamJdbcTemplate.update("DELETE FROM city WHERE id = :id", params);
	}
	
	private Map<String, Object> getMapForCity(String countryCode, City city){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", city.getName());
		map.put("country_code", countryCode);
		map.put("district", city.getDistrict());
		map.put("population", city.getPopulation());
		return map;
	}
}
