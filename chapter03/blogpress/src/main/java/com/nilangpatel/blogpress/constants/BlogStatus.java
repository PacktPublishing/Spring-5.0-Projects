package com.nilangpatel.blogpress.constants;

public enum BlogStatus {

	PUBLISHED("Published"), DRAFT("Draft");
	
	private String status;
	
	private BlogStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
}
