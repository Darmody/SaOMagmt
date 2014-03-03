package com.darmody.buumanagementsystem.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.entity.offer.Offer;
import com.darmody.buumanagementsystem.entity.offer.OfferAnswer;
import com.darmody.buumanagementsystem.entity.offer.OfferFlow;

/**
 * 2013.9.5
 * @author Caihuanyu
 * @content 报盘数据管理服务层接口
 */

public interface OfferDataManager {

	/**
	 * 通过传入的offer对象新建offer
	 * @param newOffer offer对象
	 * return 返回创建得到的key值
	 */
	public int addNewOffer(Offer newOffer);
	
	/**
	 * 批量导入报盘流程信息
	 * @param offerFlows 报盘流程信息
	 */
	public void addOfferFlowsBulk(List<OfferFlow> offerFlows);
	
	/**
	 * 批量导入报盘提交结果
	 * @param offerAnswer 报盘提交结果
	 */
	public void addOfferAnswersBulk(List<OfferAnswer> offerAnswer);
	
	/**
	 * 初始化报盘信息
	 * @param installedOffer 已成功安装的报盘
	 */
	public void initialOffer(Offer installedOffer);
	
	/**
	 * 提交报盘答案
	 * @param answer 已填好的答案
	 */
	public void submitOffer(OfferAnswer answer);
	
	/**
	 * 查看某位管理员权限下的报盘信息
	 * @param admin 管理员对象
	 * @return 查询得到的结果
	 */
	public List<Offer> viewOffersInAdminCharge(Administrator admin);
	
	/**
	 * 通过报盘对象获取报盘流程
	 * @param offer 报盘对象
	 * @return 返回查询得到的报盘流程对象
	 */
	public List<OfferFlow> getOfferFlowsByOffer(Offer offer);
	
	/**
	 * 检测所有报盘的状态
	 */
	public void checkOffersStatus();
	
	/**
	 * 对报盘提交的答案进行分析
	 * @param offerFlow 需要分析的报批流程的key值
	 * return 返回结果百分比
	 */
	public List<Double> offerAnswerAnalyze(int offerFlowKey);
	
	/**
	 * 对报盘提交的答案进行分析
	 * @param offerFlow 需要分析的报批流程的key值
	 * return 返回结果百分比
	 */
	public List<Integer> offerAnswerCountAnalyze(int offerFlowKey);
	
	
	/**
	 * 通过url获取报盘
	 * @param url 报盘的url
	 * @return 返回查询得到报盘对象
	 */
	public Offer getOfferByOfferUrl(String url);
	
	/**
	 * 查看学生是否有提交此报盘的权限
	 * @param offerUrl 报盘地址
	 * @param stuCode 学号
	 * @return 可提交返回true
	 */
	public boolean checkStudentAuthentication(String offerUrl, String stuCode);
	
	/**
	 * 获取某报盘的所有答案
	 * @param offer 报盘
	 * @return 返回查询得到的答案
	 */
	public List<OfferAnswer> getAnswers(Offer offer);
	
	/**
	 * 获取所有报盘
	 * @return 返回所有报盘
	 */
	public List<Offer> getAllOffers();
	
	/**
	 * 获取所有报盘并分页
	 * @return 返回所有报盘
	 */
	public List<Offer> getAllOffers(int firstResult, int maxResult);
	
	/**
	 * 处理接收到新答案的处理
	 * @param offerKey 报盘key值
	 */
	public void receiveNewAnswer(int offerKey);
	
	/**
	 * 检查学生是否能提交报盘
	 * @param offer 被提交的报盘
	 * @param stuCode 学生学号
	 * @return 成功返回true
	 */
	public boolean canStudentSubmitOffer(Offer offer, String stuCode);
	
	/**
	 * 通过key值获取报盘下的所有班级
	 * @param offerKey 报盘key值
	 * @return 返回查询得到的结果
	 */
	public List<BuuClass> getClassesInOffer(int offerKey);
	
	/**
	 * 获取某班级对某报盘的完成情况
	 * @param clazz 班级信息
	 * @param offerKey 报盘key值
	 * @return 返回查询得到的进度
	 */
	public int getProcessOfClass(BuuClass clazz, int offerKey);
	
	/**
	 * 通过key值获得offer
	 * @param offerKey offer的key值
	 * @return 查询得到的key值
	 */
	public Offer getOfferByKey(int offerKey);
	
	/**
	 * 生成报盘答案的excel文件
	 * @param offerKey 报盘key值
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @return 返回文件的地址
	 * @throws Exception 
	 */
	public String createOfferAnswersExcelFiles(int offerKey) throws IOException, RowsExceededException, WriteException, Exception;
	
	/**
	 * 保存学生上传的附件
	 * @param file 附件
	 * @param stuCode 学号
	 * @throws IOException 
	 */
	public String saveAttachment(File file, String contentType, String stuCode, int offerKey, int flowKey) throws IOException;
	
	/**
	 * 确认该学生是否已提交过答案
	 * @param stuCode 学号
	 * @param offerKey 报盘key值
	 * @return 判断是否已提交过答案
	 */
	public boolean hasStudentSubmitted(String stuCode, int offerKey);
	
	/**
	 * 检查学生是否能提交附件
	 * @param offer 报盘名
	 * @param stuCode 学号
	 * @return
	 */
	public boolean canStudentSumbitAttachment(Offer offer, String stuCode);
}
