package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.FindOfferDTO;
import com.takirahal.srfgroup.entities.FindOffer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface FindOfferMapper extends EntityMapper<FindOfferDTO, FindOffer> {
}
