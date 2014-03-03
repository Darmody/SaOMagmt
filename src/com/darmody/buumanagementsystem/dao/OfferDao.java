package com.darmody.buumanagementsystem.dao;

import java.util.List;

import com.darmody.buumanagementsystem.entity.offer.Offer;

/**
 * 2013.9.5
 * @author Caihuanyu
 * @content 报盘数据操作接口
 */

public interface OfferDao extends Dao{

	/**
	 * 获取所有报盘对象 
	 * @return 查询得到的报盘
	 */
	public List<Offer> getOffers();
	
	/**
	 * 获取所有报盘对象 并分页
	 * @return 查询得到的报盘
	 */
	public List<Offer> getOffers(int firstResult, int maxResult);
	
	/**
	 * 通过url获取报盘对象
	 * @param url 报盘地址
	 * @return 查询得到的结果
	 */
	public Offer getOfferByOfferUrl(String url);
}
