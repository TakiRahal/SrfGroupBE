package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.dto.FindOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.offer.dto.filter.FindOfferFilter;
import com.takirahal.srfgroup.modules.offer.services.FindOfferService;
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
@RequestMapping("/api/find-offer/")
public class FindOfferController {

    private final Logger log = LoggerFactory.getLogger(FindOfferController.class);

    @Autowired
    FindOfferService findOfferService;

    /**
     * {@code POST  /find-offers} : Create a new findOffer.
     *
     * @param findOfferDTO the findOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new findOfferDTO, or with status {@code 400 (Bad Request)} if the findOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<FindOfferDTO> createFindOffer(@RequestBody FindOfferDTO findOfferDTO) throws URISyntaxException {
        log.debug("REST request to save FindOffer : {}", findOfferDTO);
        if (findOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new findOffer cannot already have an ID idexists");
        }
        FindOfferDTO result = findOfferService.save(findOfferDTO);

        return ResponseEntity
                .created(new URI("/api/find-offers/" + result.getId()))
                // .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     *
     * @param findOfferFilter
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<FindOfferDTO>> getAllOffersForSell(FindOfferFilter findOfferFilter, Pageable pageable) {
        log.debug("REST request to get SellOffers public by criteria: {}", findOfferFilter);
        Page<FindOfferDTO> page = findOfferService.findByCriteria(findOfferFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
