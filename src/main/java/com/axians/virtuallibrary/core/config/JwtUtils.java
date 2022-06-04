package com.axians.virtuallibrary.core.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtConfig {

	private String secret;

	private Long expiration;

	public JwtConfig(
			@Value("${jwt.secret}") String secret, 
			@Value("${jwt.expiration}") Long expiration) {
		this.secret = secret;
		this.expiration = expiration;
	}
	
	public Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			System.out.println("Have an error, caused by: " + e.getMessage());
			return null;
		}
	}
	
	public Boolean tokenValidation(String token) {
		Claims claim = getClaims(token);

		if (claim == null) {
			return false;
		} else {
			String email = claim.getSubject();

			if (email == null || email.isEmpty()) {
				return false;
			} else if (claim.getExpiration().after(new Date(System.currentTimeMillis()))) {
				return false;
			} else {
				return true;
			}
		}
	}

	public String validateToken(String token) {
		return Jwts
				.builder()
					.setSubject(token)
					.setExpiration(new Date(System.currentTimeMillis() + expiration))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
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
