package com.axians.virtuallibrary.commons.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.commons.utils.JwtUtils;
import com.axians.virtuallibrary.commons.validations.exceptions.ValidateParameterEmptyException;

@Service
public class AppService {
	
	private Logger LOGGER = LoggerFactory.getLogger(AppService.class);
	
	private UserService userService;
	
	private JwtUtils jwtUtils;
	
	public AppService(UserService userService, JwtUtils jwtUtils) {
		this.userService = userService;
		this.jwtUtils = jwtUtils;
	}
	
	public String login(String email, String password) {
		LOGGER.info("Starting Login Service");
		
		ValidateParameterEmptyException.validate(email);
		ValidateParameterEmptyException.validate(password);
		LOGGER.info("Parameters validated!");
		
		UserDetails userDetails = userService.loadUserByUsername(email);
		LOGGER.info("Generating Token for user");
		
		String token = jwtUtils.generateToken(userDetails);
		
		LOGGER.debug("Token Generated:");
		LOGGER.debug(token);
		return token;
	}

}
