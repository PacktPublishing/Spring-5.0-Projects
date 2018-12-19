package com.packt.linksshr.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Mono;

@Controller
public class ViewController {

	@GetMapping(path = {"/", "/links"} )
	public Mono<String> index(ServerRequest request, ModelMap model) {
		return request.principal().map(Principal::getName)
			.flatMap(username -> {
				model.put("username", username);
				return Mono.just("links");
			});
	}
}
