package com.demo.isaccanedo.service.abstractions;

import java.util.List;

import com.demo.isaccanedo.model.User;

public interface IUserService {

	List<User> findAll();

	User findByUserName(String username);

	User findByTcno(String tcno);

	User createNewUser(User user);
	
	boolean isUsernameExist(String username);
	
	boolean isTcnoExist(String tcno);

}
