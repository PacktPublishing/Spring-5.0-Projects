package com.nilangpatel.reactor;

import java.util.stream.Stream;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class ReactorCustomSubscriber {

	public static void main(String[] args) {

		Flux<String> hrUsers = Flux.fromStream(Stream.of(
				"John", "Komal", "Harmi", "Bhakti", "Tom","Peter"));
		
		CustomSubscriber cs = new CustomSubscriber();
		
		hrUsers.subscribe(cs);
	}

	static class CustomSubscriber extends BaseSubscriber<String>{
		 @Override
         protected void hookOnSubscribe(Subscription subscription) {
			 
			 System.out.println("Fetching the values ...!!");
			 /* in case if you wish to see fetching element one by one, uncomment this */
			 /*for(int index=0; index<6;index++) {
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 subscription.request(1);
			 }*/
			 
			 /* in case if you wish to see fetching element one by one, comment this */
			 subscription.request(10);
		 }

         @Override
         protected void hookOnNext(String value) {
        	 System.out.println("Fetchig next value in hookOnNext()-->"+value);
         }

         @Override
         protected void hookOnComplete() { 
        	 System.out.println("Congratulation, Everything is completed successfully ..!!");
         }

         @Override
         protected void hookOnError(Throwable throwable) {
        	 System.out.println("Opps, Something went wrong ..!! "+throwable.getMessage());
         }

         @Override
         protected void hookOnCancel() {
        	 System.out.println("Oh !!, Operation has been cancelled ..!! ");
         }
         
         @Override
         protected void hookFinally(SignalType type) {
        	 System.out.println("Shutting down the operation, Bye ..!! "+type.name());
     	}
	}
}
