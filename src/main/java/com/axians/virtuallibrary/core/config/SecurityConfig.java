package com.axians.virtuallibrary.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.axians.virtuallibrary.api.service.UserService;
import com.axians.virtuallibrary.commons.utils.JwtUtils;
import com.axians.virtuallibrary.commons.validations.exceptions.handlers.CustomAccessDeniedHandler;
import com.axians.virtuallibrary.core.auth.JWTAuthorizationFilter;
import com.axians.virtuallibrary.core.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder encoder;
	
	private final JwtUtils jwtUtil;

	private final String PUBLIC_MATCH_POST = "/login";
	
	private final String[] WHITE_LIST_SWAGGER = { "/v2/api-docs", "/configuration/ui", "/swagger-resources",
			"/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-resources/configuration/ui",
			"/swagge‌​r-ui.html", "/swagger-resources/configuration/security" };
	
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
	      .antMatchers(PUBLIC_MATCH_POST).permitAll()
	      .antMatchers(WHITE_LIST_SWAGGER).permitAll()
	      .anyRequest().authenticated()
	      .and()
	      .addFilter(new JwtAuthenticationFilter(jwtUtil, authenticationManager()))
	      .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userService))
	      .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
	      .and()
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
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
	
}
