package com.blogApi.Service;

import java.util.List;

import com.blogApi.entity.User;
import com.blogApi.playload.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	

}
