package com.messenger_backend.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.messenger_backend.model.Chat;
import com.messenger_backend.model.Message;
import com.messenger_backend.model.UserEntity;
import com.messenger_backend.repo.ChatRepository;
import com.messenger_backend.repo.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl {
   
    private final ChatRepository chatRepository;
    private final ContactService contactService;
    private final MessageRepository messageRepository;

    public Map<Long,List<Message>> createOrgetChat(Long userId, Long contactId) {
        UserEntity sender = contactService.checkIfUserExists(userId);
        UserEntity receiver = contactService.checkIfUserExists(contactId);
           Optional<Chat> existingChat = chatRepository.findBySenderAndReceiver(sender, receiver)
            .or(() -> chatRepository.findByReceiverAndSender(sender, receiver)); // Check both directions

    Chat chat;
    if (existingChat.isPresent()) {
        chat = existingChat.get(); // Use existing chat
    } else {
        chat = createNewChat(sender, receiver); // Create new chat only if it doesn't exist
    }
        List<Message> messages = messageRepository.findByChatOrderBySentAtAsc(chat);
        Map<Long, List<Message>> allMessages = new HashMap<>();
        allMessages.put(chat.getChatId(), messages);
        return allMessages;

    }
   
    public Chat createNewChat(UserEntity sender, UserEntity receiver) {
        Chat newChat = Chat.builder()
                .sender(sender)
                .receiver(receiver)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        return chatRepository.save(newChat);
    }
} 