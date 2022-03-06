package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.entities.OfferImages;
import com.takirahal.srfgroup.offer.mapper.OfferMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", uses = { OfferMapper.class, UserMapper.class })
public interface OfferImagesMapper extends EntityMapper<OfferImagesDTO, OfferImages> {

    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    OfferImagesDTO toDto(OfferImages s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<OfferImagesDTO> toDtoIdSet(Set<OfferImages> offerImages);
}
