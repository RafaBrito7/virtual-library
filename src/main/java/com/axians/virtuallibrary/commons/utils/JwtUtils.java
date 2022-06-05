package com.axians.virtuallibrary.commons.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private final String secret;

	private final Long expiration;

	public final String TOKEN_PREFIX = "Bearer ";
	
	public final String HEADER_FIELD = "Authorization";

	public JwtUtils(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration) {
		this.secret = secret;
		this.expiration = expiration;
	}

	public String getSecret() {
		return secret;
	}

	public Long getExpiration() {
		return expiration;
	}

}
