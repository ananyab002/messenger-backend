package com.messenger_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.messenger_backend.model.UserEntity;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity getByEmail(String email);
	Optional<UserEntity>  findByEmail(String email);
	
	

}
