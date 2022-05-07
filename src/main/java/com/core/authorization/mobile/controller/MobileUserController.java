package com.core.authorization.mobile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.authorization.constant.ControllerName;
import com.core.authorization.mobile.service.PreValidationData;
import com.core.authorization.mobile.service.ProcessHeaderReponse;
import com.core.authorization.mobile.service.MobileUserService;
import com.core.authorization.util.RequestData;
import com.core.authorization.util.ResponseData;
import com.core.authorization.util.ResponseHeader;
import com.core.authorization.util.ResponseUtil;

import jara.platform.collection.GData;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserController
* @version   0.1, 2022-04-04
*/
@RestController
@RequestMapping("/USER")
@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
public class MobileUserController {
	
	private Logger logger = LoggerFactory.getLogger( MobileUserController.class );
	
	@Autowired
	private PreValidationData preValidationData;
	@Autowired
	private ProcessHeaderReponse processHeaderReponse;
	@Autowired
	private MobileUserService mobileUserService;
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param inputData
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping( value = ControllerName.MOBILE_RETRIEVE_USER_LOGIN )
	public ResponseData<GData> retrieveUserLogin( @RequestBody RequestData<GData> inputData  ) throws Exception {
		
		/*=============================================
		  * 		Set Controller Context				
		  *============================================*/
		GData controllerName = new GData();
		controllerName.setString( "controllerName", ControllerName.MOBILE_RETRIEVE_USER_LOGIN);
		ResponseUtil.setControllerName( controllerName );
		
		logger.info( ">>>>>>>>>> USER1011 Start >>>>>>>>>>" );
		GData userInfo 			= new GData();
		GData userParam 		= new GData();
		GData preOutputData 	= new GData();
		ResponseHeader	reponseHeader = new ResponseHeader();
		try {
			 /*============================================
			  * 				Pre Validation				
			  *============================================*/
			 preOutputData = preValidationData.validateData( inputData );
			
			 /*============================================
			  * 		    Request to Service		
			  *============================================*/
			 userParam.setString("userID", inputData.getBody().getString("userID") );
			 userParam.setString("password", inputData.getBody().getString("password") );
			 userInfo = mobileUserService.userLogin( userParam );
			 
		} catch ( Exception e ) {
			e.printStackTrace();
			preOutputData.setString( "errorCode", e.getMessage() );
		}
		/*============================================
		  * 			Process Response Data				
		  *============================================*/
		reponseHeader = processHeaderReponse.processResponseHeader( preOutputData );
		logger.debug( ">>>>>>>>>> USER1011 END >>>>>>>>>>" );
		return new ResponseData< GData >( reponseHeader, userInfo );
	}
	
	
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param inputData
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping( value = ControllerName.MOBILE_REGISTER_USER_INFO )
	public ResponseData<GData> registerUserInfo( @RequestBody RequestData<GData> inputData ) throws Exception {
		
		/*=============================================
		  * 		Set Controller Context				
		  *============================================*/
		GData controllerName = new GData();
		controllerName.setString( "controllerName", ControllerName.MOBILE_REGISTER_USER_INFO);
		ResponseUtil.setControllerName( controllerName );
		
		logger.info( ">>>>>>>>>> USER1021 Start >>>>>>>>>>" );
		GData preOutputData 	= new GData();
		ResponseHeader	reponseHeader = new ResponseHeader();
		try {
			
			/*============================================
			  * 				Pre Validation				
			  *============================================*/
			 preOutputData = preValidationData.validateData( inputData );
			 GData userInfoParam = new GData();
			 userInfoParam.setString("userID",            inputData.getBody().getString("userID") );
			 userInfoParam.setString("serviceStatusCode", inputData.getBody().getString("serviceStatusCode") );
			 userInfoParam.setString("profile", 		  inputData.getBody().getString("profile") );
			 userInfoParam.setString("userLogin", 		  inputData.getBody().getString("userLogin") );
			 userInfoParam.setString("subUserYN", 		  inputData.getBody().getString("subUserYN") );
			 userInfoParam.setString("password", 		  inputData.getBody().getString("password") );
			 userInfoParam.setString("userName", 		  inputData.getBody().getString("userName") );
			 userInfoParam.setString("userImage", 		  inputData.getBody().getString("userImage") );
			 
			 mobileUserService.registerUserInfo( userInfoParam );
			 
		} catch ( Exception e ) {
			e.printStackTrace();
			preOutputData.setString( "errorCode", e.getMessage() );
		}
		
		logger.info( ">>>>>>>>>> USER1021 END >>>>>>>>>>" );
		/*============================================
		  * 			Process Response Data				
		  *============================================*/
		reponseHeader = processHeaderReponse.processResponseHeader( preOutputData );
		return new ResponseData< GData >( reponseHeader, new GData() );
	}

}
