package com.takirahal.srfgroup.commentoffer.services;

import com.takirahal.srfgroup.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.commentoffer.dto.filter.CommentOfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentOfferService {

    /**
     * Save a commentOffer.
     *
     * @param commentOfferDTO the entity to save.
     * @return the persisted entity.
     */
    CommentOfferDTO save(CommentOfferDTO commentOfferDTO);

    /**
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Page<CommentOfferDTO> findByCriteria(CommentOfferFilter criteria, Pageable pageable);
}
