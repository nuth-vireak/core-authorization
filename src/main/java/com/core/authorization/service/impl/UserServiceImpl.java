package com.core.authorization.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.core.authorization.repository.UserDAO;
import com.core.authorization.service.OauthUserService;
import com.core.authorization.service.UserService;
import com.core.authorization.type.PasswordErrorCount;
import com.core.authorization.type.ResponseResultTypeCode;
import com.core.authorization.type.ServiceStatusCodeType;
import com.core.authorization.type.YnTypeCode;
import com.core.authorization.util.GeneratePasswordUtil;
import com.core.authorization.util.ValidatorUtil;

import jara.platform.collection.GData;
import jara.util.GDateUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private Logger logger = LoggerFactory.getLogger( UserServiceImpl.class );
	private GeneratePasswordUtil generatePasswordUtil;

	@Autowired
	PlatformTransactionManager txManager;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private OauthUserService	oauthUserService;
	
	@Override
	public GData userLogin(GData inputData) throws Exception {
		
		try {
			
			generatePasswordUtil = new GeneratePasswordUtil();
			
			ValidatorUtil.validate( inputData, "userID", "password" );
			
			/*=============================================================
			 * 								Step 1						 
			 * 					Retrieve User Information by ID				
			 *=============================================================*/
			GData userInfoParam = new GData();
			userInfoParam.setString("userID", inputData.getString("userID") );
			GData userInfo = userDAO.retrieveUserInfoByUserID(userInfoParam);
			
			String password      = StringUtils.EMPTY;
			String passwordSH512 = StringUtils.EMPTY;
			String successYN	 =  YnTypeCode.NO.getValue();
			boolean successLogin = true;
			
			if ( userInfo.isEmpty() ) {
				throw new Exception( ResponseResultTypeCode.USER_NOT_FOUND.getValue() );
			} else {
				/*=============================================================
				 * 								Step 2						 	
				 * 							Check USer Status					
				 *=============================================================*/
				if ( ServiceStatusCodeType.BLOCK.getValue().equals( userInfo.getString( "serviceStatusCode" ) )  ) {
					throw new Exception( ResponseResultTypeCode.USER_ID_IS_LOCKED.getValue() );
				} else if ( ServiceStatusCodeType.REMOVE.getValue().equals( userInfo.getString( "serviceStatusCode" ) ) ) {
					throw new Exception( ResponseResultTypeCode.USER_ID_IS_REMOVED.getValue() );
				}
				
				/*=============================================================
				 * 								Step 3						 	
				 * 						Validate User Password					
				 *=============================================================*/
				password =  inputData.getString("password");
				String securityKey = userInfo.getString( "userID" );
				passwordSH512 = generatePasswordUtil.generatePassword( password, securityKey );
				
				if ( !userInfo.getString("password").equals( passwordSH512 ) ) {
					
					successLogin = false;
					
					userInfo.setInt( "userPasswordErrorCount", userInfo.getInt( "userPasswordErrorCount" ) + 1 ); 
					
					if ( userInfo.getInt( "userPasswordErrorCount" ) >= PasswordErrorCount.USER_REACH_PASSWORD_ERROR_COUNT.getValue() ) {
						/*=============================================================
						 * 								Step 4						 	
						 * 						Update wrong user password				
						 *=============================================================*/
						userInfo.setString( "serviceStatusCode", 	ServiceStatusCodeType.BLOCK.getValue() );
						userInfo.setString( "serviceCaseDate", 		GDateUtil.getCurrentDate() );
						userInfo.setString( "lastloginDate", 		GDateUtil.getCurrentDate() );
						userInfo.setString( "lastloginTime", 		GDateUtil.getCurrentTime() );
						userInfo.setString( "updateID", 			userInfo.getString( "userID") );
						
						userDAO.updateUserLoginSuccess( userInfo );
						if ( YnTypeCode.YES.getValue().equals( userInfo.getString( "subUserYN" ) ) ) {
							throw new Exception( ResponseResultTypeCode.SUB_USER_REAH_PASSWORD_ERROR_COUNT.getValue() );
						} else {
							throw new Exception( ResponseResultTypeCode.MASTER_USER_REAH_PASSWORD_ERROR_COUNT.getValue() );
						}
					} 
				}
				
				/*=============================================================
				 * 								Step 5						 	
				 * 						Update Success User login				
				 *=============================================================*/
				if ( successLogin ) {
					successYN = YnTypeCode.YES.getValue();
					userInfo.setInt( "userPasswordErrorCount",   0 );
				}
				userInfo.setString( "lastloginDate",   GDateUtil.getCurrentDate() );
				userInfo.setString( "lastloginTime",   GDateUtil.getCurrentTime() );
				userInfo.setString( "serviceCaseDate", StringUtils.EMPTY );
				
				userDAO.updateUserLoginSuccess( userInfo );
				userInfo.setString( "successYN", successYN );
			}
			return userInfo;
		} catch ( Exception  e ) {
			throw e;
		}
	
	}

	@Override
	public GData registerUserInfo( GData inputData ) throws Exception {
		
		TransactionStatus 	transaction 	= txManager.getTransaction(  new DefaultTransactionAttribute( TransactionDefinition.PROPAGATION_REQUIRES_NEW )  );
		try {
			
			generatePasswordUtil = new GeneratePasswordUtil();
			ValidatorUtil.validate(	inputData
									, "userID"
									, "serviceStatusCode"
									, "password"
									, "userLogin"
									, "subUserYN" 
									, "userName" ); 
			
			GData userInfoParam = new GData();
			userInfoParam.setString( "userID", inputData.getString("userID") );
			/*=============================================================
			 * 							 Step 1					 			
			 * 						Validate user status					
			 *=============================================================*/
			if ( !ServiceStatusCodeType.NORMAL.getValue().equals( inputData.getString("serviceStatusCode") ) ) {
				throw new Exception( ResponseResultTypeCode.USER_REGISTER_STATUS_NOT_NORMAL.getDescription() ); // 01 : Normal 00 Block 02: remove
			}
			/*=============================================================
			 * 							 Step 2					 			
			 * 						validate userID							
			 *=============================================================*/
			GData userInfo = userDAO.retrieveUserInfoByUserID( userInfoParam );
			if ( !userInfo.isEmpty() ) {
				throw new Exception( ResponseResultTypeCode.USER_ID_ALREADY_EXISTING.getDescription() ); 
			} else {
				
				userInfoParam.setString("userID",            inputData.getString("userID") );
				userInfoParam.setString("serviceStatusCode", inputData.getString("serviceStatusCode") );
				
				userInfoParam.setString("profile", 		 	 inputData.getString("profile") );
				userInfoParam.setString("createID", 		 inputData.getString("userLogin") );
				userInfoParam.setString("updateID", 		 inputData.getString("userLogin") );
				userInfoParam.setString("subUserYN", 		 inputData.getString("subUserYN") );
				userInfoParam.setString("masterUserID", 	 inputData.getString("userLogin") );
				
				String password = inputData.getString("password");
				String securityKey = inputData.getString( "userID" );
				String passwordSH521 = generatePasswordUtil.generatePassword( password, securityKey);
				
				userInfoParam.setString("password", 	passwordSH521 );
				
				/*=============================================================
				 * 							 Step 2					 			
				 * 					Validate login User ID 						
				 *=============================================================*/
				GData userLoginInfoParam = new GData();
				GData userLoginInfo      = new GData();
				
				userLoginInfoParam.setString("userID", inputData.getString("userLogin") );
				userLoginInfo = userDAO.retrieveUserInfoByUserID( userLoginInfoParam );
				if ( userLoginInfo ==  null ) {
					throw new Exception( ResponseResultTypeCode.USER_LOGIN_NOT_FOUND.getDescription() );
				} 
				
				/*=============================================================
				 * 							 Step 2					 			
				 * 					Register user info							
				 *=============================================================*/
				userDAO.registerUserInfo( userInfoParam );
				
				oauthUserService.registerOauthUser( inputData );
				logger.info( "Register user information successfully." ); 
				txManager.commit( transaction );
			}
			
		} catch ( Exception e ) {
			txManager.rollback( transaction );
			throw e;
		}
		
		return null;
	}

}
