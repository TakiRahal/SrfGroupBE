package com.takirahal.srfgroup.offer.services;

import com.takirahal.srfgroup.dto.SellOfferDTO;

public interface SellOfferService {

    /**
     * Save a sellOffer.
     *
     * @param sellOfferDTO the entity to save.
     * @return the persisted entity.
     */
    SellOfferDTO save(SellOfferDTO sellOfferDTO);

}
