package com.axians.virtuallibrary.commons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.dto.UserDTO;
import com.axians.virtuallibrary.commons.model.entity.User;
import com.axians.virtuallibrary.commons.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.commons.repository.UserRepository;
import com.axians.virtuallibrary.commons.utils.enums.UserRequiredPropertiesEnum;
import com.axians.virtuallibrary.commons.validations.exceptions.GenericResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.ValidateParameterEmptyException;
import com.axians.virtuallibrary.commons.validations.exceptions.ValidateUserException;
import com.axians.virtuallibrary.commons.validations.exceptions.ValidateUserNotFoundException;

@Service
public class UserService implements UserDetailsService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void create(UserDTO userDTO) {
		LOGGER.info("Starting a user creation operation");
		
		ValidateUserException.validate(userDTO);

		LOGGER.info("Checking if exist this user in DataBase");
		Optional<User> userOpt = getUserByEmail(userDTO.getEmail());

		userOpt.ifPresentOrElse(user -> {
			LOGGER.error("This user already exist!");
			throw new GenericResourceException(HttpStatus.CONFLICT, "This user already exists! Operation Canceled!");
		}, () -> {
			try {
				User user = userDTO.generatePersistObjectToCreate();
				this.userRepository.save(user);
			} catch (Exception e) {
				throw new GenericResourceException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error saving user in database, caused by: " + e.getMessage());
			}
			LOGGER.info("User saved in database with success!");
		});
	}
	
	public Optional<User> getUserByIdentifier(String identifier) {
		LOGGER.info("Starting Find the user in the DataBase by ResourceHyperIdentifier.");
		ValidateParameterEmptyException.validate(identifier, UserRequiredPropertiesEnum.RESOURCEHYPERIDENTIFIER.name());
		return Optional.ofNullable(this.userRepository.findByResourceHyperIdentifier(identifier));
	}
	
	public List<UserDTO> listAll() {
		return this.userRepository.findAll()
				.stream()
				.map(User::generateTransportObject)
				.collect(Collectors.toList());
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = getUserByEmail(email);
		ValidateUserNotFoundException.validate(userOpt);

		User user = userOpt.get();
		return new UserSpringSecurity(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	private Optional<User> getUserByEmail(String email) {
		ValidateParameterEmptyException.validate(email, UserRequiredPropertiesEnum.EMAIL.name());
		return Optional.ofNullable(userRepository.findByEmail(email));
	}

}
