/*-----------------------------------------------------------------------------------------
 * NAME : NumeringGeneratorService.java
 * VER  : v0.1
 * PROJ : core-authorization
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2022-05-09   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.core.authorization.mobile.service;

import jara.platform.collection.GData;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName NumeringGeneratorService
* @version   0.1, 2022-05-09
*/

public interface NumeringGeneratorService {

	public GData getNumeringCommitTx( GData param ) throws Exception;
}
