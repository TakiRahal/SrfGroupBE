package com.takirahal.srfgroup.modules.commentoffer.services.impl;

import com.takirahal.srfgroup.enums.ModuleNotification;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.dto.filter.CommentOfferFilter;
import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.modules.commentoffer.mapper.CommentOfferMapper;
import com.takirahal.srfgroup.modules.commentoffer.repositories.CommentOfferRepository;
import com.takirahal.srfgroup.modules.commentoffer.services.CommentOfferService;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.services.NotificationService;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Page<CommentOfferDTO> findByCriteria(CommentOfferFilter criteria, Pageable pageable, Long offerId) {
        log.debug("find offers by criteria : {}, page: {}", pageable, offerId);
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setId(offerId);
        criteria.setOffer(offerDTO);
        return commentOfferRepository.findAll(createSpecification(criteria), pageable).map(commentOfferMapper::toDto);
    }

    @Override
    public CommentOfferDTO updateCommentOffer(CommentOfferDTO commentOfferDTO, Long id) {
        log.debug("update offer by commentOfferDTO : {}, id: {}", commentOfferDTO, id);
        if (commentOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id null");
        }
        if (!Objects.equals(id, commentOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID id invalid");
        }

        CommentOffer commentOffer = commentOfferRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Entity not found with id"));


        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, commentOffer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        commentOffer.setContent(commentOfferDTO.getContent());
        CommentOffer commentOfferUpdate = commentOfferRepository.save(commentOffer);
        return commentOfferMapper.toDto(commentOfferUpdate);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommentOffer : {}", id);

        CommentOffer commentOffer = commentOfferRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException("Entity not found with id"));

        Long useId = SecurityUtils
                .getIdByCurrentUser()
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
        if (!Objects.equals(useId, commentOffer.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized action");
        }

        commentOfferRepository.deleteById(id);
    }

    private Specification<CommentOffer> createSpecification(CommentOfferFilter criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if ( criteria.getOffer() != null && criteria.getOffer().getId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("offer").get("id"), criteria.getOffer().getId()));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
