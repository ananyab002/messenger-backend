package com.messenger_backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.messenger_backend.model.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
	
	List<Contact> findByUser_UserId(Long userId);

	boolean existsByUser_UserIdAndContact_UserId(Long userId, Long contactId);

	Optional<Contact> findByUser_UserIdAndContact_UserId(Long userId, Long contactId);

}
