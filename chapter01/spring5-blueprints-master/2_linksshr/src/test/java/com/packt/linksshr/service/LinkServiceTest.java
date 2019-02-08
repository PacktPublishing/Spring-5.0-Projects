package com.packt.linksshr.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.client.result.DeleteResult;
import com.packt.linksshr.AppConfiguration;
import com.packt.linksshr.config.TestAppConfiguration;
import com.packt.linksshr.model.Link;

import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(classes = {AppConfiguration.class, TestAppConfiguration.class})
public class LinkServiceTest {
	@Autowired LinkService linkService;
	
	@Qualifier("testMongoTemplate")
	@Autowired ReactiveMongoTemplate mongoTemplate;
	
	private List<String> linkIds = new ArrayList<>();
	
	@Before
	public void setup() {
		linkService.setMongoTemplate(mongoTemplate);
	}
	
	@Test
	public void test_newLink() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Good Java Post");
		link.setUrl("http://sanaulla.info");
		link = linkService.newLink(link).block();
		linkIds.add(link.getId());
		assertThat(link.getId()).isNotEmpty();
	}
	
	@Test
	public void test_getLinkDetail() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Getting link detail");
		link.setUrl("http://sanaulla.info");
		String linkId = linkService.newLink(link).block().getId();
		linkIds.add(linkId);
		link.setId(linkId);
		Link linkFromDb = linkService.getLinkDetail(linkId).block();
		assertThat(linkFromDb).isEqualTo(link);
	}
	
	@Test
	public void test_deleteLink() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Trying to delete link");
		link.setUrl("http://sanaulla.info");
		String linkId = linkService.newLink(link).block().getId();
		DeleteResult result = linkService.deleteLink(linkId).block();
		//System.out.println(result);
	}
	
	@Test
	public void test_addUpVote() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Trying to delete link");
		link.setUrl("http://sanaulla.info");
		String linkId = linkService.newLink(link).block().getId();
		assertThat(linkService.addUpVote(linkId).block().getModifiedCount()).isEqualTo(1);
		assertThat(linkService.addUpVote(linkId).block().getModifiedCount()).isEqualTo(1);
		
		link = linkService.getLinkDetail(linkId).block();
		assertThat(link.getUpVoteCount()).isEqualTo(2);
		assertThat(link.getVoteCount()).isEqualTo(2);
		linkIds.add(linkId);
	}
	
	@Test
	public void test_addDownVote() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Trying to delete link");
		link.setUrl("http://sanaulla.info");
		String linkId = linkService.newLink(link).block().getId();
		linkIds.add(linkId);
		assertThat(linkService.addDownVote(linkId).block().getModifiedCount()).isEqualTo(1);
		assertThat(linkService.addDownVote(linkId).block().getModifiedCount()).isEqualTo(1);
		assertThat(linkService.addUpVote(linkId).block().getModifiedCount()).isEqualTo(1);
		
		link = linkService.getLinkDetail(linkId).block();
		assertThat(link.getDownVoteCount()).isEqualTo(2);
		assertThat(link.getVoteCount()).isEqualTo(-1);
	}
	
	@Test
	public void test_editLink() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Good Java Post");
		link.setUrl("http://sanaulla.info");
		link = linkService.newLink(link).block();
		linkIds.add(link.getId());
		
		link.setCategory("spring");
		link.setTitle("Good spring post");
		linkService.editLink(link).block();
		Link linkFromDb = linkService.getLinkDetail(link.getId()).block();
		assertThat(linkFromDb).isEqualTo(link);
	}
	
	@Test public void test_getLinks() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Good Java Post");
		link.setUrl("http://sanaulla.info");
		linkIds.add(linkService.newLink(link).block().getId());
		link = new Link();
		link.setCategory("scala");
		link.setTitle("Another post");
		link.setUrl("http://www.google.com");
		linkIds.add(linkService.newLink(link).block().getId());
		
		Map<String, String> params = new HashMap<>();
		params.put("category", "java");
		
		List<Link> links = linkService.getLinks(params).collectList().block();
		assertThat(links).hasSize(1);
	}
	
	@Test public void test_getUniqueCategories() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Good Java Post");
		link.setUrl("http://sanaulla.info");
		linkIds.add(linkService.newLink(link).block().getId());
		link = new Link();
		link.setCategory("scala");
		link.setTitle("Another post");
		link.setUrl("http://www.google.com");
		linkIds.add(linkService.newLink(link).block().getId());
		List<String> categories = Flux.from(linkService.getUniqueCategories())
				.collectList().block();
		assertThat(categories).hasSize(2);
		assertThat(categories).containsSubsequence("java");
	}
	
	@Test public void test_incrementView() {
		Link link = new Link();
		link.setCategory("java");
		link.setTitle("Good Java Post");
		link.setUrl("http://sanaulla.info");
		link.setDescription("some description");
		String linkId = linkService.newLink(link).block().getId();
		linkIds.add(linkId);
		linkService.incrementView(linkId).block();
		linkService.incrementView(linkId).block();
		
		Link linkFromDb = linkService.getLinkDetail(linkId).block();
		assertThat(linkFromDb.getViewCount()).isEqualTo(2);
	}
	
	@After
	public void cleanup() {
		linkIds.forEach(id -> {
			linkService.deleteLink(id).block();
		});
	}
	
	
	
	
}
