package com.takirahal.srfgroup.commentoffer.mapper;

import com.takirahal.srfgroup.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.mapper.OfferMapper;
import com.takirahal.srfgroup.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CommentOffer} and its DTO {@link CommentOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = { OfferMapper.class, UserMapper.class })
public interface CommentOfferMapper extends EntityMapper<CommentOfferDTO, CommentOffer> {
    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "username")
    CommentOfferDTO toDto(CommentOffer s);
}