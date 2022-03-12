package com.takirahal.srfgroup.chat.mapper;

import com.takirahal.srfgroup.chat.dto.MessageDTO;
import com.takirahal.srfgroup.chat.entities.Message;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ConversationMapper.class })
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "senderUser", source = "senderUser", qualifiedByName = "username")
    @Mapping(target = "receiverUser", source = "receiverUser", qualifiedByName = "username")
    @Mapping(target = "conversation", source = "conversation", qualifiedByName = "id")
    MessageDTO toDto(Message s);
}
