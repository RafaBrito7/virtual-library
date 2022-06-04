package com.axians.virtuallibrary.api.dto;

import java.util.Set;

public class UserSpringSecurityDTO {

	private String email;

	private Set<String> authority;

	public UserSpringSecurityDTO(String email, Set<String> authority) {
		this.email = email;
		this.authority = authority;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<String> authority) {
		this.authority = authority;
	}

	public boolean isNotValid() {
		if (this.email == null || email.isEmpty()) {
			return true;
		}
		if (authority == null || authority.isEmpty()) {
			return true;
		}
		return false;
	}
}
