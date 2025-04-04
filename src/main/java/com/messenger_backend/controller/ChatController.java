package com.messenger_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.messenger_backend.service.MessageDTO;
import com.messenger_backend.serviceImpl.ChatServiceImpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("chat")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ChatController {
    private final ChatServiceImpl chatServiceImpl;

    @PostMapping("/createOrgetmessages")
    public ResponseEntity<Map<Long,List<MessageDTO>>> createOrgetChat(@RequestParam Long userId, @RequestParam Long contactId ) {
        Map<Long,List<MessageDTO>> messages = chatServiceImpl.createOrgetChat(userId, contactId);
        return ResponseEntity.ok(messages);
    }
    
    
}
