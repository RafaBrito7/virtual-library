package com.axians.virtuallibrary.commons.utils;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.axians.virtuallibrary.api.dto.UserSpringSecurityDTO;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	private String secret;

	private Long expiration;

	private Gson gson;

	public JwtUtils(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration, Gson gson) {
		this.secret = secret;
		this.expiration = expiration;
		this.gson = gson;
	}

	public Claims getClaims(String token) {
		Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		return parseClaimsJws.getBody();
	}

	public boolean validateToken(String token) {
		Claims claims = getClaims(token);

		Date expiration = claims.getExpiration();
		long currentTimeMillis = System.currentTimeMillis();

		if (currentTimeMillis > expiration.getTime()) {
			return false;
		}

		String subject = claims.getSubject();
		UserSpringSecurityDTO userDTO = gson.fromJson(subject, UserSpringSecurityDTO.class);

		if (userDTO.isNotValid()) {
			return false;
		}
		return true;
	}

//	public String validateToken(String token) {
//		return Jwts
//				.builder()
//					.setSubject(token)
//					.setExpiration(new Date(System.currentTimeMillis() + expiration))
//					.signWith(SignatureAlgorithm.HS512, secret).compact();
//	}

	public String generateToken(String email, Set<String> authority) {
		UserSpringSecurityDTO userSpring = new UserSpringSecurityDTO(email, authority);
		return generateToken(gson.toJson(userSpring));
	}

	private String generateToken(String subject) {
		Date date = new Date(System.currentTimeMillis() + expiration);
		JwtBuilder builder = Jwts.builder().setExpiration(date).setSubject(subject).signWith(SignatureAlgorithm.HS512,
				secret);
		return builder.compact();
	}

	public String getSecret() {
		return secret;
	}

	public Long getExpiration() {
		return expiration;
	}

}
