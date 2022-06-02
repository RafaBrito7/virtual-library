package com.axians.virtuallibrary.commons.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.dto.UserDTO;
import com.axians.virtuallibrary.commons.model.entity.User;
import com.axians.virtuallibrary.commons.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.commons.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//TODO: Faltando Validações(Nulo e Token)
	public void create(UserDTO userObj, String token) {
		User user = userObj.generatePersistObject();
		this.userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserSpringSecurity(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	private User getUser(String username) {
		if (username.isBlank()) {
			throw new IllegalArgumentException("The Field Username is Blank or Empty");
		}
		return userRepository.findByNameIs(username);
	}

}
