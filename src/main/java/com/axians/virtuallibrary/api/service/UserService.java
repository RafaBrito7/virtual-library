package com.axians.virtuallibrary.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.model.dto.UserDTO;
import com.axians.virtuallibrary.api.model.entity.User;
import com.axians.virtuallibrary.api.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.api.repository.UserRepository;
import com.axians.virtuallibrary.commons.utils.enums.UserRequiredPropertiesEnum;
import com.axians.virtuallibrary.commons.validations.exceptions.ConflictResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.GenericResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.NotFoundResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateConflictLoggedUser;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateParameterEmptyException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateUserException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateUserNotFoundException;

@Service
public class UserService implements UserDetailsService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void create(final UserDTO userDTO) {
		LOGGER.info("Starting a user creation operation");
		
		ValidateUserException.validate(userDTO);

		LOGGER.info("Checking if exist this user in DataBase");
		Optional<User> userOpt = getUserByEmail(userDTO.getEmail());

		userOpt.ifPresentOrElse(user -> {
			LOGGER.error("This user already exist!");
			throw new ConflictResourceException();
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
	
	public Optional<User> getUserByIdentifier(final String identifier) {
		LOGGER.info("Finding the user in the DataBase by ResourceHyperIdentifier.");
		ValidateParameterEmptyException.validate(identifier, UserRequiredPropertiesEnum.RESOURCEHYPERIDENTIFIER.name());
		return Optional.ofNullable(this.userRepository.findByResourceHyperIdentifier(identifier));
	}
	
	public List<UserDTO> listAll() {
		List<UserDTO> userList = new ArrayList<>();
		this.userRepository.listAllActive().ifPresent(opt -> {
			userList.addAll(opt.stream()
					.map(User::generateTransportObject)
					.collect(Collectors.toList()));
		});
		return userList;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = getUserByEmail(email);
		ValidateUserNotFoundException.validate(userOpt);

		User user = userOpt.get();
		return new UserSpringSecurity(user.getEmail(), user.getPassword(), new ArrayList<>(),
				user.getResourceHyperIdentifier());
	}

	private Optional<User> getUserByEmail(final String email) {
		ValidateParameterEmptyException.validate(email, UserRequiredPropertiesEnum.EMAIL.name());
		return Optional.ofNullable(userRepository.findByEmail(email));
	}
	
	private UserSpringSecurity getLoggedUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return (UserSpringSecurity) loadUserByUsername(email);
	}
	
	public UserDTO disable(final String userIdentifier) {
		LOGGER.info("Starting Service to disable a user");
		Optional<User> userOpt = getUserByIdentifier(userIdentifier);

		userOpt.ifPresentOrElse(user -> {
			UserSpringSecurity loggedUser = getLoggedUser();
			
			ValidateConflictLoggedUser.validate(user, loggedUser);
			
			user.setDeleted(true);
			this.userRepository.save(user);
			LOGGER.info("User found and disabled!");
		}, () -> {
			LOGGER.error("User not found in database with this identifier!");
			throw new NotFoundResourceException();
		});
		return userOpt.get().generateTransportObject();
	}

}
