package com.takirahal.srfgroup.modules.aboutus.controllers;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.aboutus.dto.AboutUsDTO;
import com.takirahal.srfgroup.modules.aboutus.dto.filter.AboutUsFilter;
import com.takirahal.srfgroup.modules.aboutus.services.AboutUsService;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.takirahal.srfgroup.modules.aboutus.entities.AboutUs}.
 */
@RestController
@RequestMapping("/api/aboutus/")
public class AboutUsController {

    private final Logger log = LoggerFactory.getLogger(AboutUsController.class);


    @Autowired
    AboutUsService aboutUsService;

    /**
     * {@code POST  /aboutuses} : Create a new aboutUs.
     *
     * @param aboutUsDTO the aboutUsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aboutUsDTO, or with status {@code 400 (Bad Request)} if the aboutUs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin")
    public ResponseEntity<AboutUsDTO> createAboutUs(@RequestBody AboutUsDTO aboutUsDTO) throws URISyntaxException {
        log.debug("REST request to save AboutUs : {}", aboutUsDTO);
        if (aboutUsDTO.getId() != null) {
            throw new BadRequestAlertException("A new aboutUs cannot already have an ID idexists");
        }
        AboutUsDTO result = aboutUsService.save(aboutUsDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    /**
     * {@code GET  /aboutuses} : get all the aboutuses.
     *
     * @param pageable the pagination information.
     * @param aboutUsFilter the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aboutuses in body.
     */
    @GetMapping("/admin")
    public ResponseEntity<Page<AboutUsDTO>> getAllAboutuses(AboutUsFilter aboutUsFilter, Pageable pageable) {
        log.debug("REST request to get Aboutuses by criteria: {}", aboutUsFilter);
        Page<AboutUsDTO> page = aboutUsService.findByCriteria(aboutUsFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * {@code GET  /aboutuses/:id} : get the "id" aboutUs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aboutUsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/last")
    public ResponseEntity<AboutUsDTO> getLastAboutUs() {
        log.debug("REST request to get AboutUs : {}");
        Optional<AboutUsDTO> aboutUsDTO = aboutUsService.findLastOne();
        if(!aboutUsDTO.isPresent()){
            throw new ResouorceNotFoundException("Not inseret yet from BO");
        }
        return new ResponseEntity<>(aboutUsDTO.get(), HttpStatus.OK);
    }
}
