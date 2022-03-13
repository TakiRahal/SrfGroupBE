package com.takirahal.srfgroup.modules.commentoffer.controllers;

import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.dto.filter.CommentOfferFilter;
import com.takirahal.srfgroup.modules.commentoffer.services.CommentOfferService;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.exceptions.AccountResourceException;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.utils.SecurityUtils;
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
 *
 */
@RestController
@RequestMapping("/api/comment-offer/")
public class CommentOfferController {

    private final Logger log = LoggerFactory.getLogger(CommentOfferController.class);

    @Autowired
    CommentOfferService commentOfferService;

    @GetMapping("by-offer/{commentOfferId}")
    public ResponseEntity<Page<CommentOfferDTO>> getAllCommentsByOffer(
            CommentOfferFilter criteria,
            Pageable pageable,
            @PathVariable("commentOfferId") Long commentOfferId
    ) {
        // log.debug("REST request to get CommentOffers by criteria: {}", criteria);

//        LongFilter longFilter = new LongFilter();
//        longFilter.setEquals(commentOfferId);
//        criteria.setOfferId(longFilter);

        Page<CommentOfferDTO> page = commentOfferService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * {@code POST  /comment-offers} : Create a new commentOffer.
     *
     * @param commentOfferDTO the commentOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentOfferDTO, or with status {@code 400 (Bad Request)} if the commentOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<CommentOfferDTO> createCommentOfferByCurrentUser(@RequestBody CommentOfferDTO commentOfferDTO) {
        log.debug("REST request to save CommentOffer : {}", commentOfferDTO);
        if (commentOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentOffer cannot already have an ID idexists");
        }

        Long userId = SecurityUtils.getIdByCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));

        UserDTO currentUserDTO = new UserDTO();
        currentUserDTO.setId(userId);
        commentOfferDTO.setUser(currentUserDTO);
        CommentOfferDTO result = commentOfferService.save(commentOfferDTO);

//        if (commentOfferDTO.getOffer().getUser().getId().equals(userId) == false) {
//            NotificationDTO notificationDTO = new NotificationDTO();
//            notificationDTO.setDateCreated(Instant.now());
//            notificationDTO.setContent("Test comment offer");
//            notificationDTO.setModule(ModuleNotification.CommentOffer.toString());
//            notificationDTO.setIsRead(Boolean.FALSE);
//            notificationDTO.setUser(commentOfferDTO.getOffer().getUser());
//            notificationService.saveCommentOffer(notificationDTO);
//        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }



}
