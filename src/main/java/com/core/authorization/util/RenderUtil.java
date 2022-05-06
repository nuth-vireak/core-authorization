/*-----------------------------------------------------------------------------------------
 * NAME : RenderUtil.java
 * VER  : v0.1
 * PROJ : core-authorization
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2022-05-06   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.core.authorization.util;

import java.io.IOException;

import javax.servlet.ServletResponse;

import com.google.gson.Gson;

import jara.platform.collection.GData;
/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName RenderUtil
* @version   0.1, 2022-05-06
*/

public class RenderUtil {
	public static void renderJson( ServletResponse response , ResponseData<GData> jsonObject){
		
		try {
			
			String json = new Gson().toJson(jsonObject);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
