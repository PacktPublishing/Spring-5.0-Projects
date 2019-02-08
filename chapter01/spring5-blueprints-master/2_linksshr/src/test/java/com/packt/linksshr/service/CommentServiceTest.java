package com.packt.linksshr.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.packt.linksshr.model.Comment;
import com.packt.linksshr.model.Link;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {AppConfiguration.class, TestAppConfiguration.class})
public class CommentServiceTest {

	@Autowired CommentService commentService;
	@Autowired LinkService linkService;
	
	@Qualifier("testMongoTemplate")
	@Autowired ReactiveMongoTemplate mongoTemplate;
	
	private List<String> commentIds = new ArrayList<>();
	private List<String> linkIds = new ArrayList<>();
	
	@Before
	public void setup() {
		commentService.setMongoTemplate(mongoTemplate);
		linkService.setMongoTemplate(mongoTemplate);
	}
	
	@After
	public void cleanup() {
		commentIds.stream().forEach( id -> {
			commentService.deleteComment(id).block();
		});
		linkIds.stream().forEach( id -> {
			linkService.deleteLink(id).block();
		});
	}
	
	@Test public void test_newComment() {
		Link l = new Link();
		String linkId = linkService.newLink(l).block().getId();
		linkIds.add(linkId);
		
		Comment c = new Comment();
		c.setLinkId(linkId);
		c.setContent("Sample");
		commentService.newComment(c).block();
		String id = c.getId();
		commentIds.add(id);
		Link linkFromDb = linkService.getLinkDetail(linkId).block();
		assertThat(linkFromDb.getRepliesCount()).isEqualTo(1);
		Comment cFromDb = commentService.getComment(id).block();
		assertThat(c.getContent()).isEqualTo(cFromDb.getContent());
	}
	
	@Test public void test_editComment() {
		Comment c = new Comment();
		c.setContent("Sample");
		commentService.newComment(c).block();
		String id = c.getId();
		commentIds.add(id);
		c.setContent("updated sample content");
		c.setId(id);
		commentService.editComment(c).block();
		
		Comment cFromDb = commentService.getComment(id).block();
		assertThat(c.getContent()).isEqualTo(cFromDb.getContent());
	}
	
	@Test public void test_getComments() {
		Link l = new Link();
		String linkId = linkService.newLink(l).block().getId();
		Comment c = new Comment();
		c.setContent("sample content");
		c.setLinkId(linkId);
		commentService.newComment(c).block();
		commentIds.add(c.getId());
		linkIds.add(linkId);
		c = new Comment();
		c.setContent("sample other content");
		c.setLinkId(linkId);
		commentService.newComment(c).block();
		commentIds.add(c.getId());
		
		List<Comment> commentsForLink = commentService.getComments(linkId)
				.collectList().block();
		assertThat(commentsForLink).hasSize(2);
	}
	
	@Test public void test_getReplies() {
		Link l = new Link();
		String linkId = linkService.newLink(l).block().getId();
		Comment c = new Comment();
		c.setContent("sample content");
		c.setLinkId(linkId);
		linkIds.add(linkId);
		commentService.newComment(c).block();
		String commentId = c.getId();
		commentIds.add(commentId);
		
		c = new Comment();
		c.setContent("sample reply");
		c.setLinkId(linkId);
		c.setParentId(commentId);
		commentService.newComment(c).block();
		commentIds.add(c.getId());
		c = new Comment();
		c.setContent("sample another reply");
		c.setLinkId(linkId);
		c.setParentId(commentId);
		commentService.newComment(c).block();
		commentIds.add(c.getId());
		
		
		List<Comment> replies = commentService.getReplies(commentId).collectList().block();
		assertThat(replies).hasSize(2);
	}
	
	
}
