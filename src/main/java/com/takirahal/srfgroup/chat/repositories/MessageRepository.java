package com.takirahal.srfgroup.chat.repositories;

import com.takirahal.srfgroup.chat.entities.Conversation;
import com.takirahal.srfgroup.chat.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
//    @Query("select message from Message message where message.senderUser.login = ?#{principal.username}")
//    List<Message> findBySenderUserIsCurrentUser();
//
//    @Query("select message from Message message where message.receiverUser.login = ?#{principal.username}")
//    List<Message> findByReceiverUserIsCurrentUser();
//
    Optional<Message> findFirstByConversationOrderByIdDesc(Conversation conversation);
}
