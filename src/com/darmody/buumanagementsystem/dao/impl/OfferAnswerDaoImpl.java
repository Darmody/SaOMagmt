package com.darmody.buumanagementsystem.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.darmody.buumanagementsystem.dao.OfferAnswerDao;
import com.darmody.buumanagementsystem.entity.offer.OfferAnswer;
import com.darmody.buumanagementsystem.entity.offer.OfferFlow;

/**
 * 2013.9.5
 * @author Darmody
 * @content 报盘大难数据操作实现类
 */
@Repository
public class OfferAnswerDaoImpl extends DaoImpl implements OfferAnswerDao{

	@Override
	public OfferAnswer get(Integer pk) {

		return this.get(OfferAnswer.class, (Serializable)pk);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferAnswer> getAnswersInOfferFlow(OfferFlow offerFlow) {
		
		String hql = "from OfferAnswer p where p.belongedFlow = ? ";
		
		return this.find(hql, offerFlow);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferAnswer> getAnswerBySubmmiter(String submmiter) {
		
		String hql = "from OfferAnswer p where p.submitter = " + submmiter;
		
		return this.find(hql);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfferAnswer getAnswerByOfferFlowAndSubmitter(int offerFlowKey,
			String submitter) {
		
		String hql = "from OfferAnswer p where p.submitter = " + submitter + " and p.belongedFlow = " + offerFlowKey;
		
		List<OfferAnswer> answers = this.find(hql);
		
		if(answers != null && answers.size() > 0) {
		
			return answers.get(0);
		}
		
		return null;
	}
}
