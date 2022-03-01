package com.takirahal.srfgroup.commentoffer.repositories;

import com.takirahal.srfgroup.commentoffer.entities.CommentOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentOfferRepository extends JpaRepository<CommentOffer, Long> {
}
