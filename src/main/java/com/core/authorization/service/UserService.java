package com.core.authorization.service;

import jara.platform.collection.GData;

public interface UserService {

	int registerUserInformation( GData inputData ) throws Exception;
	
	GData userLogin( GData inputData ) throws Exception;
}
