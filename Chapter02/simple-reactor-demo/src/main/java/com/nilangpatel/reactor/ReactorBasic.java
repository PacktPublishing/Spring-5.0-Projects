package com.nilangpatel.reactor;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorBasic {

	private static List<String> carModels = Arrays.asList(
	        		"Era","Magna","Sportz","Astha","Astha(O)");
	public static void main(String args[]) {
		
		 Flux<String> fewWords = Flux.just("Hello", "World");
	     Flux<String> manyWords = Flux.fromIterable(carModels);
	     Mono<String> singleWord = Mono.just("Single value");
	     

	     fewWords.subscribe(t->System.out.println(t));
	     System.out.println("-----------------------------");
	     manyWords.subscribe(System.out::println);
	     System.out.println("-----------------------------");
	     singleWord.subscribe(System.out::println);
	}
}
