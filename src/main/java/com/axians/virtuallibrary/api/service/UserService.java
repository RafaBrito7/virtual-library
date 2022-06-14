package com.axians.virtuallibrary.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.axians.virtuallibrary.commons.utils.enums.UserRolesEnum;
import com.axians.virtuallibrary.commons.validations.exceptions.ConflictResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.GenericResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.NotFoundResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateConflictLoggedUser;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateParameterEmptyException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateUserException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateUserNotFoundException;

@Service
public class UserService implements UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void create(final UserDTO userDTO) {
		logger.info("Starting a user creation operation");
		
		ValidateUserException.validate(userDTO);

		logger.info("Checking if exist this user in DataBase");
		Optional<User> userOpt = getUserByEmail(userDTO.getEmail());

		userOpt.ifPresentOrElse(user -> {
			logger.error("This user already exist!");
			throw new ConflictResourceException();
		}, () -> {
			try {
				User user = userDTO.generatePersistObjectToCreate();
				this.userRepository.save(user);
			} catch (Exception e) {
				throw new GenericResourceException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error saving user in database, caused by: " + e.getMessage());
			}
			logger.info("User saved in database with success!");
		});
	}
	
	public Optional<User> getUserByIdentifier(final String identifier) {
		logger.info("Finding the user in the DataBase by ResourceHyperIdentifier.");
		ValidateParameterEmptyException.validate(identifier, UserRequiredPropertiesEnum.RESOURCEHYPERIDENTIFIER.name());
		return Optional.ofNullable(this.userRepository.findByResourceHyperIdentifier(identifier));
	}

	public List<UserDTO> listAll() {
		List<UserDTO> userList = new ArrayList<>();
		this.userRepository.listAllActive().ifPresent(
				opt -> userList.addAll(opt.stream().map(User::generateTransportObject)
						.collect(Collectors.toList())));
		return userList;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = getUserByEmail(email);
		
		ValidateUserNotFoundException.validate(userOpt);
		
		if (!userOpt.isPresent()) {
			throw new NotFoundResourceException();
		}
		
		User user =  userOpt.get();
		UserRolesEnum roleEnum = UserRolesEnum.getEnumByName(user.getProfile());
		
		List<SimpleGrantedAuthority> asList = Arrays.asList(new SimpleGrantedAuthority(roleEnum.getRoleName()));
		return new UserSpringSecurity(user.getEmail(), user.getPassword(),
				asList , user.getResourceHyperIdentifier());
	}

	private Optional<User> getUserByEmail(final String email) {
		ValidateParameterEmptyException.validate(email, UserRequiredPropertiesEnum.EMAIL.name());
		return Optional.ofNullable(userRepository.findByEmail(email));
	}
	
	public UserSpringSecurity getLoggedUserSpring() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return (UserSpringSecurity) loadUserByUsername(email);
	}
	
	public User getLoggedUser() {
		logger.info("Searching the logged user");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<User> userOpt = getUserByEmail(email);
		
		if (!userOpt.isPresent()) {
			throw new NotFoundResourceException();
		}
		return userOpt.get();
	}
	
	public UserDTO getLoggedUserDTO() {
		return getLoggedUser().generateTransportObject();
	}
	
	public User update(User user) {
		return this.userRepository.save(user);
	}
	
	public void disable(final String userIdentifier) {
		logger.info("Starting Service to disable a user");
		Optional<User> userOpt = getUserByIdentifier(userIdentifier);

		userOpt.ifPresentOrElse(user -> {
			UserSpringSecurity loggedUser = getLoggedUserSpring();
			
			ValidateConflictLoggedUser.validate(user, loggedUser);
			
			user.setDeleted(true);
			this.userRepository.save(user);
			logger.info("User found and disabled!");
		}, () -> {
			logger.error("User not found in database with this identifier!");
			throw new NotFoundResourceException();
		});
	}

}
