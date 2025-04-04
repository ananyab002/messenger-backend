package com.messenger_backend.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.messenger_backend.model.Chat;
import com.messenger_backend.model.Message;
import com.messenger_backend.model.UserEntity;
import com.messenger_backend.repo.MessageRepository;
import com.messenger_backend.service.MessageDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl {

    private final MessageRepository messageRepository;

    public Map<Long, List<MessageDTO>> getAllMessages(Chat chat) {
        List<Message> messages = messageRepository.findByChatOrderBySentAtAsc(chat);

        List<MessageDTO> allMessageDTOs = messages.stream()
                .map(message -> mapOfMessageDTO(message, message.getSender())).collect(Collectors.toList());

        Map<Long, List<MessageDTO>> allMessages = new HashMap<>();
        allMessages.put(chat.getChatId(), allMessageDTOs);


        return allMessages;
    }
        
    public MessageDTO mapOfMessageDTO(Message message, UserEntity userEntity) {
        return MessageDTO.builder()
                .chatId(message.getChat().getChatId())
                .content(message.getContent())
                .senderId(userEntity.getUserId())
                .msgId(message.getMsgId())
                .sentAt(message.getSentAt())
                .isRead(message.getIsRead())
                        .build();
    }
}
