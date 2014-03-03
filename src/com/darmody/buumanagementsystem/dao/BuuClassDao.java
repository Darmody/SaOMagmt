package com.darmody.buumanagementsystem.dao;

import java.util.List;

import com.darmody.buumanagementsystem.entity.BuuClass;


/**
 * 2013.8.24
 * @author Caihuanyu
 * @content 班级数据操作接口
 */

public interface BuuClassDao extends Dao{
	
	/**
	 * 通过班级信息获取班级对象
	 * @param name 班级信息名称
	 * @return 返回查询得到的班级信息对象
	 */
	public BuuClass getBuuClassByName(String name);
	
	/**
	 * 通过类型获取buuClass对象
	 * @param classType 所需类型
	 * @param fatherKey 系的key值
	 * @return 返回查询结果
	 */
	public List<BuuClass> getBuuClassesByClassTypeAndFatherKey(String classType, int fatherKey);
	
	/**
	 * 通过账号获取buuClass对象
	 * @param loginAccount 账号
	 * @return 返回查询结果
	 */
	public BuuClass getBuuClassesByLoginAccount(String loginAccount);
	
	/**
	 * 批量删除班级信息
	 * @param buuClasses 需被删除的班级信息
	 */
	public void deleteBuuClasses(List<BuuClass> buuClasses);
	
	/**
	 * 获取所有班级
	 * @return 返回查询得到的所有班级
	 */
	public List<BuuClass> getAllClasses();
}
