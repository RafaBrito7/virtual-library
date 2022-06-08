package com.axians.virtuallibrary.core.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.axians.virtuallibrary.api.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.api.service.UserService;
import com.axians.virtuallibrary.commons.utils.JwtUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final JwtUtils jwtUtils;
	
	private final UserService userService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils, 
			UserService userService) {
		super(authenticationManager);
		this.jwtUtils = jwtUtils;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(jwtUtils.HEADER_FIELD);

		if (header == null || !header.startsWith(jwtUtils.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(jwtUtils.HEADER_FIELD);

		if (token != null) {
			String user = JWT.require(Algorithm.HMAC512(jwtUtils.getSecret().getBytes())).build()
					.verify(token.replace(jwtUtils.TOKEN_PREFIX, "")).getSubject();

			if (user != null) {
				UserSpringSecurity userDetails = (UserSpringSecurity) this.userService.loadUserByUsername(user);
				return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
						userDetails.getAuthorities());
			}
			return null;
		}
		return null;
	}

}
