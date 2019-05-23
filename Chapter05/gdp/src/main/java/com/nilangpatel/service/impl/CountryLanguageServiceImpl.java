package com.nilangpatel.service.impl;

import com.nilangpatel.service.CountryLanguageService;
import com.nilangpatel.domain.CountryLanguage;
import com.nilangpatel.repository.CountryLanguageRepository;
import com.nilangpatel.service.dto.CountryLanguageDTO;
import com.nilangpatel.service.mapper.CountryLanguageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CountryLanguage.
 */
@Service
@Transactional
public class CountryLanguageServiceImpl implements CountryLanguageService {

    private final Logger log = LoggerFactory.getLogger(CountryLanguageServiceImpl.class);

    private final CountryLanguageRepository countryLanguageRepository;

    private final CountryLanguageMapper countryLanguageMapper;

    public CountryLanguageServiceImpl(CountryLanguageRepository countryLanguageRepository, CountryLanguageMapper countryLanguageMapper) {
        this.countryLanguageRepository = countryLanguageRepository;
        this.countryLanguageMapper = countryLanguageMapper;
    }

    /**
     * Save a countryLanguage.
     *
     * @param countryLanguageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CountryLanguageDTO save(CountryLanguageDTO countryLanguageDTO) {
        log.debug("Request to save CountryLanguage : {}", countryLanguageDTO);

        CountryLanguage countryLanguage = countryLanguageMapper.toEntity(countryLanguageDTO);
        countryLanguage = countryLanguageRepository.save(countryLanguage);
        return countryLanguageMapper.toDto(countryLanguage);
    }

    /**
     * Get all the countryLanguages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CountryLanguageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CountryLanguages");
        return countryLanguageRepository.findAll(pageable)
            .map(countryLanguageMapper::toDto);
    }


    /**
     * Get one countryLanguage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CountryLanguageDTO> findOne(Long id) {
        log.debug("Request to get CountryLanguage : {}", id);
        return countryLanguageRepository.findById(id)
            .map(countryLanguageMapper::toDto);
    }

    /**
     * Delete the countryLanguage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CountryLanguage : {}", id);
        countryLanguageRepository.deleteById(id);
    }
}
