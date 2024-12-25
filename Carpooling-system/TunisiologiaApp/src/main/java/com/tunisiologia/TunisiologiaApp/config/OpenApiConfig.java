package com.tunisiologia.TunisiologiaApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Mariem Khalouaoui",
						email = "khalouaouimariem@gmail.com"
						), 
				description = "OpenApi for Covoirini app",
				title = "OpenApi specification for Covoirini app",
				version = "1.0", 
				license = @License(
						name = "Covoirini"
						), 
				termsOfService = "Terms of service"
				), 
		servers = {
				@Server(
						description = "LOCAL ENV", 
						url = "http://localhost:8080"
						)
		}, 
		security = {
				@SecurityRequirement(
						name = "bearerAuth"
						)
		}
		)
@SecurityScheme(
		name = "bearerAuth", 
		description = "JWT description", 
		scheme = "bearer", 
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT", 
		in = SecuritySchemeIn.HEADER
		)
public class OpenApiConfig {

}
