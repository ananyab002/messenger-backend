package com.messenger_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;


@Document(indexName = "users")
@Setting(settingPath = "setup_index.json")
public class UserSearchEntity {

	@Id
    private long userId;
	
	@Field(type = FieldType.Text, analyzer = "autocomplete_analyzer", searchAnalyzer = "standard")
    private String email;
	
	@Field(type = FieldType.Text, analyzer = "autocomplete_analyzer", searchAnalyzer = "standard")
    private String name;
	
	@Field(type = FieldType.Keyword )
    private String phoneNumber;
	
	@Field(type = FieldType.Keyword )
	private String country;
	
	@Field(type = FieldType.Keyword )
	private String image;
    
	public UserSearchEntity() {}
	
	
	public UserSearchEntity(long userId, String email, String name, String phoneNumber,String country, String img) {
		super();
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.country=country;
		this.image=img;
	}


	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	@Override
	public String toString() {
		return "UserSearchEntity [userId=" + userId + ", email=" + email + ", name=" + name + "]";
	}

	
   
}

