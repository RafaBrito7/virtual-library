package com.axians.virtuallibrary.commons.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.axians.virtuallibrary.api.dto.UserDTO;

@SuppressWarnings("serial")
@Entity
public class User implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 60, nullable = false)
	private String name;

	@Column(length = 120, nullable = false, unique = true)
	private String email;

	@Column(length = 255, nullable = false)
	private String password;

	@Column(length = 255)
	private String profile;

	@Column(nullable = false)
	private Date createdDate;

	@Column(columnDefinition = "tinyint default 0")
	private Boolean deleted;

	public User() {}

	public User(String name, String email, String password, String profile, Date createdDate, Boolean deleted) {
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
	
	public UserDTO generateTransportObject() {
		return new UserDTO(this.name, this.email, this.password, this.profile, 
				this.createdDate, this.deleted);
	}

}
