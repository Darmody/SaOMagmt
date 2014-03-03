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

import com.darmody.buumanagementsystem.entity.offer.Offer;
import com.darmody.buumanagementsystem.entity.offer.OfferAnswer;
import com.darmody.buumanagementsystem.entity.offer.OfferFlow;
import com.darmody.buumanagementsystem.service.OfferDataManager;
import com.darmody.buumanagementsystem.util.Constraint;
import com.darmody.buumanagementsystem.util.Utility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Before;

/**
 * 2013.9.5
 * @author Darmody
 * @content 前后台报盘数据交互控制器
 */
@InterceptorRefs ({
	@InterceptorRef("annotationInterceptor")
})
@ParentPackage("default")
@Controller
public class OfferDataStudentAction extends ActionSupport{

	private static final long serialVersionUID = 4520089100325955383L;

	private static Log logger = LogFactory.getLog(OfferDataStudentAction.class);
	
	private String loginAccount;
	
	@Resource
	private OfferDataManager odmgr;
	
//	@Resource
//	private AdminDataManager admgr;
	
	private String returnMessage = "没有权限";
	
	private List<Offer> offers;
	
	@Before
	public void before() {
		
		this.getLoginAccount();
	}
	
	@Action(value="viewAllOffersInPagination",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String viewAllOffersInPagination() {
		
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();
			
			String firstResult = parameters.get("firstResult") == null ? "" : ((String[]) parameters.get("firstResult"))[0];	
			
			String maxResult = parameters.get("maxResult") == null ? "" : ((String[]) parameters.get("maxResult"))[0];			
			
			int max = Integer.parseInt(maxResult);
			
			int first = Integer.parseInt(firstResult);
			
			first = (first - 1) * max;
			
			this.offers = this.odmgr.getAllOffers(first, max);
			
			return SUCCESS;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			this.returnMessage = Constraint.ERROR_MESSAGE;

			logger.error(this.loginAccount + " 在 " + Utility.getSystemCurrentTime() + " 查看报盘时：" + e.getMessage());
			
			return ERROR;
		}
	}
	
	@Action(value="submitOffer",
			results={
				@Result(name=SUCCESS, type="json", params = { "contentType", "text/html"}),
				
				@Result(name=ERROR, type="json", 
						params = { "contentType", "text/html"})
			}
	)
	public String submitOffer() {
		
		try {
			
			ActionContext ctx = ActionContext.getContext();
			
			Map<String, Object> parameters = ctx.getParameters();

			List<OfferAnswer> offerAnswers = this.constructOfferAnswersFromFormParam();
			
			String offerUrl = parameters.get("offerUrl") == null ? "" : ((String[]) parameters.get("offerUrl"))[0];
			
			String stuCode = (String) ctx.getSession().get(Constraint.SESSION_LOGIN);
			
			Offer offer = this.odmgr.getOfferByOfferUrl(offerUrl);
			
			if(!this.odmgr.checkStudentAuthentication(offerUrl, stuCode)) {
				
				this.returnMessage = "您没有权限提交";
				
				return ERROR;
			}
			
			if(!this.odmgr.canStudentSubmitOffer(offer, stuCode)) {
		
				this.returnMessage = "报盘已停止提交或您已提交过";
				
				return ERROR;
			}
			
			this.odmgr.addOfferAnswersBulk(offerAnswers);
			
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
}
