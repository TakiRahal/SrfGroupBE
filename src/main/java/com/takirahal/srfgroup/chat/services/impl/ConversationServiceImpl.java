package com.takirahal.srfgroup.chat.services.impl;

import com.takirahal.srfgroup.chat.dto.ConversationDTO;
import com.takirahal.srfgroup.chat.dto.ConversationVM;
import com.takirahal.srfgroup.chat.dto.ConversationWithLastMessageDTO;
import com.takirahal.srfgroup.chat.dto.Filter.ConversationFilter;
import com.takirahal.srfgroup.chat.dto.MessageDTO;
import com.takirahal.srfgroup.chat.entities.Conversation;
import com.takirahal.srfgroup.chat.mapper.ConversationMapper;
import com.takirahal.srfgroup.chat.mapper.MessageMapper;
import com.takirahal.srfgroup.chat.repositories.ConversationRepository;
import com.takirahal.srfgroup.chat.repositories.MessageRepository;
import com.takirahal.srfgroup.chat.services.ConversationService;
import com.takirahal.srfgroup.chat.services.MessageService;
import com.takirahal.srfgroup.exceptions.AccountResourceException;
import com.takirahal.srfgroup.mapper.UserMapper;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.user.dto.UserDTO;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final Logger log = LoggerFactory.getLogger(ConversationServiceImpl.class);

    @Autowired
    ConversationMapper conversationMapper;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MessageService messageService;

    @Override
    public ConversationDTO save(ConversationDTO conversationDTO) {
        log.debug("Request to save Conversation : {}", conversationDTO);
        Conversation conversation = conversationMapper.toEntity(conversationDTO);
        conversation = conversationRepository.save(conversation);
        return conversationMapper.toDto(conversation);
    }

    @Override
    public boolean createConversationMessage(ConversationVM conversationVM) {
        log.debug("Request to save Conversation : {}", conversationVM);

        UserDTO senderUserDTO = SecurityUtils.getCurrentUser()
                .map(userMapper::toCurrentUserPrincipal)
                .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        UserDTO receiverUserDTO = conversationVM.getConversation().getReceiverUser();

        Optional<Conversation> conversationSenderReceiver = conversationRepository.findBySenderUserAndReceiverUser(
                userMapper.toEntity(senderUserDTO),
                userMapper.toEntity(receiverUserDTO)
        );
        Optional<Conversation> conversationReceiverSender = conversationRepository.findByReceiverUserAndSenderUser(
                userMapper.toEntity(senderUserDTO),
                userMapper.toEntity(receiverUserDTO)
        );

        // Add new message
        if (conversationSenderReceiver != null && conversationSenderReceiver.isPresent()) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setContent(conversationVM.getContent());
            messageDTO.setIsRead(Boolean.FALSE);
            messageDTO.setSenderUser(senderUserDTO);
            messageDTO.setReceiverUser(conversationVM.getConversation().getReceiverUser());
            messageDTO.setConversation(conversationMapper.toDto(conversationSenderReceiver.get()));
            messageService.save(messageDTO);

        // Add new message
        } else if (conversationReceiverSender != null && conversationReceiverSender.isPresent()) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setContent(conversationVM.getContent());
            messageDTO.setIsRead(Boolean.FALSE);
            messageDTO.setSenderUser(senderUserDTO);
            messageDTO.setReceiverUser(conversationVM.getConversation().getReceiverUser());
            messageDTO.setConversation(conversationMapper.toDto(conversationReceiverSender.get()));

        // Create new conversation
        } else {
            conversationVM.getConversation().setSenderUser(senderUserDTO);
            ConversationDTO result = save(conversationVM.getConversation());

            // Add new message
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setContent(conversationVM.getContent());
            messageDTO.setIsRead(Boolean.FALSE);
            messageDTO.setSenderUser(senderUserDTO);
            messageDTO.setReceiverUser(conversationVM.getConversation().getReceiverUser());
            messageDTO.setConversation(result);
            messageService.save(messageDTO);
        }
        return true;
    }

    @Override
    public Page<ConversationWithLastMessageDTO> getOffersByCurrentUser(ConversationFilter conversationFilter, Pageable pageable) {
        log.debug("find offers by criteria : {}, page: {}", pageable);
        Page<ConversationDTO> conversationDTOPage = conversationRepository.findAll(createSpecification(conversationFilter), pageable).map(conversationMapper::toDto);

        List<ConversationWithLastMessageDTO> listConversationWithLastMessage = new ArrayList<>();
        conversationDTOPage.forEach(
                convDTO -> {
                    MessageDTO messageDTO = messageMapper.toDto(
                            messageRepository.findFirstByConversationOrderByIdDesc(conversationMapper.toEntity(convDTO)).get()
                    );
                    ConversationWithLastMessageDTO conversationWithLastMessageDTO = new ConversationWithLastMessageDTO();
                    conversationWithLastMessageDTO.setConversation(convDTO);
                    conversationWithLastMessageDTO.setMessage(messageDTO);
                    listConversationWithLastMessage.add(conversationWithLastMessageDTO);
                }
        );

        Page<ConversationWithLastMessageDTO> conversationWithLastMessageDTOS = new PageImpl<ConversationWithLastMessageDTO>(
                listConversationWithLastMessage,
                pageable,
                conversationDTOPage.getTotalPages()
        );
        return conversationWithLastMessageDTOS;
    }

    private Specification<Conversation> createSpecification(ConversationFilter conversationFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
