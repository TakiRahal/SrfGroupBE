package com.takirahal.srfgroup.offer.mapper;

import com.takirahal.srfgroup.dto.SellOfferDTO;
import com.takirahal.srfgroup.entities.SellOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.mapper.OfferImagesMapper;
import com.takirahal.srfgroup.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface SellOfferMapper extends EntityMapper<SellOfferDTO, SellOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "username")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    SellOfferDTO toDto(SellOffer sellOffer);

    @Mapping(target = "offerImages", ignore = true)
    SellOffer toEntity(SellOfferDTO sellOfferDTO);

    @Named("toDtoDetailsOffer")
    @Mapping(target = "user", source = "user", qualifiedByName = "publicUser")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    SellOfferDTO toDtoDetailsOffer(SellOffer sellOffer);
}
