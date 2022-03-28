package com.takirahal.srfgroup.modules.contactus.services;

import com.takirahal.srfgroup.modules.contactus.dto.ContactUsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactUsService {
    ContactUsDTO save(ContactUsDTO contactUsDTO);

    Page<ContactUsDTO> findByCriteria(Pageable pageable);
}
