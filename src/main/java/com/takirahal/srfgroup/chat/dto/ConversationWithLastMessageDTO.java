package com.takirahal.srfgroup.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationWithLastMessageDTO {
    ConversationDTO conversation;
    MessageDTO message;
}
