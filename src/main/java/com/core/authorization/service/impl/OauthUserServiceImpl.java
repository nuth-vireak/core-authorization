package com.core.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.authorization.repository.UserDAO;
import com.core.authorization.service.OauthUserService;

import jara.platform.collection.GData;

@Service
public class OauthUserServiceImpl implements OauthUserService {

	private Logger logger = LoggerFactory.getLogger( OauthUserServiceImpl.class );
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	public int registerOauthUser(GData inputData) throws Exception {
		
	
		try {
			
			userDAO.retrieveUserInfoByUserID(inputData);
			
			
		} catch ( Exception e) {
			logger.error(">>>>>>>>>> register ");
		}
		return 0;
	}

}
