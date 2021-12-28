package com.core.authorization.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jara.platform.collection.GData;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('write')")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/get")
	public GData home() {
		GData data = new GData();
		data.put("message", "sample for request resource");
		return data;
	}

}
