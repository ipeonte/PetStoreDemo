package com.example.demo.rest.security.auth_server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Security Configuration
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
@Configuration
public class AuthServerConfig extends WebSecurityConfigurerAdapter {

	@Value("${cookie.domain}")
	String cdomain;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        // Allow access to login page
        .antMatchers("/login").anonymous()
        .anyRequest().authenticated().and()
        
        // Using basic form login
        .formLogin()
        .defaultSuccessUrl("http://" + cdomain + "/");
        	//.httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Define few users & roles
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER_0")
			.and().withUser("pet_keeper").password("password").roles("PET_KEEPER_01", "USER_0")
			.and().withUser("manager").password("password").roles("MANAGER_10", "PET_KEEPER_01", "USER_0");
	}

}
