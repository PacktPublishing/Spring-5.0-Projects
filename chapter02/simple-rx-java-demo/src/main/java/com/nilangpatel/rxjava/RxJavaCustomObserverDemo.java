package com.nilangpatel.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJavaCustomObserverDemo {

	public static void main(String[] args) {

		Observable<String> months =
				Observable.just("January", "February", "March", "April", 
						"May","June","July","August");

		Observer<String> customObserver = new Observer<String>() {
			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("Subscription initiated ...");
			}
			@Override
			public void onNext(String value) {
				System.out.println("The value " + value +" is received from Observable");
			}
			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}
			@Override
			public void onComplete() {
				System.out.println("Receive all the Data !");
			}
		};

		months.filter(month -> month.endsWith("y"))
						.subscribe(customObserver);
	}
}

