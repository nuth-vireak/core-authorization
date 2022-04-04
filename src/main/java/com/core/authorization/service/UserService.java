package com.core.authorization.service;

import jara.platform.collection.GData;

public interface UserService {

	public GData userLogin( GData inputData ) throws Exception;
	
	public GData registerUserInfo( GData inputData ) throws Exception;
}
