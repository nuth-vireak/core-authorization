package com.core.authorization.util;

import com.core.authorization.type.ResponseResultTypeCode;

import jara.platform.collection.GData;

public class ContextHolder  {
	
	private static ContextThreadLocal contextThreadLocal = new ContextThreadLocal();
	

	public static GData getContext() {
		return contextThreadLocal.getMData();
	}
	
	public static String getContext( String contextKey ) {
		return (String)contextThreadLocal.getMData().get( contextKey );
	}
	
	public static Object getContextObject( String contextKey ) {
		return contextThreadLocal.getMData().get( contextKey );
	}
	
	public static void setContextObject( String contextKey, Object context ) throws Exception {
		setContext( contextKey, context );
	}

	public static void setContext( String contextKey, Object context ) throws Exception {
		if ( contextThreadLocal == null ) {
			throw new Exception( ResponseResultTypeCode.CONTEXT_DATA_IS_EMPTY.getValue() );
		}
		contextThreadLocal.getMData().set( contextKey, context );
	}
	
	public static void setContext( Object caller, String contextKey, GData context ) throws Exception {
		// TODO caller check?
		
		setContext( contextKey, context );
	}
	
	/**
	 * To remove context data
	 */
	public static void remove() {
		contextThreadLocal.remove();
	}

	public static void remove( String contextKey ) {
		contextThreadLocal.getMData().remove( contextKey );
	}
	
	public static void clear() {
		
		if ( contextThreadLocal != null ) {
			contextThreadLocal.remove();
		}
	}
	
	public static void destroy() {
		clear();
		if ( contextThreadLocal != null ) {
			contextThreadLocal = null;
		}
	}

}
