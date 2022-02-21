package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.dto.filter.OfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {

    Page<OfferDTO> findByCriteria(OfferFilter offerFilter, Pageable pageable);
}
