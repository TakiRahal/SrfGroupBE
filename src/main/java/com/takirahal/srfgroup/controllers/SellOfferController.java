package com.takirahal.srfgroup.controllers;

import com.takirahal.srfgroup.dto.SellOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.services.SellOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sell-offers/")
public class SellOfferController {


    private final Logger log = LoggerFactory.getLogger(SellOfferController.class);

    private static final String ENTITY_NAME = "sellOffer";

    @Autowired
    SellOfferService sellOfferService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

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
                .created(new URI("/api/sell-offers/" + result.getId()))
                // .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
