package com.takirahal.srfgroup.address.services;

import com.takirahal.srfgroup.dto.AddressDTO;
import com.takirahal.srfgroup.entities.Address;
import com.takirahal.srfgroup.mapper.AddressMapper;
import com.takirahal.srfgroup.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

public interface AddressService {

    Page<AddressDTO> findByCriteria(AddressDTO addressDTO, Pageable pageable);
}
