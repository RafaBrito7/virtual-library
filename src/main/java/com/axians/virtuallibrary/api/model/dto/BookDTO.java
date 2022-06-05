package com.axians.virtuallibrary.api.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.axians.virtuallibrary.api.model.entity.Book;
import com.axians.virtuallibrary.commons.utils.Utils;
import com.axians.virtuallibrary.commons.utils.enums.StatusBookEnum;

public class BookDTO {

	@NotNull(message = "Title cannot be null!")
	@NotBlank(message = "Title cannot be empty!")
	private String title;

	@NotNull(message = "Category cannot be null!")
	@NotBlank(message = "Category cannot be empty!")
	private String category;

	private Integer inventory;

	private Boolean available;

	private String resourceHyperIdentifier;
	
	private StatusBookEnum status;
	
	public BookDTO() {}

	public BookDTO(String title, String category) {
		this.title = title;
		this.category = category;
	}
	
	public BookDTO(String title, String category, Integer inventory, Boolean available,
			String resourceHyperIdentifier,StatusBookEnum status) {
		this.title = title;
		this.category = category;
		this.inventory = inventory;
		this.available = available;
		this.resourceHyperIdentifier = resourceHyperIdentifier;
		this.status = status;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
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
		return "BookDTO [title=" + title + ", category=" + category + ", inventory=" + inventory + ", available="
				+ available + ", resourceHyperIdentifier=" + resourceHyperIdentifier + "]";
	}
	
	public Book generatePersistObject() {
		return new Book(title, category, available, resourceHyperIdentifier, status);
	}
	
	public Book generatePersistObjectToCreate() {
		 Book book = new Book(title, category);
		 book.setAvailable(true);
		 book.setResourceHyperIdentifier(Utils.generateResourceHyperIdentifier());
		 book.setStatus(StatusBookEnum.AVAILABLE);
		 return book;
	}

}
