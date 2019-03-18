package com.nilangpatel.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class RxJavaHotObservable2 {

	public static void main(String args[]) {
		
		
		Observable<Long> observableInt = Observable.interval(2, TimeUnit.SECONDS);
		
		ConnectableObservable<Long> connectableIntObservable = observableInt.publish();
		connectableIntObservable.subscribe(i -> System.out.println("Observable #1 : "+i));

		connectableIntObservable.connect();
						
		addDelay(7000);
		connectableIntObservable.
			subscribe(i -> System.out.println("Observable #2 : "+i));

		addDelay(10000);

       
	}
	
	private static void addDelay(int miliseconds) {
		try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}
