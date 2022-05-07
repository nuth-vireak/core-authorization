/*-----------------------------------------------------------------------------------------
 * NAME : ImageConversionUtil.java
 * VER  : v0.1
 * PROJ : core-authorization
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2022-05-07   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.core.authorization.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

/**
* <PRE>
*  -- Image Conversion  --
* </PRE>
*
* @logicalName ImageConversionUtil
* @version   0.1, 2022-05-07
*/

public class ImageConversionUtil {
	
	/**
	 * -- Base 64 To Byte --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param base64String
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static byte[] Base64ToByte( String base64String ) {
		byte[] result = null;
		if ( StringUtils.isBlank(base64String) ) {
			return result;
		}
		try {
			result = Base64.decodeBase64( base64String );
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	/**
	 * -- Byte To Base 64 --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param obj
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static String byteToBase64( Object obj ) {
		String result = "";
		if ( obj == null ) {
			return result;
		}
		try {
			byte[] bytea = (byte[]) obj;
			result = Base64.encodeBase64String( bytea );
		} catch ( Exception e ) {
			// skipped
		}
		return result;
	}

}
