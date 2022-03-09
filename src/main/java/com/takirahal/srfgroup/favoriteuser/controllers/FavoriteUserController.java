package com.takirahal.srfgroup.favoriteuser.controllers;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.favoriteuser.dto.FavoriteUserDTO;
import com.takirahal.srfgroup.favoriteuser.dto.filter.FavoriteUserFilter;
import com.takirahal.srfgroup.favoriteuser.services.FavoriteUserService;
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

/**
 * REST controller for managing {@link com.takirahal.srfgroup.favoriteuser.entities.FavoriteUser}.
 */
@RestController
@RequestMapping("/api/favoriteuser")
public class FavoriteUserController {

    private final Logger log = LoggerFactory.getLogger(FavoriteUserController.class);

    @Autowired
    FavoriteUserService favoriteUserService;

    /**
     * {@code POST  /favorites} : Create a new favorite.
     *
     * @param favoriteDTO the favoriteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoriteDTO, or with status {@code 400 (Bad Request)} if the favorite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/create")
    public ResponseEntity<FavoriteUserDTO> createFavorite(@RequestBody FavoriteUserDTO favoriteDTO) throws URISyntaxException {
        log.debug("REST request to save Favorite : {}", favoriteDTO);
        if (favoriteDTO.getId() != null) {
            throw new BadRequestAlertException("A new favorite cannot already have an ID idexists");
        }
        FavoriteUserDTO result = favoriteUserService.save(favoriteDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    /**
     * {@code GET  /favorites} : get all the favorites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favorites in body.
     */
    @GetMapping("/current-user")
    public ResponseEntity<Page<FavoriteUserDTO>> getFavoritesCurrentUser(FavoriteUserFilter criteria, Pageable pageable) {
        log.debug("REST request to get Favorites by criteria: {}", criteria);
        Page<FavoriteUserDTO> page = favoriteUserService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
