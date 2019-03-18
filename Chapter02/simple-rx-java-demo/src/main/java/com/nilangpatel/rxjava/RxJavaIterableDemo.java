package com.nilangpatel.rxjava;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RxJavaIterableDemo {

	public static void main(String[] args) {

		List<EmployeeRating> employeeList = new ArrayList<EmployeeRating>();

		EmployeeRating employeeRating1 = new EmployeeRating();
		employeeRating1.setName("Lilly");
		employeeRating1.setRating(6);
		employeeList.add(employeeRating1);

		employeeRating1 = new EmployeeRating();
		employeeRating1.setName("Peter");
		employeeRating1.setRating(5);
		employeeList.add(employeeRating1);

		employeeRating1 = new EmployeeRating();
		employeeRating1.setName("Bhakti");
		employeeRating1.setRating(9);
		employeeList.add(employeeRating1);

		employeeRating1 = new EmployeeRating();
		employeeRating1.setName("Harmi");
		employeeRating1.setRating(9);
		employeeList.add(employeeRating1);


		Observable<EmployeeRating> employeeRatingSource = Observable.fromIterable(employeeList);

		employeeRatingSource.filter(employeeRating -> employeeRating.getRating() >=7)
			.subscribe(empRating -> System.out.println("Star Employee: " + empRating.getName() 
				+ " Rating : "+empRating.getRating()));

	}

}

class EmployeeRating{
	private String name;
	private int rating;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

}
