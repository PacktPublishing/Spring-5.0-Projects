package com.packt.linksshr.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.packt.linksshr.AppConfiguration;
import com.packt.linksshr.config.TestAppConfiguration;
import com.packt.linksshr.model.User;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {AppConfiguration.class, TestAppConfiguration.class})
public class UserServiceTest {

	@Autowired UserService userService;
	
	@Qualifier("testMongoTemplate")
	@Autowired ReactiveMongoTemplate mongoTemplate;
	
	private List<String> usernamesToBeDeleted = new ArrayList<>();
	
	@Before public void setup() {
		userService.setMongoTemplate(mongoTemplate);
	}
	@After public void cleanup() {
		usernamesToBeDeleted.forEach(username -> userService.deleteUser(username).block());
	}
	
	@Test public void test_newUser() {
		User user = new User();
		user.setUsername("user1");
		user.setEmail("user1@gmail.com");
		user.setName("First User");
		user.setPassword("password");
		userService.newUser(user).block();
		usernamesToBeDeleted.add(user.getUsername());
		User userFromDb = userService.getUserDetail(user.getUsername()).block();
		assertThat(userFromDb.getUsername()).isEqualTo(user.getUsername());
		assertThat(userFromDb.getEmail()).isEqualTo(user.getEmail());
		assertThat(userFromDb.getRoles()).hasSize(1);
		assertThat(userFromDb.getRoles().get(0)).isEqualTo("ROLE_USER");
	}
	
	@Test public void test_userExists() {
		assertThat(userService.userExists("user1")).isFalse();
		
		User user = new User();
		user.setUsername("user1");
		user.setEmail("user1@gmail.com");
		user.setName("First User");
		user.setPassword("password");
		userService.newUser(user).block();
		usernamesToBeDeleted.add(user.getUsername());
		assertThat(userService.userExists("user1")).isTrue();
	}
	
	@Test public void test_editUser() {
		User user = new User();
		user.setUsername("user1");
		user.setEmail("user1@gmail.com");
		user.setName("First User");
		user.setPassword("password");
		userService.newUser(user).block();
		usernamesToBeDeleted.add(user.getUsername());
		
		user.setEmail("user2@gmail.com");
		user.setName("Second User");
		userService.editUser(user).block();
		
		User userFromDb = userService.getUserDetail(user.getUsername()).block();
		assertThat(userFromDb.getUsername()).isEqualTo(user.getUsername());
		assertThat(userFromDb.getEmail()).isEqualTo(user.getEmail());
		assertThat(userFromDb.getName()).isEqualTo(user.getName());
		assertThat(userFromDb.getRoles()).hasSize(1);
	}
}
