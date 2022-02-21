package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.dto.OfferImagesDTO;
import com.takirahal.srfgroup.repositories.OfferImagesRepository;
import com.takirahal.srfgroup.services.OfferImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferImagesServiceImpl implements OfferImagesService {

    private final Logger log = LoggerFactory.getLogger(OfferImagesServiceImpl.class);

    @Autowired
    OfferImagesRepository offerImagesRepository;

    @Override
    public OfferImagesDTO save(OfferImagesDTO offerImagesDTO) {
        return null;
    }
}
