package com.axians.virtuallibrary.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.jsonwebtoken.io.SerializationException;
import io.jsonwebtoken.io.Serializer;

public class UserSpringSecurityDTO implements Serializer<UserSpringSecurityDTO>{

	@NotNull(message = "Email cannot be null!")
	@NotBlank(message = "Email cannot be empty!")
	private String email;

	@NotNull(message = "Password cannot be null!")
	@NotBlank(message = "Password cannot be empty!")
	private String password;

	public UserSpringSecurityDTO(String email, String password) {
		this.email = email;
		this.password = password;
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

	public boolean isNotValid() {
		if (this.email == null || email.isEmpty()) {
			return true;
		}
		if (password == null || password.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public byte[] serialize(UserSpringSecurityDTO t) throws SerializationException {
		return null;
	}
}
