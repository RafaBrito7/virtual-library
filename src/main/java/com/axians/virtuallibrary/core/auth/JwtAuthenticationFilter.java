package com.axians.virtuallibrary.core.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.axians.virtuallibrary.api.dto.UserSpringSecurityDTO;
import com.axians.virtuallibrary.commons.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.commons.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private JwtUtils jwtUtils;
	
	private AuthenticationManager authManager;
	
	public JwtAuthenticationFilter(JwtUtils jwtUtils, AuthenticationManager authManager) {
		this.jwtUtils = jwtUtils;
		this.authManager = authManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ServletInputStream inputStream = request.getInputStream();
			UserSpringSecurityDTO credentialsDTO = new ObjectMapper().readValue(inputStream, UserSpringSecurityDTO.class);

			return authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							credentialsDTO.getEmail(), 
							credentialsDTO.getPassword(), 
							new ArrayList<>()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (AccountExpiredException e) {
			throw new AccountExpiredException(e.getMessage());
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage());
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException {
		UserSpringSecurity subject = (UserSpringSecurity) authResult.getPrincipal();

		String token = JWT.create().withSubject((subject).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtUtils.getExpiration()))
				.sign(Algorithm.HMAC512(jwtUtils.getSecret().getBytes()));

		String body = jwtUtils.TOKEN_PREFIX + token;

		response.addHeader(jwtUtils.HEADER_FIELD, jwtUtils.TOKEN_PREFIX + token);
		response.getWriter().write(body);
		response.getWriter().flush();
	}

}
