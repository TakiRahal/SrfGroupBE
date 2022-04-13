package com.takirahal.srfgroup.modules.contactus.controllers;


import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.contactus.dto.ContactUsDTO;
import com.takirahal.srfgroup.modules.contactus.services.ContactUsService;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.services.impl.ValidateCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    ValidateCaptchaService validateCaptchaService;

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

        final boolean isValidCaptcha = validateCaptchaService.validateCaptcha(contactUsDTO.getCaptchaResponse());
        log.debug("Response recaptcha : {}", isValidCaptcha);

        if(!isValidCaptcha){
            throw new UnauthorizedException("Invalid recaptcha");
        }

        if (contactUsDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactUs cannot already have an ID idexists");
        }
        ContactUsDTO result = contactUsService.save(contactUsDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * {@code GET  /contactus} : get all the categories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("admin/list")
    public ResponseEntity<Page<ContactUsDTO>> getAllPublicCategories(Pageable pageable) {
        log.debug("REST request to get ContactUs by criteria: {}");
        Page<ContactUsDTO> page = contactUsService.findByCriteria(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


}
