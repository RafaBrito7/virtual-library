package com.axians.virtuallibrary.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axians.virtuallibrary.api.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);
	
	public User findByResourceHyperIdentifier(String resourceHyperIdentifier);

	@Query(value = "SELECT * FROM user WHERE deleted = 0", nativeQuery = true)
	public Optional<List<User>> listAllActive();
}
