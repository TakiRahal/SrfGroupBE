package com.takirahal.srfgroup.favoriteuser.mapper;

import com.takirahal.srfgroup.favoriteuser.dto.FavoriteUserDTO;
import com.takirahal.srfgroup.favoriteuser.entities.FavoriteUser;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link FavoriteUser} and its DTO {@link FavoriteUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface FavoriteUserMapper extends EntityMapper<FavoriteUserDTO, FavoriteUser> {
    @Mapping(target = "currentUser", source = "currentUser", qualifiedByName = "username")
    @Mapping(target = "favoriteUser", source = "favoriteUser", qualifiedByName = "username")
    FavoriteUserDTO toDto(FavoriteUser s);
}