package com.messenger_backend.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.messenger_backend.model.UserSearchEntity;
import com.messenger_backend.search.repo.UserSearchRepo;

@Service
public class UserSearchService {
	
	@Autowired
	UserSearchRepo userSearchRepo;
	
	public UserSearchEntity indexUser(long id, String email,String name, String phoneNumber, String country, String img) {
		UserSearchEntity userSearch=new UserSearchEntity(id,email,name,phoneNumber,country,img);
		System.out.println(userSearch.getCountry());
		return userSearchRepo.save(userSearch);
	}
	
	public List<UserSearchEntity> searchUsers(String query){
        /*List<UserSearchEntity> result = userSearchRepo.findByNameStartingWith(query);
        if (result.isEmpty() || result.size()<1) {
        	System.out.println(("Inside fuzzy"));
            // Fallback to fuzzy search if no prefix matches
            result = userSearchRepo.fuzzySearchByName(query);
        }
        return result;*/
		
		if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        return userSearchRepo.fuzzySearchByNameorEmail(query);
		
		
	}

}
