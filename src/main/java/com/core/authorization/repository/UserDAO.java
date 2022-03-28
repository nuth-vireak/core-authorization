package com.core.authorization.repository;

import org.apache.ibatis.annotations.Mapper;

import jara.platform.collection.GData;

@Mapper
public interface UserDAO {
	
	public GData getUserbyId ( GData data );
	
	public GData retrieveUserInfoByUserID( GData param ) throws Exception;
	
	public long updateUserLoginSuccess( GData param ) throws Exception ;
	
}
