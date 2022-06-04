package com.axians.virtuallibrary.api.dto;

import io.jsonwebtoken.io.SerializationException;
import io.jsonwebtoken.io.Serializer;

public class UserSpringSecurityDTO implements Serializer<UserSpringSecurityDTO>{

	private String email;

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
