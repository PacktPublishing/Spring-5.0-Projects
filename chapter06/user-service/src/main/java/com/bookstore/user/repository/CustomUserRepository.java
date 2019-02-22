package com.bookstore.user.repository;

import com.bookstore.user.model.User;

public interface CustomUserRepository {

	User findByUserName(String userName);
	
	User findByEmail(String email);
	
}
