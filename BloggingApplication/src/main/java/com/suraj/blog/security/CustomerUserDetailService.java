package com.suraj.blog.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.suraj.blog.exceptions.ResourceNotFoundException;
import com.suraj.blog.models.User;
import com.suraj.blog.repositories.UserRepo;

@Service
public class CustomerUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User ", " email : "+username, 0));
		return user;
	}

}
