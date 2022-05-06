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

		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpServletRequest request = (HttpServletRequest) req;
		 
		 String erorrYN = YnTypeCode.NO.getValue(); 
		 ResponseHeader header = new ResponseHeader(); 
		 GData body = new GData(); 
		 ResponseData<GData> responseData = new ResponseData(header,body);
		
		try {
			
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
			response.setHeader("Access-Control-Max-Age", "3600");
			
			String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

			String URL = request.getRequestURI();
			String URLName = StringUtils.substring(URL, URL.length() - 12, URL.length());
			
			/*====================================
			 * 		  Request token validation
			 *====================================*/
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
						header.setHeader(YnTypeCode.NO.getValue(), ResponseResultTypeCode.UNSUCCESS_MESSAGE.getValue(),
								ResponseResultTypeCode.INVALID_OAUTH_USER_OR_PASSWORD.getDescription(), "");
					} else {
						String secret_id = userInfo.getString("client_secret");
						// Check Password
						boolean check_pass = bCryptPasswordEncoder.matches(password, secret_id );
						if ( !check_pass ) {
							erorrYN = YnTypeCode.YES.getValue();
							header.setHeader(YnTypeCode.NO.getValue(), ResponseResultTypeCode.UNSUCCESS_MESSAGE.getValue(),
									ResponseResultTypeCode.INVALID_OAUTH_PASSWORD.getDescription(), "");
						}
					}
					
				} catch ( Exception e ) {
					e.printStackTrace();
					erorrYN = YnTypeCode.YES.getValue();
					header.setHeader(YnTypeCode.NO.getValue(), ResponseResultTypeCode.UNSUCCESS_MESSAGE.getValue(),
							ResponseResultTypeCode.INVALID_OAUTH_USER_OR_PASSWORD.getDescription(), "");
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			erorrYN = YnTypeCode.YES.getValue();
			header.setHeader(YnTypeCode.NO.getValue(), ResponseResultTypeCode.UNSUCCESS_MESSAGE.getValue(),
					ResponseResultTypeCode.GENERAL_MESSAGE.getDescription(), "");
		}
		/*==============================================
		 * 				 Format Error Response
		 *==============================================*/
		if (erorrYN.equals(YnTypeCode.YES.getValue())) {
			responseData.setHeader(header);
			RenderUtil.renderJson( res, responseData);
			return ;
			
		/*=============================================
		 * 					Success 
		 *=============================================*/
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
