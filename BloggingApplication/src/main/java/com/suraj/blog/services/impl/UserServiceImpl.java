package com.suraj.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.suraj.blog.exceptions.ResourceNotFoundException;
import com.suraj.blog.models.Role;
import com.suraj.blog.models.User;
import com.suraj.blog.payloads.UserDto;
import com.suraj.blog.repositories.RoleRepo;
import com.suraj.blog.repositories.UserRepo;
import com.suraj.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=dtoToUser(userDto);
		User savedUser=userRepo.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto,Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " id ", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser =userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " id ", userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users= userRepo.findAll();
		return users.stream().map(u->userToDto(u)).collect(Collectors.toList());
	}

	@Override
	public UserDto deleteUser(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " id ", userId));
		userRepo.delete(user);
		return userToDto(user);
	}

	public User dtoToUser(UserDto userDto) {
		User user=modelMapper.map(userDto, User.class);

		return user;
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto=modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user=modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role= roleRepo.findById(502).get();
		
		user.getRoles().add(role);
		User newUser=userRepo.save(user);
		return modelMapper.map(newUser, UserDto.class);
	}
}
