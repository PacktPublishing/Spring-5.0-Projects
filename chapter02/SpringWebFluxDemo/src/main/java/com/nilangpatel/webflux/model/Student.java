package com.nilangpatel.webflux.model;


import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="Student")
public class Student {

	@Id
	@JsonIgnore
	private String id;
	
	@NotNull(message="Roll no can not be empty")
	private Integer rollNo;
	
	@NotNull(message="Name can not be empty")
	private String name;
	
	@NotNull(message="Standard can not be empty")
	private Integer standard;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStandard() {
		return standard;
	}

	public void setStandard(Integer standard) {
		this.standard = standard;
	}

	public Integer getRollNo() {
		return rollNo;
	}

	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", standard=" + standard + "]";
	}
	
}
