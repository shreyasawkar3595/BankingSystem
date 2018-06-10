package com.wissen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wissen.model.User;

@Component
public interface UserRepository extends CrudRepository<User, String> {

	User findByUserName(String userName);
}
