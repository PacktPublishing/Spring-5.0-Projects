package com.nilangpatel.worldgdp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CountryGDP {

	private Short year;
	private Double value;
	
	public Short getYear() {
		return year;
	}
	public void setYear(Short year) {
		this.year = year;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
