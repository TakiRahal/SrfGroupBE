package com.takirahal.srfgroup.offer.controllers;

import com.takirahal.srfgroup.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.offer.dto.filter.SellOfferFilter;
import com.takirahal.srfgroup.offer.services.SellOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/sell-offer/")
public class SellOfferController {


    private final Logger log = LoggerFactory.getLogger(SellOfferController.class);

    private static final String ENTITY_NAME = "sellOffer";

    @Autowired
    SellOfferService sellOfferService;

    /**
     * {@code POST  /sell-offers} : Create a new sellOffer.
     *
     * @param sellOfferDTO the sellOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sellOfferDTO, or with status {@code 400 (Bad Request)} if the sellOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<SellOfferDTO> createSellOffer(@RequestBody SellOfferDTO sellOfferDTO) throws URISyntaxException {
        log.debug("REST request to save SellOffer : {}", sellOfferDTO);
        if (sellOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new sellOffer cannot already have an ID idexists");
        }
        SellOfferDTO result = sellOfferService.save(sellOfferDTO);

        return ResponseEntity
                .created(new URI("/api/sell-offer/" + result.getId()))
                // .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     *
     * @param sellOfferFilter
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<SellOfferDTO>> getAllOffersForSell(SellOfferFilter sellOfferFilter, Pageable pageable) {
        log.debug("REST request to get SellOffers public by criteria: {}", sellOfferFilter);
        Page<SellOfferDTO> page = sellOfferService.findByCriteria(sellOfferFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
