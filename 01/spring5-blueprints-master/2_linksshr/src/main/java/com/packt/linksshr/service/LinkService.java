package com.packt.linksshr.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.DistinctPublisher;
import com.packt.linksshr.model.Link;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LinkService {

	@Autowired ReactiveMongoTemplate mongoTemplate;
	
	public void setMongoTemplate(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public Mono<Link> newLink(Link link) {
		link.setPostedOn(LocalDateTime.now());
		return mongoTemplate.insert(link);
	}
	
	public Mono<Link> editLink(Link link) {
		link.setUpdatedOn(LocalDateTime.now());
		return mongoTemplate.save(link);
	}
	
	public Flux<Link> getLinks(Map<String, String> params){
		Query query = new Query();
		params.entrySet().stream().map(entry -> {
			return Criteria.where(entry.getKey()).is(entry.getValue());
		}).forEach(criteria -> {
			query.addCriteria(criteria);	
		});
		return mongoTemplate.find(query, Link.class);
	}
	
	public Mono<Link> getLinkDetail(String id){
		return mongoTemplate.findById(id, Link.class);
	}
	
	public Mono<DeleteResult> deleteLink(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoTemplate.remove(query, Link.class);
	}
	
	
	public Mono<UpdateResult> addUpVote(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		
		Update update = new Update();
		update.inc("upVoteCount", 1);
		return mongoTemplate.updateFirst(query, update, Link.class);
	}
	
	public Mono<UpdateResult> addDownVote(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		
		Update update = new Update();
		update.inc("downVoteCount", 1);
		return mongoTemplate.updateFirst(query, update, Link.class);
	}
	
	public DistinctPublisher<String> getUniqueCategories(){
		return mongoTemplate.getCollection("link")
				.distinct("category", String.class);
	}
	
	public Mono<UpdateResult> incrementView(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.inc("viewCount", 1);
		return mongoTemplate.updateFirst(query, update, Link.class);
	}
	
	public Mono<UpdateResult> incrementRepliesCount(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.inc("repliesCount", 1);
		return mongoTemplate.updateFirst(query, update, Link.class);
	}
}
