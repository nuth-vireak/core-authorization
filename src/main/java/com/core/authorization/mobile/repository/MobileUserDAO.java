package com.core.authorization.mobile.repository;

import org.apache.ibatis.annotations.Mapper;

import jara.platform.collection.GData;

@Mapper
public interface MobileUserDAO {
	
	public GData getUserbyId ( GData data );
	
	public GData retrieveUserInfoByUserID( GData param ) throws Exception;
	
	public long updateUserLoginSuccess( GData param ) throws Exception ;
	
	public long updateUserInfo( GData param ) throws Exception;
	
	public long registerUserInfo( GData param ) throws Exception;
	
}
