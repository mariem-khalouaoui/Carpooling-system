package com.tunisiologia.TunisiologiaApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tunisiologia.TunisiologiaApp.Repositories.UserRepo;
import com.tunisiologia.TunisiologiaApp.models.UserPrincipal;
import com.tunisiologia.TunisiologiaApp.models.Users;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	Users user= repo.findByUsername(username);
	
	if (user==null) {
		System.out.println("User not found");
		throw new UsernameNotFoundException("User not found");
	}
		 return new UserPrincipal(user);
	}

}