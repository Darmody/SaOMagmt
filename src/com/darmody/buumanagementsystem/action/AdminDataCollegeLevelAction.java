package com.darmody.buumanagementsystem.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.service.AdminDataManager;
import com.darmody.buumanagementsystem.util.Constraint;
import com.darmody.buumanagementsystem.util.Utility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

/**
 * 2013.7.25 16:15
 * @author Caihuanyu
 * @content 前后台管理员数据交互控制器
 */

@InterceptorRefs ({
	@InterceptorRef("collegeLevelAuthorityInterceptor"),
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class AdminDataCollegeLevelAction extends ActionSupport {

	private static final long serialVersionUID = -4424360264231904584L;

	private static Log logger = LogFactory.getLog(AdminDataCollegeLevelAction.class);
	
	private String loginAccount;

	private List<Administrator> administrators;
	
	@Resource
	private AdminDataManager admgr;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="addAdmin",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html", "excludeProperties", "administrators"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String addAdmin() {
	
		try {
		
			Administrator administrator = this.constructAdministratorFromFormParam();
			
			administrator.setAdminType("2");
			
			administrator.setPassword(Constraint.DEFAULT_PASSWORD);
			
			this.admgr.addNewAdministrator(administrator);
			
			this.returnMessage = "添加成功";
			
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 成功添加管理员 " + administrator.getLoginAccount());
			
			return SUCCESS;
			
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 添加管理员时出错.出错信息:" + e.getMessage());
			
			return ERROR;
		}
	}

	@Action(value="deleteAdmins",
			results={
				@Result(name=SUCCESS, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "administrators"}),
				@Result(name=ERROR, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "administrators"})
			}
	)
	public String deleteAdmins() {
		
		try {
		
			ActionContext ctx = ActionContext.getContext();
		
			Map<String, Object> parameters = ctx.getParameters();
			
			String keys = parameters.get("PK_adminKey") == null ? "" : ((String[]) parameters.get("PK_adminKey"))[0];	
		
			if(keys != null && !keys.equals("")) {
				
				String[] adminKeys = keys.split(":");
			
				List<Administrator> admins = new ArrayList<Administrator>();
			
				if(adminKeys != null) {
		
					for(int i = 0; i < adminKeys.length; i++) {
					
						if(adminKeys[i] != null && !adminKeys[i].equals("")) {
					
							Administrator admin = new Administrator();
							
							admin.setPK_adminKey(Integer.parseInt(adminKeys[i]));
						
							admins.add(admin);		
						}
					}
				
					if(this.admgr.cleanAdministratorsData(admins)) {
				
						logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 批量删除了管理员");			
						
						this.returnMessage = "删除成功";
					
						return SUCCESS;
					}
				}
			}
		
			this.returnMessage = "删除失败";
		
			return ERROR;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 批量删除管理员数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
		
	private Administrator constructAdministratorFromFormParam() {

		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		String loginAccount = parameters.get("loginAccount") == null ? "" : ((String[]) parameters.get("loginAccount"))[0];	
		String password = parameters.get("password") == null ? "" : ((String[]) parameters.get("password"))[0];
		String adminKey = parameters.get("PK_adminKey") == null ? "" : ((String[]) parameters.get("PK_adminKey"))[0];
		String adminType = parameters.get("adminType") == null ? "" : ((String[]) parameters.get("adminType"))[0];
		String name = parameters.get("name") == null ? "" : ((String[]) parameters.get("name"))[0];
		String phone = parameters.get("phone") == null ? "" : ((String[]) parameters.get("phone"))[0];
		String email = parameters.get("email") == null ? "" : ((String[]) parameters.get("email"))[0];
		
		int key = -1;
		
		if(!adminKey.equals("")) {
		
			key = Integer.parseInt(adminKey);
			 
		}
		
		return new Administrator(key, loginAccount, password, adminType, name, phone, email);
	}

	private void getLoginAccount() {
		
		ActionContext ctx = ActionContext.getContext();
		
		
		Map<String, Object> session = ctx.getSession();
		
		this.loginAccount = (String) session.get(Constraint.SESSION_LOGIN);
	}
	
	@JSON
	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	@JSON
	public List<Administrator> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(List<Administrator> administrators) {
		this.administrators = administrators;
	}
}