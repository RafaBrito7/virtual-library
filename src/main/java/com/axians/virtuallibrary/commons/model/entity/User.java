package com.axians.virtuallibrary.commons.model.entity;

import java.util.Date;

import com.axians.virtuallibrary.api.dto.UserDTO;

public class User {

	private String name;

	private String email;

	private String password;

	private String profile;

	private Date createdDate;

	private String actions;

	public User() {}

	public User(String name, String email, String password, String profile, Date createdDate, String actions) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.createdDate = createdDate;
		this.actions = actions;
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

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}
	
	public UserDTO generateTransportObject() {
		return new UserDTO(this.name, this.email, this.password, this.profile, 
				this.createdDate, this.actions);
	}

}
