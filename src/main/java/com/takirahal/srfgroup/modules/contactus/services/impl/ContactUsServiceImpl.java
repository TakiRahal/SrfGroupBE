package com.takirahal.srfgroup.modules.contactus.services.impl;

import com.takirahal.srfgroup.modules.contactus.dto.ContactUsDTO;
import com.takirahal.srfgroup.modules.contactus.entities.ContactUs;
import com.takirahal.srfgroup.modules.contactus.mapper.ContactUsMapper;
import com.takirahal.srfgroup.modules.contactus.repositories.ContactUsRepository;
import com.takirahal.srfgroup.modules.contactus.services.ContactUsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    private final Logger log = LoggerFactory.getLogger(ContactUsServiceImpl.class);


    @Autowired
    ContactUsRepository contactUsRepository;

    @Autowired
    ContactUsMapper contactUsMapper;

    @Override
    public ContactUsDTO save(ContactUsDTO contactUsDTO) {
        log.debug("Request to save ContactUs : {}", contactUsDTO);
        ContactUs contactUs = contactUsMapper.toEntity(contactUsDTO);
        contactUs = contactUsRepository.save(contactUs);
        return contactUsMapper.toDto(contactUs);
    }
}
