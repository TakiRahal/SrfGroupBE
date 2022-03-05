package com.takirahal.srfgroup.contactus.services.impl;

import com.takirahal.srfgroup.contactus.dto.ContactUsDTO;
import com.takirahal.srfgroup.contactus.entities.ContactUs;
import com.takirahal.srfgroup.contactus.mapper.ContactUsMapper;
import com.takirahal.srfgroup.contactus.repositories.ContactUsRepository;
import com.takirahal.srfgroup.contactus.services.ContactUsService;
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
