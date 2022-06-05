package com.axians.virtuallibrary.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.axians.virtuallibrary.commons.service.UserService;
import com.axians.virtuallibrary.commons.utils.JwtUtils;
import com.axians.virtuallibrary.core.auth.JWTAuthorizationFilter;
import com.axians.virtuallibrary.core.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private PasswordEncoder encoder;
	
	private JwtUtils jwtUtil;

	private static final String PUBLIC_MATCH_POST = "/login";
	
	private UserService userService;
	
	public SecurityConfig(UserService userService, PasswordEncoder encoder, JwtUtils jwtUtil) {
		this.userService = userService;
		this.encoder = encoder;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http
		  .cors()
		  .and()
	      .csrf().disable()
	      .authorizeRequests()
	      .antMatchers("/admin/**").hasRole("ADMIN")
	      .antMatchers("/user*").hasAnyRole("ADMIN", "USER")
	      .antMatchers(PUBLIC_MATCH_POST).permitAll()
	      .anyRequest().authenticated()
	      .and()
	      .addFilter(new JwtAuthenticationFilter(jwtUtil, authenticationManager()))
	      .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil))
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		  
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
	
}
