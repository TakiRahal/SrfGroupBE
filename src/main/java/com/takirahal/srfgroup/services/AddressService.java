package com.takirahal.srfgroup.services;

import com.takirahal.srfgroup.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    Page<AddressDTO> findByCriteria(AddressDTO addressDTO, Pageable pageable);
}
