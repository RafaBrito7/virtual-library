package com.axians.virtuallibrary.api.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.axians.virtuallibrary.api.model.dto.UserDTO;
import com.axians.virtuallibrary.commons.utils.enums.StatusUserEnum;
import com.axians.virtuallibrary.commons.utils.enums.UserRolesEnum;

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

	@Column(length = 20)
	private String profile;

	@Column(nullable = false)
	private Date createdDate;

	@Column(columnDefinition = "tinyint default 0")
	private Boolean deleted;
	
	@Column(nullable = false, unique = true)
	private String resourceHyperIdentifier;
	
	@Column(name = "book_id")
	@ElementCollection(targetClass = Book.class, fetch = FetchType.LAZY)
	private List<Book> rentedBooks = new ArrayList<>();

	public User() {}

	public User(String name, String email, String password, String profile, Date createdDate, Boolean deleted,
			String resourceHyperIdentifier, List<Book> rentedBooks) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile = profile;
		this.createdDate = createdDate;
		this.deleted = deleted;
		this.resourceHyperIdentifier = resourceHyperIdentifier;
		this.rentedBooks = rentedBooks;
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

	public List<Book> getRentedBooks() {
		return rentedBooks;
	}

	public void setRentedBooks(List<Book> rentedBooks) {
		this.rentedBooks = rentedBooks;
	}

	public UserDTO generateTransportObject() {
		UserDTO userDTO = new UserDTO(this.name, this.email, this.password, UserRolesEnum.getEnumByName(profile),
				this.resourceHyperIdentifier, this.createdDate);
		userDTO.setStatus(this.deleted == false ? StatusUserEnum.ACTIVE : StatusUserEnum.INACTIVE);

		if (this.rentedBooks != null) {
			userDTO.setRentedBooks(
					this.rentedBooks.stream().map(Book::generateTransportObject).collect(Collectors.toList()));
		}
		return userDTO;
	}

	public void addRentedBook(Book book) {
		this.rentedBooks.add(book);
	}
	
	public void removeBook(Book book) {
		this.rentedBooks.remove(book);
	}
}
