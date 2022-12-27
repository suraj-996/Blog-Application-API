package com.suraj.blog.controllers;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.blog.exceptions.ApiException;
import com.suraj.blog.payloads.JwtAuthRequest;
import com.suraj.blog.payloads.JwtAuthResponse;
import com.suraj.blog.payloads.UserDto;
import com.suraj.blog.security.JwtTokenHelper;
import com.suraj.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token= jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
		
	}
	
	private void authenticate(String username,String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid Username or password !!");
		}
	}
	
	//register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		UserDto registerUser= userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}
}
