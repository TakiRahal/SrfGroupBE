package com.takirahal.srfgroup.offer.services;

import com.takirahal.srfgroup.offer.dto.OfferImagesDTO;

public interface OfferImagesService {

    /**
     * Save a offerImages.
     *
     * @param offerImagesDTO the entity to save.
     * @return the persisted entity.
     */
    OfferImagesDTO save(OfferImagesDTO offerImagesDTO);
}
