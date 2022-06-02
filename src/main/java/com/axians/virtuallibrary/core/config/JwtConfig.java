package com.axians.virtuallibrary.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

	private String secret;

	private Long expiration;

	public JwtConfig(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration) {
		this.secret = secret;
		this.expiration = expiration;
	}

	public Boolean validateToken(String token) {
		return false;
	}

	public String generateToken(String userName) {
		return null;
	}

	public String getSecret() {
		return secret;
	}

	public Long getExpiration() {
		return expiration;
	}

}
