package com.wissen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.wissen.model.Account;
import com.wissen.model.User;


@Component
public interface AccountRepository extends JpaRepository<Account, String> {

	List<Account> findByType(String type);
	List<Account> findByUser(User user);

}
