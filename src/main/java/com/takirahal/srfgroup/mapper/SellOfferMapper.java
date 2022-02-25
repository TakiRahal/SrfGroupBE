package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.SellOfferDTO;
import com.takirahal.srfgroup.entities.SellOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface SellOfferMapper extends EntityMapper<SellOfferDTO, SellOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "username")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    SellOfferDTO toDto(SellOffer sellOffer);

    @Mapping(target = "offerImages", ignore = true)
    SellOffer toEntity(SellOfferDTO sellOfferDTO);
}
