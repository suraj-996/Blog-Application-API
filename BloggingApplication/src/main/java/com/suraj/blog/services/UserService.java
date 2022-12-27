package com.suraj.blog.services;


import java.util.List;

import com.suraj.blog.payloads.UserDto;

public interface UserService {
	public UserDto registerUser(UserDto userDto);
	public UserDto createUser(UserDto userDto);
	public UserDto updateUser(UserDto userDto,Integer userId);
	public UserDto getUserById(Integer userId);
	public List<UserDto> getAllUsers();
	public UserDto deleteUser(Integer userId);
	 
}
