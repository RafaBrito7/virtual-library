package com.axians.virtuallibrary.core.auth;

import java.io.IOException;
import java.util.ArrayList;

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
import com.axians.virtuallibrary.commons.utils.JwtUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtUtils jwtUtils;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		super(authenticationManager);
		this.jwtUtils = jwtUtils;
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
			// parse the token.
			String user = JWT.require(Algorithm.HMAC512(jwtUtils.getSecret().getBytes())).build()
					.verify(token.replace(jwtUtils.TOKEN_PREFIX, "")).getSubject();

			if (user != null) {
				// new arraylist means authorities
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}

}
