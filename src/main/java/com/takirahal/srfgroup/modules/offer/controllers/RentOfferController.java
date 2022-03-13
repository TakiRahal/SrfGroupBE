package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.offer.dto.filter.RentOfferFilter;
import com.takirahal.srfgroup.modules.offer.services.RentOfferService;
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


    /**
     *
     * @param rentOfferFilter
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<RentOfferDTO>> getAllOffersForRent(RentOfferFilter rentOfferFilter, Pageable pageable) {
        log.debug("REST request to get SellOffers public by criteria: {}", rentOfferFilter);
        Page<RentOfferDTO> page = rentOfferService.findByCriteria(rentOfferFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
