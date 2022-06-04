package com.axians.virtuallibrary.core.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axians.virtuallibrary.api.dto.UserSpringSecurityDTO;
import com.axians.virtuallibrary.commons.service.AppService;

@RestController
@RequestMapping("/login")
public class AppLogin {
	
	private AppService appService;
	
	public AppLogin(AppService appService) {
		this.appService = appService;
	}

	@PostMapping
	public ResponseEntity<?> listAll(@RequestBody UserSpringSecurityDTO userDto) {
		String token = this.appService.login(userDto);
		return ResponseEntity.ok(token);
	}
}
