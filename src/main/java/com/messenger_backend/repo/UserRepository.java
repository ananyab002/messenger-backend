package com.messenger_backend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.messenger_backend.model.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity getByEmail(String email);
	Optional<UserEntity>  findByEmail(String email);
}
