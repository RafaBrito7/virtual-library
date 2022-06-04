package com.axians.virtuallibrary.commons.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.dto.UserSpringSecurityDTO;
import com.axians.virtuallibrary.commons.utils.JwtUtils;

@Service
public class AppService {
	
	private Logger LOGGER = LoggerFactory.getLogger(AppService.class);
	
	private UserService userService;
	
	private JwtUtils jwtUtils;
	
	public AppService(UserService userService, JwtUtils jwtUtils) {
		this.userService = userService;
		this.jwtUtils = jwtUtils;
	}
	
	public String login(UserSpringSecurityDTO userDto) {
		LOGGER.info("Starting Login Service");
		
		UserDetails userDetails = userService.loadUserByUsername(userDto.getEmail());
		LOGGER.info("Generating Token for user");
		
		String token = jwtUtils.generateToken(userDetails);
		
		LOGGER.debug("Token Generated:");
		LOGGER.debug(token);
		return token;
	}

}
