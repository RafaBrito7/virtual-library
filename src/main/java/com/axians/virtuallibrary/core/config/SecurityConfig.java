package com.axians.virtuallibrary.core.config;

import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.axians.virtuallibrary.commons.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private Environment environment;
	
	private PasswordEncoder encoder;

	private static final String PUBLIC_MATCH_POST = "/login";
	
	private UserService userService;
	
	public SecurityConfig(UserService userService, Environment environment, PasswordEncoder encoder) {
		this.userService = userService;
		this.environment = environment;
		this.encoder = encoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(encoder);
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().and().cors().disable();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCH_POST).permitAll()
//			.anyRequest().authenticated();
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] profiles = this.environment.getActiveProfiles();
		if(!Set.of(profiles).contains("prod")) {
			http.cors().disable();
			http.csrf().disable();			
		}
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCH_POST)
		.permitAll()
		.anyRequest().authenticated();
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		  http
//		  .cors().disable()
//	      .csrf().disable()
//	      .authorizeRequests()
//	      .antMatchers("/admin/**").hasRole("ADMIN")
//	      .antMatchers("/user*").hasAnyRole("ADMIN", "USER")
//	      .antMatchers(PUBLIC_MATCH_POST).permitAll()
//	      .anyRequest().authenticated()
//	      .and()
//	      .formLogin();
//	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
}
