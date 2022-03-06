package com.takirahal.srfgroup.offer.services;

import com.takirahal.srfgroup.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.offer.dto.filter.SellOfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellOfferService {

    /**
     * Save a sellOffer.
     *
     * @param sellOfferDTO the entity to save.
     * @return the persisted entity.
     */
    SellOfferDTO save(SellOfferDTO sellOfferDTO);

    Page<SellOfferDTO> findByCriteria(SellOfferFilter sellOfferFilter, Pageable pageable);
}
