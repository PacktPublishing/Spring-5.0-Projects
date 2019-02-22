package com.nilangpatel.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.nilangpatel.domain.Country;
import com.nilangpatel.domain.*; // for static metamodels
import com.nilangpatel.repository.CountryRepository;
import com.nilangpatel.service.dto.CountryCriteria;
import com.nilangpatel.service.dto.CountryDTO;
import com.nilangpatel.service.mapper.CountryMapper;

/**
 * Service for executing complex queries for Country entities in the database.
 * The main input is a {@link CountryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountryDTO} or a {@link Page} of {@link CountryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountryQueryService extends QueryService<Country> {

    private final Logger log = LoggerFactory.getLogger(CountryQueryService.class);

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    public CountryQueryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    /**
     * Return a {@link List} of {@link CountryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountryDTO> findByCriteria(CountryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Country> specification = createSpecification(criteria);
        return countryMapper.toDto(countryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CountryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountryDTO> findByCriteria(CountryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.findAll(specification, page)
            .map(countryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Country> specification = createSpecification(criteria);
        return countryRepository.count(specification);
    }

    /**
     * Function to convert CountryCriteria to a {@link Specification}
     */
    private Specification<Country> createSpecification(CountryCriteria criteria) {
        Specification<Country> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Country_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Country_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Country_.name));
            }
            if (criteria.getContinent() != null) {
                specification = specification.and(buildSpecification(criteria.getContinent(), Country_.continent));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), Country_.region));
            }
            if (criteria.getSurfaceArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurfaceArea(), Country_.surfaceArea));
            }
            if (criteria.getPopulation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPopulation(), Country_.population));
            }
            if (criteria.getLifeExpectancy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLifeExpectancy(), Country_.lifeExpectancy));
            }
            if (criteria.getLocalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalName(), Country_.localName));
            }
            if (criteria.getGovernmentForm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGovernmentForm(), Country_.governmentForm));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(Country_.cities, JoinType.LEFT).get(City_.id)));
            }
            if (criteria.getCountryLanguageId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryLanguageId(),
                    root -> root.join(Country_.countryLanguages, JoinType.LEFT).get(CountryLanguage_.id)));
            }
        }
        return specification;
    }
}
