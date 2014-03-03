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
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.service.AdminDataManager;
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
	@InterceptorRef("collegeLevelAuthorityInterceptor"),
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class BuuClassDataCollegeLevelAction extends ActionSupport {

	private static final long serialVersionUID = 7818708640189416705L;

	private static Log logger = LogFactory.getLog(BuuClassDataCollegeLevelAction.class);
	
	private String loginAccount;

	private List<BuuClass> faculties;
	
	private List<BuuClass> majors;
	
	private List<BuuClass> classes;
	
	@Resource
	private BuuClassDataManager bcdmgr;
	
	@Resource 
	private AdminDataManager admgr;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="addBuuClass",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html", "excludeProperties", "faculties,classes,majors"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String addBuuClass() {
	
		try {
		
			BuuClass buuClass = this.constructBuuClassFromFormParam();
			
			List<Administrator> admins = buuClass.getAdmins();
			
			if(buuClass.getClassType().equals("2") && !admgr.isAdminExistByCheckLoginAccount(buuClass.getClassName())) {
			
				Administrator monitor = new Administrator();
				
				monitor.setAdminType("1");
				
				monitor.setEmail(buuClass.getClassName() + "@buu.com");
				
				monitor.setLoginAccount(buuClass.getClassName());
				
				monitor.setName(buuClass.getClassName() + "班长");
				
				monitor.setPassword(Constraint.DEFAULT_PASSWORD);
				
				monitor.setPhone("");
				
				List<BuuClass> chargedClass = new ArrayList<BuuClass>();
				
				chargedClass.add(buuClass);
				
				monitor.setChargedClasses(chargedClass);
				
				this.admgr.addNewAdministrator(monitor);

				admins.add(monitor);
			}
			
			buuClass.setAdmins(admins);
			
			this.bcdmgr.addNewBuuClass(buuClass);
			
		//	buuClass = this.bcdmgr.getBuuClassByName(buuClass.getClassName());
			
			for(int i = 0; i < buuClass.getAdmins().size(); i++) {
				
				List<BuuClass> newClasses = buuClass.getAdmins().get(i).getChargedClasses();
				
				newClasses.add(buuClass);
				
				buuClass.getAdmins().get(i).setChargedClasses(newClasses);
				
				this.admgr.checkAdminLevel(buuClass.getAdmins().get(i));
			}
			
			this.returnMessage = "添加成功";
			
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 成功添加了系/专业/班级 " + buuClass.getClassName());
			
			return SUCCESS;
			
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "添加系/专业/班级时出错 .出错信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="deleteBuuClasses",
			results={
				@Result(name=SUCCESS, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "faculties,classes,majors"}),
				@Result(name=ERROR, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "faculties,classes,majors"})
			}
	)
	public String deleteBuuClasses() {
		
		try {
		
			ActionContext ctx = ActionContext.getContext();
		
			Map<String, Object> parameters = ctx.getParameters();
			
			String keys = parameters.get("PK_classKey") == null ? "" : ((String[]) parameters.get("PK_classKey"))[0];	
		
			if(keys != null && !keys.equals("")) {
				
				String[] classKeys = keys.split(":");
			
				List<BuuClass> buuClasses = new ArrayList<BuuClass>();
			
				if(classKeys != null) {
		
					for(int i = 0; i < classKeys.length; i++) {
					
						if(classKeys[i] != null && !classKeys[i].equals("")) {
					
							BuuClass buuClass = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(classKeys[i]));
						
							buuClass.setPK_classKey(Integer.parseInt(classKeys[i]));
							
							if(buuClass.getClassType().equals("0")) {
								
								List<BuuClass> majors = this.bcdmgr.fetchMajorsData(buuClass.getPK_classKey());
								
								buuClasses.addAll(majors);
								
								for(BuuClass major : majors) {
									
									buuClasses.addAll(this.bcdmgr.fetchClassData(major.getPK_classKey()));
								}
							
							} else if(buuClass.getClassType().equals("1")){
								
								buuClasses.addAll(this.bcdmgr.fetchClassData(buuClass.getPK_classKey()));	
							}
							
							buuClasses.add(buuClass);		
						}
					}
					
					this.bcdmgr.cleanBuuClassesData(buuClasses);
				
					logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 批量删除了系/专业/班级信息");			
						
					this.returnMessage = "删除成功";
						
					return SUCCESS;
				}
			}
		
			this.returnMessage = Constraint.ERROR_MESSAGE;
		
			return ERROR;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 批量删除系/专业/班级数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	private BuuClass constructBuuClassFromFormParam() {

		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		String classType = parameters.get("classType") == null ? "" : ((String[]) parameters.get("classType"))[0];	
		String fatherClassKey = parameters.get("fatherClassKey") == null ? "" : ((String[]) parameters.get("fatherClassKey"))[0];
		String classKey = parameters.get("PK_classKey") == null ? "" : ((String[]) parameters.get("PK_classKey"))[0];
		String[] admins = (String[]) parameters.get("admins");
		String className = parameters.get("className") == null ? "" : ((String[]) parameters.get("className"))[0];
		
		int key = -1;
		
		if(!classKey.equals("")) {
		
			key = Integer.parseInt(classKey);
			 
		}
		
		List<Administrator> administrators = new ArrayList<Administrator>();
		
		for(int i = 0; i < admins.length; i++) {
			
			administrators.add(this.admgr.getAdminInfoByLoginAccount(admins[i]));
		}
		
		return new BuuClass(key, className, classType, Integer.parseInt(fatherClassKey), administrators);
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
