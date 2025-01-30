package com.messenger_backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.messenger_backend.model.UserEntity;


public interface UserService extends UserDetailsService{

	public UserEntity registerUser(UserEntity userData);

	//public void deleteUserById(Long id);
}
