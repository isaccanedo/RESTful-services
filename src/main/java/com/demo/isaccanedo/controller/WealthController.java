package com.demo.isaccanedo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.isaccanedo.configuration.Constants;
import com.demo.isaccanedo.exception.BadRequestException;
import com.demo.isaccanedo.model.User;
import com.demo.isaccanedo.model.Wealth;
import com.demo.isaccanedo.request.RetrieveWealthRequest;
import com.demo.isaccanedo.response.RetrieveWealthResponse;
import com.demo.isaccanedo.service.abstractions.IUserService;
import com.demo.isaccanedo.service.abstractions.IWealthService;

@RestController
@RequestMapping(value = "/wealth", produces = { MediaType.APPLICATION_JSON_VALUE })
public class WealthController {

	private IWealthService wealthService;
	private IUserService userService;

	@Autowired
	public WealthController(IWealthService wealthService, IUserService userService) {
		this.wealthService = wealthService;
		this.userService = userService;
	}

	@PostMapping("/retrieve")
	public RetrieveWealthResponse retrieveWealth(@RequestBody RetrieveWealthRequest request) {

		if (request.getUsername() == null || request.getUsername().equals("")) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDUSERNAME);
		}

		User user = userService.findByUserName(request.getUsername());
		Wealth wealth = wealthService.findWealth(user.getId());

		RetrieveWealthResponse response = new RetrieveWealthResponse();
		response.setWealth(wealth);
		return response;
	}

}
