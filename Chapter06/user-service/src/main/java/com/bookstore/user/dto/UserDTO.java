package com.bookstore.user.dto;

import javax.validation.constraints.NotNull;

public class UserDTO {

	private Integer id;
	
	@NotNull(message="UserName can not be empty")
	private String username;
	
	@NotNull(message="Email can not be empty")
	private String email;
	
	@NotNull(message="Mobile can not be empty")
	private String mobile;
	
	@NotNull(message="Password can not be empty")
	private String password;
	
	@NotNull(message="Confirm Password can not be empty")
	private String confirmPassword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
