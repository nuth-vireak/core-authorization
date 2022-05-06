package com.core.authorization.mobile.repository;

import org.apache.ibatis.annotations.Mapper;

import jara.platform.collection.GData;

@Mapper
public interface MobileOauthUserDAO {
	
	public long registerOauthUserInfo( GData param ) throws Exception;
	
	public GData retrieveOauthUserInfo( GData param ) throws Exception;

}
