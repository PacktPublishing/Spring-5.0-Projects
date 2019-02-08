package com.packt.linksshr.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.packt.linksshr.model.Comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentService {

	@Autowired ReactiveMongoTemplate mongoTemplate;
	@Autowired LinkService linkService;
	
	public void setMongoTemplate(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public Mono<UpdateResult> newComment(Comment comment) {
		comment.setPostedOn(LocalDateTime.now());
		return mongoTemplate.insert(comment).flatMap( c -> {
			comment.setId(c.getId());
			return linkService.incrementRepliesCount(c.getLinkId());
		});
	}
	
	public Mono<Comment> editComment(Comment comment){
		comment.setUpdatedOn(LocalDateTime.now());
		return mongoTemplate.save(comment);
	}
	
	public Flux<Comment> getComments(String linkId){
		Query query = new Query();
		query.addCriteria(Criteria.where("linkId").is(linkId));
		return mongoTemplate.find(query, Comment.class);
	}
	
	public Flux<Comment> getReplies(String commentId){
		Query query = new Query();
		query.addCriteria(Criteria.where("parentId").is(commentId));
		return mongoTemplate.find(query, Comment.class);
	}
	
	public Mono<Comment> getComment(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Comment.class);
	}
	
	public Mono<DeleteResult> deleteComment(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Comment.class);
	}
}
