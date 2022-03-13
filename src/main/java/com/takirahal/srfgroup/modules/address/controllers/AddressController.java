package com.takirahal.srfgroup.modules.address.controllers;

import com.takirahal.srfgroup.modules.address.dto.AddressDTO;
import com.takirahal.srfgroup.modules.address.services.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address/")
public class AddressController {

    private final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressService addressService;

    /**
     *
     * @param addressDTO
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<AddressDTO>> getAllPublicAddresses(AddressDTO addressDTO, Pageable pageable) {
        log.debug("REST request to get Addresses by criteria: {}", addressDTO);
        Page<AddressDTO> page = addressService.findByCriteria(addressDTO, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
