package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.FindOfferDTO;
import com.takirahal.srfgroup.entities.FindOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface FindOfferMapper extends EntityMapper<FindOfferDTO, FindOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "username")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    FindOfferDTO toDto(FindOffer findOffer);

    @Mapping(target = "offerImages", ignore = true)
    FindOffer toEntity(FindOfferDTO findOfferDTO);
}
