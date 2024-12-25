package com.tunisiologia.TunisiologiaApp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Hello from admin")
//@SecurityRequirement(name = "bearerAuth")
//@Hidden
public class AdminController {
	
	@Operation(
			description = "get endpoint for admin roles only", 
			summary = "this is a test resources for admin roles only", 
			responses = {
					@ApiResponse(description="Success", responseCode = "200"),
					@ApiResponse(description="Unauthorized / Invalid token", responseCode = "403"),
					@ApiResponse(description="Internal server error", responseCode = "500"),
			})
	
	
	
	//@Hidden
	@GetMapping(value ="/admin",  produces = "application/json")
	public String HelloFromAdminOnly() {
		return "Hello from admin only" ;
	}
}
