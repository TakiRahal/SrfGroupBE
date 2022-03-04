package com.takirahal.srfgroup.mapper;

import com.takirahal.srfgroup.user.dto.UserDTO;
import com.takirahal.srfgroup.user.entities.User;
import com.takirahal.srfgroup.security.UserPrincipal;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Named("currentUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "sourceRegister", source = "sourceRegister")
    @Mapping(target = "idOneSignal", source = "idOneSignal")
    @Mapping(target = "authorities", source = "authorities")
    UserDTO toCurrentUser(UserPrincipal user);

    @Named("username")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    UserDTO toDtoIdUsername(User user);

    @Named("currentUserToEntity")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    User currentUserToEntity(UserPrincipal user);

    @Named("publicUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "sourceRegister", source = "sourceRegister")
    @Mapping(target = "idOneSignal", source = "idOneSignal")
    @Mapping(target = "authorities", source = "authorities")
    UserDTO toDtoPublicUser(User user);
}
