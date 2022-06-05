package com.axians.virtuallibrary.api.model.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSpringSecurity implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String email;
	
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private String resourceHyperIdentifier;

	public UserSpringSecurity(String email, String password, Collection<? extends GrantedAuthority> authorities,
			String resourceHyperIdentifier) {
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.resourceHyperIdentifier = resourceHyperIdentifier;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getResourceHyperIdentifier() {
		return resourceHyperIdentifier;
	}

}
