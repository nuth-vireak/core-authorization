package com.core.authorization.repository;

import org.apache.ibatis.annotations.Mapper;

import jara.platform.collection.GData;

@Mapper
public interface OauthUserDAO {
	
	public long registerOauthUserInfo( GData param ) throws Exception;

}
