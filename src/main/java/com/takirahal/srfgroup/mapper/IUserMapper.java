package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.dto.UserDTO;
import com.takirahal.srfgroup.entities.User;
import com.takirahal.srfgroup.security.UserPrincipal;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface IUserMapper extends EntityMapper<UserDTO, User> {

    @Named("currentUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "sourceProvider", source = "sourceProvider")
    @Mapping(target = "idOneSignal", source = "idOneSignal")
    @Mapping(target = "authorities", source = "authorities")
    public UserDTO toCurrentUser(UserPrincipal user);
}
