package com.messenger_backend.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.messenger_backend.model.Chat;
import com.messenger_backend.model.UserEntity;
import com.messenger_backend.repo.ChatRepository;
import com.messenger_backend.service.MessageDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl {
   
    private final ChatRepository chatRepository;
    private final ContactService contactService;
    private final MessageServiceImpl messageServiceImpl;

    public Map<Long,List<MessageDTO>> createOrgetChat(Long userId, Long contactId) {
        UserEntity user1 = contactService.checkIfUserExists(userId);
        UserEntity user2 = contactService.checkIfUserExists(contactId);
           Optional<Chat> existingChat = chatRepository.findByUser1AndUser2(user1, user2)
            .or(() -> chatRepository.findByUser2AndUser1(user1, user2)); // Check both directions

    Chat chat;
    if (existingChat.isPresent()) {
        chat = existingChat.get(); // Use existing chat
    } else {
        chat = createNewChat(user1, user2);
    }
    return messageServiceImpl.getAllMessages(chat);

    }
   
    public Chat createNewChat(UserEntity user1, UserEntity user2) {
        Chat newChat = Chat.builder()
                .user1(user1)
                .user2(user2)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        return chatRepository.save(newChat);
    }
} 