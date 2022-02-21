package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.AddressDTO;
import com.takirahal.srfgroup.entities.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
}
