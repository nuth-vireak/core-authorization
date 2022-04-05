package com.core.authorization.util;

import jara.platform.collection.GData;

public class ResponseUtil {

	private static final String CONTROLLER_NAME = "CONTROLLER_NAME";

	public static void setControllerName(GData controllerName) throws Exception {

		GData mMultiData = getControllerName();
		mMultiData.appendFrom(controllerName);

		ContextHolder.setContextObject(	CONTROLLER_NAME, mMultiData	);
	}

	public static GData getControllerName() {

		GData mMultiData = ( GData ) ContextHolder.getContextObject(	CONTROLLER_NAME	);
		if (mMultiData == null) {
			mMultiData = new GData();
		}
		return mMultiData;
	}

	public static void clearControllerName() {

		ContextHolder.remove(	CONTROLLER_NAME	);

	}
}
