/*-----------------------------------------------------------------------------------------
 * NAME : NumeringInfoDAO.java
 * VER  : v0.1
 * PROJ : core-authorization
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2022-05-09   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.core.authorization.mobile.repository;

import org.apache.ibatis.annotations.Mapper;

import jara.platform.collection.GData;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName NumeringInfoDAO
* @version   0.1, 2022-05-09
*/
@Mapper
public interface NumeringInfoDAO {
	
	public GData retrieveNumeringInfo( GData param ) throws Exception;

}
