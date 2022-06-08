package com.axians.virtuallibrary.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axians.virtuallibrary.api.model.dto.UserDTO;
import com.axians.virtuallibrary.api.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User Operations")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
public class UserRest {
	
	private UserService userService;
	
	public UserRest(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/create")
	@ApiOperation("Operation to create a User, returns status 200 if performed")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request, some parameter invalid"),
			@ApiResponse(responseCode = "403", description = "Forbidden, not authorized to create"),
			@ApiResponse(responseCode = "409", description = "Conflict with Other Equals Entity"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> create(@Valid @RequestBody UserDTO user) {
		this.userService.create(user);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.CREATED));
	}
	
	@GetMapping("/list")
	@ApiOperation("Operation to list all users with status actived")
	public ResponseEntity<?> listAll() {
		List<UserDTO> users = this.userService.listAll();
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/disable/{userIdentifier}")
	@ApiOperation("Operation to disable user, change your status to INACTIVE")
	public ResponseEntity<?> disable(@PathVariable("userIdentifier") String userIdentifier) {
		UserDTO userDTO = this.userService.disable(userIdentifier);
		return ResponseEntity.ok(userDTO);
	} 

}
