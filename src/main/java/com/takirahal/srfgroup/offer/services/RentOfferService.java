package com.takirahal.srfgroup.offer.services;

import com.takirahal.srfgroup.dto.RentOfferDTO;

public interface RentOfferService {

    /**
     * Save a rentOffer.
     *
     * @param rentOfferDTO the entity to save.
     * @return the persisted entity.
     */
    RentOfferDTO save(RentOfferDTO rentOfferDTO);
}
