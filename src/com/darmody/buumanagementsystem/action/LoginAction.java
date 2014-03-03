package com.darmody.buumanagementsystem.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.entity.Student;
import com.darmody.buumanagementsystem.service.AdminDataManager;
import com.darmody.buumanagementsystem.service.BuuClassDataManager;
import com.darmody.buumanagementsystem.service.StudentsDataManager;
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
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = 9091569254887610396L;

	private static Log logger = LogFactory.getLog(LoginAction.class);
	
	private String loginAccount;
	
	@Resource
	private AdminDataManager admgr;
	
	@Resource
	private StudentsDataManager sdmgr;
	
	@Resource
	private BuuClassDataManager bcdmgr;
	
	private int adminType;
	
	private String login;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="adminLogin",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String adminLogin() {
	
		try {
		
			Administrator administrator = this.constructAdministratorFromFormParam();
		
			int adminLevel = this.admgr.adminValidate(administrator);
		
			if(administrator != null && adminLevel > 0) {
		
				ActionContext ctx = ActionContext.getContext();
			
				ctx.getSession().put(Constraint.SESSION_LOGIN, 
						administrator.getLoginAccount());
			
				ctx.getSession().put(Constraint.SESSION_ADMIN_LEVEL, adminLevel);
				
				this.adminType = adminLevel;
				
				this.returnMessage = "登录成功";
	
		//		logger.info(administrator.getLoginAccount() + " 在 " + Utility.getSystemCurrentTime() + " 以管理员身份登录");
				
				return SUCCESS;
			}
		
			this.returnMessage = "登录失败";
		
			return ERROR;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "以管理员身份登录时出错.错误信息:" + e.getMessage());
			
			return ERROR;
		}
	}

	@Action(value="studentLogin",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String studentLogin() {
	
		try {
		
			Student student = this.constructStudentFromFormParam();
		
			if(student != null && sdmgr.loginValidate(student.getStuCode(), student.getPassword())) {
			
				ActionContext ctx = ActionContext.getContext();
			
				ctx.getSession().put(Constraint.SESSION_LOGIN, 
						student.getStuCode());
			
				ctx.getSession().put(Constraint.SESSION_ADMIN_LEVEL, 0);
				
				this.adminType = 0;
				
				this.returnMessage = "登录成功";
	
			//	logger.info(student.getStuCode() + " 在 " + Utility.getSystemCurrentTime() + " 以学生身份登录");
				
				return SUCCESS;
			}
		
			this.returnMessage = "登录失败";
		
			return ERROR;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "以管理员身份登录时出错.错误信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="logout",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String logout() {
	
		try {
	
			HttpSession session = ServletActionContext.getRequest().getSession();
			
			session.invalidate();
			
			return SUCCESS;
			
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "以管理员身份登录时出错.错误信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="visitSession",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String visitSession() {
	
		try {
	
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> session = ctx.getSession();
			
			this.login = (String)(session.get(Constraint.SESSION_LOGIN) == null ? "" : session.get(Constraint.SESSION_LOGIN));
			
			this.adminType = (Integer)(session.get(Constraint.SESSION_ADMIN_LEVEL) == null ? 0 : session.get(Constraint.SESSION_ADMIN_LEVEL));
			
			return SUCCESS;
			
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "以管理员身份登录时出错.错误信息:" + e.getMessage());
			
			return ERROR;
		}
	}

	private Student constructStudentFromFormParam() {

		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		String name = parameters.get("name") == null ? "" : ((String[]) parameters.get("name"))[0];	
		String classInfo = parameters.get("classCode") == null ? "" : ((String[]) parameters.get("classCode"))[0];
		String stuCode = parameters.get("stuCode") == null ? "" : ((String[]) parameters.get("stuCode"))[0];
		String strKey = parameters.get("PK_stuKey") == null ? "" : ((String[]) parameters.get("PK_stuKey"))[0];

		String thnic = parameters.get("thnic") == null ? "" :  ((String[]) parameters.get("thnic"))[0];
		String schoolRoll = parameters.get("schoolRoll") == null ? "" :  ((String[]) parameters.get("schoolRoll"))[0];
		String schoolingYear = parameters.get("schoolingYear") == null ? "" :  ((String[]) parameters.get("schoolingYear"))[0];
		String gender = parameters.get("gender") == null ? "" :  ((String[]) parameters.get("gender"))[0];
		String iDNum = parameters.get("IDNum") == null ? "" :  ((String[]) parameters.get("IDNum"))[0];
		String birthday = parameters.get("birthday") == null ? "" :  ((String[]) parameters.get("birthday"))[0];
		String dormitoryCode = parameters.get("dormitoryCode") == null ? "" :  ((String[]) parameters.get("dormitoryCode"))[0];
		String facultyInfo = parameters.get("faculty") == null ? "" :  ((String[]) parameters.get("faculty"))[0];
		String majorInfo = parameters.get("major") == null ? "" :  ((String[]) parameters.get("major"))[0];
		String grade = parameters.get("grade") == null ? "" :  ((String[]) parameters.get("grade"))[0];
		String password = parameters.get("password") == null ? "" :  ((String[]) parameters.get("password"))[0];
		String counselor = parameters.get("classAdmin3") == null ? "" :  ((String[]) parameters.get("classAdmin3"))[0];
		String phone = parameters.get("phone") == null ? "" :  ((String[]) parameters.get("phone"))[0];	
		
		BuuClass faculty = new BuuClass();
		
		BuuClass major = new BuuClass();
		
		BuuClass clazz = new BuuClass();
		
		if(!facultyInfo.equals("") && !majorInfo.equals("") && !classInfo.equals("")) {
		
			faculty = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(facultyInfo));
		
			major = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(majorInfo));
		
			clazz = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(classInfo));
		}
		
		int key = -1;
		
		if(!strKey.equals("")) {
		
			key = Integer.parseInt(strKey);
			
			
		}
		
		return new Student(key, name, stuCode, thnic, schoolRoll, schoolingYear, grade,
				gender, iDNum, birthday, dormitoryCode, faculty, major, clazz, password, counselor, phone);
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
		
		int key = -1;
		
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
	public int getAdminType() {
		return adminType;
	}

	public void setAdminType(int adminType) {
		this.adminType = adminType;
	}

	@JSON
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
