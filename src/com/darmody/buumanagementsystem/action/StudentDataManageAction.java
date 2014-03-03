package com.darmody.buumanagementsystem.action;

import java.io.File;
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
import com.darmody.buumanagementsystem.entity.Student;
import com.darmody.buumanagementsystem.entity.offer.Offer;
import com.darmody.buumanagementsystem.service.BuuClassDataManager;
import com.darmody.buumanagementsystem.service.OfferDataManager;
import com.darmody.buumanagementsystem.service.StudentsDataManager;
import com.darmody.buumanagementsystem.util.Constraint;
import com.darmody.buumanagementsystem.util.Utility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

/**
 * 2013.10.1
 * @author Darmody
 * @content 前后台学生数据管理交互控制器
 */
@InterceptorRefs ({
	@InterceptorRef("annotationInterceptor"),
	@InterceptorRef("fileUploadStack")
})
@ParentPackage("default")
@Controller
public class StudentDataManageAction extends ActionSupport{

	private static final long serialVersionUID = -3826953219544602305L;

	private static Log logger = LogFactory.getLog(StudentDataManageAction.class);
	
	private String loginAccount;

	@Resource
	private StudentsDataManager sdmgr;
	
	@Resource
	private OfferDataManager odmgr;
	
	@Resource
	private BuuClassDataManager bcdmgr;
	
	private File file;
	
	private String fileFileName;
	
	private String fileContentType;
	
