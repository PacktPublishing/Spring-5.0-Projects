package com.nilangpatel.rxjava;

import io.reactivex.Observable;

public class RxJavaBasics {

	public static void main(String[] args) {
		
		/* Observable */
		Observable<String> adminUsers = 
				Observable.just("Dave", 
								"John", 
								"Nilang", 
								"Komal",
								"David");
		
		/* Observer in form of lambda expression */
		//adminUsers.subscribe(s -> System.out.println(s));
		
		/* User map operator */
		adminUsers.map(s->s.startsWith("D") ? s:"*******")
		            .subscribe(s -> System.out.println(s));
	}

}
