package com.takirahal.srfgroup.chat.mapper;

import com.takirahal.srfgroup.chat.dto.ConversationDTO;
import com.takirahal.srfgroup.chat.entities.Conversation;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.mapper.UserMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ConversationMapper extends EntityMapper<ConversationDTO, Conversation> {
    @Mapping(target = "senderUser", source = "senderUser", qualifiedByName = "username")
    @Mapping(target = "receiverUser", source = "receiverUser", qualifiedByName = "username")
    ConversationDTO toDto(Conversation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConversationDTO toDtoId(Conversation conversation);
}
