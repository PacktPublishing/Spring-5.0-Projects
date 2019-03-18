package com.nilangpatel.webflux;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.nilangpatel.webflux.websocket.handler.SampleWebSocketHandler;

@SpringBootApplication
@EnableWebFlux
public class SpringWebFluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxDemoApplication.class, args);
	}

	@Autowired
	SampleWebSocketHandler studentWebSocketHandler;

	@Bean
	public HandlerMapping webSockertHandlerMapping() {
		Map<String, WebSocketHandler> map = new HashMap<>();
		map.put("/student", studentWebSocketHandler);

		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setUrlMap(map);
		return mapping;
	}
	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}
}
