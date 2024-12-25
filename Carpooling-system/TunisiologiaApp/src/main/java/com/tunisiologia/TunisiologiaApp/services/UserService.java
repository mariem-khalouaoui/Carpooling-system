package com.tunisiologia.TunisiologiaApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tunisiologia.TunisiologiaApp.Repositories.TokenRepo;
import com.tunisiologia.TunisiologiaApp.Repositories.UserRepo;
import com.tunisiologia.TunisiologiaApp.models.AuthenticationResponse;
import com.tunisiologia.TunisiologiaApp.models.Token;
import com.tunisiologia.TunisiologiaApp.models.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	TokenRepo tokenRepo;
	
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public AuthenticationResponse register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		
		// save the generated token.
		revokeAllTokensByUser(user);
		saveUserToken(accessToken,refreshToken, user);
		
		return new AuthenticationResponse(accessToken,refreshToken, user.getFirstName(), user.getLastName(), user.getRole(), user.getUsername(), user.getId());
	}
	
	public void saveUserToken(String accessToken, String refreshToken, Users user) {
		Token token = new Token();
		token.setAccessToken(accessToken);
		token.setRefreshToken(refreshToken);
		token.setLoggedOut(false);
		token.setUser(user);
		tokenRepo.save(token);
	}

	public void revokeAllTokensByUser(Users user) {
		List<Token> validTokenListByUser = tokenRepo.findAllTokenByUser(user.getId()); 
		
		if(!validTokenListByUser.isEmpty()) {
			validTokenListByUser.forEach(t -> t.setLoggedOut(true));
		}
		tokenRepo.saveAll(validTokenListByUser);
	}
	
	
	
	public AuthenticationResponse verify(Users user) {
		 authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		Users request = userRepo.findByUsername(user.getUsername());
		
		String accessToken = jwtService.generateAccessToken(request);
		String refreshToken = jwtService.generateRefreshToken(request);
		// save the generated token.
		revokeAllTokensByUser(request);
		saveUserToken(accessToken, refreshToken,request);
		
		 return  new AuthenticationResponse(accessToken,refreshToken, request.getFirstName(), request.getLastName(), request.getRole(), request.getUsername(), request.getId());
	}

	public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); 
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return new ResponseEntity<AuthenticationResponse>(HttpStatus.UNAUTHORIZED);
		}
		
		String token = authHeader.substring(7); 
		
		String username = jwtService.extractUserName(token);
		Users user = userRepo.findByUsername(username);
		
		if(jwtService.isValidRefreshToken(token, user)) {
			String accessToken = jwtService.generateAccessToken(user);
			String refreshToken = jwtService.generateRefreshToken(user);
			
			revokeAllTokensByUser(user);
			saveUserToken(accessToken,refreshToken, user);
			
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(
					accessToken,
					refreshToken, 
					user.getFirstName(), 
					user.getLastName(), 
					user.getRole(), 
					user.getUsername(), user.getId())
					, HttpStatus.OK);
		}
		return new ResponseEntity<AuthenticationResponse>(HttpStatus.UNAUTHORIZED);
		}
}
