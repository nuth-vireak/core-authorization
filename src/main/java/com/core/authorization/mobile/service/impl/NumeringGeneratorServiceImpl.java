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

import com.core.authorization.mobile.repository.NumeringDetailDAO;
import com.core.authorization.mobile.repository.NumeringInfoDAO;
import com.core.authorization.mobile.service.NumeringGeneratorService;
import com.core.authorization.type.ResponseResultTypeCode;
import com.core.authorization.type.YnTypeCode;
import com.core.authorization.util.TypeConversionUtil;

import jara.platform.collection.GData;
import jara.util.GDateUtil;

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
	@Autowired
	private NumeringDetailDAO numeringDetailDAO;
	
	@Override
	public GData getNumeringCommitTx( GData inputData ) throws Exception {
		
		/*=============================================================
		 *  Open new transaction in case error rollback transaction
		 *=============================================================*/
		TransactionStatus 	transaction 	= txManager.getTransaction(  new DefaultTransactionAttribute( TransactionDefinition.PROPAGATION_REQUIRES_NEW )  );
		
		GData outputData = new GData();
		
		try {
			
			String sLastNmbrItem 			= StringUtils.EMPTY; 
			String sNmbrCtnt				= StringUtils.EMPTY;	
			String sNmbrDscExisYn 			= YnTypeCode.NO.getValue(); 
			
			BigDecimal bdLastNo				= BigDecimal.ZERO;
			BigDecimal bdNxtrNmbrNo 		= BigDecimal.ZERO;
			BigDecimal bdNmbrStrtNo			= BigDecimal.ZERO;
			
			GData selectNumberingDetail 	= new GData();
			GData selectNumberingInfo		= new GData();
			GData iNumberingDetail			= new GData();
			

			String numberingTypeCode = inputData.getString( "numberingTypeCode" );
			String numberingCombinationCodeDetail = inputData.getString( "numberingCombinationCodeDetail" );
			
			if ( StringUtils.isBlank( numberingTypeCode ) ) {
				throw new Exception( ResponseResultTypeCode.NUMERING_TYPE_CODE_EMPTY.getValue() );
			}
			
			try {
				selectNumberingInfo = numeringInfoDAO.retrieveNumeringInfo( inputData );
				if ( selectNumberingInfo == null ) {
					throw new Exception( ResponseResultTypeCode.NUMERING_INFO_NOT_FOUND.getValue() );
				}
			} catch ( Exception e ) {
				throw new Exception( ResponseResultTypeCode.NUMERING_INFO_NOT_FOUND.getValue() );
			}
			
			try {
				selectNumberingDetail = numeringDetailDAO.retrieveNumberingDetailForUpdate( inputData );
				if ( selectNumberingDetail  ==  null ) {
					sNmbrDscExisYn =  YnTypeCode.NO.getValue();
				} else {
					sNmbrDscExisYn = YnTypeCode.YES.getValue();
				}
			} catch ( Exception e ) {
				sNmbrDscExisYn = YnTypeCode.NO.getValue();
			}
			
			if( YnTypeCode.YES.getValue().equals(sNmbrDscExisYn)) {
				
				bdNmbrStrtNo = selectNumberingInfo.getBigDecimal( "numberingStartNo" );
				bdNxtrNmbrNo = selectNumberingDetail.getBigDecimal( "numberingLastNo" );
				
				if( TypeConversionUtil.toLong(bdNmbrStrtNo) > TypeConversionUtil.toLong(bdNxtrNmbrNo)) {
					
					bdLastNo = bdNmbrStrtNo;
					
				} else {
					
					bdLastNo = bdNxtrNmbrNo.add( BigDecimal.ONE) ; 
				}
			} else {
				bdLastNo = bdNmbrStrtNo.add( BigDecimal.ONE) ; 
			}
			
			// check Digit
			int iDigit = TypeConversionUtil.toInteger( selectNumberingInfo.getBigDecimal( "numberingSeqNoDigit" ) );
			// padding
			sNmbrCtnt = StringUtils.leftPad( TypeConversionUtil.toString( bdLastNo ) + "", iDigit, "0" );
			
			sLastNmbrItem =  sNmbrCtnt.toString();
			outputData.setString( "cmptNumbering", sLastNmbrItem )            ;
				
			try {
				
				if ( YnTypeCode.NO.getValue().equals (sNmbrDscExisYn ) ) {
					
					iNumberingDetail.setString( "numberingTypeCode"             , numberingTypeCode );
					iNumberingDetail.setString( "numberingCombinationCodeDetail", numberingCombinationCodeDetail );
					iNumberingDetail.setBigDecimal( "numberingLastNo"           , bdLastNo );
					iNumberingDetail.setString( "firstRegisterDate"             , GDateUtil.getCurrentDate() );
					iNumberingDetail.setString( "firstRegisterTime"             , GDateUtil.getCurrentTime() );
					iNumberingDetail.setString( "lastTransactionDate"           , GDateUtil.getCurrentDate() );
					iNumberingDetail.setString( "lastChangeDate"                , GDateUtil.getCurrentDate() );
					iNumberingDetail.setString( "lastChangeTime"                , GDateUtil.getCurrentTime() );
					
					numeringDetailDAO.registerNumberingDetail(iNumberingDetail);
					
				} else {
					selectNumberingDetail.setString("numberingTypeCode", 				numberingTypeCode);
					selectNumberingDetail.setString("numberingCombinationCodeDetail", 	numberingCombinationCodeDetail);
					selectNumberingDetail.setBigDecimal( "numberingLastNo"           , 	bdLastNo );
					selectNumberingDetail.setString( "lastTransactionDate"           , 	GDateUtil.getCurrentDate() );
					selectNumberingDetail.setString( "lastChangeDate"                , 	GDateUtil.getCurrentDate() );
					selectNumberingDetail.setString( "lastChangeTime"                , 	GDateUtil.getCurrentTime() );
					
					numeringDetailDAO.updateNumberingDetail(selectNumberingDetail);
					
				}
				
			} catch ( Exception e ) {
				e.printStackTrace();
				throw new Exception(  ResponseResultTypeCode.NUMERING_DETAIL_INSER_OR_UPDATE_ERROR.getValue() );
			} 
			
			txManager.commit( transaction );
		} catch (Exception e) {
			txManager.rollback( transaction );
			e.printStackTrace();
			throw e;
		}
		return outputData;	
	}

}
