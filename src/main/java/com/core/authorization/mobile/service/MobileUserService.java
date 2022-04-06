package com.core.authorization.mobile.service;

import jara.platform.collection.GData;

public interface MobileUserService {

	public GData userLogin( GData inputData ) throws Exception;
	
	public GData registerUserInfo( GData inputData ) throws Exception;
}
