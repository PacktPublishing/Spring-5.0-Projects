package com.bookstore.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bookstore.inventory.inerceptor.FeignClientInterceptor;

import feign.RequestInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages="com.bookstore.inventory.repository")
@EnableFeignClients(basePackages="com.bookstore.inventory")
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	@Bean
    public RequestInterceptor getFeignClientInterceptor() {
        return new FeignClientInterceptor();
    }
}

