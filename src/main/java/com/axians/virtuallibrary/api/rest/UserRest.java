package com.axians.virtuallibrary.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axians.virtuallibrary.api.dto.UserDTO;
import com.axians.virtuallibrary.commons.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRest {
	
	private UserService userService;
	
	public UserRest(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody UserDTO user) {
		this.userService.create(user);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.CREATED));
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listAll() {
		List<UserDTO> users = this.userService.listAll();
		return ResponseEntity.ok(users);
	}

}
