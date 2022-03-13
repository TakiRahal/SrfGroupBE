package com.takirahal.srfgroup.chat.services;

import com.takirahal.srfgroup.chat.dto.MessageDTO;

public interface MessageService {
    /**
     * Save a message.
     *
     * @param messageDTO the entity to save.
     * @return the persisted entity.
     */
    MessageDTO save(MessageDTO messageDTO);
}
