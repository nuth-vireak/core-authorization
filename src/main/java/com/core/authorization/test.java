package com.core.authorization;

import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
	
	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password = bCryptPasswordEncoder.encode("133654775");
		long ss = TimeUnit.HOURS.toMillis(1);
		System.out.println(ss);
	}

}
