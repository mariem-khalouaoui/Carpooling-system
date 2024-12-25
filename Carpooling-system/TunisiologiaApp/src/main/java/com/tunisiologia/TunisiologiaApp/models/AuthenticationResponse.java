package com.tunisiologia.TunisiologiaApp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	private Role role;
	private String firstName; 
	private String lastName; 
	private String userName;
	private int userId;
	
	
	public AuthenticationResponse(String token, String refreshToken,String fistName, String lastName, Role role, String userName, int userId) {
		this.accessToken = token;
		this.refreshToken = refreshToken;
		this.firstName = fistName;
		this.lastName = lastName;
		this.role = role;
		this.userName = userName;
		this.userId = userId;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUserId () {
		return userId;
	}


	public void setUserId(int id) {
		this.userId = id;
	}
	
	
	
}
