package com.nilangpatel.rxjava;

import io.reactivex.Observable;

public class RxJavaColdObservable {

	public static void main(String[] args) {

		Observable<String> source =
				Observable.create(
						data-> {
							System.out.println(" ** Starting Emitting **");
							data.onNext("One");
							data.onNext("Two");
							data.onNext("Three");
							data.onNext("Four");
							data.onNext("Five");
						});
				//("One","Two","Three","Four","Five");

		//first observer
		source.filter(data->data.contains("o"))
			 .subscribe(data -> System.out.println("Observer 1 Received:" + data));

		//second observer
		source.subscribe(data -> System.out.println("Observer 2 Received:" + data));

	}

}