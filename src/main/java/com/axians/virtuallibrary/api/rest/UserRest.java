package com.axians.virtuallibrary.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserRest {
	
	private final UserService userService;
	
	public UserRest(UserService userService) {
		this.userService = userService;
	}
	
	@PreAuthorize("hasRole('ROOT')")
	@PostMapping("/create")
	@ApiOperation("Operation to create a user for system(Only users with root permissions)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a status of created"),
			@ApiResponse(responseCode = "400", description = "Bad Request, some parameter invalid"),
			@ApiResponse(responseCode = "403", description = "Forbidden, not authorized to create"),
			@ApiResponse(responseCode = "409", description = "Conflict, there is already a user created with the parameters passed"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> create(@RequestBody @Valid UserDTO user) {
		this.userService.create(user);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.CREATED));
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
	@GetMapping(value =  "/list", produces="application/json")
	@ApiOperation("Operation to list all users with status actived(Only users with permissions: root or admin)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a list of all users active ordened by created date"),
			@ApiResponse(responseCode = "204", description = "Return a empty list if no have user in database"),
			@ApiResponse(responseCode = "403", description = "Forbidden, the user don't have permission"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<List<UserDTO>> listAll() {
		List<UserDTO> users = this.userService.listAll();
		
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
	@PutMapping("/disable/{userIdentifier}")
	@ApiOperation("Operation to disable user, change your status to INACTIVE(Only users with permissions: root or admin)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User disable operation performed successfully"),
			@ApiResponse(responseCode = "403", description = "Forbidden, the user don't have permission"),
			@ApiResponse(responseCode = "404", description = "User not found in database with this identifier"),
			@ApiResponse(responseCode = "409", description = "The user tried to disable his own user who is logged in"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> disable(@PathVariable("userIdentifier") String userIdentifier) {
		UserDTO userDTO = this.userService.disable(userIdentifier);
		return ResponseEntity.ok(userDTO);
	} 
	
	@PreAuthorize("hasRole('ROOT')")
	@PutMapping("/update/{userIdentifier}")
	@ApiOperation("Operation to update user property(Only users with permissions: root)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User update operation performed successfully"),
			@ApiResponse(responseCode = "403", description = "Forbidden, the user don't have permission"),
			@ApiResponse(responseCode = "404", description = "User not found in database with this identifier"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> update(@PathVariable("userIdentifier") String userIdentifier) {
		//TODO: FALTA IMPLEMENTAR UPDATE
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED));
	} 
	
	@PreAuthorize("hasRole('ROOT')")
	@DeleteMapping("/delete/{userIdentifier}")
	@ApiOperation("Operation to delete user(Only users with permissions: root)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User delete operation performed successfully"),
			@ApiResponse(responseCode = "403", description = "Forbidden, the user don't have permission"),
			@ApiResponse(responseCode = "404", description = "User not found in database with this identifier"),
			@ApiResponse(responseCode = "409", description = "The user tried to delete his own user who is logged in"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> delete(@PathVariable("userIdentifier") String userIdentifier) {
		//TODO: FALTA IMPLEMENTAR DELETE
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED));
	} 
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'USER')")
	@GetMapping(value =  "/logged", produces="application/json")
	@ApiOperation("Operation to get the logged user(Only users with permissions: root, admin or user)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a transport object of the logged user"),
			@ApiResponse(responseCode = "403", description = "Forbidden, the user don't have permission"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<UserDTO> getLoggedUser() {
		return ResponseEntity.ok(this.userService.getLoggedUserDTO());
	}

}
