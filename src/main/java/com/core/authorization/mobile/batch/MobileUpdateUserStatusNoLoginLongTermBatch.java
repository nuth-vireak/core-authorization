package com.core.authorization.mobile.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.core.authorization.mobile.repository.MobileUserDAO;

import jara.platform.collection.GData;
import jara.util.GDateUtil;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class MobileUpdateUserStatusNoLoginLongTermBatch {

		@Autowired
		private MobileUserDAO mobileUserDAO;
		@Autowired
		PlatformTransactionManager txManager;
		
		@Scheduled(fixedRate = 60*60*1000 )
		public void launchJob() throws Exception {
			
			System.out.println(">>>>>>>>>>Start>>>>>>");
			
			TransactionStatus transaction = txManager.getTransaction( new DefaultTransactionAttribute( TransactionDefinition.PROPAGATION_REQUIRES_NEW));
			
			try {
				
				GData userParam = new GData();
				userParam.setString("userID", "nang98" );
				GData userInfo = mobileUserDAO.retrieveUserInfoByUserID(userParam);
				
			 	userInfo.setString("lastloginDate", GDateUtil.getCurrentDate() );
			 	userInfo.setString("lastloginTime", GDateUtil.getCurrentTime());
				//mobileUserDAO.updateUserInfo(userInfo);
				
				System.out.println(">>>>>>>>>>user Info>>>>>>" + userInfo );
				
				txManager.commit(transaction);
			} catch (Exception e) {
				txManager.rollback(transaction);
			}
			
		}

}
