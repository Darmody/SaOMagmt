package com.darmody.buumanagementsystem.dao;

import java.util.List;

import com.darmody.buumanagementsystem.entity.offer.OfferAnswer;
import com.darmody.buumanagementsystem.entity.offer.OfferFlow;

/**
 * 2013.9.5
 * @author Caihuanyu
 * @content 报盘答案信息数据操作接口
 */

public interface OfferAnswerDao extends Dao{

	/**
	 * 通过报盘流程获取所有答案
	 * @param offerFlowKey 报盘流程key值
	 * @return 返回查询得到的结果
	 */
	public List<OfferAnswer> getAnswersInOfferFlow(OfferFlow offerFlow);
	
	/**
	 * 通过提交者获取答案
	 * @param submmiter 提交者
	 * @return 查询得到的答案
	 */
	public List<OfferAnswer> getAnswerBySubmmiter(String submmiter);
	
	/**
	 * 通过报盘流程及提交者获取答案
	 * @param offerFlowKey 报盘流程key值
	 * @param submitter 提交者
	 * @return 返回查询得到的结果
	 */
	public OfferAnswer getAnswerByOfferFlowAndSubmitter(int offerFlowKey, String submitter);
}
