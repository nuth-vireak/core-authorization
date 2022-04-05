package com.core.authorization.util;

import java.util.Map;

import jara.platform.collection.GData;

public class ContextThreadLocal  extends ThreadLocal<  Map<String, Object > > {
	
	protected Map<String, Object> initialValue() {
		return new GData(); 
	}
	
	public GData getMData() {
		return (GData) this.get(); 
	}
	
	public void clear() {
		((GData) this.get()).clear();
	}
}
