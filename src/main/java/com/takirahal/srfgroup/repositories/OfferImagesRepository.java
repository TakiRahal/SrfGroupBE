package com.takirahal.srfgroup.repositories;

import com.takirahal.srfgroup.entities.OfferImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferImagesRepository extends JpaRepository<OfferImages, Long>, JpaSpecificationExecutor<OfferImages> {
}
