package com.darmody.buumanagementsystem.dao;

import java.util.List;

import com.darmody.buumanagementsystem.entity.Administrator;

/**
 * 2013.7.25 16:05
 * @author Caihuanyu
 * @content 管理员数据操作接口
 */

public interface AdminDao extends Dao{

	/**
	 * 通过账号及密码获取管理员实体
	 * @param loginAccount 需被获取的管理员的账号
	 * @param password 需被获取的管理员的密码 
	 * @return 返回管理员实体
	 */
	public Administrator getAdminAccountAndPass(String loginAccount, String password);
	
	/**
	 * 通过名字获取管理员
	 * @param name 管理员的名字
	 * @return 查询得到的管理员对象
	 */
	public Administrator getAdminByName(String name);
	
	/**
	 * 通过账号获取管理员
	 * @param account 管理员的账号
	 * @return 查询得到的管理员对象
	 */
	public Administrator getAdminByLoginAccount(String account);
	
	/**
	 * 获取所有管理员信息
	 * @return 所有管理员对象
	 */
	public List<Administrator> getAdministrators();
	
	/**
	 * 获取所有管理员信息
	 * @return 所有管理员对象
	 */
	public List<Administrator> getAdministrators(int firstResult, int maxResult);
	
	/**
	 * 删除管理员信息
	 * @param administrators 需被删除的管理员对象
	 */
	public void deleteAdministrators(List<Administrator> administrators);
}
