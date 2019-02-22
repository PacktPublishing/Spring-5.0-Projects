package com.nilangpatel.domain.enumeration;

/**
 * The Continent enumeration.
 */
public enum Continent {
	ASIA("Asia"), EUROPE("Europe"), NORTH_AMERICA("North America"), AFRICA("Africa"), OCEANIA("Oceania"), ANTARCTICA("Antarctica"), SOUTH_AMERICA("South America");
    private String name;
	Continent(String name){
		this.name=name;
	}
	public String getName() {
		return this.name;
	}
	public static Continent getContinent(String name) {
		Continent returnContinent = null;
		switch(name){
			case "Asia": returnContinent = Continent.ASIA;break;
			case "Europe": returnContinent = Continent.EUROPE;break;
			case "North America": returnContinent = Continent.NORTH_AMERICA;break;
			case "Africa": returnContinent = Continent.AFRICA;break;
			case "Oceania": returnContinent = Continent.OCEANIA;break;
			case "Antarctica": returnContinent = Continent.ANTARCTICA;break;
			case "South America": returnContinent = Continent.SOUTH_AMERICA;break;
			default: returnContinent = null;
		}
		return returnContinent;
	}

	public String test(){
		return "";
	}
}
