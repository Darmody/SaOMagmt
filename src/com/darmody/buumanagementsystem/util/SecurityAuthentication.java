package com.darmody.buumanagementsystem.util;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

/**
 * 2013.7.29 17:32
 * @author Caihuanyu
 * @content 权限认证
 */

public class SecurityAuthentication {

	public static int adminAuthentication() {
		
		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> session = ctx.getSession();
		
		int adminLevel = 0;
		
		if(session == null) {
			
			return adminLevel;
		}
		
		String admin = (String) session.get(Constraint.SESSION_LOGIN);
	
		if(admin != null && !"".equals(admin)) {
	
			adminLevel = (Integer) session.get(Constraint.SESSION_ADMIN_LEVEL);
		}
		
		return adminLevel;
	}
}
