package com.darmody.buumanagementsystem.service;

import java.util.List;

import com.darmody.buumanagementsystem.entity.BuuClass;

/**
 * 2013.8.24 22:18
 * @author Caihuanyu
 * @content 班级数据管理服务层接口
 */

public interface BuuClassDataManager {

	/**
	 * 根据提供的详细信息获取班级对象
	 * @param BuuClass 需要查询的班级信息实体
	 * @return 返回查询得到的班级对象
	 */
	public BuuClass getBuuClass(BuuClass buuClass);
	
	/**
	 * 通过key值获取班级对象
	 * @param classKey 需查询对象的key值
	 * @return 返回查询得到的班级实体
	 */
	public BuuClass getBuuClassByClassKey(int classKey);
	
	/**
	 * 获取所有系的信息
	 * @return 返回所有系对象
	 */
	public List<BuuClass> fetchFacultiesData();
	
	/**
	 * 获取所有专业的信息
	 * @param 专业所属系的key值
	 * @return 返回所有专业对象
	 */
	public List<BuuClass> fetchMajorsData(int facultyKey);
	
	/**
	 * 获取所有班级的信息
	 * @param 需查看的班级所在的专业
	 * @return 返回所有班级对象
	 */
	public List<BuuClass> fetchClassData(int majorKey);
	
	/**
	 * 添加新的班级对象
	 * @param buuClass 新的班级对象
	 */
	public void addNewBuuClass(BuuClass buuClass);
	
	/**
	 * 删除班级信息
	 * @param buuClasses 需被删除的班级对象
	 */
	public void cleanBuuClassesData(List<BuuClass> buuClasses);
	
	/**
	 * 通过key值获取班级总人数信息
	 * @param classKey 班级key值
	 * @return 返回查询得到的人数
	 */
	public int getBuuClassSize(int classKey);
	
	/**
	 * 通过账号获取班级信息
	 * @param loginAccounts 所有账号信息
	 * @return 返回查询到的班级对象
	 * @deprecated
	 */
	public List<BuuClass> fetchBuuClassesDataByLoginAccounts(String[] loginAccounts);
	
	/**
	 * 通过班级名称获取班级对象
	 * @param name 班级名
	 * @return 返回查询得到的班级
	 */
	public BuuClass getBuuClassByName(String name);
}
