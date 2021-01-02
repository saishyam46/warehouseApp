package com.it.sero.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception
 	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	};
	
	protected void configure(HttpSecurity http)throws Exception
	{
		http.authorizeRequests()
		.antMatchers("/user/showLogin","/user/showForgotPwd","/user/newPwdGen").permitAll()
		.antMatchers("/user/showactivateUserotp","/user/activatebyOtp").permitAll()
		.antMatchers("/user**").hasAuthority("ADMIN")
		.antMatchers("/uom**","/order**","/st**","/whUser**","/part**").hasAnyAuthority("ADMIN","APPUSER")
		.antMatchers("/po**","/grn**","/saleorder**","/shipping**").hasAuthority("APPUSER")
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/user/showLogin")
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/user/setup", true)
		.failureUrl("/user/showLogin?error")
		
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/user/showLogin?success")
		;
	}
}
