package com.takirahal.srfgroup.offer.controllers;

import com.takirahal.srfgroup.dto.RentOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.offer.services.RentOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/rent-offer/")
public class RentOfferController {

    private final Logger log = LoggerFactory.getLogger(RentOfferController.class);

    @Autowired
    RentOfferService rentOfferService;

    /**
     * {@code POST  /rent-offers} : Create a new rentOffer.
     *
     * @param rentOfferDTO the rentOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentOfferDTO, or with status {@code 400 (Bad Request)} if the rentOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<RentOfferDTO> createRentOffer(@RequestBody RentOfferDTO rentOfferDTO) throws URISyntaxException {
        log.debug("REST request to save RentOffer : {}", rentOfferDTO);
        if (rentOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new rentOffer cannot already have an ID idexists");
        }
        RentOfferDTO result = rentOfferService.save(rentOfferDTO);

        return ResponseEntity
                .created(new URI("/api/rent-offer/" + result.getId()))
                // .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
