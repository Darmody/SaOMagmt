package com.darmody.buumanagementsystem.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.InterceptorRef;
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
 * 2013.7.18
 * @author Darmody
 * @content 前后台学生数据交互控制器
 */
@InterceptorRefs ({
	@InterceptorRef("monitorLevelAuthorityInterceptor"),
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class StudentDataAction extends ActionSupport{

	private static final long serialVersionUID = 4934605870005482477L;
	
	private static Log logger = LogFactory.getLog(StudentDataAction.class);
	
	private String loginAccount;

	@Resource
	private StudentsDataManager sdmgr;
	
	@Resource
	private AdminDataManager admgr;
	
	@Resource
	private BuuClassDataManager bcdmgr;
	
	private List<Student> students;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="addStudent",
			results={
			@Result(name=SUCCESS, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "students"}),
			@Result(name=ERROR, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "students"})
		}
	)
	public String addStudent() {
		
		try {
			
			if(this.isMonitor()) {
				
				return ERROR;
			}
			
			Student student = this.constructStudentFromFormParam();
			
			if(student == null) {
			
				return ERROR;
			}
			
			if(!this.adminLevelCheck(student)) {
				
				return ERROR;
			}
		
			student.setPassword(Constraint.DEFAULT_PASSWORD);
			
			int key = this.sdmgr.addNewStudent(student);
		
			if(key != 0) {
				
				logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 添加了学生：" + student.getStuCode());
				
				this.returnMessage = "添加成功";
			
				return SUCCESS;
			}
		
			this.returnMessage = "添加失败";
		
			return ERROR;
		
		} catch (Exception e) {
			
			this.returnMessage = "error";
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 添加学生时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="viewStudents", 
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "returnMessage"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewStudents() {
		
		try {
		
			this.students = this.sdmgr.fetchStudentsData();
		
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了学生数据");
			
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = "error";
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看学生数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="viewStudentsInPagination", 
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "returnMessage"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewStudentsInPagination() {
		
		try {
		
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String firstResult = parameters.get("firstResult") == null ? "" : ((String[]) parameters.get("firstResult"))[0];	
			
			String maxResult = parameters.get("maxResult") == null ? "" : ((String[]) parameters.get("maxResult"))[0];	
			
			int max = Integer.parseInt(maxResult);
			
			int first = Integer.parseInt(firstResult);
			
			first = (first - 1) * max;
			
			this.students = this.sdmgr.fetchStudentsData(first, max);
		
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了学生数据");
			
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = "error";
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看学生数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="searchStudents", 
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "returnMessage"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String searchStudents() {
		
		try {
		
			Student student = this.constructStudentFromFormParam();
		
			if(student == null) {
			
				return ERROR;
			}
		
			this.students = this.sdmgr.fetchStudentSData(student);
		
			if(students != null && students.size() > 0) {
			
				logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 检索了数据");
				
				return SUCCESS;
			}
		
			return ERROR;
		
		} catch(Exception e) {
			
			this.returnMessage = "error";
			
			e.printStackTrace();
			
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 检索数据出错.出错信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="searchStudentsInPagination", 
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "returnMessage"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String searchStudentsInPagination() {
		
		try {
		
			Student student = this.constructStudentFromFormParam();
		
			if(student == null) {
			
				return ERROR;
			}
		
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String firstResult = parameters.get("firstResult") == null ? "" : ((String[]) parameters.get("firstResult"))[0];	
			
			String maxResult = parameters.get("maxResult") == null ? "" : ((String[]) parameters.get("maxResult"))[0];		
			
			int max = Integer.parseInt(maxResult);
			
			int first = Integer.parseInt(firstResult);
			
			first = (first - 1) * max;
			
			this.students = this.sdmgr.fetchStudentSData(student, first, max);
		
			if(students != null && students.size() > 0) {
			
				logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 检索了数据");
				
				return SUCCESS;
			}
		
			return ERROR;
		
		} catch(Exception e) {
			
			this.returnMessage = "error";
			
			e.printStackTrace();
			
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 检索数据出错.出错信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="updateStudent",
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "students"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "students"})
			}
	)
	public String updateStudent() {
		
		try {
		
			Student student = this.constructStudentFromFormParam();
			
//			if(this.isMonitor()) {
//				
//				Administrator monitor = this.admgr.getAdminInfoByLoginAccount(loginAccount);
//				
//				if(!monitor.getChargedClasses().get(0).getClassName().equals(student.getClassInfo().getClassName())) {
//					
//					return ERROR;
//				}
//				
//			}
			
			if(!this.adminLevelCheck(student)) {
				
				return ERROR;
			}
		
			if(student == null) {
		
				this.returnMessage = Constraint.ERROR_MESSAGE;
				
				return ERROR;
			}
	
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> session = ctx.getSession();
		
			if(session.get(Constraint.SESSION_ADMIN_LEVEL).equals("1")) {
				
				Administrator monitor = this.admgr.getAdminInfoByLoginAccount((String) session.get(Constraint.SESSION_LOGIN)); 
				
				if(monitor.getChargedClasses().get(0).getPK_classKey() != student.getClassInfo().getPK_classKey()) {
					
					this.returnMessage = Constraint.ERROR_MESSAGE;
					
					return ERROR;
				}
			}
			
			this.sdmgr.updateStudentData(student);
		
			this.returnMessage = "更新成功";
			
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 更新了学生：" + student.getStuCode());
				
			return SUCCESS;
		
		} catch (Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 更新学生数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="deleteStudent",
			results={
				@Result(name=SUCCESS, location="/studentDataManage.html"),
				@Result(name=ERROR, location="/studentDataManage.html")
			}
	)
	public String deleteStudent() {
		
		try {
			
			if(this.isMonitor()) {
				
				return ERROR;
			}
		
			Student student = this.constructStudentFromFormParam();		
			
			if(student == null) {
			
				this.returnMessage = Constraint.ERROR_MESSAGE;
				
				return ERROR;
			}
			
			student = this.sdmgr.fetchStudentData(student.getPK_stuKey());
			
			if(!this.adminLevelCheck(student)) {
				
				return ERROR;
			}
			
			if(this.sdmgr.deleteStudent(student.getPK_stuKey())) {
			
				logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 删除了学生：" + student.getStuCode());	
				
				return SUCCESS;
			}
		
			return ERROR;
			
		} catch (Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
		
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 删除学生数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="deleteStudents",
			results={
				@Result(name=SUCCESS, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "students"}),
				@Result(name=ERROR, type="json", 
					params = { "contentType", "text/html", "excludeProperties", "students"})
			}
	)
	public String deleteStudents() {
		
		try {
		
			if(this.isMonitor()) {
				
				return ERROR;
			}
			
			ActionContext ctx = ActionContext.getContext();
		
			Map<String, Object> parameters = ctx.getParameters();
			
			String keys = parameters.get("PK_stuKey") == null ? "" : ((String[]) parameters.get("PK_stuKey"))[0];	
		
			if(keys != null && !keys.equals("")) {
				
				String[] stuKeys = keys.split(":");
			
				List<Student> students = new ArrayList<Student>();
			
				if(stuKeys != null) {
		
					for(int i = 0; i < stuKeys.length; i++) {

						Student student = this.sdmgr.fetchStudentData(Integer.parseInt(stuKeys[i]));
					
						if(!this.adminLevelCheck(student)) {
							
							return ERROR;
						}
						
						if(stuKeys[i] != null && !stuKeys[i].equals("")) {
					
							students.add(student);		
						}
					}
				
					if(this.sdmgr.cleanStudentsData(students)) {
				
						logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 批量删除了学生");			
						
						this.returnMessage = "删除成功";
					
						return SUCCESS;
					}
				}
			}
		
			this.returnMessage = "删除失败";
		
			return ERROR;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 批量删除学生数据时出错：" + e.getMessage());
			
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
		
		BuuClass faculty = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(facultyInfo));
		
		BuuClass major = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(majorInfo));
		
		BuuClass clazz = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(classInfo));

		int key = -1;
		
		if(!strKey.equals("")) {
		
			key = Integer.parseInt(strKey);	
		}
		
		return new Student(key, name, stuCode, thnic, schoolRoll, schoolingYear, grade,
				gender, iDNum, birthday, dormitoryCode, faculty, major, clazz, password, counselor, phone);
	}

	private boolean isMonitor() {
		
		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> session = ctx.getSession();

		System.out.println(session.get(Constraint.SESSION_ADMIN_LEVEL));
		
		return session.get(Constraint.SESSION_ADMIN_LEVEL).equals(1);
	}
	
	private boolean adminLevelCheck(Student student) {
	
		Administrator admin = this.admgr.getAdminInfoByLoginAccount(loginAccount);
		
		if(admin.getAdminType().equals("7")) {
			
			return true;
		}
		
		for(BuuClass buuclass : admin.getChargedClasses()) {
			
			if(buuclass.getPK_classKey() == student.getClassInfo().getPK_classKey()) {
				
				return true;
			}
			
			if(buuclass.getPK_classKey() == student.getMajorInfo().getPK_classKey()) {
				
				return true;
			}

			if(buuclass.getPK_classKey() == student.getFacultyInfo().getPK_classKey()) {
	
				return true;
			}
		}
		
		return false;
	}
	
	private void getLoginAccount() {
		
		ActionContext ctx = ActionContext.getContext();
		
		
		Map<String, Object> session = ctx.getSession();
		
		this.loginAccount = (String) session.get(Constraint.SESSION_LOGIN);
	}
	
	/*
	 * Getter and Setter
	 */
	
	@JSON
	public List<Student> getStudents() {
		
		return this.students;
	}

	public void setStudents(List<Student> students) {
	
		this.students = students;
	}

	@JSON
	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
}
