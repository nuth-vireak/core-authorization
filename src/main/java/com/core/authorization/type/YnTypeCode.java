/**
 * 
 */
package com.core.authorization.type;

/**
 * @author Hoem Somnang
 *
 */
public enum YnTypeCode {
	
	YES( "Y", "Yes" ),
	NO( "N", "No" );
	
	private String value;
	private String description;
	
	private YnTypeCode(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	

}
