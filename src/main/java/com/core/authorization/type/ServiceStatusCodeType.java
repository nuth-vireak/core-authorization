package com.core.authorization.type;

public enum ServiceStatusCodeType {

	NORMAL( "01", "Normal user" ),
	BLOCK( "00", "User block" ),
	REMOVE( "02", "User remove" );
	
	private String value;
	private String desscription;
	
	
	
	private ServiceStatusCodeType(String value, String desscription) {
		this.value = value;
		this.desscription = desscription;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDesscription() {
		return desscription;
	}
	public void setDesscription(String desscription) {
		this.desscription = desscription;
	}
	
}
