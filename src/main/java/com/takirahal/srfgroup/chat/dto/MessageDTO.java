package com.takirahal.srfgroup.chat.dto;

import com.takirahal.srfgroup.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private Long id;

    @Lob
    private String content;

    private Boolean isRead;

    private UserDTO senderUser;

    private UserDTO receiverUser;

    private ConversationDTO conversation;
}
