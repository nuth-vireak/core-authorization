package com.core.authorization.mobile.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.core.authorization.mobile.repository.MobileOauthUserDAO;
import com.core.authorization.mobile.service.MobileOauthUserService;
import com.core.authorization.type.ResponseResultTypeCode;

import jara.platform.collection.GData;

@Service
public class MobileOauthUserServiceImpl implements MobileOauthUserService {

	private Logger logger = LoggerFactory.getLogger( MobileOauthUserServiceImpl.class );
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	MobileOauthUserDAO	mobileOauthUserDAO; 
	
	@Override
	public int registerOauthUser(GData inputData) throws Exception {
		
		try {
			
			String password = bCryptPasswordEncoder.encode(inputData.getString( "password" ));
			GData userOauthParam = new GData();
			userOauthParam.setString( "client_id", inputData.getString("userID") );
			userOauthParam.setString( "client_secret", password );

			mobileOauthUserDAO.registerOauthUserInfo( userOauthParam );
			
		} catch ( Exception e) {
			logger.error(">>>>>>>>>> register ");
			throw new Exception( ResponseResultTypeCode.USER_OAUTH_CANNOT_REGISTER.getValue() );
		}
		return 0;
	}

}
