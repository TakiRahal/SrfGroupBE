package com.takirahal.srfgroup.modules.commentoffer.services.impl;

import com.takirahal.srfgroup.enums.ModuleNotification;
import com.takirahal.srfgroup.exceptions.AccountResourceException;
import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.dto.filter.CommentOfferFilter;
import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.modules.commentoffer.mapper.CommentOfferMapper;
import com.takirahal.srfgroup.modules.commentoffer.repositories.CommentOfferRepository;
import com.takirahal.srfgroup.modules.commentoffer.services.CommentOfferService;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentOfferServiceImpl implements CommentOfferService {

    private final Logger log = LoggerFactory.getLogger(CommentOfferServiceImpl.class);

    @Autowired
    CommentOfferRepository commentOfferRepository;

    @Autowired
    CommentOfferMapper commentOfferMapper;

    @Autowired
    NotificationService notificationService;

    @Override
    public CommentOfferDTO save(CommentOfferDTO commentOfferDTO) {
        log.debug("Request to save CommentOffer : {}", commentOfferDTO);
        CommentOffer commentOffer = commentOfferMapper.toEntity(commentOfferDTO);
        commentOffer = commentOfferRepository.save(commentOffer);

        Long userId = SecurityUtils.getIdByCurrentUser().orElseThrow(() -> new AccountResourceException("Current user login not found"));

        // Add notification
        if (!commentOfferDTO.getOffer().getUser().getId().equals(userId)) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setDateCreated(Instant.now());
            notificationDTO.setContent("Test comment offer");
            notificationDTO.setModule(ModuleNotification.CommentOffer.toString());
            notificationDTO.setIsRead(Boolean.FALSE);
            notificationDTO.setUser(commentOfferDTO.getOffer().getUser());
            notificationService.save(notificationDTO);
        }

        return commentOfferMapper.toDto(commentOffer);
    }

    @Override
    public Page<CommentOfferDTO> findByCriteria(CommentOfferFilter criteria, Pageable pageable) {
        log.debug("find offers by criteria : {}, page: {}", pageable);
        return commentOfferRepository.findAll(pageable).map(commentOfferMapper::toDto);
    }


}
