package com.takirahal.srfgroup.chat.services.impl;

import com.takirahal.srfgroup.chat.dto.ConversationDTO;
import com.takirahal.srfgroup.chat.dto.ConversationWithLastMessageDTO;
import com.takirahal.srfgroup.chat.dto.Filter.ConversationFilter;
import com.takirahal.srfgroup.chat.dto.MessageDTO;
import com.takirahal.srfgroup.chat.entities.Conversation;
import com.takirahal.srfgroup.chat.mapper.ConversationMapper;
import com.takirahal.srfgroup.chat.mapper.MessageMapper;
import com.takirahal.srfgroup.chat.repositories.ConversationRepository;
import com.takirahal.srfgroup.chat.repositories.MessageRepository;
import com.takirahal.srfgroup.chat.services.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    @Override
    public ConversationDTO save(ConversationDTO conversationDTO) {
        log.debug("Request to save Conversation : {}", conversationDTO);
        Conversation conversation = conversationMapper.toEntity(conversationDTO);
        conversation = conversationRepository.save(conversation);
        return conversationMapper.toDto(conversation);
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
        Page<ConversationWithLastMessageDTO> pageConversationWithLastMessage = new Page<ConversationWithLastMessageDTO>() {
            @Override
            public int getTotalPages() {
                return conversationDTOPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return conversationDTOPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super ConversationWithLastMessageDTO, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return conversationDTOPage.getNumber();
            }

            @Override
            public int getSize() {
                return conversationDTOPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return conversationDTOPage.getNumberOfElements();
            }

            @Override
            public List<ConversationWithLastMessageDTO> getContent() {
                return listConversationWithLastMessage;
            }

            @Override
            public boolean hasContent() {
                return conversationDTOPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return conversationDTOPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return conversationDTOPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return conversationDTOPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return conversationDTOPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return conversationDTOPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return conversationDTOPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return conversationDTOPage.previousPageable();
            }

            @Override
            public Iterator<ConversationWithLastMessageDTO> iterator() {
                return null;
            }
        };
        return pageConversationWithLastMessage;
    }

    private Specification<Conversation> createSpecification(ConversationFilter conversationFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
