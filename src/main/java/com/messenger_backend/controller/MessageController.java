package com.messenger_backend.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.messenger_backend.model.Chat;
import com.messenger_backend.model.Message;
import com.messenger_backend.model.UserEntity;
import com.messenger_backend.repo.ChatRepository;
import com.messenger_backend.repo.MessageRepository;
import com.messenger_backend.service.ContactDTO;
import com.messenger_backend.service.MessageDTO;
import com.messenger_backend.service.MessageRequestDTO;
import com.messenger_backend.serviceImpl.ContactService;
import com.messenger_backend.serviceImpl.MessageServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ContactService contactService;
    private final MessageServiceImpl messageServiceImpl;

    @MessageMapping("/send")
    public ResponseEntity<Object> sendMessage(MessageRequestDTO messageRequestDTO) {
        System.out.println(messageRequestDTO.getSenderId());
        try {
            UserEntity userEntity = contactService.checkIfUserExists(messageRequestDTO.getSenderId());
            Chat chat = chatRepository.findById(messageRequestDTO.getChatId())
                    .orElseThrow(() -> new RuntimeException("Chat not found"));

            Message message = Message.builder()
                    .chat(chat)
                    .content(messageRequestDTO.getContent())
                    .sentAt(LocalDateTime.now())
                    .sender(userEntity)
                    .isRead(false)
                    .build();

            messageRepository.save(message);
            MessageDTO messageDTO = messageServiceImpl.mapOfMessageDTO(message, userEntity);
            ContactDTO contactDTO = contactService.addContact(messageRequestDTO.getReceiverId(),
                    messageRequestDTO.getSenderId());

            simpMessagingTemplate.convertAndSend("/topic/chats/" + chat.getChatId(), messageDTO);
            simpMessagingTemplate.convertAndSend("/topic/contacts/" + messageRequestDTO.getReceiverId(), contactDTO);

            return ResponseEntity.status(HttpStatus.OK).body(messageDTO);
        } catch (RuntimeException e) {

            // Return appropriate status based on error type
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (e.getMessage().contains("not found")) {
                status = HttpStatus.NOT_FOUND;
            } else if (e.getMessage().contains("Invalid")) {
                status = HttpStatus.BAD_REQUEST;
            }

            return ResponseEntity.status(status).body(e.getMessage());
        }

    }

}
