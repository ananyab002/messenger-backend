package com.messenger_backend.service;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    private Long msgId;
    private Long chatId;
    private String content;
    private Long senderId;
    private LocalDateTime sentAt;
    private Boolean isRead;
    private String emojiReaction;

}
