package com.packt.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.packt.model.CountryGDP;

@Service
public class WorldBankApiClient {

	String API_BASE_URL = "http://api.worldbank.org";
	String GDP_URL = "/countries/%s/indicators/NY.GDP.MKTP.CD?"
			+ "format=json&date=2007:2017";
	
	public List<CountryGDP> getGDP(String countryCode) {
		 //http://api.worldbank.org/countries/BR/indicators/NY.GDP.MKTP.CD?format=json&date=2007:2017
		WebClient webClient = WebClient.create(API_BASE_URL);
		List responseBody = webClient.get()
			.uri(String.format(GDP_URL, countryCode))
			.exchange()
			.flatMap( response -> response.bodyToMono(List.class))
			.block();
		//the second element is the actual data and its an array of object
		List dataList = (List) responseBody.get(1);
		List<CountryGDP> data = new ArrayList<>();
		for ( Object gdpObject : dataList ) {
			Map<String, Object> gdpMap = (Map<String, Object>)gdpObject;
			String valueStr = (String)gdpMap.get("value");
			String yearStr = gdpMap.get("date").toString();
			CountryGDP gdp = new CountryGDP();
			gdp.setValue(valueStr != null ? Double.valueOf(valueStr) : null);
			gdp.setYear(Short.valueOf(yearStr));
			data.add(gdp);
		}
		
		return data;
	}
}
