package com.darmody.buumanagementsystem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.darmody.buumanagementsystem.dao.AdminDao;
import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.service.AdminDataManager;

/**
 * 2013.7.25 16:21
 * @author Caihuanyu
 * @content 管理员数据管理服务接口实现类
 */

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AdminDataManagerImpl implements AdminDataManager{

	@Resource
	private AdminDao adminDao;
	
	@Override
	public int adminValidate(Administrator administrator) {
		
		Administrator admin = adminDao.getAdminAccountAndPass(administrator.getLoginAccount(), administrator.getPassword());
			
		if(admin != null) {
			
			return Integer.parseInt(admin.getAdminType());
		}
		
		return 0;
	}

	@Override
	public Administrator getAdminInfoByAdminName(String name) {
			
		Administrator admin = adminDao.getAdminByName(name);
			
		if(admin != null) {
						
			return admin;
		}
		
		return null;
	}

	@Override
	public void addNewAdministrator(Administrator administrator) {
		
		adminDao.create(administrator);
	}

	@Override
	public List<Administrator> fetchAdministratorsData() {
		
		return this.adminDao.getAdministrators();
	}

	@Override
	public boolean cleanAdministratorsData(List<Administrator> administrators) {
		
		this.adminDao.deleteAdministrators(administrators);
	
		return true;
	}

	@Override
	public Administrator getAdminInfoByLoginAccount(String loginAccount) {

		return this.adminDao.getAdminByLoginAccount(loginAccount);
	}

	@Override
	public void checkAdminLevel(Administrator administrator) {
		
		if(administrator.getAdminType().equals("1") || administrator.getAdminType().equals("7")) {
			
			return;
		}
		
		int finalLevel = 2;
		
		List<BuuClass> buuClasses = administrator.getChargedClasses();
		
		for(int i = 0; i < buuClasses.size(); i++) {
			
			int tmpLevel = Integer.parseInt(buuClasses.get(i).getClassType());
			
			if( tmpLevel < finalLevel) {
				
				finalLevel = tmpLevel; 
			}
		}
		
		switch(finalLevel) {
		
			case 0 : administrator.setAdminType("4");break;
			
			case 1 : administrator.setAdminType("3");break;
			
			case 2 : administrator.setAdminType("2");break;
		}
		
		
		this.adminDao.update(administrator);
	}

	@Override
	public boolean isAdminExistByCheckLoginAccount(String loginAccount) {
		
		Administrator admin = this.adminDao.getAdminByLoginAccount(loginAccount);
		
		if(admin != null && admin.getLoginAccount() != null && !"".equals(admin.getLoginAccount())) {
			
			return true;
		}
		
		return false;
	}

	@Override
	public void updateAdministratorInfo(Administrator administrator) {
		
		this.adminDao.update(administrator);
	}

	@Override
	public List<Administrator> fetchAdministratorsData(int firstResult,
			int maxResult) {
		
		return this.adminDao.getAdministrators(firstResult, maxResult);
	}
}
