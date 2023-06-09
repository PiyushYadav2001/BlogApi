package com.blogApi.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.blogApi.Exception.ResourceNotFoundException;
import com.blogApi.Repo.UserRepo;
import com.blogApi.Service.UserService;
import com.blogApi.entity.User;
import com.blogApi.playload.UserDto;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo repo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
	 User user=	this.dtoToUser(userDto);
	 User savedUser=this.repo.save(user);
		return this.UserToDto(savedUser);
	}
	

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		 User user=this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		 user.setName(userDto.getName());
		 user.setAbout(userDto.getAbout());
		 user.setEmail(userDto.getEmail());
		 user.setPassword(user.getPassword());
		 
		   User updateUser= this.repo.save(user);
		     UserDto userDto2= this.UserToDto(updateUser);
		return userDto2;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
	     User user=	this.repo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "ID", userId));
	     return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users=this.repo.findAll();
		List<UserDto> userDto = users.stream().map(user->this.UserToDto(user)).collect(Collectors.toList());
		
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
       User user = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
	  this.repo.delete(user);
	}
	
	private User 	dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto,User.class);
//	     user.setId(userDto.getId());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());

		return user;
	}
	public  UserDto  UserToDto(User user) {
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

}
