package com.tunisiologia.TunisiologiaApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tunisiologia.TunisiologiaApp.models.AuthenticationResponse;
import com.tunisiologia.TunisiologiaApp.models.Users;
import com.tunisiologia.TunisiologiaApp.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

	@Autowired
	private UserService service;

	@CrossOrigin(origins = "http://localhost:4200/")
	@PostMapping(value = "/register",  produces = "application/json")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody Users user) {
		return ResponseEntity.ok(service.register(user)) ;
	}

	@CrossOrigin(origins = "http://localhost:4200/")
	@PostMapping(value="/login",  produces = "application/json")
	public ResponseEntity<AuthenticationResponse> login (@RequestBody Users user) {
		return ResponseEntity.ok(service.verify(user)) ;
	}

	@PostMapping(value ="/refresh_token", produces = "application/json")
	public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		return service.refreshToken(request, response);
	}
	


	

}
