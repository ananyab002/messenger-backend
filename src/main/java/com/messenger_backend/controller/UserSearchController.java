package com.messenger_backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.messenger_backend.model.UserSearchEntity;
import com.messenger_backend.service.UserSearchService;


@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserSearchController {

	@Autowired
	UserSearchService userService;
	
	
	@GetMapping("/search")
	public ResponseEntity<List<UserSearchEntity>> searchUsers(@RequestParam String query){
		List<UserSearchEntity> searchResult= userService.searchUsers(query);
		return ResponseEntity.ok(searchResult);
		
		
	}
	
}
