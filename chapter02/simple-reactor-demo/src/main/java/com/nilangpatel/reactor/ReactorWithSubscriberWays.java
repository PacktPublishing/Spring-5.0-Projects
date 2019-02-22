package com.nilangpatel.reactor;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;

public class ReactorWithSubscriberWays {

	public static void main(String[] args) {
		
		List<String> monthList = Arrays.asList(
        		"January","February","March","April","May");
		
		Flux<String> months = Flux.fromIterable(monthList);
		/* No events is consumed. */
		months.subscribe();
		/* Only value event is consumed  */
		months.subscribe(month->System.out.println("->"+month));
		
		/* Value and Error (total 2) events are handled  */
		months.subscribe(month->System.out.println("-->"+month),
							e->e.printStackTrace());
		
		/* Value, Error and Completion (total 3) events are subscribed */
		months.subscribe(month->System.out.println("--->"+month),
										e->e.printStackTrace(),
						()->System.out.println("Finished at THIRD PLACE.. !!"));
		
		/* Value, Error, Completion and Subscription (total 4) events are subscribed */
		months.subscribe(month->System.out.println("---->"+month),
										e->e.printStackTrace(),
						 ()->System.out.println("Finished at FOURTH PLACE ..!!"),
						 s -> {System.out.println("Subscribed :");
						 s.request(5L);}); 
						 
	}

}
