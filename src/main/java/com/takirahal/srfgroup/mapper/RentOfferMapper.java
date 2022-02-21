package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.RentOfferDTO;
import com.takirahal.srfgroup.entities.RentOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface RentOfferMapper extends EntityMapper<RentOfferDTO, RentOffer> {
//    @Mapping(target = "user", source = "user", qualifiedByName = "login")
//    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
//    RentOfferDTO toDto(RentOffer rentOffer);
//
//    @Mapping(target = "removeOfferImages", ignore = true)
//    RentOffer toEntity(RentOfferDTO rentOfferDTO);
}
