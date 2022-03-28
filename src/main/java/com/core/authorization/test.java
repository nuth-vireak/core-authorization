package com.core.authorization;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
	
	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password = bCryptPasswordEncoder.encode("133654775");
		
		System.out.println(password);
	}

}
