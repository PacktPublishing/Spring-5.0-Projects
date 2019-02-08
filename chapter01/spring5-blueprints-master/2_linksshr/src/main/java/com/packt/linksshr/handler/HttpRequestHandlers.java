package com.packt.linksshr.handler;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mongodb.client.result.UpdateResult;
import com.packt.linksshr.model.Comment;
import com.packt.linksshr.model.Link;
import com.packt.linksshr.model.User;
import com.packt.linksshr.service.CommentService;
import com.packt.linksshr.service.LinkService;
import com.packt.linksshr.service.UserService;

import ch.qos.logback.core.net.SyslogOutputStream;
import reactor.core.publisher.Mono;

@Configuration
public class HttpRequestHandlers {
	@Autowired LinkService linkService;
	@Autowired CommentService commentService;
	@Autowired UserService userService;
	
	Mono<ServerResponse> message(ServerRequest request) {
		return request.principal()
				.map(Principal::getName)
				.flatMap(username ->
					ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.syncBody(Collections.singletonMap("message", "Hello " + username + "!"))
				);
	}
	
	Mono<ServerResponse> getLinks(ServerRequest request) {
		Map<String, String> params = request.queryParams().toSingleValueMap();
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(linkService.getLinks(params), Link.class);
	}
	
	Mono<ServerResponse> newLink(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(request.principal().map(Principal::getName)
						.flatMap( username -> {
							return request.bodyToMono(Link.class)
									.flatMap( link -> { 
										link.setPostedBy(username);
										link.setPostedOn(LocalDateTime.now());
										return linkService.newLink(link);
									});
						}), Object.class);
		
	}
	
	Mono<ServerResponse> getLink(ServerRequest request){
		String linkId = request.pathVariable("linkId");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(linkService.getLinkDetail(linkId), Link.class);
	}
	
	Mono<ServerResponse> newComment(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(request.principal().map(Principal::getName)
						.flatMap( username -> {
							return request.bodyToMono(Comment.class)
									.flatMap(comment -> {
										comment.setPostedBy(username);
										comment.setPostedOn(LocalDateTime.now());
										return commentService.newComment(comment);
									});
						}), UpdateResult.class);
	}
	
	Mono<ServerResponse> getComments(ServerRequest request) {
		String linkId = request.pathVariable("linkId");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(commentService.getComments(linkId), Comment.class);
	}
	
	Mono<ServerResponse> newUpVote(ServerRequest request){
		String linkId = request.pathVariable("linkId");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(linkService.addUpVote(linkId).flatMap(
						result -> linkService.getLinkDetail(linkId)), Link.class);
	}
	
	Mono<ServerResponse> newDownVote(ServerRequest request){
		String linkId = request.pathVariable("linkId");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(linkService.addDownVote(linkId).flatMap(
						result -> linkService.getLinkDetail(linkId)), Link.class);
	}
	
	Mono<ServerResponse> newUser(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(request.bodyToMono(User.class).flatMap(
						user -> userService.newUser(user)), User.class);
	}
	
	@Bean
	RouterFunction<?> routerFunctions() {
		return RouterFunctions.route(RequestPredicates.GET("/message"), this::message)
			.andRoute(RequestPredicates.GET("/api/links"), this::getLinks)
			.andRoute(RequestPredicates.GET("/api/links/{linkId}"), this::getLink)
			.andRoute(RequestPredicates.POST("/api/links"), this::newLink)
			.andRoute(RequestPredicates.POST("/api/links/{linkId}/upVote"), this::newUpVote)
			.andRoute(RequestPredicates.POST("/api/links/{linkId}/downVote"), this::newDownVote)
			.andRoute(RequestPredicates.POST("/api/links/{linkId}/comments"), this::newComment)
			.andRoute(RequestPredicates.GET("/api/links/{linkId}/comments"), this::getComments)
			.andRoute(RequestPredicates.POST("/api/users"), this::newUser)
			;
	}
}
