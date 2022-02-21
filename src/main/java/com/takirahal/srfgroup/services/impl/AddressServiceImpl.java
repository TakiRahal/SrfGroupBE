package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.dto.AddressDTO;
import com.takirahal.srfgroup.entities.Address;
import com.takirahal.srfgroup.mapper.AddressMapper;
import com.takirahal.srfgroup.repositories.AddressRepository;
import com.takirahal.srfgroup.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressMapper addressMapper;

    @Override
    public Page<AddressDTO> findByCriteria(AddressDTO addressDTO, Pageable page) {
        return addressRepository.findAll(createSpecification(addressDTO), page).map(address -> addressMapper.toDto(address));
    }

    protected Specification<Address> createSpecification(AddressDTO address) {
        Specification<Address> specification = Specification.where(null);
        return specification;
    }
}
