package com.packt.linksshr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.packt.linksshr.model.User;
import com.packt.linksshr.model.UserPrincipal;

import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService {

	@Autowired ReactiveMongoTemplate mongoTemplate;
	
	public void setMongoTemplate(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public boolean userExists(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		Long count = mongoTemplate.count(query, User.class).block();
		return count > 0;
	}
	
	public Mono<User> newUser(User user){
		user.getRoles().add("ROLE_USER");
		return mongoTemplate.insert(user);
	}
	
	public Mono<User> getUserDetail(String username){
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, User.class);
	}
	
	public Mono<User> editUser(User user){
		return mongoTemplate.save(user);
	}
	
	public Mono<DeleteResult> deleteUser(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return mongoTemplate.remove(query, User.class);
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		Mono<User> user = getUserDetail(username);
		return user.map(u ->{
			UserPrincipal userPrincipal = new UserPrincipal();
			userPrincipal.setUser(u);
			return userPrincipal;
		});
	}
}
