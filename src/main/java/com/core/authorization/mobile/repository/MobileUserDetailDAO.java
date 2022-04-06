
package com.core.authorization.mobile.repository;

import org.apache.ibatis.annotations.Mapper;

import jara.platform.collection.GData;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserDetailDAO
* @version   0.1, 2022-04-05
*/
@Mapper
public interface MobileUserDetailDAO {
	
	public long registerUserDetail( GData inputData ) throws Exception;
}
