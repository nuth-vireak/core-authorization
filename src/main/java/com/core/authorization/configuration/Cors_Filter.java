package com.core.authorization.configuration;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.core.authorization.mobile.repository.MobileOauthUserDAO;
import com.core.authorization.type.ResponseResultTypeCode;
import com.core.authorization.type.YnTypeCode;
import com.core.authorization.util.RenderUtil;
import com.core.authorization.util.ResponseData;
import com.core.authorization.util.ResponseHeader;

import jara.platform.collection.GData;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Cors_Filter implements Filter {

	
	@Autowired
	private MobileOauthUserDAO mobileOauthUserDAO;
	  
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	 
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		/*==============================================================
		 * 							NOTED
		 * 				Every request are access this method
		 *==============================================================*/
		String erorrYN 		= YnTypeCode.NO.getValue(); 
		GData body 			= new GData();
		
		final HttpServletResponse response 	= (HttpServletResponse) res;
		final HttpServletRequest request 	= (HttpServletRequest) req;
		ResponseHeader header 				= new ResponseHeader(); 
		ResponseData<GData> responseData 	= new ResponseData(header,body);
		ResponseResultTypeCode responseResultTypeCode = null;
		
		try {
			
			response.setHeader("Access-Control-Allow-Origin", 	"*");
			response.setHeader("Access-Control-Allow-Methods", 	"POST, PUT, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Allow-Headers", 	"Authorization, Content-Type");
			response.setHeader("Access-Control-Max-Age", 		"3600");
			
			String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

			String URL		= request.getRequestURI();
			String URLName  = StringUtils.substring(URL, URL.length() - 12, URL.length());
			
			/*==============================================================
			 * 								STEP 1
			 * 		 				 Request token validation
			 *==============================================================*/
			if ("/oauth/token".equals(URLName)) {

				String userDetail = StringUtils.substring(requestTokenHeader, 6, requestTokenHeader.length());
				// Decoding string
				Base64.Decoder decoder = Base64.getDecoder();
				String userInfoDecode = new String(decoder.decode(userDetail));
				String userName = StringUtils.substring(userInfoDecode, 0, userInfoDecode.indexOf(":") );
				String password = StringUtils.substring(userInfoDecode, userInfoDecode.indexOf(":") + 1,
						userInfoDecode.length());
				
				GData oauthUserParam = new GData();
				oauthUserParam.setString( "client_id", userName );
				try {
					
					GData userInfo = mobileOauthUserDAO.retrieveOauthUserInfo( oauthUserParam );
					
					if ( userInfo == null ) {
						erorrYN = YnTypeCode.YES.getValue();
						responseResultTypeCode  = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.INVALID_OAUTH_USER_OR_PASSWORD.getValue() ); 
					} else {
						String secret_id = userInfo.getString("client_secret");
						// Check Password
						boolean check_pass = bCryptPasswordEncoder.matches(password, secret_id );
						if ( !check_pass ) {
							erorrYN = YnTypeCode.YES.getValue();
							responseResultTypeCode  = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.INVALID_OAUTH_PASSWORD.getValue() ); 
						}
					}
					
				} catch ( Exception e ) {
					e.printStackTrace();
					erorrYN = YnTypeCode.YES.getValue();
					responseResultTypeCode  = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.INVALID_OAUTH_USER_OR_PASSWORD.getValue() ); 
					
				}
			/*==============================================================
			 * 								STEP 2
			 * 	  				Validate token is empty or not
			 *==============================================================*/
			} else if ( StringUtils.isNotBlank( requestTokenHeader ) ) {
				
				 if ( !requestTokenHeader.contains("bearer") ) {
						erorrYN = YnTypeCode.YES.getValue();
						responseResultTypeCode  = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.INVALID_BEARER.getValue() ); 
				} else if ( requestTokenHeader.contains("bearer") ) {
					// bearer 16045045-50fc-40ef-8d18-daa1a4797e30
					String token = StringUtils.substring( requestTokenHeader, 7, requestTokenHeader.length() );
					
					if ( StringUtils.isBlank( token ) ) {
						erorrYN = YnTypeCode.YES.getValue();
						responseResultTypeCode  = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.INVALID_TOKEN.getValue() ); 
					}
				} 
				
			/*==============================================================
			 * 							STEP 3
			 * 				Validate Authorization is empty or not
			 *==============================================================*/	
			} else if ( StringUtils.isBlank( requestTokenHeader ) ) {
				erorrYN = YnTypeCode.YES.getValue();
				responseResultTypeCode  = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.INVALID_AUTHORIZATION.getValue() ); 
			}

		} catch (Exception e) {
			e.printStackTrace();
			erorrYN = YnTypeCode.YES.getValue();
			responseResultTypeCode = ResponseResultTypeCode.getReponseMessage( ResponseResultTypeCode.GENERAL_MESSAGE.getValue() ); // General System Error
		}
		
		
		/*==============================================================
		 * 							STEP 4
		 * 				 	 Format Error Response
		 *==============================================================*/
		if (erorrYN.equals(YnTypeCode.YES.getValue())) {
			
			header.setHeader(YnTypeCode.NO.getValue(), ResponseResultTypeCode.UNSUCCESS_MESSAGE.getValue(), responseResultTypeCode.getDescription(), "");
			responseData.setHeader(header);
			RenderUtil.renderJson( res, responseData);
			return ;
			
		/*==============================================================
		 * 							STEP 5
		 * 						OH LA LA OH LA LA 
		 *==============================================================*/
		} else {
			if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				chain.doFilter(req, res);
			}
		}
		
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}
