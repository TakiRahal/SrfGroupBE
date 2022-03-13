package com.takirahal.srfgroup.modules.chat.services.impl;

import com.takirahal.srfgroup.modules.chat.dto.MessageDTO;
import com.takirahal.srfgroup.modules.chat.entities.Message;
import com.takirahal.srfgroup.modules.chat.mapper.MessageMapper;
import com.takirahal.srfgroup.modules.chat.repositories.MessageRepository;
import com.takirahal.srfgroup.modules.chat.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        log.debug("Request to save Message : {}", messageDTO);
        Message message = messageMapper.toEntity(messageDTO);
        message = messageRepository.save(message);
        return messageMapper.toDto(message);
    }
}
