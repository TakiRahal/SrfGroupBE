package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.OfferDTO;
import com.takirahal.srfgroup.entities.Offer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class , AddressMapper.class, CategoryMapper.class })
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "username")
    @Mapping(target = "address", source = "address", qualifiedByName = "id")
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    OfferDTO toDto(Offer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfferDTO toDtoId(Offer offer);
}
