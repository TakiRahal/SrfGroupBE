package com.takirahal.srfgroup.chat.services.impl;

import com.takirahal.srfgroup.chat.dto.MessageDTO;
import com.takirahal.srfgroup.chat.entities.Message;
import com.takirahal.srfgroup.chat.mapper.MessageMapper;
import com.takirahal.srfgroup.chat.repositories.MessageRepository;
import com.takirahal.srfgroup.chat.services.MessageService;
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
