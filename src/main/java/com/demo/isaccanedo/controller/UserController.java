package com.demo.isaccanedo.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.isaccanedo.configuration.Constants;
import com.demo.isaccanedo.exception.BadCredentialsException;
import com.demo.isaccanedo.exception.BadRequestException;
import com.demo.isaccanedo.model.User;
import com.demo.isaccanedo.request.CreateUserRequest;
import com.demo.isaccanedo.response.CreateUserResponse;
import com.demo.isaccanedo.response.FindAllUsersResponse;
import com.demo.isaccanedo.service.abstractions.IUserService;
import com.demo.isaccanedo.service.abstractions.IWealthService;

@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

	private IUserService userService;
	private IWealthService wealthService;

	@Autowired
	public UserController(IUserService userService, IWealthService wealthService) {
		this.userService = userService;
		this.wealthService = wealthService;
	}

	@GetMapping("/find/all")
	public FindAllUsersResponse findAll() {
		List<User> userList = userService.findAll();
		
		FindAllUsersResponse response = new FindAllUsersResponse();
		response.setUserList(userList);
		return response;
	}

	@PostMapping("/create")
	public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDUSERNAME);
		}
		
		if (request.getPassword() == null || request.getPassword().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDPASSWORD);
		}

		if (request.getTcno() == null || request.getTcno().length() != 11 || !Pattern.matches("[0-9]+", request.getTcno())) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDTCNO);
		}

		boolean isUsernameExist = userService.isUsernameExist(request.getUsername());
		if (isUsernameExist) {
			throw new BadCredentialsException(Constants.MESSAGE_SAMEUSERNAMEEXIST);
		}

		boolean isTcnoExist = userService.isTcnoExist(request.getTcno());
		if (isTcnoExist) {
			throw new BadCredentialsException(Constants.MESSAGE_SAMETCNOEXIST);
		}

		User user = userService.createNewUser(new User(request.getUsername(), request.getPassword(), request.getTcno()));
		wealthService.newWealthRecord(user.getId());

		CreateUserResponse response = new CreateUserResponse();
		response.setUsername(user.getUsername());
		response.setTcno(user.getTcno());
		return response;
	}

}
