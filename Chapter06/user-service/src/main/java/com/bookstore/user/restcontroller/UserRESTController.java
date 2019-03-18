package com.bookstore.user.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.user.dto.UserDTO;
import com.bookstore.user.model.User;
import com.bookstore.user.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserRESTController {
	
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserRESTController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping(value="/register",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> registerNewUser(@Valid @RequestBody UserDTO userDto,Errors errors) {
		
		if(userDto.getUsername() != null) {
			User existingUser = userRepository.findByUsername(userDto.getUsername());
			if(existingUser !=null) {
				
				errors.reject("Existing username", "User is already exist with usernae '"+userDto.getUsername()+"'. ");
			}
			existingUser = userRepository.findByEmail(userDto.getEmail());
			if(existingUser !=null) {
				errors.reject("Existing username", "User is already exist with email '"+userDto.getEmail()+"'. ");
			}
			
			if(!userDto.getPassword().equalsIgnoreCase(userDto.getConfirmPassword())) {
				errors.reject("password not match", "password and confirm password are not same");
			}
		}
		
		if(errors.hasErrors()) {
			List<String> errorMsg = new ArrayList<String>();
			errors.getAllErrors().forEach(a -> errorMsg.add(a.getDefaultMessage()));
			return new ResponseEntity<List<String>>(errorMsg, HttpStatus.BAD_REQUEST);
		}else {
			User userEntity = new User();
			userEntity.setUsername(userDto.getUsername());
			userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
			userEntity.setEmail(userDto.getEmail());
			userEntity.setMobile(userDto.getMobile());
			
			userRepository.save(userEntity);
			List<String> msgLst = Arrays.asList("User registered successfully");
			return new ResponseEntity<List<String>>(msgLst, HttpStatus.OK);
		}
	}
}
