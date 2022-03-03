package com.takirahal.srfgroup.offer.mapper;

import com.takirahal.srfgroup.dto.FindOfferDTO;
import com.takirahal.srfgroup.entities.FindOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.mapper.OfferImagesMapper;
import com.takirahal.srfgroup.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface FindOfferMapper extends EntityMapper<FindOfferDTO, FindOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "username")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    FindOfferDTO toDto(FindOffer findOffer);

    @Mapping(target = "offerImages", ignore = true)
    FindOffer toEntity(FindOfferDTO findOfferDTO);

    @Named("toDtoDetailsOffer")
    @Mapping(target = "user", source = "user", qualifiedByName = "publicUser")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    FindOfferDTO toDtoDetailsOffer(FindOffer findOffer);
}
