package com.takirahal.srfgroup.commentoffer.services.impl;

import com.takirahal.srfgroup.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.commentoffer.dto.filter.CommentOfferFilter;
import com.takirahal.srfgroup.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.commentoffer.mapper.CommentOfferMapper;
import com.takirahal.srfgroup.commentoffer.repositories.CommentOfferRepository;
import com.takirahal.srfgroup.commentoffer.services.CommentOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentOfferServiceImpl implements CommentOfferService {

    private final Logger log = LoggerFactory.getLogger(CommentOfferServiceImpl.class);

    @Autowired
    CommentOfferRepository commentOfferRepository;

    @Autowired
    CommentOfferMapper commentOfferMapper;

    @Override
    public CommentOfferDTO save(CommentOfferDTO commentOfferDTO) {
        log.debug("Request to save CommentOffer : {}", commentOfferDTO);
        CommentOffer commentOffer = commentOfferMapper.toEntity(commentOfferDTO);
        commentOffer = commentOfferRepository.save(commentOffer);
        return commentOfferMapper.toDto(commentOffer);
    }

    @Override
    public Page<CommentOfferDTO> findByCriteria(CommentOfferFilter criteria, Pageable pageable) {
        log.debug("find offers by criteria : {}, page: {}", pageable);
        return commentOfferRepository.findAll(pageable).map(commentOfferMapper::toDto);
    }
}
