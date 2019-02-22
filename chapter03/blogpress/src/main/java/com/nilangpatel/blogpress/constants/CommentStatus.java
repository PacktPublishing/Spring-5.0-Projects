package com.nilangpatel.blogpress.constants;

public enum CommentStatus {

	APPROVED("A"), MODERATE("M"),DELETED("D"),REJECTED("R");
	
	private String status;
	
	private CommentStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
