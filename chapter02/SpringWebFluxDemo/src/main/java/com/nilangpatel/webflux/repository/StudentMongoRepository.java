package com.nilangpatel.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nilangpatel.webflux.model.Student;

import reactor.core.publisher.Mono;

@Repository
public interface StudentMongoRepository extends ReactiveMongoRepository<Student, String>{
	public Mono<Student> findByRollNo(Integer rollNo);
	public Mono<Student> findByName(String name);
}
