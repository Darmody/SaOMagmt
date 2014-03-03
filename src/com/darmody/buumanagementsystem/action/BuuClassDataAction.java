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

import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.service.BuuClassDataManager;
import com.darmody.buumanagementsystem.util.Constraint;
import com.darmody.buumanagementsystem.util.Utility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

/**
 * 2013.7.25 16:15
 * @author Caihuanyu
 * @content 前后台班级数据交互控制器
 */

@InterceptorRefs ({
	@InterceptorRef("monitorLevelAuthorityInterceptor"),
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class BuuClassDataAction extends ActionSupport {

	private static final long serialVersionUID = -5473222717691536417L;

	private static Log logger = LogFactory.getLog(BuuClassDataAction.class);
	
	private String loginAccount;

	private List<BuuClass> faculties;
	
	private List<BuuClass> majors;
	
	private List<BuuClass> classes;
	
	@Resource
	private BuuClassDataManager bcdmgr;
	
//	@Resource 
//	private AdminDataManager admgr;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="viewBuuFaculties",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html", "excludeProperties", "classes,majors"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewBuuFaculties() {
	
		try {
	
			this.faculties = this.bcdmgr.fetchFacultiesData();
			
		//	logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了所有系的信息");
			
			return SUCCESS;
			
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看系数据时出错。出错信息：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="viewBuuClasses",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html", "excludeProperties", "faculties,majors"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewBuuClasses() {
	
		try {
	
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String key = ((String[]) parameters.get("majorKey"))[0];
			
			this.classes = this.bcdmgr.fetchClassData(Integer.parseInt(key));
			
		//	logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了所有班级信息");
			
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看班级信息时出错.出错信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="viewBuuMajors",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html", "excludeProperties", "faculties,classes"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewBuuMajors() {
	
		try {
	
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String key = ((String[]) parameters.get("facultyKey"))[0];
			
			this.majors = this.bcdmgr.fetchMajorsData(Integer.parseInt(key));
			
		//	logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了专业信息 ");
			
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "查看专业时出错.出错信息:" + e.getMessage());
			
			return ERROR;
		}
	}	
	
//	private BuuClass constructBuuClassFromFormParam() {
//
//		ActionContext ctx = ActionContext.getContext();
//		
//		Map<String, Object> parameters = ctx.getParameters();
//		
//		String classType = parameters.get("classType") == null ? "" : ((String[]) parameters.get("classType"))[0];	
//		String fatherClassKey = parameters.get("fatherClassKey") == null ? "" : ((String[]) parameters.get("fatherClassKey"))[0];
//		String classKey = parameters.get("PK_classKey") == null ? "" : ((String[]) parameters.get("PK_classKey"))[0];
//		String[] admins = (String[]) parameters.get("admins");
//		String className = parameters.get("className") == null ? "" : ((String[]) parameters.get("className"))[0];
//		
//		int key = -1;
//		
//		if(!classKey.equals("")) {
//		
//			key = Integer.parseInt(classKey);
//			 
//		}
//		
//		List<Administrator> administrators = new ArrayList<Administrator>();
//		
//		for(int i = 0; i < admins.length; i++) {
//			
//			administrators.add(this.admgr.getAdminInfoByLoginAccount(admins[i]));
//		}
//		
//		return new BuuClass(key, className, classType, Integer.parseInt(fatherClassKey), administrators);
//	}
	
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

	public void setFaculties(List<BuuClass> faculties) {
		this.faculties = faculties;
	}
	
	@JSON
	public List<BuuClass> getFaculties() {
		
		return this.faculties;
	}

	@JSON
	public List<BuuClass> getMajors() {
		return majors;
	}

	public void setMajors(List<BuuClass> majors) {
		this.majors = majors;
	}

	@JSON
	public List<BuuClass> getClasses() {
		return classes;
	}

	public void setClasses(List<BuuClass> classes) {
		this.classes = classes;
	}
}
