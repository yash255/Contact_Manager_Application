package com.contact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.contact.entities.User;
import com.contact.repo.UserRepo;

public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//fetching user from database
		
	User user =	userRepo.getUserByName(username);
	
	if(user == null) {
		throw new UsernameNotFoundException("Could not found user !!");
	}
		CustomUserDetail customUserDetail = new CustomUserDetail(user);
		return customUserDetail;
	}
	
	

}
