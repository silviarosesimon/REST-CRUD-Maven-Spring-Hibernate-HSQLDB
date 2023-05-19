/**
* Configuration for Spring Security and Hibernate SessionFactory.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.sample.assignment.dao.HibernateSessionFactory;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Loading Properties from File
	Properties prop = PropertyFileConfig.readPropertyFileFromClasspath();

	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	//Enabling HTTP basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.anyRequest().authenticated()
				.and().httpBasic()
				.authenticationEntryPoint(authEntryPoint);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(prop.getProperty("http.user.name"))
				.password(prop.getProperty("http.user.password"))
					.roles(prop.getProperty("http.user.role"));
	}
	
	//Initializing session factory
	@Bean
	public SessionFactory sessionFactory() {
	    return HibernateSessionFactory.getSessionFactory();
	}
	
}