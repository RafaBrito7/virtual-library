package com.axians.virtuallibrary.commons.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.axians.virtuallibrary.commons.model.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public User findByNameIs(String name);
}
