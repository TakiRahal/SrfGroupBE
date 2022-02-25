package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.FindOfferDTO;

public interface FindOfferService {

    /**
     * Save a findOffer.
     *
     * @param findOfferDTO the entity to save.
     * @return the persisted entity.
     */
    FindOfferDTO save(FindOfferDTO findOfferDTO);
}
