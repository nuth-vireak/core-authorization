package com.core.authorization.service;

import jara.platform.collection.GData;

public interface OauthUserService {
	
	int registerOauthUser( GData inputData ) throws Exception;

}
