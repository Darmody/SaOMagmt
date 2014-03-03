package com.darmody.buumanagementsystem.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.entity.offer.Offer;
import com.darmody.buumanagementsystem.entity.offer.OfferAnswer;
import com.darmody.buumanagementsystem.entity.offer.OfferFlow;
import com.darmody.buumanagementsystem.service.AdminDataManager;
import com.darmody.buumanagementsystem.service.BuuClassDataManager;
import com.darmody.buumanagementsystem.service.OfferDataManager;
import com.darmody.buumanagementsystem.util.Constraint;
import com.darmody.buumanagementsystem.util.Utility;

/**
 * 2013.9.5
 * @author Darmody
 * @content 前后台报盘数据交互控制器
 */
@InterceptorRefs ({
	@InterceptorRef("monitorLevelAuthorityInterceptor"),
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class OfferDataAdminAction extends ActionSupport{

	private static final long serialVersionUID = -6884937686465715263L;

	private static Log logger = LogFactory.getLog(OfferDataAdminAction.class);
	
	private String loginAccount;
	
	@Resource
	private OfferDataManager odmgr;
	
	@Resource
	private AdminDataManager admgr;
	
	@Resource
	private BuuClassDataManager bcdmgr;
	
	private List<BuuClass> classesInfo;
	
	private List<Integer> classProcess = new ArrayList<Integer>();
	
	private List<Integer> classSize = new ArrayList<Integer>();
	
	private List<OfferFlow> offerFlows;
	
	private Offer offer;
	
	private List<Offer> offers;
	
	private List<Double> answerPrecent = new ArrayList<Double>();
	
	private List<Integer> count = new ArrayList<Integer>();
	
	private String returnMessage = "没有权限";
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="generateNewOffer",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String generateNewOffer() {
		
		try {

			Offer newOffer = this.constructOfferFromFormParam();
			
			if(newOffer == null) {
				
				this.returnMessage = Constraint.ERROR_MESSAGE;
				
				return ERROR;
			}
			
			int key = this.odmgr.addNewOffer(newOffer);
			
			if(key <= 0) {
				
				this.returnMessage = Constraint.ERROR_MESSAGE;
				
				return ERROR;
			}
			
			List<OfferFlow> offerflows = this.constructOfferFlowsFromFormParam(newOffer);
			
			if(offerflows == null) {
				
				this.returnMessage = Constraint.ERROR_MESSAGE;
				
				return ERROR;
			}
			
			this.odmgr.addOfferFlowsBulk(offerflows);
			
			File newOfferDictionary = new File(Constraint.OFFER_LOCATION + "/" + key + "/");
			
			newOfferDictionary.mkdirs();
			
			String htmlStr = this.getHtmlStrFromFormParam();
			
			if(htmlStr.equals("")) {
				
				this.returnMessage = Constraint.ERROR_MESSAGE;
				
				return ERROR;
			}
			
			newOffer.setOfferUrl("/offer/" + key + "/" + "offer-" + key + ".html");
			
			//String startDateStr = newOffer.getOfferStartDate();
			
			Date nowDate = new Date();
			
			if(Utility.dateCompare(newOffer.getOfferStartDate(), Utility.YMDdf.format(nowDate))  < 1) {
			
				newOffer.setOfferStatus(Constraint.OFFER_STATUS_ENABLE);
			}
			
			newOffer.setOfferCreateDate(Utility.df.format(new Date()));
			
			this.odmgr.initialOffer(newOffer);
			
			Utility.createHtml(htmlStr, Constraint.OFFER_LOCATION + "/" + key + "/" + "offer-" + key + ".html");
			
			this.returnMessage = "报盘创建成功";
			
			return SUCCESS;
	
		} catch (Exception e) {
			
			e.printStackTrace();
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 创建新报盘时出错：" + e.getMessage());
			
			return ERROR;
		}
	}

	@Action(value="viewOfferByAdmin",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewOfferByAdmin() {
		
		try {

			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String loginAccount = parameters.get("loginAccount") == null ? "" : ((String[]) parameters.get("loginAccount"))[0];
			
//			String firstResult = parameters.get("firstResult") == null ? "" : ((String[]) parameters.get("firstResult"))[0];	
//			
//			String maxResult = parameters.get("maxResult") == null ? "" : ((String[]) parameters.get("maxResult"))[0];			
//			
//			int max = Integer.parseInt(maxResult);
//			
//			int first = Integer.parseInt(firstResult);
//			
//			first = (first - 1) * max;
//			
			Administrator admin = this.admgr.getAdminInfoByLoginAccount(loginAccount);
			
			this.setOffers(this.odmgr.viewOffersInAdminCharge(admin));
			
			return SUCCESS;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看报盘信息时时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="viewOfferByAdminInPagination",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewOfferByAdminInPagination() {
		
		try {

			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String loginAccount = parameters.get("loginAccount") == null ? "" : ((String[]) parameters.get("loginAccount"))[0];
			
			String firstResult = parameters.get("firstResult") == null ? "" : ((String[]) parameters.get("firstResult"))[0];	
			
			String maxResult = parameters.get("maxResult") == null ? "" : ((String[]) parameters.get("maxResult"))[0];			
			
			int max = Integer.parseInt(maxResult);
			
			int first = Integer.parseInt(firstResult);
			
			first = (first - 1) * max;
			
			Administrator admin = this.admgr.getAdminInfoByLoginAccount(loginAccount);
			
			List<Offer> offers = this.odmgr.viewOffersInAdminCharge(admin);
			
			if(first >= offers.size()) {
				
				return SUCCESS;
			}
			
			max = first + max;
			
			if(max > offers.size()) {
				
				max = offers.size();
			}
			
			this.setOffers(offers.subList(first, max));
			
			return SUCCESS;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看报盘信息时时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="viewOfferFlowDetails",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewOfferFlowDetails() {
		
		try {

			Offer theOffer = this.constructOfferFromFormParam();
			
			this.offer = this.odmgr.getOfferByKey(theOffer.getPK_offerKey());
			
			offerFlows = this.odmgr.getOfferFlowsByOffer(offer);
		
			for(OfferFlow offerFlow : offerFlows) {
				
				if(offerFlow.getFlowSpecies().equals("0")) {
					
					List<Integer> theCount = null;
					
					answerPrecent.addAll(this.odmgr.offerAnswerAnalyze(offerFlow.getPK_flowKey()));
					
					theCount = this.odmgr.offerAnswerCountAnalyze(offerFlow.getPK_flowKey());
					
					count.addAll(theCount);
				}
			}
			
			return SUCCESS;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看报盘详细信息时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
//	@Action(value="calculateAnswerPrecent",
//			results={
//				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
//				
//				@Result(name=ERROR, type="json", 
//						params = { "contentType", "text/html"})
//			}
//	)
//	public String calculateAnswerPrecent() {
//		
//		try {
//
//			ActionContext ctx = ActionContext.getContext();
//			
//			Map<String, Object> parameters = ctx.getParameters();
//			
//			String strKey = parameters.get("PK_flowKey") == null ? "" : ((String[]) parameters.get("PK_flowKey"))[0];
//			
//			answerPrecent = this.odmgr.offerAnswerAnalyze(Integer.parseInt(strKey));
//			
//			return SUCCESS;
//			
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			
//			this.returnMessage = Constraint.ERROR_MESSAGE;
//			
//			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看报盘答案百分比统计时出错：" + e.getMessage());
//			
//			return ERROR;
//		}
//	}

	@Action(value="viewClassesProcess",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewClassesProcess() {
		
		try {

			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String strKey = parameters.get("PK_offerKey") == null ? "" : ((String[]) parameters.get("PK_offerKey"))[0];
			
			this.classesInfo = this.odmgr.getClassesInOffer(Integer.parseInt(strKey));
			
			for(BuuClass clazz : classesInfo) {
				
				this.classProcess.add(this.odmgr.getProcessOfClass(clazz, Integer.parseInt(strKey)));
				
				this.classSize.add(this.bcdmgr.getBuuClassSize(clazz.getPK_classKey()));
			}
			
			return SUCCESS;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			this.returnMessage = Constraint.ERROR_MESSAGE;
			
			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看报盘答案完成进度时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="submitOfferByAdmin",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String submitOfferByAdmin() {
		
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();

			List<OfferAnswer> offerAnswers = this.constructOfferAnswersFromFormParam();
			
			String offerUrl = parameters.get("offerUrl") == null ? "" : ((String[]) parameters.get("offerUrl"))[0];
			
			String[] stuCode = (String[]) parameters.get("stuCode");
			
			Offer offer = this.odmgr.getOfferByOfferUrl(offerUrl);
			
			String loginAccount = (String) ctx.getSession().get(Constraint.SESSION_LOGIN);
			
			Administrator admin = this.admgr.getAdminInfoByLoginAccount(loginAccount);
			
			List<Offer> offers = this.odmgr.viewOffersInAdminCharge(admin);
			
			boolean canSubmit = false;
			
			for(Offer theOffer : offers) {
				
				if(offer.getPK_offerKey() == theOffer.getPK_offerKey()) {
					
					canSubmit = true;
					
					break;
				}
			}
			
			if(!canSubmit) {
				
				return ERROR;
			}
			
			for(int i = 0; i < stuCode.length; i++) {
			
				if(stuCode[i] == null || stuCode[i].equals("")) {
					
					continue;
				}
				
				if(!this.odmgr.checkStudentAuthentication(offerUrl, stuCode[i])) {
				
					this.returnMessage = stuCode[i] + " 没有权限提交";
				
					return ERROR;
				}
			
				if(!this.odmgr.canStudentSubmitOffer(offer, stuCode[i])) {
		
					this.returnMessage = "报盘已停止提交或" + stuCode[i] + "已提交过";
				
					return ERROR;
				}
				
				List<OfferAnswer> finalAnswers = new ArrayList<OfferAnswer>();
				
				for(OfferAnswer answer : offerAnswers) {
				
					OfferAnswer theAnswer = new OfferAnswer();
					
					theAnswer.setAnswerRecord(answer.getAnswerRecord());
					
					theAnswer.setBelongedFlow(answer.getBelongedFlow());
					
					theAnswer.setSubmitter(stuCode[i]);
					
					finalAnswers.add(theAnswer);
				}

				this.odmgr.addOfferAnswersBulk(finalAnswers);
			}
		//	this.odmgr.receiveNewAnswer(this.odmgr.getOfferByOfferUrl(offerUrl).getPK_offerKey());
			
			this.returnMessage = "报盘答案提交成功";
			
			return SUCCESS;
	
		} catch (Exception e) {
			
			e.printStackTrace();
			
			this.returnMessage = Constraint.ERROR_MESSAGE;

			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 提交报盘答案时出错：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	private String getHtmlStrFromFormParam() {
		
		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		return parameters.get("htmlStr") == null ? "" : ((String[]) parameters.get("htmlStr"))[0];
	}
	
	private Offer constructOfferFromFormParam() {

		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		String strKey = parameters.get("PK_offerKey") == null ? "" : ((String[]) parameters.get("PK_offerKey"))[0];
		String offerName = parameters.get("offerName") == null ? "" : ((String[]) parameters.get("offerName"))[0];	
		String offerDescription = parameters.get("offerDescription") == null ? "" : ((String[]) parameters.get("offerDescription"))[0];	
		String offerStartDate = parameters.get("offerStartDate") == null ? "" : ((String[]) parameters.get("offerStartDate"))[0];	
		String offerCloseDate = parameters.get("offerCloseDate") == null ? "" : ((String[]) parameters.get("offerCloseDate"))[0];	
		String offerCreateDate = parameters.get("offerCreateDate") == null ? "" : ((String[]) parameters.get("offerCreateDate"))[0];
		String countLimit = parameters.get("countLimit") == null ? "" : ((String[]) parameters.get("countLimit"))[0];	
		String[] buuClasses = (String[]) parameters.get("offerSubmitters");
		
		Set<BuuClass> classes = new HashSet<BuuClass>();
		
		if(buuClasses != null) {
		
			for(int i = 0; i < buuClasses.length; i++) {
			
				classes.add(this.bcdmgr.getBuuClassByClassKey(Integer.parseInt(buuClasses[i])));
			}
		}
		
		if(countLimit.equals("")) {
			
			countLimit = "0";
		}
		
		int key = -1;
		
		if(!strKey.equals("")) {
		
			key = Integer.parseInt(strKey);
		}
		
		return new Offer(key, offerName, offerDescription, Constraint.OFFER_STATUS_DISABLE, "", offerStartDate,
				offerCloseDate, offerCreateDate,  Integer.parseInt(countLimit), 0, loginAccount, classes);
	}
	
	private List<OfferFlow> constructOfferFlowsFromFormParam(Offer belongedOffer) {
		
		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		ArrayList<OfferFlow> offerFlows = new ArrayList<OfferFlow>();
		
		String[] questions = (String[]) parameters.get("question");	
		String[] questionTypes = (String[]) parameters.get("questionType");
		String[] optioncount = (String[]) parameters.get("optionCount");	
		String[] answers = (String[]) parameters.get("answer");
		
		if(questions == null || questionTypes == null || optioncount == null || answers == null) {
			
			return null;
		}
		
		int j = 0;
		
		for(int i = 0; i < questions.length; i++) {
			
			OfferFlow newOfferFlow = new OfferFlow(-1, questions[i], String.valueOf(i + 1), questionTypes[i], "0", 0, belongedOffer);	
			
			offerFlows.add(newOfferFlow);
			
			int startIndex = j;
			
			for(; j - startIndex < Integer.parseInt(optioncount[i]); j++) {
				
				OfferFlow answer = new OfferFlow(-1, answers[j], String.valueOf(j - startIndex + 1), questionTypes[i], "1", i + 1, belongedOffer);
				
				offerFlows.add(answer);
			}
		}
		
		return offerFlows;
	}
		
	private List<OfferAnswer> constructOfferAnswersFromFormParam() {

		ActionContext ctx = ActionContext.getContext();
		
		Map<String, Object> parameters = ctx.getParameters();
		
		String[] answerRecords = ((String[]) parameters.get("answer"));
		String submitter = parameters.get("submitter") == null ? "" : ((String[]) parameters.get("submitter"))[0];
		String offerUrl = parameters.get("offerUrl") == null ? "" : ((String[]) parameters.get("offerUrl"))[0];
		
		List<OfferAnswer> offerAnswers = new ArrayList<OfferAnswer>();
		
		Offer offer = this.odmgr.getOfferByOfferUrl(offerUrl);
		
		List<OfferFlow> belongedFlows = this.odmgr.getOfferFlowsByOffer(offer);
		
		for(int i = 0; i < answerRecords.length; i++) {
			
			OfferFlow belongedFlow = new OfferFlow();
			
			for(int j = 0; j < belongedFlows.size(); j++) {
				
				if(belongedFlows.get(j).getFlowSpecies().equals("0") 
						&& belongedFlows.get(j).getFlowNum().equals(String.valueOf(i + 1))) {
					
					belongedFlow = belongedFlows.get(j);
					
					break;
				}
			}
			
			
			OfferAnswer answer = new OfferAnswer();
			
			answer.setAnswerRecord(answerRecords[i]);
			
			answer.setBelongedFlow(belongedFlow);
			
			answer.setSubmitter(submitter);
			
			offerAnswers.add(answer);
		}
		
		return offerAnswers;
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
	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	@JSON
	public List<OfferFlow> getOfferFlows() {
		return offerFlows;
	}

	public void setOfferFlows(List<OfferFlow> offerFlows) {
		this.offerFlows = offerFlows;
	}

	@JSON
	public List<Double> getAnswerPrecent() {
		return answerPrecent;
	}

	public void setAnswerPrecent(List<Double> answerPrecent) {
		this.answerPrecent = answerPrecent;
	}

	@JSON
	public List<BuuClass> getClassesInfo() {
		return classesInfo;
	}

	public void setClassesInfo(List<BuuClass> classesInfo) {
		this.classesInfo = classesInfo;
	}

	@JSON
	public List<Integer> getClassProcess() {
		return classProcess;
	}

	public void setClassProcess(List<Integer> classProcess) {
		this.classProcess = classProcess;
	}

	@JSON
	public List<Integer> getClassSize() {
		return classSize;
	}

	public void setClassSize(List<Integer> classSize) {
		this.classSize = classSize;
	}

	@JSON
	public List<Integer> getCount() {
		return count;
	}

	public void setCount(List<Integer> count) {
		this.count = count;
	}

	@JSON
	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	
}
