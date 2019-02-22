package com.nilangpatel.service;

import com.nilangpatel.service.dto.CountryLanguageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CountryLanguage.
 */
public interface CountryLanguageService {

    /**
     * Save a countryLanguage.
     *
     * @param countryLanguageDTO the entity to save
     * @return the persisted entity
     */
    CountryLanguageDTO save(CountryLanguageDTO countryLanguageDTO);

    /**
     * Get all the countryLanguages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CountryLanguageDTO> findAll(Pageable pageable);


    /**
     * Get the "id" countryLanguage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CountryLanguageDTO> findOne(Long id);

    /**
     * Delete the "id" countryLanguage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
