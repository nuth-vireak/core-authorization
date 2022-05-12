package com.core.authorization.type;

public enum ResponseResultTypeCode {
	
	SUCCESS_MESSAGE( "0000", "Success" ),
	UNSUCCESS_MESSAGE( "0001", "Unsuccess" ),
	
	// Common Message Start with 1001
	BODY_DATA_IS_EMPTY( "1001", "Data body is empty or null"  ),
	USERID_IS_REQUIRED( "1002", "UserID is required " ),
	NUMERING_TYPE_CODE_EMPTY( "1003", "Numering type code is empty " ),
	NUMERING_INFO_NOT_FOUND( "1004", "Numering info not found" ),
	
	// User Information Message start with 2001
	USER_NOT_FOUND( "2001" , "UserID not found " ),
	SUB_USER_REAH_PASSWORD_ERROR_COUNT( "2002", "You have reached the maximum password error count, Please contact your master user"),
	MASTER_USER_REAH_PASSWORD_ERROR_COUNT( "2003", "You have reached the maximum password error count, Please contact developer"),
	USER_STATUS_NOT_NORMAL( "2004", "UserID not normal, please contact master user"),
	REQUEST_BODY_IS_NULL ( "2005", "Request body is null"),
	USER_REGISTER_STATUS_NOT_NORMAL ( "2006", "User status not normal" ),
	MISSING_FIELD( "2007", "Missing field"),
	USER_ID_ALREADY_EXISTING( "2008", "User ID already existing"),
	USER_LOGIN_NOT_FOUND ( "2009", "User login not found" ),
	USER_MASTER_NOT_FOUND ( "2010", "User master not found" ),
	USER_REGISTER_ERROR ( "2011" , "User register error" ),
	USER_ID_NOT_FOUND ( "2012", "User ID not found" ),
	PASSWORD_NOT_CORRECT ( "2013", "Password not correct"),
	USER_ID_IS_LOCKED ( "2014", "User ID is locked"),
	USER_ID_IS_REMOVED ( "2015", "User ID is removed"),
	USER_OAUTH_CANNOT_REGISTER( "2016", "There are problem while register oauth user "),
	USER_DETAIL_CANNOT_REGISTER( "2017", "There are problem while register user detail "),
	
	// General Message Start with 3001
	GENERAL_MESSAGE( "3001", "General System Error" ),
	MISSING_REQUIRED_FIELD( "3002", "Missing required field" ),
	MISSING_CONTROLLER_NAME( "3003", "Missing controller name" ),
	CONTEXT_DATA_IS_EMPTY( "3004", "Context data is empty" ),
	
	// OAUTH Message Start with 4001
	INVALID_OAUTH_USER ( "4001", "Invalid client_id "),
	INVALID_OAUTH_PASSWORD ( "4002", "Invalid  client_secret"),
	INVALID_OAUTH_USER_OR_PASSWORD ( "4001", "Invalid client_id or client_secret"),
	INVALID_AUTHORIZATION ( "4003", "Authorization is empty"),
	INVALID_TOKEN  ( "4004", "Token is empty"),
	INVALID_BEARER  ( "4005", "Bearer  is empty");
	
	private String value;
	private String description;
	
	private ResponseResultTypeCode(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static ResponseResultTypeCode getReponseMessage( String errorCode ) {
		
		ResponseResultTypeCode result = null;
		ResponseResultTypeCode[] values = ResponseResultTypeCode.values();
		for ( ResponseResultTypeCode resultTypeCode: values ) {
			if ( resultTypeCode.getValue().equals( errorCode ) ) {
				result = resultTypeCode;
				break;
			}
		}
		return result;
	}

}
