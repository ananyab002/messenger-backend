package com.messenger_backend.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.messenger_backend.service.ContactDTO;
import com.messenger_backend.serviceImpl.ContactService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("contacts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ContactController {

    
     private final ContactService contactService;

    @PostMapping
    public ResponseEntity<?> addContact(
            @RequestParam Long userId,
            @RequestParam Long contactId) {
        System.out.println(userId + "" + contactId);
       try {
            contactService.addContact(userId, contactId);
            List<ContactDTO> allContacts = contactService.getAllContacts(userId);
            return ResponseEntity.ok(allContacts);
        } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}