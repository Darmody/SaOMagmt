package com.darmody.buumanagementsystem.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 2013.7.17
 * @author Darmody
 * @content 数据库原子操作接口
 */

public interface Dao {

	/**
	 * 查找实体
	 * @param <T> 动态传入实体类
	 * @param pk 主键
	 * @return 根据指定主键返回的实体
	 */
	Object get(Integer pk);
	
	/**
	 * 保存实体
	 * @param entity 需要保存的实体
	 */
	int create(Object entity);
	
	/**
	 * 更新实体
	 * @param entity 需要更新的实体
	 */
	void update(Object entity);
	
	/**
	 * 删除实体
	 * @param entity 需要删除的实体
	 */
	void delete(Object entity);
	
	/**
	 * 通过key值删除实体
	 * @param key 需被删除的实体key值
	 */
	void delete(Integer key);
	
	/**
	 * 执行查询的方法
	 * @param entityClass 实体类
	 * @param whereJpql 指定查询返回的第一条记录
	 * @param orderBy 用于排序，如果无需排序该参数设为null.Map对象的key为实体字段名
	 * ，value为ASC/DESC，如：LinkedHashMap<String, String> orderBy 
	 *      = new LinkedHashMap<String, String>();
	 * orderBy.put("itemName", "DESC");表明根据itemName降序排列；
	 * 如果放入多个key-value对，则第一次放入的key-value对为首要关键字，
	 * 第二次放入的key-value对为次要排序关键字……
	 * @param args 作为为JPQL查询字符串的参数的值
	 * @return 返回查询得到的实体List
	 */
	public <T> List<T> getResultList(Class<T> entityClass, String where,
			LinkedHashMap<String, String> orderBy, Object... args);
	
	/**
	 * 执行查询、并进行分页的方法
	 * @param entityClass 实体类
	 * @param whereJpql 指定查询返回的第一条记录
	 * @param firstResult 指定查询返回的第一条记录
	 * @param maxResult 设置查询最多返回多少几条记录
	 * @param orderBy 用于排序，如果无需排序该参数设为null.Map对象的key为实体字段名
	 * ，value为ASC/DESC，如：LinkedHashMap<String, String> orderBy
	 *     = new LinkedHashMap<String, String>();
	 * orderBy.put("itemName", "DESC");表明根据itemName降序排列；
	 * 如果放入多个key-value对，则第一次放入的key-value对为首要关键字，
	 * 第二次放入的key-value对为次要排序关键字……
	 * @param args 作为为JPQL查询字符串的参数的值
	 * @return 返回查询得到的实体List
	 */
	<T> List<T> getResultList(Class<T> entityClass
		, String where
		, int firstResult 
		, int maxResult
		, LinkedHashMap<String , String> orderBy
		, Object... args);
}
