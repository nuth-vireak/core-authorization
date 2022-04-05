package com.core.authorization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.authorization.repository.UserDetailDAO;
import com.core.authorization.service.UserDetailService;
import com.core.authorization.type.ResponseResultTypeCode;
import com.core.authorization.util.ValidatorUtil;

import jara.platform.collection.GData;

@Service
public class UserDetailServiceImpl implements UserDetailService{

	private Logger logger = LoggerFactory.getLogger( UserDetailServiceImpl.class );
	
	@Autowired
	private UserDetailDAO	userDetailDAO;
	
	
	@Override
	public long registerUserDetail( GData inputData ) throws Exception {
		
		try {
			
			ValidatorUtil.validate( inputData, "userID", "userName" );
			 
			GData userDetailInfo = new GData();
			userDetailInfo.setString( "userID"			, inputData.getString("userID") );
			userDetailInfo.setString( "userName"		, inputData.getString("userName") );
			userDetailInfo.setString( "phone"			, inputData.getString("phone") );
			userDetailInfo.setString( "email"			, inputData.getString("email") );
			userDetailInfo.setString( "remark"			, inputData.getString("remark") );
			userDetailInfo.setString( "createID"		, inputData.getString("userLogin") );
			userDetailInfo.setString( "updateID"		, inputData.getString("userLogin") );
			
			userDetailDAO.registerUserDetail( userDetailInfo );
			
		} catch ( Exception e ) {
			logger.error( e.getMessage() );
			e.printStackTrace();
			throw new Exception( ResponseResultTypeCode.USER_DETAIL_CANNOT_REGISTER.getValue() );
		}
		return 1;
	}

}
