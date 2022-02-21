package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.OfferImagesDTO;

public interface OfferImagesService {

    /**
     * Save a offerImages.
     *
     * @param offerImagesDTO the entity to save.
     * @return the persisted entity.
     */
    OfferImagesDTO save(OfferImagesDTO offerImagesDTO);
}