	private Student student;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="attachmentFileUpload",
			results={
				@Result(name=ActionSupport.SUCCESS, location="/common/importSuccess.html"),
				
				@Result(name=ActionSupport.ERROR, type="json", 
						params = { "contentType", "text/html", "includeProperties", "returnMessage"})
			}
	)
	public String attachmentFileUpload() {
	
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> session = ctx.getSession();

			Map<String, Object> parameters = ctx.getParameters();
			
			String strKey = parameters.get("PK_offerKey") == null ? "" : ((String[]) parameters.get("PK_offerKey"))[0];
			
			String flowNum = parameters.get("flowNum") == null ? "" : ((String[]) parameters.get("flowNum"))[0];
			
//			if(!this.odmgr.canStudentSubmitOffer(offer, stuCode)) {
//				
//				return ERROR;
//			}
//	
			String stuCode = fileFileName.split("[.]")[0];
			
			Offer offer = this.odmgr.getOfferByKey(Integer.parseInt(strKey));
			
			if(!this.odmgr.canStudentSumbitAttachment(offer, stuCode)) {
				
				this.returnMessage = "报盘已停止提交或该学生没有权限提交或已提交过";
				
				return ERROR;
			}
			
			Student student = this.sdmgr.fetchStudentData(stuCode);
			
			if(student == null) {
				
				this.returnMessage = "文件名输入有误！请检查";
				
				return ERROR;
			}
			
			String className = student.getClassInfo().getClassName();
			
			String address = Constraint.WEB_LOCATION + "offer/" + strKey + "/resultDetails/" + className;
			
			File tmpFile = new File(address);
			
			if(!tmpFile.exists()) {
				
				tmpFile.mkdirs();
			}
			
			Student stu = this.sdmgr.fetchStudentData(fileFileName.substring(0, fileFileName.lastIndexOf(".")));
			
			tmpFile = new File(address + "/" + strKey + "_" + flowNum + "_" + stu.getName() + "_" +fileFileName);
			
			tmpFile.createNewFile();
			
			Utility.copyFile(file, tmpFile);
			
	//		this.odmgr.saveAttachment(file, contentType, stuCode, Integer.parseInt(strKey), Integer.parseInt(flowNum));
			
			this.returnMessage = "附件上传成功";
		
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = "error";
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "批量导入学生数据出错.错误信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="viewStudent", 
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "returnMessage"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewStudent() {
		
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();

			String stuCode = parameters.get("stuCode") == null ? "" : ((String[]) parameters.get("stuCode"))[0];

			this.student = this.sdmgr.fetchStudentData(stuCode);
		
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了学生数据");
			
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看学生数据时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="changeStudentPassword", 
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html", "excludeProperties", "student"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String changeStudentPassword() {
		
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();

			String newPassword = parameters.get("newPassword") == null ? "" : ((String[]) parameters.get("newPassword"))[0];
			
			String lastPassword = parameters.get("lastPassword") == null ? "" : ((String[]) parameters.get("lastPassword"))[0];

			String stuCode = parameters.get("stuCode") == null ? "" : ((String[]) parameters.get("stuCode"))[0];
			
			Student student = this.sdmgr.fetchStudentData(stuCode);
			
			if(lastPassword.equals("") || !student.getPassword().equals(lastPassword)) {
				
				this.returnMessage = "原密码输入错误";
				
				return ERROR;
			}
			
			student.setPassword(newPassword);
			
			this.sdmgr.updateStudentData(student);
			
			logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 修改了密码");
			
			this.returnMessage = "密码修改成功";
			
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 修改密码时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
//	private Student constructStudentFromFormParam() {
//
//		ActionContext ctx = ActionContext.getContext();
//		
//		Map<String, Object> parameters = ctx.getParameters();
//		
//		String name = parameters.get("name") == null ? "" : ((String[]) parameters.get("name"))[0];	
//		String classInfo = parameters.get("classCode") == null ? "" : ((String[]) parameters.get("classCode"))[0];
//		String stuCode = parameters.get("stuCode") == null ? "" : ((String[]) parameters.get("stuCode"))[0];
//		String strKey = parameters.get("PK_stuKey") == null ? "" : ((String[]) parameters.get("PK_stuKey"))[0];
//
//		String thnic = parameters.get("thnic") == null ? "" :  ((String[]) parameters.get("thnic"))[0];
//		String schoolRoll = parameters.get("schoolRoll") == null ? "" :  ((String[]) parameters.get("schoolRoll"))[0];
//		String schoolingYear = parameters.get("schoolingYear") == null ? "" :  ((String[]) parameters.get("schoolingYear"))[0];
//		String gender = parameters.get("gender") == null ? "" :  ((String[]) parameters.get("gender"))[0];
//		String iDNum = parameters.get("IDNum") == null ? "" :  ((String[]) parameters.get("IDNum"))[0];
//		String birthday = parameters.get("birthday") == null ? "" :  ((String[]) parameters.get("birthday"))[0];
//		String dormitoryCode = parameters.get("dormitoryCode") == null ? "" :  ((String[]) parameters.get("dormitoryCode"))[0];
//		String facultyInfo = parameters.get("faculty") == null ? "" :  ((String[]) parameters.get("faculty"))[0];
//		String majorInfo = parameters.get("major") == null ? "" :  ((String[]) parameters.get("major"))[0];
//		String grade = parameters.get("grade") == null ? "" :  ((String[]) parameters.get("grade"))[0];
//		String password = parameters.get("password") == null ? "" :  ((String[]) parameters.get("password"))[0];
//		String counselor = parameters.get("classAdmin3") == null ? "" :  ((String[]) parameters.get("classAdmin3"))[0];
//		String phone = parameters.get("phone") == null ? "" :  ((String[]) parameters.get("phone"))[0];	
//		
//		BuuClass faculty = new BuuClass();
//		
//		BuuClass major = new BuuClass();
//		
//		BuuClass clazz = new BuuClass();
//		
//		if(!facultyInfo.equals("") && !majorInfo.equals("") && !classInfo.equals("")) {
//		
//			faculty = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(facultyInfo));
//		
//			major = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(majorInfo));
//		
//			clazz = this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(classInfo));
//		}
//		
//		int key = -1;
//		
//		if(!strKey.equals("")) {
//		
//			key = Integer.parseInt(strKey);
//			
//			
//		}
//		
//		return new Student(key, name, stuCode, thnic, schoolRoll, schoolingYear, grade,
//				gender, iDNum, birthday, dormitoryCode, faculty, major, clazz, password, counselor, phone);
//	}

	private void getLoginAccount() {
		
		ActionContext ctx = ActionContext.getContext();
		
		
		Map<String, Object> session = ctx.getSession();
		
		this.loginAccount = (String) session.get(Constraint.SESSION_LOGIN);
	}
	
	/*
	 * Getter and Setter
	 */
	
	@JSON
	public Student getStudent() {
		
		return this.student;
	}

	public void setStudent(Student student) {
	
		this.student = student;
	}

	@JSON
	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
}
