package com.core.authorization.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.authorization.constant.ControllerName;
import com.core.authorization.service.PreValidationData;
import com.core.authorization.service.ProcessHeaderReponse;
import com.core.authorization.service.UserService;
import com.core.authorization.util.RequestData;
import com.core.authorization.util.ResponseData;
import com.core.authorization.util.ResponseHeader;

import jara.platform.collection.GData;

@RestController
@RequestMapping("/USER")
@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private PreValidationData preValidationData;
	@Autowired
	private ProcessHeaderReponse processHeaderReponse;
	@Autowired
	private UserService userService;
	
	@PostMapping( value = ControllerName.RETRIEVE_USER_LOGIN )
	public ResponseData<GData> retrieveUserLogin( @RequestBody RequestData<GData> inputData  ) throws Exception {
		
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
			 userInfo = userService.userLogin( userParam );
			 
		} catch ( Exception e ) {
			preOutputData.setString( "errorCode", e.getMessage() );
		}
		/*============================================
		  * 			Process Response Data				
		  *============================================*/
		reponseHeader = processHeaderReponse.processResponseHeader( preOutputData );
		logger.debug( ">>>>>>>>>> USER1011 END >>>>>>>>>>" );
		return new ResponseData< GData >( reponseHeader, userInfo );
	}
	
	
	
	@PostMapping( value = ControllerName.REGISTER_USER_INFO )
	public ResponseData<GData> registerUserInfo( @RequestBody RequestData<GData> inputData ) throws Exception {
		
		logger.info( ">>>>>>>>>> USER1021 Start >>>>>>>>>>" );
		GData preOutputData 	= new GData();
		ResponseHeader	reponseHeader = new ResponseHeader();
		try {
			
			/*============================================
			  * 				Pre Validation				
			  *============================================*/
			 preOutputData = preValidationData.validateData( inputData );
			 
			 
		} catch ( Exception e ) {
			preOutputData.setString( "errorCode", e.getMessage() );
		}
		
		logger.info( ">>>>>>>>>> USER1021 END >>>>>>>>>>" );
		/*============================================
		  * 			Process Response Data				
		  *============================================*/
		reponseHeader = processHeaderReponse.processResponseHeader( preOutputData );
		return null;
	}

}
