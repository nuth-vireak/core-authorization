/*-----------------------------------------------------------------------------------------
 * NAME : NumeringGeneratorServiceImpl.java
 * VER  : v0.1
 * PROJ : core-authorization
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2022-05-09   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.core.authorization.mobile.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.core.authorization.mobile.repository.NumeringInfoDAO;
import com.core.authorization.mobile.service.NumeringGeneratorService;
import com.core.authorization.type.ResponseResultTypeCode;
import com.core.authorization.type.YnTypeCode;

import jara.platform.collection.GData;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName NumeringGeneratorServiceImpl
* @version   0.1, 2022-05-09
*/
@Service
public class NumeringGeneratorServiceImpl implements NumeringGeneratorService {
	
	@Autowired
	PlatformTransactionManager txManager;
	@Autowired
	private NumeringInfoDAO numeringInfoDAO;
	
	@Override
	public GData getNumeringCommitTx( GData inputData ) throws Exception {
		/*=============================================================
		 *  Open new transaction in case error rollback transaction
		 *=============================================================*/
		TransactionStatus 	transaction 	= txManager.getTransaction(  new DefaultTransactionAttribute( TransactionDefinition.PROPAGATION_REQUIRES_NEW )  );
		
		GData outputData = new GData();
		
		try {
			
			String sLastNumeringItem 	= "";
			String resultDigit			= "";
			String sNumeringContent		= "";
			String existingYN			= YnTypeCode.NO.getValue(); // Y: Update, N: Register
			
			BigDecimal bdLastNo			= BigDecimal.ZERO;
			BigDecimal bdNextNumeringNo	= BigDecimal.ZERO;
			BigDecimal bdNumeringStartNo	= BigDecimal.ZERO;
			
			GData selectNumeringDetail	= new GData();
			GData selectNumeringInfo	= new GData();
			GData iNumeringDetail		= new GData();
			
			String numberingTypeCode = inputData.getString( "numberingTypeCode" );
			String numberingCombinationCodeDetail = inputData.getString( "numberingCombinationCodeDetail" );
			
			if ( StringUtils.isBlank( numberingTypeCode ) ) {
				throw new Exception( ResponseResultTypeCode.NUMERING_TYPE_CODE_EMPTY.getValue() );
			}
			
			/*=========================================================================
			 * 							Validate Numering Info						
			 *=========================================================================*/
			try {
				selectNumeringInfo = numeringInfoDAO.retrieveNumeringInfo( inputData );	
				if ( selectNumeringInfo == null ) {
					throw new Exception( ResponseResultTypeCode.NUMERING_INFO_NOT_FOUND.getValue() );
				}
			} catch (Exception e) {
				throw new Exception( ResponseResultTypeCode.NUMERING_INFO_NOT_FOUND.getValue() );
			}
			/*=========================================================================
			 * 							Check Numering Detail							
			 *=========================================================================*/
			
			
			txManager.commit( transaction );
		} catch (Exception e) {
			txManager.rollback( transaction );
			throw e;
		}
		return null;
	}

}
