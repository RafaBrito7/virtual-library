package com.axians.virtuallibrary.commons.validations.exceptions.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.google.gson.JsonObject;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JsonObject json = new JsonObject();

		String message = "The user don't have permission to perform this operation";

		json.addProperty("HttpCode", "403");
		json.addProperty("HttpError", "Forbidden");
		json.addProperty("Message", message);
		json.addProperty("User", auth.getName());
		auth.getAuthorities().stream()
				.forEach(authority -> json.addProperty("UserPermission", authority.getAuthority().replace("ROLE_", "")));
		json.addProperty("Permission", "ADMIN");

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(403);
		response.getWriter().write(json.toString());
	}

}
