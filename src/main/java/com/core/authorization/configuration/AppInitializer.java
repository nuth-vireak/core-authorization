/*-----------------------------------------------------------------------------------------
 * NAME : FilterRegistrationBean.java
 * VER  : v0.1
 * PROJ : core-authorization
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2022-05-06   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.core.authorization.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName FilterRegistrationBean
* @version   0.1, 2022-05-06
*/

@Configuration
public class AppInitializer
{
    @Bean
    public FilterRegistrationBean<Cors_Filter> sessionTimeoutFilter()
    {
        FilterRegistrationBean<Cors_Filter> registrationBean = new FilterRegistrationBean<>();
        Cors_Filter filter = new Cors_Filter();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/oauth/token");
        registrationBean.setOrder(1); // set precedence
        return registrationBean;
    }
}