package com.nilangpatel.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.nilangpatel.service.CountryQueryService;
import com.nilangpatel.service.CountryService;
import com.nilangpatel.service.dto.CountryCriteria;
import com.nilangpatel.service.dto.CountryDTO;
import com.nilangpatel.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api/open")
public class GenericRestResource {

	private final Logger log = LoggerFactory.getLogger(GenericRestResource.class);

    private final CountryQueryService countryQueryService;
    
    private final CountryService countryService;

    public GenericRestResource(CountryService countryService, CountryQueryService countryQueryService) {
        this.countryQueryService = countryQueryService;
        this.countryService = countryService;
    }
    
    @GetMapping("/search-countries")
    @Timed
    public ResponseEntity<List<CountryDTO>> getAllCountriesForGdp(CountryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get a page of Countries");
        Page<CountryDTO> page = countryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/open/search-countries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/show-gdp/{id}")
    @Timed
    public ResponseEntity<CountryDTO> getCountryDetails(@PathVariable Long id) {
        log.debug("Get Country Details to show GDP information");
        CountryDTO countryDto = new CountryDTO();
        Optional<CountryDTO> countryData  = countryService.findOne(id);
        return ResponseEntity.ok().body(countryData.orElse(countryDto));
    }
}
