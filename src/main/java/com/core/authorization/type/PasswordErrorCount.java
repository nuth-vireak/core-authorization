package com.core.authorization.type;

public enum PasswordErrorCount {

	USER_REACH_PASSWORD_ERROR_COUNT( 5, "You have reached the maximum password error count");
	
	private int value;
	private String description;
	private PasswordErrorCount(int value, String description) {
		this.value = value;
		this.description = description;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
