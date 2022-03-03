package com.takirahal.srfgroup.offer.controllers;

import com.takirahal.srfgroup.dto.FindOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.offer.services.FindOfferService;
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
}
