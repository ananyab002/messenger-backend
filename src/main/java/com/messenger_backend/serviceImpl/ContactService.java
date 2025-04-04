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
	public ContactDTO addContact(Long userId, Long contactId) {

		if (userId.equals(contactId)) {
			throw new IllegalArgumentException("Cannot add yourself as a contact");
		}

		UserEntity user = checkIfUserExists(userId);
		UserEntity contact = checkIfUserExists(contactId);

		Contact existingContact = contactRepository
				.findByUser_UserIdAndContact_UserId(userId, contactId)
				.orElse(null);

		System.out.println("Existing contact" + existingContact);
		if (existingContact == null) {
			Contact contactInfo = new Contact();
			contactInfo.setContact(contact);
			contactInfo.setUser(user);
			existingContact = contactRepository.save(contactInfo);
			return mapContactInfo(existingContact);
		}

		return null;

	}

	public UserEntity checkIfUserExists(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User or Contact not found")); // Using RuntimeException
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
				.map(contact -> mapContactInfo(contact))
				.collect(Collectors.toList());
	}
}
