package com.takirahal.srfgroup.modules.chat.controllers;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.chat.dto.ConversationVM;
import com.takirahal.srfgroup.modules.chat.dto.MessageDTO;
import com.takirahal.srfgroup.modules.chat.services.MessageService;
import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.dto.filter.CommentOfferFilter;
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

@RestController
@RequestMapping("/api/message/")
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    /**
     *
     * @param messageDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("create")
    public ResponseEntity<MessageDTO> addMessage(@RequestBody MessageDTO messageDTO)  {
        log.debug("REST request to save mMessage : {}", messageDTO);
        if (messageDTO.getId() != null) {
            throw new BadRequestAlertException("A new message cannot already have an ID idexists");
        }
        MessageDTO result = messageService.save(messageDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     *
     * @param pageable
     * @param conversationId
     * @return
     */
    @GetMapping("by-conversation/{conversationId}")
    public ResponseEntity<Page<MessageDTO>> getMessagesByConversation(
            Pageable pageable,
            @PathVariable("conversationId") Long conversationId
    ) {
        Page<MessageDTO> page = messageService.findByCriteria(pageable, conversationId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
