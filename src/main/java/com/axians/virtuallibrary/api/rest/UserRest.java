package com.axians.virtuallibrary.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axians.virtuallibrary.api.dto.UserDTO;
import com.axians.virtuallibrary.commons.service.UserService;

@RestController
@RequestMapping("user")
public class UserRest {
	
	private UserService userService;
	
	public UserRest(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestHeader("token") String token, @RequestBody UserDTO user) {
		
		return null;
	}

}
