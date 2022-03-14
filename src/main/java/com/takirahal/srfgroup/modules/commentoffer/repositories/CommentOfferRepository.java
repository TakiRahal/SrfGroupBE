package com.takirahal.srfgroup.modules.commentoffer.repositories;

import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentOfferRepository extends JpaRepository<CommentOffer, Long>, JpaSpecificationExecutor<CommentOffer> {
}
