package com.takirahal.srfgroup.repositories;

import com.takirahal.srfgroup.entities.OfferImages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferImagesRepository extends JpaRepository<OfferImages, Long>, JpaSpecificationExecutor<OfferImages> {

    @Query("SELECT DISTINCT(offImgs.offer.id), offImgs.path FROM OfferImages offImgs INNER JOIN offImgs.offer o")
    Page<OfferImages> getListExistOfferImages(Pageable pageabe);
}
