package com.core.authorization.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.authorization.repository.UserDAO;
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
	private UserDAO userDAO;
	@Autowired
	private PreValidationData preValidationData;
	@Autowired
	private ProcessHeaderReponse processHeaderReponse;
	@Autowired
	private UserService userService;
	
	@PostMapping( value = "/USER1011" )
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
			
			 userParam.setString("userID", inputData.getBody().getString("userID") );
			 userParam.setString("password", inputData.getBody().getString("password") );
			 userInfo = userService.userLogin( userParam );
			 
		} catch ( Exception e ) {
			preOutputData.setString( "errorCode", e.getMessage() );
		}
		/*============================================
		  * 	Process Response Header				
		  *============================================*/
		reponseHeader = processHeaderReponse.processResponseHeader( preOutputData );
		logger.debug( ">>>>>>>>>> USER1011 END >>>>>>>>>>" );
		return new ResponseData< GData >( reponseHeader, userInfo );
	}

}
