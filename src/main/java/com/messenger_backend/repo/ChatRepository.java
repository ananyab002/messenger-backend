package com.messenger_backend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.messenger_backend.model.Chat;
import com.messenger_backend.model.UserEntity;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findBySenderAndReceiver(UserEntity sender, UserEntity receiver);
    Optional<Chat> findByReceiverAndSender(UserEntity sender, UserEntity receiver);
}
