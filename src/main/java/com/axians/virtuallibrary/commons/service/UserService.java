package com.axians.virtuallibrary.commons.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.dto.UserDTO;
import com.axians.virtuallibrary.commons.model.entity.User;
import com.axians.virtuallibrary.commons.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.commons.repository.UserRepository;
import com.axians.virtuallibrary.commons.validations.exceptions.ValidateParameterEmptyException;
import com.axians.virtuallibrary.commons.validations.exceptions.ValidateUserNotFoundException;

@Service
public class UserService implements UserDetailsService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//TODO: Faltando Validações(Nulo e Token)
	public void create(UserDTO userObj, String token) {
		LOGGER.info("Starting a user creation operation");
		User user = userObj.generatePersistObject();
//		this.userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = loadUserByEmail(email);
		ValidateUserNotFoundException.validate(userOpt);

		User user = userOpt.get();
		return new UserSpringSecurity(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	private Optional<User> loadUserByEmail(String email) {
		return Optional.ofNullable(userRepository.findByEmail(email));
	}

}
