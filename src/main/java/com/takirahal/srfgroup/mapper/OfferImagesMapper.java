package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.OfferImagesDTO;
import com.takirahal.srfgroup.entities.OfferImages;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { OfferMapper.class, UserMapper.class })
public interface OfferImagesMapper extends EntityMapper<OfferImagesDTO, OfferImages> {
}
