package com.takirahal.srfgroup.modules.contactus.controllers;


import com.takirahal.srfgroup.modules.contactus.dto.ContactUsDTO;
import com.takirahal.srfgroup.modules.contactus.services.ContactUsService;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * REST controller for managing {@link com.takirahal.srfgroup.modules.contactus.entities.ContactUs}.
 */
@RestController
@RequestMapping("/api/contactus/")
public class ContactUsController {

    private final Logger log = LoggerFactory.getLogger(ContactUsController.class);

    @Autowired
    ContactUsService contactUsService;

    /**
     * {@code POST  /contactuses} : Create a new contactUs.
     *
     * @param contactUsDTO the contactUsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactUsDTO, or with status {@code 400 (Bad Request)} if the contactUs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public")
    public ResponseEntity<ContactUsDTO> createContactUs(@RequestBody ContactUsDTO contactUsDTO) throws URISyntaxException {
        log.debug("REST request to save ContactUs : {}", contactUsDTO);
        if (contactUsDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactUs cannot already have an ID idexists");
        }
        ContactUsDTO result = contactUsService.save(contactUsDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
