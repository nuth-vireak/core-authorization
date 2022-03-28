/**
 * 
 */
package com.core.authorization.type;

/**
 * @author Hoem Somnang
 *
 */
public enum ControllerName {
	
	
	USER1011( "USER1011", "Retreive User Info By UserID" ); 
	
	private String value;
	private String description;
	
	private ControllerName(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
