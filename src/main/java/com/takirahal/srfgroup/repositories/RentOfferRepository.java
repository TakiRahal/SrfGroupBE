package com.takirahal.srfgroup.repositories;

import com.takirahal.srfgroup.entities.RentOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RentOfferRepository extends JpaRepository<RentOffer, Long>, JpaSpecificationExecutor<RentOffer> {
}
