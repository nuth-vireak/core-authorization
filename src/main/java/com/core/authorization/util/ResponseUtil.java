package com.core.authorization.util;

import org.apache.commons.lang3.StringUtils;

import jara.platform.collection.GData;
import jara.platform.collection.GListData;

public class ResponseUtil {

	private static final String CONTROLLER_NAME = "CONTROLLER_NAME";

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param gData
	 * @param param
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static GData toGData( GData gData, String... param ) {
		GData outputData = new GData();
		for ( String key: param ) {
			if ( gData.containsKey(key) ) {
				outputData.set(key, gData.get(key) );
			} else {
				outputData.set(key, StringUtils.EMPTY );
			}
		}
		return outputData;
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param listName
	 * @param mMultiData
	 * @param param
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static GData toMData( String listName, GListData mMultiData, String... param ) {
		return toGDataWithListName( mMultiData, listName, param );
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param mMultiData
	 * @param listName
	 * @param param
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	private static GData toGDataWithListName( GListData mMultiData, String listName, String... param ) {
		GData outputData = new GData();
		
		GListData grid = new GListData();
		for ( GData GData : mMultiData.toListGData() ) {
			GData cloneGData = new GData();
			for ( String key : param ) {
				if ( GData.containsKey( key ) ) {
					cloneGData.set( key, GData.get( key ) );
				} else {
					cloneGData.set( key, StringUtils.EMPTY );
				}
			}
			grid.add( cloneGData );
		}
		outputData.set( String.format( "%s", listName ), grid );
		
		return outputData;
	}
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param controllerName
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static void setControllerName(GData controllerName) throws Exception {

		GData mMultiData = getControllerName();
		mMultiData.appendFrom(controllerName);

		ContextHolder.setContextObject(	CONTROLLER_NAME, mMultiData	);
	}

	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static GData getControllerName() {

		GData mMultiData = ( GData ) ContextHolder.getContextObject(	CONTROLLER_NAME	);
		if (mMultiData == null) {
			mMultiData = new GData();
		}
		return mMultiData;
	}

	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @exception 
	 * @fullPath 
	 */
	public static void clearControllerName() {

		ContextHolder.remove(	CONTROLLER_NAME	);

	}
}
