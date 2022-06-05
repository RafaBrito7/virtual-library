package com.axians.virtuallibrary.api.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.axians.virtuallibrary.api.model.entity.User;
import com.axians.virtuallibrary.commons.utils.Utils;
import com.axians.virtuallibrary.commons.utils.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class UserDTO {

	@NotNull(message = "Name cannot be null!")
	@NotBlank(message = "Name cannot be empty!")
	private String name;

	@NotNull(message = "Email cannot be null!")
	@NotBlank(message = "Email cannot be empty!")
	private String email;

	@NotNull(message = "Password cannot be null!")
	@NotBlank(message = "Password cannot be empty!")
	private String password;

	private String profile;

	@JsonIgnoreProperties
	private StatusEnum status;
	
	private String resourceHyperIdentifier;
	
	private Date createdDate;
	
	private List<BookDTO> rentedBooks = new ArrayList<>();

	public UserDTO() {}

	public UserDTO(String name, String email, String password, String profile, String resourceHyperIdentifier,
			Date createdDate) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.resourceHyperIdentifier = resourceHyperIdentifier;
		this.createdDate = createdDate;
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
	
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getResourceHyperIdentifier() {
		return resourceHyperIdentifier;
	}

	public void setResourceHyperIdentifier(String resourceHyperIdentifier) {
		this.resourceHyperIdentifier = resourceHyperIdentifier;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<BookDTO> getRentedBooks() {
		return rentedBooks;
	}

	public void setRentedBooks(List<BookDTO> rentedBooks) {
		this.rentedBooks = rentedBooks;
	}

	public User generatePersistObject() {
		return new User(this.name, this.email, this.password, this.profile);
	}
	
	public User generatePersistObjectToCreate() {
		User user = generatePersistObject();
		user.setResourceHyperIdentifier(Utils.generateResourceHyperIdentifier());
		user.setPassword(Utils.encoderPassword(this.password));
		user.setCreatedDate(new Date());
		user.setDeleted(false);
		return user;
	}

}
