package com.core.authorization.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.core.authorization.repository.UserDAO;
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
	@Autowired
	PlatformTransactionManager txtManager;
	@Autowired
	private UserDAO userDAO;
	private GeneratePasswordUtil generatePasswordUtil;
	
	@Override
	public int registerUserInformation( GData inputData ) throws Exception {
		
		TransactionStatus txtStatus = txtManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			txtManager.commit( txtStatus );
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>> Register User Information Error >>>>>>>>>>: " + e.getMessage() );
			txtManager.rollback( txtStatus );
		}
		
		return 0;
	}

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

}
