package com.demo.isaccanedo.response;

import java.util.List;

import com.demo.isaccanedo.model.User;

import lombok.Data;

@Data
public class FindAllUsersResponse {
	List<User> userList;
}
