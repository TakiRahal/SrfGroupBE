package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.dto.filter.OfferFilter;
import com.takirahal.srfgroup.entities.Offer;
import com.takirahal.srfgroup.mapper.CustomOfferMapper;
import com.takirahal.srfgroup.repositories.OfferRepository;
import com.takirahal.srfgroup.services.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Autowired
    OfferRepository offerRepository;

    CustomOfferMapper customOfferMapper;

    @Override
    public Page<OfferDTO> findByCriteria(OfferFilter offerFilter, Pageable page) {
        log.debug("find offers by criteria : {}, page: {}", page);
        return offerRepository.findAll(createSpecification(offerFilter), page).map(customOfferMapper::toDto);
    }

    protected Specification<Offer> createSpecification(OfferFilter offerFilter) {
        Specification<Offer> specification = Specification.where(null);
        return specification;
    }
}
