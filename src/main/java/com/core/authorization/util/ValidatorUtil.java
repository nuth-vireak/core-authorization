package com.core.authorization.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.core.authorization.type.ResponseResultTypeCode;

import jara.platform.collection.GData;
import jara.platform.collection.GListData;

public class ValidatorUtil {
	
	public static void validate( GData param, String... field ) throws Exception {
		for ( String key:field ) {
			if ( StringUtils.isEmpty(StringUtils.trim( param.getString( key) ) ) ) {
				throw new Exception(  ResponseResultTypeCode.MISSING_REQUIRED_FIELD.getValue() );
			}
		}
	}
	
	public static GData emptyToBeNull( GData param, String... field ) {
		String sTemp = null;
		for ( String key: field ) {
			sTemp = param.getString(key);
			if ( StringUtils.isEmpty( sTemp ) ) {
				param.set(key, null );
			}
		}
		return param;
	}
	
	public static GData nullToBeEmpyt( GData param, String... field ) {
		
		String sTemp = null;
		for ( String key: field ) {
			sTemp = param.getString(key);
			if ( StringUtils.isNumeric(sTemp) ) {
				param.set(key, StringUtils.EMPTY );
			}
		}
		return param;
	}
	
	
	public static void findDuplicated( GListData grid01, String... fields ) throws Exception {
		
		final Set<String> set = new HashSet<String> ();
		for ( GData gData: grid01.toListGData()  ) {
			StringBuilder sb = new StringBuilder();
			for ( String field: fields ) {
				String value = gData.getString(field);
				sb.append(value);
			}
			if ( !set.add( sb.toString() ) ) {
				throw new Exception( String.format( "There are duplicated field %s in the grid01.", Arrays.asList( fields ) ) );
			}
		}
	}
	
	public static void findDifferential( GListData grid01, String field ) throws Exception {
		final Set<String> set = new HashSet<String>();
		for ( GData GData : grid01.toListGData() ) {
			if ( set.add( GData.getString( field ) ) ) {
				if ( set.size() > 1 ) {
					throw new Exception( String.format( "There are different field of %s in the grid01", field ) );
				}
			}
		}
	}
	
	public static GListData removeDuplicated( GListData grid01, String... fields ) {
		final Set<String> set = new HashSet<String>();
		GListData outputData = new GListData();
		for ( GData GData : grid01.toListGData() ) {
			StringBuilder sb = new StringBuilder();
			for ( String field : fields ) {
				String value = GData.getString( field );
				sb.append( value );
			}
			if ( !set.add( sb.toString() ) ) {
				continue;
			}
			outputData.add( GData );
		}
		return outputData;
	}
	
	

}
