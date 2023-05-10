package com.example.demo.payload.response;

import java.util.List;

public class UserInfoResponse {
	   private String token;
	    private Long id;
	    private String username;
	    private String email;
	    private List<String> roles;
	public UserInfoResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }


	public UserInfoResponse(Long id2, String username2, String email2, List<String> roles2) {
		this.id=id2;
		this.username=username2;
		this.email=email2;
		this.roles=roles2;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}