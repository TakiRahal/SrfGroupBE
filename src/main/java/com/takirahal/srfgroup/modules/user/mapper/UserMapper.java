package com.takirahal.srfgroup.modules.user.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.security.UserPrincipal;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Named("currentUserPrincipal")
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
    UserDTO toCurrentUserPrincipal(UserPrincipal user);

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
    @Mapping(target = "address", source = "address")
    UserDTO toCurrentUser(User user);

    @Named("email")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    UserDTO toDtoIdEmail(User user);

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

    @Named("searchOffers")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "sourceRegister", source = "sourceRegister")
    UserDTO toDtoSearchOffers(User user);

    @Named("commentOffer")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "sourceRegister", source = "sourceRegister")
    UserDTO toDtoCommentOffer(User user);
}
