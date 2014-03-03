package com.darmody.buumanagementsystem.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.darmody.buumanagementsystem.dao.OfferFlowDao;
import com.darmody.buumanagementsystem.entity.offer.OfferFlow;

/**
 * 2013.9.5
 * @author Darmody
 * @content 报盘数据操作实现类
 */
@Repository
public class OfferFlowDaoImpl extends DaoImpl implements OfferFlowDao{

	@Override
	public OfferFlow get(Integer pk) {

		return this.get(OfferFlow.class, (Serializable)pk);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferFlow> getOfferFlowsByOfferKey(int offerKey) {
	
		String hql = "from OfferFlow p where p.belongedOffer = " + offerKey;
		
		return this.find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfferFlow getOfferFlowByOfferFlowKey(int offerFlowKey) {
		
		String hql = "from OfferFlow p where p.PK_flowKey = " + offerFlowKey;
		
		List<OfferFlow> offerFlows = this.find(hql);	
		
		if(offerFlows != null && offerFlows.size() == 1) {
			
			return offerFlows.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfferFlow getOfferFlowByFatherKeyAndFlowNum(int fatherKey, int num) {
		
		String hql = "from OfferFlow p where p.fatherFlowKey = " + fatherKey + " and p.flowNum = " + num;
		
		List<OfferFlow> offerFlows = this.find(hql);	
		
		if(offerFlows != null && offerFlows.size() == 1) {
			
			return offerFlows.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OfferFlow getOfferFlowByFatherKey(int fatherKey) {
		
		String hql = "from OfferFlow p where p.fatherFlowKey = " + fatherKey;
		
		List<OfferFlow> offerFlows = this.find(hql);	
		
		if(offerFlows != null && offerFlows.size() == 1) {
			
			return offerFlows.get(0);
		}
		
		return null;
	}

	@Override
	public int getOptionsNumByOfferFlowKey(int offerKey) {
		
		String hql = "from OfferFlow p where p.fatherFlowKey = " + offerKey;
		
		return this.find(hql).size();	
		
	}
}
