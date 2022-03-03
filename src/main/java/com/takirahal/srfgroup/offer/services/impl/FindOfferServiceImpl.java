package com.takirahal.srfgroup.offer.services.impl;

import com.takirahal.srfgroup.dto.FindOfferDTO;
import com.takirahal.srfgroup.dto.OfferImagesDTO;
import com.takirahal.srfgroup.entities.FindOffer;
import com.takirahal.srfgroup.offer.mapper.FindOfferMapper;
import com.takirahal.srfgroup.repositories.FindOfferRepository;
import com.takirahal.srfgroup.offer.services.FindOfferService;
import com.takirahal.srfgroup.services.OfferImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindOfferServiceImpl implements FindOfferService {

    private final Logger log = LoggerFactory.getLogger(FindOfferServiceImpl.class);

    @Autowired
    FindOfferMapper findOfferMapper;

    @Autowired
    FindOfferRepository findOfferRepository;

    @Autowired
    OfferImagesService offerImagesService;

    @Override
    public FindOfferDTO save(FindOfferDTO findOfferDTO) {
        log.debug("Request to save FindOffer : {}", findOfferDTO);
        FindOffer findOffer = findOfferMapper.toEntity(findOfferDTO);
        findOffer = findOfferRepository.save(findOffer);

        FindOfferDTO result = findOfferMapper.toDto(findOffer);

        for (OfferImagesDTO offerImagesDTO : findOfferDTO.getOfferImages()) {
            offerImagesDTO.setOffer(result);
            offerImagesService.save(offerImagesDTO);
        }

        return result;
    }
}
