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
		//	Example
		//	"0 0 * * * *"                 the top of every hour of every day.
		//	"*/10 * * * * *"              every ten seconds.
		//	"0 0 8-10 * * *"              8, 9 and 10 o'clock of every day.
		//	"0 0/30 8-10 * * *"           8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
		//	"0 0 9-17 * * MON-FRI"        on the hour nine-to-five weekdays
		//	"0 0 0 25 12 ?"               every Christmas Day at midnight
		
		// second, minute, hour, day of month, month, day(s) of week
		@Scheduled(cron = "0 0 15 * * *" )  // run every day at 13 PM 
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
