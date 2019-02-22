package com.nilangpatel.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.nilangpatel.domain.enumeration.Continent;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Country entity. This class is used in CountryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /countries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable {
    /**
     * Class for filtering Continent
     */
    public static class ContinentFilter extends Filter<Continent> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private ContinentFilter continent;

    private StringFilter region;

    private FloatFilter surfaceArea;

    private IntegerFilter population;

    private FloatFilter lifeExpectancy;

    private StringFilter localName;

    private StringFilter governmentForm;

    private LongFilter cityId;

    private LongFilter countryLanguageId;

    public CountryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ContinentFilter getContinent() {
        return continent;
    }

    public void setContinent(ContinentFilter continent) {
        this.continent = continent;
    }

    public StringFilter getRegion() {
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public FloatFilter getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(FloatFilter surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public IntegerFilter getPopulation() {
        return population;
    }

    public void setPopulation(IntegerFilter population) {
        this.population = population;
    }

    public FloatFilter getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(FloatFilter lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public StringFilter getLocalName() {
        return localName;
    }

    public void setLocalName(StringFilter localName) {
        this.localName = localName;
    }

    public StringFilter getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(StringFilter governmentForm) {
        this.governmentForm = governmentForm;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getCountryLanguageId() {
        return countryLanguageId;
    }

    public void setCountryLanguageId(LongFilter countryLanguageId) {
        this.countryLanguageId = countryLanguageId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountryCriteria that = (CountryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(continent, that.continent) &&
            Objects.equals(region, that.region) &&
            Objects.equals(surfaceArea, that.surfaceArea) &&
            Objects.equals(population, that.population) &&
            Objects.equals(lifeExpectancy, that.lifeExpectancy) &&
            Objects.equals(localName, that.localName) &&
            Objects.equals(governmentForm, that.governmentForm) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(countryLanguageId, that.countryLanguageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        continent,
        region,
        surfaceArea,
        population,
        lifeExpectancy,
        localName,
        governmentForm,
        cityId,
        countryLanguageId
        );
    }

    @Override
    public String toString() {
        return "CountryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (continent != null ? "continent=" + continent + ", " : "") +
                (region != null ? "region=" + region + ", " : "") +
                (surfaceArea != null ? "surfaceArea=" + surfaceArea + ", " : "") +
                (population != null ? "population=" + population + ", " : "") +
                (lifeExpectancy != null ? "lifeExpectancy=" + lifeExpectancy + ", " : "") +
                (localName != null ? "localName=" + localName + ", " : "") +
                (governmentForm != null ? "governmentForm=" + governmentForm + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
                (countryLanguageId != null ? "countryLanguageId=" + countryLanguageId + ", " : "") +
            "}";
    }

}
