package com.darmody.buumanagementsystem.action.interceptor;

import com.darmody.buumanagementsystem.util.SecurityAuthentication;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限控制拦截器，对方法实行权限拦截
 * @author Darmody
 * @version 1.0 2013.9.28
 */

public class MonitorLevelAuthorityInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 6391473097883269608L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		if(SecurityAuthentication.adminAuthentication() < 1) {
			
			return ActionSupport.ERROR;
		}
		
		return invocation.invoke();
	}
}
