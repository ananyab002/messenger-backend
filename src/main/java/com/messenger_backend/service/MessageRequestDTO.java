package com.messenger_backend.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDTO {
    private Long chatId;
    private String content;
    private Long senderId;
    private Long receiverId;
    private Long repliedToMsgId;
}
