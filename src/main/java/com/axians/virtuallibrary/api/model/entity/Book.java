package com.axians.virtuallibrary.api.model.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.commons.utils.enums.CategoryBookEnum;
import com.axians.virtuallibrary.commons.utils.enums.StatusBookEnum;

@SuppressWarnings("serial")
@Entity
public class Book implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 60, nullable = false)
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoryBookEnum category;

	@Column(columnDefinition = "tinyint default 1")
	private Boolean available;

	@Column(nullable = false, unique = true)
	private String resourceHyperIdentifier;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusBookEnum status;

	public Book() {}

	public Book(String title, CategoryBookEnum category, String resourceHyperIdentifier, StatusBookEnum status, Boolean available) {
		this.title = title;
		this.category = category;
		this.title = resourceHyperIdentifier;
		this.status = status;
		this.available = available;
	}
	
	public Book(String title, CategoryBookEnum category) {
		this.title = title;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CategoryBookEnum getCategory() {
		return category;
	}

	public void setCategory(CategoryBookEnum category) {
		this.category = category;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getResourceHyperIdentifier() {
		return resourceHyperIdentifier;
	}

	public void setResourceHyperIdentifier(String resourceHyperIdentifier) {
		this.resourceHyperIdentifier = resourceHyperIdentifier;
	}

	public StatusBookEnum getStatus() {
		return status;
	}

	public void setStatus(StatusBookEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", category=" + category
				+ ", available=" + available + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(category, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(category, other.category) && Objects.equals(title, other.title);
	}

	public BookDTO generateTransportObject() {
		return new BookDTO(title, category.name(), resourceHyperIdentifier, status, available);
	}
	
	public void rent() {
		this.available = false;
		this.status = StatusBookEnum.RENTED;
	}
	
	public void refund() {
		this.available = true;
		this.status = StatusBookEnum.AVAILABLE;
	}
	
	public Boolean isAvailable() {
		return this.available;
	}
	
	public Boolean isRented() {
		if (status.equals(StatusBookEnum.RENTED)) {
			return true;
		}
		return false;
	}
	
	public Boolean isDisabled() {
		if (status.equals(StatusBookEnum.DISABLED)) {
			return true;
		}
		return false;
	}
	
}
