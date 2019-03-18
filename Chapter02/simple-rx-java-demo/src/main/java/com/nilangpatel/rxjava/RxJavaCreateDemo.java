package com.nilangpatel.rxjava;

import io.reactivex.Observable;

public class RxJavaCreateDemo {

	public static void main(String[] args) {
		Observable<String> daysOfWeek = Observable.create(
				sourceEmitter -> {
					try {
					sourceEmitter.onNext("Sunday");
					sourceEmitter.onNext("Monday");
					sourceEmitter.onNext("Tuesday");
					sourceEmitter.onNext("Wednesday");
					sourceEmitter.onNext("Thursday");
					sourceEmitter.onNext("Friday");
					sourceEmitter.onNext("Saturday");
					sourceEmitter.onComplete();
					// Below data will not be transmitted 
					//sourceEmitter.onNext("SSSSDAY");
					}catch(Exception e) {
						sourceEmitter.onError(e);
					}
				});

		Observable<String> daysInUpperCase= daysOfWeek.map(day->day.toUpperCase())
				.filter(day->day.startsWith("S"));
		daysInUpperCase.subscribe(day->System.out.println("Day is -->"+day));
	}
}
