package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.SellOfferDTO;
import com.takirahal.srfgroup.entities.SellOffer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface SellOfferMapper extends EntityMapper<SellOfferDTO, SellOffer> {
}
