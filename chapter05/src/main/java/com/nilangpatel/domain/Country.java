package com.nilangpatel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.nilangpatel.domain.converter.ContinentEnumConvertor;
import com.nilangpatel.domain.enumeration.Continent;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "code", length = 3, nullable = false)
    private String code;

    @NotNull
    @Size(max = 52)
    @Column(name = "name", length = 52, nullable = false)
    private String name;

    @NotNull
    //@Enumerated(EnumType.STRING)
    @Convert(converter=ContinentEnumConvertor.class)
    @Column(name = "continent", nullable = false)
    private Continent continent;

    @NotNull
    @Size(max = 26)
    @Column(name = "region", length = 26, nullable = false)
    private String region;

    @NotNull
    @Column(name = "surface_area", nullable = false)
    private Float surfaceArea;

    @NotNull
    @Column(name = "population", nullable = false)
    private Integer population;

    @Column(name = "life_expectancy")
    private Float lifeExpectancy;

    @NotNull
    @Size(max = 45)
    @Column(name = "local_name", length = 45, nullable = false)
    private String localName;

    @NotNull
    @Size(max = 45)
    @Column(name = "government_form", length = 45, nullable = false)
    private String governmentForm;

    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<City> cities = new HashSet<>();
    @OneToMany(mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CountryLanguage> countryLanguages = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Country code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public Country continent(Continent continent) {
        this.continent = continent;
        return this;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public Country region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Float getSurfaceArea() {
        return surfaceArea;
    }

    public Country surfaceArea(Float surfaceArea) {
        this.surfaceArea = surfaceArea;
        return this;
    }

    public void setSurfaceArea(Float surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Integer getPopulation() {
        return population;
    }

    public Country population(Integer population) {
        this.population = population;
        return this;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Float getLifeExpectancy() {
        return lifeExpectancy;
    }

    public Country lifeExpectancy(Float lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
        return this;
    }

    public void setLifeExpectancy(Float lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public String getLocalName() {
        return localName;
    }

    public Country localName(String localName) {
        this.localName = localName;
        return this;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public Country governmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
        return this;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public Set<City> getCities() {
        return cities;
    }

    public Country cities(Set<City> cities) {
        this.cities = cities;
        return this;
    }

    public Country addCity(City city) {
        this.cities.add(city);
        city.setCountry(this);
        return this;
    }

    public Country removeCity(City city) {
        this.cities.remove(city);
        city.setCountry(null);
        return this;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public Set<CountryLanguage> getCountryLanguages() {
        return countryLanguages;
    }

    public Country countryLanguages(Set<CountryLanguage> countryLanguages) {
        this.countryLanguages = countryLanguages;
        return this;
    }

    public Country addCountryLanguage(CountryLanguage countryLanguage) {
        this.countryLanguages.add(countryLanguage);
        countryLanguage.setCountry(this);
        return this;
    }

    public Country removeCountryLanguage(CountryLanguage countryLanguage) {
        this.countryLanguages.remove(countryLanguage);
        countryLanguage.setCountry(null);
        return this;
    }

    public void setCountryLanguages(Set<CountryLanguage> countryLanguages) {
        this.countryLanguages = countryLanguages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", continent='" + getContinent() + "'" +
            ", region='" + getRegion() + "'" +
            ", surfaceArea=" + getSurfaceArea() +
            ", population=" + getPopulation() +
            ", lifeExpectancy=" + getLifeExpectancy() +
            ", localName='" + getLocalName() + "'" +
            ", governmentForm='" + getGovernmentForm() + "'" +
            "}";
    }
}
