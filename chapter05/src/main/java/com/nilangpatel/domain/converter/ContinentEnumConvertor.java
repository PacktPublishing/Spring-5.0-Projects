package com.nilangpatel.domain.converter;

import javax.persistence.AttributeConverter;

import com.nilangpatel.domain.enumeration.Continent;

public class ContinentEnumConvertor implements AttributeConverter<Continent, String>{
	@Override
	public String convertToDatabaseColumn(Continent continent) {
		return continent.getName();
	}
	@Override
	public Continent convertToEntityAttribute(String continentValue) {
		return Continent.getContinent(continentValue);
	}
}
