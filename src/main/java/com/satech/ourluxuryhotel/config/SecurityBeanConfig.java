package com.satech.ourluxuryhotel.config;

import com.satech.ourluxuryhotel.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityBeanConfig {


    @Autowired
    CustomUserDetailService customUserDetailService;

    // to implement caching user details
    @Bean
    public CachingUserDetailsService cachingUserDetailsService(UserDetailsService userDetailsService) {
        return new CachingUserDetailsService(customUserDetailService);
    }

}
