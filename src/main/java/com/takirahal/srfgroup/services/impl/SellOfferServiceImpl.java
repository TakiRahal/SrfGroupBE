package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.dto.SellOfferDTO;
import com.takirahal.srfgroup.entities.SellOffer;
import com.takirahal.srfgroup.mapper.SellOfferMapper;
import com.takirahal.srfgroup.repositories.SellOfferRepository;
import com.takirahal.srfgroup.services.SellOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellOfferServiceImpl implements SellOfferService {

    private final Logger log = LoggerFactory.getLogger(SellOfferServiceImpl.class);

    @Autowired
    SellOfferMapper sellOfferMapper;

    @Autowired
    SellOfferRepository sellOfferRepository;

    @Override
    public SellOfferDTO save(SellOfferDTO sellOfferDTO) {
        log.debug("Request to save SellOffer : {}", sellOfferDTO);
        SellOffer sellOffer = sellOfferMapper.toEntity(sellOfferDTO);
        // sellOffer = sellOfferRepository.save(sellOffer);
        return sellOfferMapper.toDto(sellOffer);
    }
}
