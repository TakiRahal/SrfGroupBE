package com.takirahal.srfgroup.controllers;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.dto.filter.OfferFilter;
import com.takirahal.srfgroup.services.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/offer/")
public class OfferController {

    private final Logger log = LoggerFactory.getLogger(OfferController.class);

    @Autowired
    OfferService offerService;

    /**
     * {@code GET  /offers} : get all the offers.
     *
     * @param pageable the pagination information.
     // * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offers in body.
     */
    @GetMapping("public")
    public ResponseEntity<Page<OfferDTO>> getAllOffers(OfferFilter offerFilter, Pageable pageable) {
        log.debug("REST request to get Offers by criteria: {}", pageable);
        Page<OfferDTO> page = offerService.findByCriteria(offerFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}