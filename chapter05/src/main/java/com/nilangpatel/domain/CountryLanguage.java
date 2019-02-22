package com.nilangpatel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.nilangpatel.domain.enumeration.TrueFalse;

/**
 * A CountryLanguage.
 */
@Entity
@Table(name = "country_language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CountryLanguage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "language", nullable = false)
    private String language;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_official", nullable = false)
    private TrueFalse isOfficial;

    @NotNull
    @Column(name = "percentage", nullable = false)
    private Float percentage;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("countryLanguages")
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public CountryLanguage language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public TrueFalse getIsOfficial() {
        return isOfficial;
    }

    public CountryLanguage isOfficial(TrueFalse isOfficial) {
        this.isOfficial = isOfficial;
        return this;
    }

    public void setIsOfficial(TrueFalse isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Float getPercentage() {
        return percentage;
    }

    public CountryLanguage percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Country getCountry() {
        return country;
    }

    public CountryLanguage country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        CountryLanguage countryLanguage = (CountryLanguage) o;
        if (countryLanguage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryLanguage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryLanguage{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", isOfficial='" + getIsOfficial() + "'" +
            ", percentage=" + getPercentage() +
            "}";
    }
}
