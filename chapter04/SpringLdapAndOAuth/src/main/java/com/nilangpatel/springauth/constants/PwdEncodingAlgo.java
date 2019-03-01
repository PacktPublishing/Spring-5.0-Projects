package com.nilangpatel.springauth.constants;

public enum PwdEncodingAlgo {

	BCrypt("bcrypt"), Pbkf2("pbkdf2"), SCrypt("scrypt");
	
	private String status;
	
	private PwdEncodingAlgo(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
