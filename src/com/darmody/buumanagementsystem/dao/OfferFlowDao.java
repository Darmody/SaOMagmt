package com.darmody.buumanagementsystem.dao;

import java.util.List;

import com.darmody.buumanagementsystem.entity.offer.OfferFlow;

/**
 * 2013.9.5
 * @author Caihuanyu
 * @content 报盘流信息数据操作接口
 */

public interface OfferFlowDao extends Dao{

	/**
	 * 通过报盘key值读取
	 * @param offerKey 报盘key值
	 * @return 返回查询得到的报盘流程
	 */
	public List<OfferFlow> getOfferFlowsByOfferKey(int offerKey);
	
	/**
	 * 通过key值获取offerflow
	 * @param offerFlowKey offerflowkey值
	 * @return 查询得到的结果
	 */
	public OfferFlow getOfferFlowByOfferFlowKey(int offerFlowKey);
	
	/**
	 * 通过父key及选项顺序查找报盘流程
	 * @param fatherKey 父key
	 * @param num 选项顺序
	 * @return 查询得到的报盘流程
	 */
	public OfferFlow getOfferFlowByFatherKeyAndFlowNum(int fatherKey, int num);
	
	/**
	 * 通过父key获取报盘流程
	 * @param fatherKey 父key
	 * @return 查询得到的报盘流程
	 */
	public OfferFlow getOfferFlowByFatherKey(int fatherKey);
	
	/**
	 * 通过key值获取问题的选项个数
	 * @param offerKey offer的key值
	 * @return 选项个数
	 */
	public int getOptionsNumByOfferFlowKey(int offerKey);
}
