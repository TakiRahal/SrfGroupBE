package com.takirahal.srfgroup.offer.services;

import com.takirahal.srfgroup.entities.OfferImages;
import com.takirahal.srfgroup.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.offer.dto.filter.OfferImagesFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferImagesService {

    /**
     * Save a offerImages.
     *
     * @param offerImagesDTO the entity to save.
     * @return the persisted entity.
     */
    OfferImagesDTO save(OfferImagesDTO offerImagesDTO);

    Page<OfferImages> getListExistOfferImages(OfferImagesFilter offerImagesFilter, Pageable pageable);
}
