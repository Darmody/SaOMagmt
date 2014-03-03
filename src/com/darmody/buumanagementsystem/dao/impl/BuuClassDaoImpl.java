package com.darmody.buumanagementsystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.darmody.buumanagementsystem.dao.BuuClassDao;
import com.darmody.buumanagementsystem.entity.BuuClass;

/**
 * 2013.8.24
 * @author Darmody
 * @content 班级数据操作实现类
 */

@Repository
public class BuuClassDaoImpl extends DaoImpl implements BuuClassDao{

	@Override
	public BuuClass get(Integer pk) {

		return this.get(BuuClass.class, pk);
	}

	@Override
	public BuuClass getBuuClassByName(String name) {
		
		@SuppressWarnings("unchecked")
		List<BuuClass> classes = this.find("from BuuClass p where p.className = ? ", name);
		
		if(classes != null && classes.size() == 1) {
			
			return classes.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BuuClass> getBuuClassesByClassTypeAndFatherKey(String classType, int fatherKey) {

		return this.find("from BuuClass p where p.classType = ? and p.fatherClassKey = ?", classType, fatherKey);	
	}

	@Override
	public void deleteBuuClasses(List<BuuClass> buuClasses) {

		for(int i = 0; i < buuClasses.size(); i++) {
			
			this.delete(buuClasses.get(i).getPK_classKey());
		}
	}

	@Override
	public BuuClass getBuuClassesByLoginAccount(String loginAccount) {
		
		@SuppressWarnings("unchecked")
		List<BuuClass> classes = this.find("from BuuClass p where p.loginAccount = ? ", loginAccount);
		
		if(classes != null && classes.size() == 1) {
			
			return classes.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BuuClass> getAllClasses() {
		
		return this.find("from BuuClass p where p.classType = ? ", "2");		
	}
	
	
}
