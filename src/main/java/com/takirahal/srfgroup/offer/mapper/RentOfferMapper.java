package com.takirahal.srfgroup.offer.mapper;

import com.takirahal.srfgroup.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.entities.RentOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.mapper.OfferImagesMapper;
import com.takirahal.srfgroup.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface RentOfferMapper extends EntityMapper<RentOfferDTO, RentOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "username")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    RentOfferDTO toDto(RentOffer rentOffer);

    @Named("toDtoSearchOffers")
    @Mapping(target = "user", source = "user", qualifiedByName = "searchOffers")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    RentOfferDTO toDtoSearchOffers(RentOffer rentOffer);

    @Mapping(target = "offerImages", ignore = true)
    RentOffer toEntity(RentOfferDTO rentOfferDTO);

    @Named("toDtoDetailsOffer")
    @Mapping(target = "user", source = "user", qualifiedByName = "publicUser")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    RentOfferDTO toDtoDetailsOffer(RentOffer rentOffer);
}
