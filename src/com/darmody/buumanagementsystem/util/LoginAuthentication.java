package com.darmody.buumanagementsystem.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 2013.7.22
 * @author Darmody
 * @content 登录控制过滤器
 */

public class LoginAuthentication implements Filter{
	
	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session = httpRequest.getSession();

		if(session == null && !httpRequest.getServletPath().endsWith("login.html")) {
			
			httpResponse.sendRedirect(httpRequest.getContextPath() + Constraint.ADMIN_LOGIN_PAGE);
			
			return;
		}
		
		String login = (String)session.getAttribute(Constraint.SESSION_LOGIN);
		
	//	String studentLogin = (String)session.getAttribute(Constraint.SESSION_LOGIN_STUDENT);
		
		if((login == null || "".equals(login)) && !httpRequest.getServletPath().endsWith("login.html")) {

			
			httpResponse.sendRedirect(httpRequest.getContextPath() + Constraint.ADMIN_LOGIN_PAGE);
			
			return;
		}
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
