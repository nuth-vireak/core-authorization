package com.core.authorization.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.core.authorization.repository.UserDAO;

import jara.platform.collection.GData;

/**
 * @author sokkheang.huo
 *
 */

@Component
public class DaoAuthentication implements AuthenticationProvider {

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = null;
		String password = null;
		
		GData findUserById = new GData();

		try {
			
			GData params = new GData();

			name = authentication.getName();
			password = authentication.getCredentials().toString();
			
			params.setString("userName", name);

			findUserById = userDAO.getUserbyId(params);
			
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage());
		}

		// Check Password
		boolean check_pass = bCryptPasswordEncoder.matches(password, findUserById.getString("pwd"));
		if (!check_pass) {
			throw new BadCredentialsException("Your password invalid !");
		}

		if (!findUserById.getString("userid").equals(name)) {
			throw new BadCredentialsException("Your username is invalid !");
		}
		
		if ("".equals(name)) {
			throw new BadCredentialsException("Your username is empty !");
		}
		
		if ("".equals(password)) {
			throw new BadCredentialsException("Your password is empty !");
		}

		if (authentication.getPrincipal().toString().isEmpty()) {
			throw new BadCredentialsException("Access denined");
		}

		if (name != null && password != null) {
			return new UsernamePasswordAuthenticationToken(name, bCryptPasswordEncoder.encode(password), new ArrayList<GrantedAuthority>());
		} else {
			throw new BadCredentialsException("External system authentication failed");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
