/*-----------------------------------------------------------------------------------------
 * NAME : TypeConversionUtil.java
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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName TypeConversionUtil
* @version   0.1, 2022-05-07
*/

public class TypeConversionUtil {
	
	/**
	 * -- Convert Object to Long --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static long toLong( Object obj )  {
		
		long longNum = 0L;
		
		try {
			if ( obj instanceof String ) {
				return Long.parseLong( (String) obj );
			} else if ( obj instanceof Integer ) {
				return ( ( Integer ) obj ).longValue();
			} else if ( obj instanceof Long ) {
				return ( ( Long) obj ).longValue();
			} else if ( obj instanceof BigDecimal ) {
				return ( ( BigDecimal) obj ).longValue();
			} else if ( obj instanceof BigInteger  ) {
				return ( ( BigInteger ) obj ).longValue();
			} else {
				return longNum;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return longNum;
	}
	
	/**
	 * -- Convert Object to Interger --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param obj
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static Integer toInteger( Object obj ) {
		int change = 0;
		try {
			if ( obj instanceof String ) {
				return Integer.parseInt( (String) obj );
			} else if ( obj instanceof Integer ) {
				return ( (Integer) obj ).intValue();
			} else if ( obj instanceof Long ) {
				return ( (Long) obj ).intValue();
			} else if ( obj instanceof BigInteger ) {
				return ( (BigInteger) obj ).intValue();
			} else if ( obj instanceof BigDecimal ) {
				return ( (BigDecimal) obj ).intValue();
			} else {
				return change;
			}
		} catch ( Exception e ) {
			return change;
		}
	}
	
	/**
	 * -- Convert Object to BigDecimal --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param obj
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static BigDecimal toBigDecimal( Object obj ) {
		 BigDecimal change = BigDecimal.ZERO;
		try {
			if ( obj instanceof char[] ) {
				return new BigDecimal( (char[]) obj );
			} else if ( obj instanceof String ) {
				return new BigDecimal( (String) obj );
			} else if ( obj instanceof Double ) {
				return new BigDecimal( (Double) obj );
			} else if ( obj instanceof Integer ) {
				return new BigDecimal( (Integer) obj );
			} else if ( obj instanceof Long ) {
				return new BigDecimal( (Long) obj );
			} else if ( obj instanceof BigInteger ) {
				return new BigDecimal( (BigInteger) obj );
			} else if ( obj instanceof BigDecimal ) {
				return (BigDecimal) obj;
			} else {
				return change;
			}
		} catch ( Exception e ) {
			return change;
		}
	}
	
	/**
	 * -- Convert Object to String --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param obj
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static String toString( Object obj ) {
		 String change = StringUtils.EMPTY;
		 
		try {
			if ( obj instanceof String ) {
				return Integer.toString( Integer.parseInt( (String) obj ) );
			} else if ( obj instanceof Integer ) {
				return Integer.toString( ( (Integer) obj ).intValue() );
			} else if ( obj instanceof Long ) {
				return Long.toString( ( (Long) obj ).intValue() );
			} else if ( obj instanceof BigInteger ) {
				BigInteger bi = (BigInteger) obj;
				return bi.toString();
			} else if ( obj instanceof BigDecimal ) {
				BigDecimal bd = (BigDecimal) obj;
				return bd.toString();
			} else {
				return change;
			}
		} catch ( Exception e ) {
			return change;
		}
	}

}
