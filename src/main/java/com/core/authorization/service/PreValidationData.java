package com.core.authorization.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.authorization.constant.ControllerName;
import com.core.authorization.repository.UserDAO;
import com.core.authorization.type.ResponseResultTypeCode;
import com.core.authorization.util.RequestData;
import com.core.authorization.util.ResponseUtil;

import jara.platform.collection.GData;

@Service
public class PreValidationData  {
	
	@Autowired
	private UserDAO userDAO;
	private Logger logger = LoggerFactory.getLogger( PreValidationData.class );
	
	public GData validateData( RequestData<GData>  inputData ) throws Exception {
		
		/*=================================
		 * 	  Validate Controller Name
		 *=================================*/
		GData controllerName = ResponseUtil.getControllerName();
		if ( controllerName.isEmpty() || controllerName == null ) {
			throw new Exception( ResponseResultTypeCode.MISSING_CONTROLLER_NAME.getValue() );
		}
		
		logger.debug( ">>>>>>>>>> Pre Validation Data Start >>>>>>>>>>: " + inputData );
		/*=================================
		 * 	  Validate Request Data body
		 *=================================*/
		if ( inputData.getBody() == null ) {
			throw new Exception( ResponseResultTypeCode.BODY_DATA_IS_EMPTY.getValue() );
		}
		/*=================================
		 * 	  Validate User Information
		 *=================================*/
		String userID = inputData.getBody().getString("userID");
		if ( StringUtils.isBlank( userID ) ) {
			throw new Exception(  ResponseResultTypeCode.USERID_IS_REQUIRED.getValue() );
		}
		
		GData userParam = new GData();
		GData userInfo	= new GData();
		userParam.setString( "userID", userID );
		
		if ( !ControllerName.REGISTER_USER_INFO.equals( controllerName.getString("controllerName") ) ) {
			userInfo  = userDAO.retrieveUserInfoByUserID( userParam );
			if ( userInfo == null ) {
				throw new Exception( ResponseResultTypeCode.USER_NOT_FOUND.getValue() );
			}
		}
		
		GData outputData = new GData();
		outputData.setGData("userInfo", userInfo );
		
		logger.debug( ">>>>>>>>>> Pre Validation Data End >>>>>>>>>>: " + outputData );
		
		return outputData;
		
	}

}
