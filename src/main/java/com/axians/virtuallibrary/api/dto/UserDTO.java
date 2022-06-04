package com.axians.virtuallibrary.api.dto;

import java.util.Date;

import com.axians.virtuallibrary.commons.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class UserDTO {

	private String name;

	private String email;

	private String password;

	private String profile;

	private Date createdDate;

	@JsonIgnoreProperties
	private Boolean deleted;

	public UserDTO() {}

	public UserDTO(String name, String email, String password, String profile, Date createdDate, Boolean deleted) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.createdDate = createdDate;
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public User generatePersistObject() {
		return new User(this.name, this.email, this.password, this.profile, 
				this.createdDate, this.deleted);
	}

}
