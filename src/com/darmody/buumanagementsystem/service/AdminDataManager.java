package com.darmody.buumanagementsystem.service;

import java.util.List;

import com.darmody.buumanagementsystem.entity.Administrator;

/**
 * 2013.7.25 16:18
 * @author Caihuanyu
 * @content 管理员数据管理服务层接口
 */

public interface AdminDataManager {

	/**
	 * 验证管理员身份
	 * @param administrator 需被验证的管理员对象
	 * @return 验证通过返回权限级别
	 */
	public int adminValidate(Administrator administrator);
	
	/**
	 * 通过管理员的名字获取管理员对象 
	 * @param name 管理员的名字
	 * @return 返回管理员对象
	 */
	public Administrator getAdminInfoByAdminName(String name);
	
	/**
	 * 通过管理员账号获取管理员对象
	 * @param loginAccount 管理员的账号
	 * @return 返回管理员对象
	 */
	public Administrator getAdminInfoByLoginAccount(String loginAccount);
	
	/**
	 * 添加新管理员
	 * @param administrator 新添加的管理员 
	 */
	public void addNewAdministrator(Administrator administrator);
	
	/**
	 * 更新管理员信息
	 * @param administrator 需被更新的管理员对象
	 */
	public void updateAdministratorInfo(Administrator administrator);
	
	/**
	 * 获取所有管理员的信息
	 * @return 所有管理员对象
	 */
	public List<Administrator> fetchAdministratorsData();
	
	/**
	 * 获取所有管理员的信息并分页
	 * @return 所有管理员对象
	 */
	public List<Administrator> fetchAdministratorsData(int firstResult, int maxResult);
	
	
	/**
	 * 删除管理员信息
	 * @param administrators 待删除的管理员对象
	 * @return 删除成功返回true
	 */
	public boolean cleanAdministratorsData(List<Administrator> administrators);
	
	/**
	 * 检查并更新管理员的权限
	 * @param administrator 待检查的管理员
	 */
	public void checkAdminLevel(Administrator administrator);
	
	/**
	 * 通过账号检测管理员是否存在
	 * @param loginAccount 管理员账号
	 * @return 查询得到的管理员账号
	 */
	public boolean isAdminExistByCheckLoginAccount(String loginAccount);
}
