package com.axians.virtuallibrary.api.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Book implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 60, nullable = false)
	private String title;

	@Column(length = 60, nullable = false)
	private String category;

	@Column(columnDefinition = "integer default 1")
	private Integer inventory;

	@Column(columnDefinition = "tinyint default 0")
	private Boolean available;

	@Column(nullable = false, unique = true)
	private String resourceHyperIdentifier;

	public Book() {
	}

	public Book(Integer id, String title, String category, Integer inventory, Boolean available,
			String resourceHyperIdentifier) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.inventory = inventory;
		this.available = available;
		this.title = resourceHyperIdentifier;
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

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", category=" + category + ", inventory=" + inventory
				+ ", available=" + available + "]";
	}

}
