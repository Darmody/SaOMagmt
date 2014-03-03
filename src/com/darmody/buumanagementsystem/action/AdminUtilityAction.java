package com.darmody.buumanagementsystem.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

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
import com.darmody.buumanagementsystem.service.AdminDataManager;
import com.darmody.buumanagementsystem.service.OfferDataManager;
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
	@InterceptorRef("monitorLevelAuthorityInterceptor"),
	@InterceptorRef("annotationInterceptor"),
	@InterceptorRef("fileUploadStack")
})
@ParentPackage("default")
@Controller
public class AdminUtilityAction extends ActionSupport{

	private static final long serialVersionUID = -7051412366045044532L;
	
	private static Log logger = LogFactory.getLog(AdminUtilityAction.class);
	
	private String loginAccount;

	private File file;
	
	private String fileName;
	
	private String inputPath;
	
	private String contentType;
	
	@SuppressWarnings("unused")
	private InputStream inputStream;
	
	private String address = "";
	
	@Resource 
	StudentsDataManager sdmgr;

	@Resource
	OfferDataManager odmgr;
	
	@Resource
	AdminDataManager admgr;
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}

	@Action(value="offerAnswersExport",
			results={
				@Result(name=ActionSupport.SUCCESS, type="stream", params = { "contentType", "application/octet-stream;charset=IOS8859-1",
						"inputName", "inputStream", "contentDisposition", "attachment;filename=\"result.zip\"", "bufferSize", "4096 * 4096"}),
				
				@Result(name=ActionSupport.ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String offerAnswersExport() {
		
		try {
		
			ActionContext ctx = ActionContext.getContext();
		
			Map<String, Object> parameters = ctx.getParameters();
		
			String strKey = parameters.get("PK_offerKey") == null ? "" : ((String[]) parameters.get("PK_offerKey"))[0];
		
			address = this.odmgr.createOfferAnswersExcelFiles(Integer.parseInt(strKey)); 
		
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	@Action(value="studentExcelTemplateDownload",
			results={
				@Result(name=ActionSupport.SUCCESS, type="stream", params = { "contentType", "application/octet-stream;charset=IOS8859-1",
						"inputName", "inputStream", "contentDisposition", "attachment;filename=\"template.xls\"", "bufferSize", "4096 * 4096"}),
				
				@Result(name=ActionSupport.ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String studentExcelTemplateDownload() {
		
		try {
		
			address = "/conf/template.xls"; 
		
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	@Action(value="studentsDataFileUpload",
			results={
				@Result(name=ActionSupport.SUCCESS, location="/common/importSuccess.html"),
				
				@Result(name=ActionSupport.ERROR, type="json", 
						params = { "contentType", "text/html", "includeProperties", "returnMessage"})
			}
	)
	public String studentDataFileUpload() {
	
		try {
	
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> session = ctx.getSession();
			
			if(session.get(Constraint.SESSION_ADMIN_LEVEL).equals("1")) {
				
				return ERROR;
			}
			
			this.sdmgr.bulkAddStudentsDataByExcelFile(file);
			
			this.returnMessage = "学生数据导入成功";
		
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = "error";
			
			e.printStackTrace();
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + "批量导入学生数据出错.错误信息:" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="changeAdminPassword", 
			results={
				@Result(name=SUCCESS, type="json", 
						params = { "contentType", "text/html"}),
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String changeAdminPassword() {
		
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();

			String newPassword = parameters.get("newPassword") == null ? "" : ((String[]) parameters.get("newPassword"))[0];
			
			String lastPassword = parameters.get("lastPassword") == null ? "" : ((String[]) parameters.get("lastPassword"))[0];

			String loginAccount = parameters.get("loginAccount") == null ? "" : ((String[]) parameters.get("loginAccount"))[0];
			
			Administrator admin = this.admgr.getAdminInfoByLoginAccount(loginAccount);
			
			if(lastPassword.equals("") || !admin.getPassword().equals(lastPassword)) {
				
				this.returnMessage = "原密码输入错误";
				
				return ERROR;
			}
			
			admin.setPassword(newPassword);
			
			this.admgr.updateAdministratorInfo(admin);
			
			//logger.info(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看了学生数据");
			
			this.returnMessage = "密码修改成功";
			
			return SUCCESS;
		
		} catch(Exception e) {
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 修改密码时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	private void getLoginAccount() {
		
		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> session = ctx.getSession();
		
		this.loginAccount = (String) session.get(Constraint.SESSION_LOGIN);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@JSON
	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	
	public InputStream getInputStream() throws IOException {
		
		return ServletActionContext.getServletContext().getResourceAsStream(address);
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
