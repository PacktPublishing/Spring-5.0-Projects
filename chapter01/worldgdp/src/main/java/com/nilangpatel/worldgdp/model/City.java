package com.nilangpatel.worldgdp.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class City {

	private Long id;
	
	@NotNull 
	@Size(max = 35) 
	private String name;
	
	@NotNull 
	@Size(max = 3, min = 3) 
	private String countryCode;
	
	private Country country;
	
	@NotNull 
	@Size(max = 20) 
	private String district;
	
	@NotNull 
	private Long population;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}
	
	
}
