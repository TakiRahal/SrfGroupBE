package com.takirahal.srfgroup.repositories;

import com.takirahal.srfgroup.entities.SellOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellOfferRepository extends JpaRepository<SellOffer, Long> {
}
