package com.nilangpatel.service;

import com.nilangpatel.service.dto.CityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing City.
 */
public interface CityService {

    /**
     * Save a city.
     *
     * @param cityDTO the entity to save
     * @return the persisted entity
     */
    CityDTO save(CityDTO cityDTO);

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" city.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CityDTO> findOne(Long id);

    /**
     * Delete the "id" city.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
