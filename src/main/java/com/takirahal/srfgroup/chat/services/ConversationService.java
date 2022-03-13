package com.takirahal.srfgroup.chat.services;

import com.takirahal.srfgroup.chat.dto.ConversationDTO;
import com.takirahal.srfgroup.chat.dto.ConversationVM;
import com.takirahal.srfgroup.chat.dto.ConversationWithLastMessageDTO;
import com.takirahal.srfgroup.chat.dto.Filter.ConversationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConversationService {

    /**
     * Save a conversation.
     *
     * @param conversationDTO the entity to save.
     * @return the persisted entity.
     */
    ConversationDTO save(ConversationDTO conversationDTO);

    /**
     * Save a conversation.
     *
     * @param conversationDTO the entity to save.
     * @return the persisted entity.
     */
    boolean createConversationMessage(ConversationVM conversationDTO);

    Page<ConversationWithLastMessageDTO> getOffersByCurrentUser(ConversationFilter conversationFilter, Pageable pageable);
}
