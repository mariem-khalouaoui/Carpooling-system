package com.tunisiologia.TunisiologiaApp.services;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tunisiologia.TunisiologiaApp.Repositories.TokenRepo;
import com.tunisiologia.TunisiologiaApp.models.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

	@Autowired 
	TokenRepo tokenRepo;
	
	@Value("${application.security.jwt.secret-key}")
	private String SECRET_KEY="";
	
	@Value("${application.security.jwt.access-token-expiration}")
	private long AccessTokenExpiration;
	
	@Value("${application.security.jwt.refresh-token-expiration}")
	private long RefreshTokenExpiration;
	
	
	public boolean isValid(String token, UserDetails user) {
		String username = extractUserName(token);
		boolean isLoggedOutToken = tokenRepo.findByAccessToken(token).map(t->!t.isLoggedOut()).orElse(false);
		return (username.equals(user.getUsername()) && !isTokenExpired(token) && isLoggedOutToken);
	}
	
	
	public boolean isValidRefreshToken(String token, Users user) {
		String username = extractUserName(token);
		boolean isLoggedOutToken = tokenRepo.findByRefreshToken(token).map(t->!t.isLoggedOut()).orElse(false);

		return (username.equals(user.getUsername()) && !isTokenExpired(token) && isLoggedOutToken);

	}
	
	private  boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token); 
		return resolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
			public String generateAccessToken(Users user) {
				return generateToken(user, AccessTokenExpiration);
			}
			
			public String generateRefreshToken(Users user) {
				return generateToken(user, RefreshTokenExpiration);
			}
			
			private String generateToken(Users user , long expirationTime) {
				String token = Jwts
						.builder()
						.subject(user.getUsername())
						.issuedAt(new Date(System.currentTimeMillis()))
						.expiration(new Date(System.currentTimeMillis() + expirationTime))
		                .signWith(getSigningKey()) 
		                .compact();
				return token;
			}
			
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	

}
