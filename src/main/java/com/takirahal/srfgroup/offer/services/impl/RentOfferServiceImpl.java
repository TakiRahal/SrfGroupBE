package com.takirahal.srfgroup.offer.services.impl;

import com.takirahal.srfgroup.dto.OfferImagesDTO;
import com.takirahal.srfgroup.dto.RentOfferDTO;
import com.takirahal.srfgroup.entities.RentOffer;
import com.takirahal.srfgroup.offer.mapper.RentOfferMapper;
import com.takirahal.srfgroup.repositories.RentOfferRepository;
import com.takirahal.srfgroup.offer.services.OfferImagesService;
import com.takirahal.srfgroup.offer.services.RentOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentOfferServiceImpl implements RentOfferService {

    private final Logger log = LoggerFactory.getLogger(RentOfferServiceImpl.class);

    @Autowired
    RentOfferMapper rentOfferMapper;

    @Autowired
    RentOfferRepository rentOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Override
    public RentOfferDTO save(RentOfferDTO rentOfferDTO) {
        log.debug("Request to save RentOffer : {}", rentOfferDTO);
        RentOffer rentOffer = rentOfferMapper.toEntity(rentOfferDTO);
        rentOffer = rentOfferRepository.save(rentOffer);

        RentOfferDTO result = rentOfferMapper.toDto(rentOffer);

        for (OfferImagesDTO offerImagesDTO : rentOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(result);
            offerImagesService.save(offerImagesDTO);
        }

        return result;
    }
}
