package com.darmody.buumanagementsystem.scheduledtask.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.darmody.buumanagementsystem.dao.OfferDao;
import com.darmody.buumanagementsystem.entity.offer.Offer;
import com.darmody.buumanagementsystem.scheduledtask.CheckOfferStatusJob;
import com.darmody.buumanagementsystem.util.Constraint;
import com.darmody.buumanagementsystem.util.Utility;

/**
 * 2013.9.30
 * @author Darmody
 * @content 检查报盘状态的定时任务实现类
 */

@Component
public class CheckOfferStatusJobImpl implements CheckOfferStatusJob{

	@Resource
	OfferDao offerDao;
	
	@Scheduled(cron="0 0 1 * * ? ")
	@Override
	public void execute() {
		
		try {
		
			List<Offer> offers = this.offerDao.getOffers();
		
			for(Offer offer : offers) {
				
				if(offer.getOfferStatus().equals(Constraint.OFFER_STATUS_ENABLE) || (offer.getCountLimit() != 0 && offer.getCountLimit() <= offer.getCountFinish())) {
					
					if(Utility.dateCompare(offer.getOfferCloseDate(), Utility.YMDdf.format(new Date())) < 1) {
						
						offer.setOfferStatus(Constraint.OFFER_STATUS_DISABLE);
						
						this.offerDao.update(offer);
					}
				
				} else {
	
					if(Utility.dateCompare(offer.getOfferStartDate(), Utility.YMDdf.format(new Date())) != 1 &&
							Utility.dateCompare(offer.getOfferCloseDate(), Utility.YMDdf.format(new Date())) == 1
							&& (offer.getCountLimit() == 0 || offer.getCountLimit() > offer.getCountFinish())) {
						
						offer.setOfferStatus(Constraint.OFFER_STATUS_ENABLE);
						
						this.offerDao.update(offer);
					}
				}
			}
	
		} catch(Exception e) {
			
			e.printStackTrace();
		}
	}
}
