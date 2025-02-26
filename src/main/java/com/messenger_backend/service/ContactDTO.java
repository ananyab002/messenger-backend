package com.messenger_backend.service;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ContactDTO {
    private Long id;
    private Long userId;
    private Long contactId;
    private String contactName;
	private String phoneNumber;
	private String image;
    private LocalDateTime createdAt;

}