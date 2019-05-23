package com.nilangpatel.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.nilangpatel.domain.enumeration.TrueFalse;

/**
 * A DTO for the CountryLanguage entity.
 */
public class CountryLanguageDTO implements Serializable {

    private Long id;

    @NotNull
    private String language;

    @NotNull
    private TrueFalse isOfficial;

    @NotNull
    private Float percentage;

    private Long countryId;

    private String countryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public TrueFalse getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(TrueFalse isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryLanguageDTO countryLanguageDTO = (CountryLanguageDTO) o;
        if (countryLanguageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryLanguageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryLanguageDTO{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", isOfficial='" + getIsOfficial() + "'" +
            ", percentage=" + getPercentage() +
            ", country=" + getCountryId() +
            ", country='" + getCountryName() + "'" +
            "}";
    }
}
