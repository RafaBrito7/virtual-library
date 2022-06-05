package com.axians.virtuallibrary.api.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.axians.virtuallibrary.api.model.dto.UserDTO;
import com.axians.virtuallibrary.commons.utils.enums.StatusEnum;

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
	
	@Column(nullable = false, unique = true)
	private String resourceHyperIdentifier;

	public User() {}

	public User(String name, String email, String password, String profile, Date createdDate, Boolean deleted,
			String resourceHyperIdentifier) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.createdDate = createdDate;
		this.deleted = deleted;
		this.resourceHyperIdentifier = resourceHyperIdentifier;
	}
	
	public User(String name, String email, String password, String profile) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceHyperIdentifier() {
		return resourceHyperIdentifier;
	}

	public void setResourceHyperIdentifier(String resourceHyperIdentifier) {
		this.resourceHyperIdentifier = resourceHyperIdentifier;
	}

	public UserDTO generateTransportObject() {
		UserDTO userDTO = new UserDTO(this.name, this.email, this.password, this.profile, this.resourceHyperIdentifier);
		userDTO.setStatus(this.deleted == false ? StatusEnum.ACTIVE : StatusEnum.INACTIVE);
		return userDTO;
	}

}
