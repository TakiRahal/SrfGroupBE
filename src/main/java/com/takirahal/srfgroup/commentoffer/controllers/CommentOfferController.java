package com.takirahal.srfgroup.commentoffer.controllers;

import com.takirahal.srfgroup.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.commentoffer.dto.filter.CommentOfferFilter;
import com.takirahal.srfgroup.commentoffer.services.CommentOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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
}
