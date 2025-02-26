package com.messenger_backend.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.messenger_backend.model.Contact;
import com.messenger_backend.model.UserEntity;
import com.messenger_backend.repo.ContactRepository;
import com.messenger_backend.repo.UserRepository;
import com.messenger_backend.service.ContactDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {


	private final ContactRepository contactRepository;

	private final UserRepository userRepository;
	
	@Transactional
	public ContactDTO addContact(Long userId,Long contactId) {
UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")); // Using RuntimeException
        UserEntity contact = userRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found")); // Using RuntimeException

        if (contactRepository.existsByUser_UserIdAndContact_UserId(userId, contactId)) {
            throw new RuntimeException("Contact already exists"); // Using RuntimeException
        }
		
		Contact contactInfo=new Contact();
		contactInfo.setContact(contact);
		contactInfo.setUser(user);
		
		Contact savedContact= contactRepository.save(contactInfo);
		
		return mapContactInfo(savedContact);
		
	}

	private ContactDTO mapContactInfo(Contact savedContact) {

		return ContactDTO.builder()
				.id(savedContact.getId())
				.userId(savedContact.getUser().getUserId())
				.contactId(savedContact.getContact().getUserId())
				.contactName(savedContact.getContact().getName())
				.phoneNumber(savedContact.getContact().getPhoneNumber())
				.image(savedContact.getContact().getImage())
				.createdAt(savedContact.getCreatedAt())
				.build();
	}
	
	public List<ContactDTO> getAllContacts(long userId) {
		return contactRepository.findByUser_UserId(userId).stream()
		.map(contact-> mapContactInfo(contact))
		.collect(Collectors.toList());
	}
}
