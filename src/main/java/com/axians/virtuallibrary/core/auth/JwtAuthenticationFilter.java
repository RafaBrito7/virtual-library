package com.axians.virtuallibrary.core.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.axians.virtuallibrary.api.model.dto.UserSpringSecurityDTO;
import com.axians.virtuallibrary.api.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.commons.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final JwtUtils jwtUtils;
	
	private final AuthenticationManager authManager;
	
	public JwtAuthenticationFilter(JwtUtils jwtUtils, AuthenticationManager authManager) {
		this.jwtUtils = jwtUtils;
		this.authManager = authManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ServletInputStream inputStream = request.getInputStream();
			UserSpringSecurityDTO credentialsDTO = new ObjectMapper().readValue(inputStream,
					UserSpringSecurityDTO.class);

			return authManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(),
					credentialsDTO.getPassword(), new ArrayList<>()));
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
		
		this.logger.info("User Authenticated with success!");

		String token = JWT.create().withSubject((subject).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtUtils.getExpiration()))
				.sign(Algorithm.HMAC512(jwtUtils.getSecret().getBytes()));

		String body = jwtUtils.TOKEN_PREFIX + token;
		JsonObject json = new JsonObject();
		json.addProperty("token", body);

		response.addHeader(jwtUtils.HEADER_FIELD, jwtUtils.TOKEN_PREFIX + token);
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		super.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				
				Map<String, Object> header = new HashMap<>();
				header.put("error", HttpStatus.UNAUTHORIZED.name());
				header.put("timestamp", Calendar.getInstance().getTime());
				header.put("message", exception.getMessage() + " Invalid Username or Password! ");
				
				response.getOutputStream()
		          .println(new ObjectMapper().writeValueAsString(header));
			}
		});
		super.unsuccessfulAuthentication(request, response, failed);
	}

}
