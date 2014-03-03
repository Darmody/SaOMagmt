package com.darmody.buumanagementsystem.action;

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
	@InterceptorRef("monitorLevelAuthorityInterceptor"),
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class AdminDataAction extends ActionSupport {

	private static final long serialVersionUID = 1945151680606983528L;
	
	private static Log logger = LogFactory.getLog(AdminDataAction.class);
	
	private String loginAccount;
	
	private List<Administrator> administrators;

	private Administrator administrator;
	
	@Resource
	private AdminDataManager admgr;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="viewAdminData",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewAdminData() {
	
		try {
		
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String loginAccount = parameters.get("loginAccount") == null ? "" : ((String[]) parameters.get("loginAccount"))[0];	
			
			this.administrator = this.admgr.getAdminInfoByLoginAccount(loginAccount);
		
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "查看管理员信息出错.错误信息:" + e.getMessage());
			
			return ERROR;
		}
	}

	@Action(value="getAdmins",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewAdmins() {
	
		try {
	
			this.administrators = this.admgr.fetchAdministratorsData();
			
	//		logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了管理员信息");
			
			return SUCCESS;
			
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "查看管理员信息时出错.出错信息：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="updateAdmin",
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String updateAdmin() {
		
		try {
			
			Administrator admin = this.constructAdministratorFromFormParam();
			
			if(admin == null) {
			
				this.returnMessage = Constraint.ERROR_MESSAGE;
				
				return ERROR;
			}
			
			this.admgr.updateAdministratorInfo(admin);
		
			this.returnMessage = "更新成功";
			
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 更新了管理员：" + admin.getLoginAccount());
				
			return SUCCESS;
		
		} catch (Exception e) {
			
			this.returnMessage = "error";
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 更新管理员数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="getAdminsInPagination",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewAdminsInPagination() {
	
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String firstResult = parameters.get("firstResult") == null ? "" : ((String[]) parameters.get("firstResult"))[0];	
			
			String maxResult = parameters.get("maxResult") == null ? "" : ((String[]) parameters.get("maxResult"))[0];			
			
			int max = Integer.parseInt(maxResult);
			
			int first = Integer.parseInt(firstResult);
			
			first = (first - 1) * max;
			
			this.administrators = this.admgr.fetchAdministratorsData(first, max);
			
	//		logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了管理员信息");
			
			return SUCCESS;
			
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "查看管理员信息时出错.出错信息：" + e.getMessage());
			
			return ERROR;
		}
	}	
	
		
	private Administrator constructAdministratorFromFormParam() {

		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		String loginAccount = parameters.get("loginAccount") == null ? "" : ((String[]) parameters.get("loginAccount"))[0];	
		String password = parameters.get("password") == null ? "" : ((String[]) parameters.get("password"))[0];
		String strKey = parameters.get("PK_adminKey") == null ? "" : ((String[]) parameters.get("PK_adminKey"))[0];
		String adminType = parameters.get("adminType") == null ? "" : ((String[]) parameters.get("adminType"))[0];
		String name = parameters.get("name") == null ? "" : ((String[]) parameters.get("name"))[0];
		String phone = parameters.get("phone") == null ? "" : ((String[]) parameters.get("phone"))[0];
		String email = parameters.get("email") == null ? "" : ((String[]) parameters.get("email"))[0];
		
		int key = 0;
		
		if(!strKey.equals("")) {
		
			key = Integer.parseInt(strKey);
			 
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
	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator adminstrator) {
		this.administrator = adminstrator;
	}

	@JSON
	public List<Administrator> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(List<Administrator> administrators) {
		this.administrators = administrators;
	}
}
