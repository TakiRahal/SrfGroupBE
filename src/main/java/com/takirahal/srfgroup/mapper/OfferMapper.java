package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.entities.Offer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, AddressMapper.class, CategoryMapper.class })
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {
}
