package com.takirahal.srfgroup.offer.services;

import com.takirahal.srfgroup.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.offer.dto.filter.RentOfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentOfferService {

    /**
     * Save a rentOffer.
     *
     * @param rentOfferDTO the entity to save.
     * @return the persisted entity.
     */
    RentOfferDTO save(RentOfferDTO rentOfferDTO);

    Page<RentOfferDTO> findByCriteria(RentOfferFilter rentOfferFilter, Pageable pageable);
}
