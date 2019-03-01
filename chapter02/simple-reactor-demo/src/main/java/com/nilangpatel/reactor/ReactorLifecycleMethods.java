package com.nilangpatel.reactor;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;

public class ReactorLifecycleMethods {

	public static void main(String[] args) {
		List<String> designationList = Arrays.asList(
				"Jr Consultant","Associate Consultant","Consultant",
				"Sr Consultant","Principal Consultant");

		Flux<String> designationFlux = Flux.fromIterable(designationList);

		designationFlux.doOnComplete(
				() -> System.out.println("Operation Completed ..!!"))
		.doOnNext(
				value -> System.out.println("value in onNext() ->"+value))
		.doOnSubscribe(subscription -> {
			System.out.println("Fetching the values ...!!");
			for(int index=0; index<6;index++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					e.printStackTrace();
					}
					subscription.request(1); // This will send request for next element to Observable
				}
			})
		.doOnError(
				throwable-> {
					System.out.println("Opps, Something went wrong ..!! "
							+throwable.getMessage());
				})
		.doFinally(
				(signalType->
					System.out.println("Shutting down the operation, Bye ..!! "
					+signalType.name())))
		.subscribe();
	}

}
