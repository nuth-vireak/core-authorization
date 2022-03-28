/**
 * 
 */
package com.core.authorization.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.authorization.type.ResponseResultTypeCode;
import com.core.authorization.type.YnTypeCode;
import com.core.authorization.util.ResponseHeader;

import jara.platform.collection.GData;
import jara.util.GDateUtil;

/**
 * @author Hoem Somnang
 *
 */
@Service
public class ProcessHeaderReponse {
	
	private Logger logger = LoggerFactory.getLogger( ProcessHeaderReponse.class);
	
	public ResponseHeader processResponseHeader( GData preOutputData ) throws Exception {
		
		/*==========================================
		 * 			Prepare error message			
		 *==========================================*/
		ResponseResultTypeCode responseResultTypeCode = null;
		String errorCode = preOutputData.getString("errorCode");
		if ( errorCode != null ) {
			logger.debug( ">>>>>>>>>> Process Header Reponse Error Message >>>>>>>>>>: "  + errorCode );
			if ( errorCode.length() > 4 ) {
				responseResultTypeCode = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.GENERAL_MESSAGE.getValue() ); // General System Error
			} else {
				responseResultTypeCode = ResponseResultTypeCode.getReponseMessage( errorCode );
			}
		}
		
		/*===========================================
		 * Last Login User Information :MERCHANT.USERS
		 *==========================================*/
		GData userInfoForUpdate = preOutputData.getGData( "userInfo" );
		userInfoForUpdate.setString( "lastLoginDate", GDateUtil.getCurrentDate( GDateUtil.FORMAT_DATE) );
		userInfoForUpdate.setString( "lastLoginTime", GDateUtil.getCurrentTime() );
		
		/*==========================================
		 * 			Prepare Header Ouput			
		 *==========================================*/
		ResponseHeader header = new ResponseHeader();
		if ( responseResultTypeCode != null ) {
			header.setHeader( YnTypeCode.NO.getValue() , ResponseResultTypeCode.UNSUCCESS_MESSAGE.getValue(), responseResultTypeCode.getDescription(), GDateUtil.getCurrentDate( GDateUtil.FORMAT_DATE) );
		}else {
			header.setHeader( YnTypeCode.YES.getValue() , ResponseResultTypeCode.SUCCESS_MESSAGE.getValue(),  ResponseResultTypeCode.SUCCESS_MESSAGE.getDescription(), GDateUtil.getCurrentDate( GDateUtil.FORMAT_DATE) );
		}
		return header;
	}
}
