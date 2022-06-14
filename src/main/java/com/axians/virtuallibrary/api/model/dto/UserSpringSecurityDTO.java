package com.axians.virtuallibrary.api.model.dto;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.core.serializer.Serializer;
import org.springframework.security.core.GrantedAuthority;

public class UserSpringSecurityDTO implements Serializer<UserSpringSecurityDTO>{

	@NotNull(message = "Email cannot be null!")
	@NotBlank(message = "Email cannot be empty!")
	private String email;

	@NotNull(message = "Password cannot be null!")
	@NotBlank(message = "Password cannot be empty!")
	private String password;
	
	@NotNull(message = "Authority cannot be null!")
	@NotBlank(message = "Authority cannot be empty!")
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSpringSecurityDTO() {
	}

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

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
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
	public void serialize(UserSpringSecurityDTO object, OutputStream outputStream) throws IOException {
		// Adicionar serialização
	}
}
