package com.takirahal.srfgroup.chat.controllers;

import com.takirahal.srfgroup.chat.dto.ConversationDTO;
import com.takirahal.srfgroup.chat.dto.ConversationVM;
import com.takirahal.srfgroup.chat.dto.ConversationWithLastMessageDTO;
import com.takirahal.srfgroup.chat.dto.Filter.ConversationFilter;
import com.takirahal.srfgroup.chat.services.ConversationService;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/conversation/")
public class ConversationController {

    private final Logger log = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    ConversationService conversationService;

    /**
     * {@code POST  /conversations} : Create a new conversation.
     *
     * @param conversationVM the conversationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conversationDTO, or with status {@code 400 (Bad Request)} if the conversation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create/message")
    public ResponseEntity<Boolean> createConversationMessage(@RequestBody ConversationVM conversationVM) throws URISyntaxException {
        log.debug("REST request to save Conversation : {}", conversationVM);
        if (conversationVM.getConversation().getId() != null) {
            throw new BadRequestAlertException("A new conversation cannot already have an ID idexists");
        }
        conversationService.createConversationMessage(conversationVM);
        return ResponseEntity
                .created(new URI("/api/conversation/"))
                .body(true);
    }

    /**
     *
     * @param conversationFilter
     * @param pageable
     * @return
     */
    @GetMapping("current-user")
    public ResponseEntity<Page<ConversationWithLastMessageDTO>> getAllConversationsCurrentUser(
            ConversationFilter conversationFilter,
            Pageable pageable
    ) {
        log.debug("REST request to get Conversations by criteria: {}", conversationFilter);
        Page<ConversationWithLastMessageDTO> page = conversationService.getOffersByCurrentUser(conversationFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
