package com.messenger_backend.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.ResourceNotFoundException;
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

    public Map<String, Object> getAllMessages(Chat chat) {
        List<Message> messages = messageRepository.findByChatOrderBySentAtAsc(chat);

        List<MessageDTO> allMessageDTOs = messages.stream()
                .map(message -> mapOfMessageDTO(message, message.getSender())).collect(Collectors.toList());

        Map<String, Object> allMessages = new HashMap<>();
        allMessages.put("chatId", chat.getChatId());
        allMessages.put("messages", allMessageDTOs);

        return allMessages;
    }

    public MessageDTO mapOfMessageDTO(Message message, UserEntity userEntity) {
        Message repliedToMessage = message.getRepliedTo();
        return MessageDTO.builder()
                .chatId(message.getChat().getChatId())
                .content(message.getContent())
                .senderId(userEntity.getUserId())
                .msgId(message.getMsgId())
                .sentAt(message.getSentAt())
                .isRead(message.getIsRead())
                .emojiReaction(message.getEmojiReaction())
                .repliedToMsgId(
                        repliedToMessage != null ? repliedToMessage.getMsgId() : null)
                .repliedToMsgContent(repliedToMessage != null ? repliedToMessage.getContent() : null)
                .build();
    }

    public MessageDTO saveEmojiReaction(Long msgId, String emoji) {
        Optional<Message> messageOptional = messageRepository.findById(msgId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setEmojiReaction(emoji);
            messageRepository.save(message);
            return mapOfMessageDTO(message, message.getSender());
        } else {
            throw new ResourceNotFoundException("Message Id " + msgId + "is not found");
        }
    }
}
