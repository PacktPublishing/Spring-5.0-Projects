package com.nilangpatel.webflux.functinal.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nilangpatel.webflux.model.Student;
import com.nilangpatel.webflux.repository.StudentMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StudentHandler {

	@Autowired
	private StudentMongoRepository studentMongoRepository;

	public  Mono<ServerResponse> getStudent(ServerRequest serverRequest) {
		int rollNo = getInt(serverRequest.pathVariable("rollNo"));
		Mono<Student> studentMonoObj = studentMongoRepository.findByRollNo(rollNo);
		return ServerResponse.ok().body(studentMonoObj, Student.class);
	}

	public  Mono<ServerResponse> getAllStudent(ServerRequest serverRequest) {
		Flux<Student> allStudents = studentMongoRepository.findAll();
		return ServerResponse.ok().body(allStudents, Student.class);
	}


	private int getInt(String intStr) {
		int returnVal=0;
		if(intStr !=null && !intStr.isEmpty()) {
			try {
				returnVal = Integer.parseInt(intStr);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return returnVal;
	}
}
