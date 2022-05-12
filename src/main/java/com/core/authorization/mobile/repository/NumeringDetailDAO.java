/*-----------------------------------------------------------------------------------------
 * NAME : NumeringDetailDAO.java
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
* @logicalName NumeringDetailDAO
* @version   0.1, 2022-05-09
*/
@Mapper
public interface NumeringDetailDAO {
	
	public GData retrieveNumberingDetailForUpdate( GData param ) throws Exception;
	
	public long registerNumberingDetail( GData param ) throws Exception;
	
	public long updateNumberingDetail( GData param ) throws Exception;

}
