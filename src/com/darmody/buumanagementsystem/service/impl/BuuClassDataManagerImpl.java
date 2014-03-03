package com.darmody.buumanagementsystem.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.darmody.buumanagementsystem.dao.AdminDao;
import com.darmody.buumanagementsystem.dao.BuuClassDao;
import com.darmody.buumanagementsystem.dao.OfferDao;
import com.darmody.buumanagementsystem.dao.StudentDao;
import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.entity.Student;
import com.darmody.buumanagementsystem.entity.offer.Offer;
import com.darmody.buumanagementsystem.service.BuuClassDataManager;

/**
 * 2013.7.25 16:21
 * @author Caihuanyu
 * @content 班级数据管理服务接口实现类
 */

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BuuClassDataManagerImpl implements BuuClassDataManager{

	@Resource
	private BuuClassDao classDao;
	
	@Resource
	private StudentDao studentDao;
	
	@Resource 
	private OfferDao offerDao;

	@Resource
	private AdminDao adminDao;
	
	@Override
	public BuuClass getBuuClass(BuuClass buuClass) {
		
		return classDao.getBuuClassByName(buuClass.getClassName());
	}

	@Override
	public List<BuuClass> fetchFacultiesData() {
		
		return this.classDao.getBuuClassesByClassTypeAndFatherKey("0", -1);
	}

	@Override
	public void addNewBuuClass(BuuClass buuClass) {

		this.classDao.create(buuClass);
	}

	@Override
	public void cleanBuuClassesData(List<BuuClass> buuClasses) {
		
		List<Administrator> admins = new ArrayList<Administrator>();
		
		for(BuuClass clazz : buuClasses) {
			
			for(Administrator admin : clazz.getAdmins()) {
				
				if(admin.getAdminType().equals("1")) {
					
					admins.add(admin);
					
					break;
				}
			}
			
			if(clazz.getClassType().equals("0")) {
				
				this.studentDao.deleteStudents(this.studentDao.getStudentsByFacultyInfo(clazz.getPK_classKey()));
			
			} else if(clazz.getClassType().equals("1")) {
				
				this.studentDao.deleteStudents(this.studentDao.getStudentsByMajorInfo(clazz.getPK_classKey()));		
			
			} else {
		
				this.studentDao.deleteStudents(this.studentDao.getStudentsByClassInfo(clazz.getPK_classKey()));
			}
		}
		
		List<Offer> offers = this.offerDao.getOffers();
		
		Set<BuuClass> deleteClasses = new HashSet<BuuClass>();
		
		for(Offer offer : offers) {
			
			for(BuuClass buuclass : offer.getBuuClasses()) {
				
				for(BuuClass theClass : buuClasses) {
					
					if(buuclass.getPK_classKey() == theClass.getPK_classKey()) {
						
						deleteClasses.add(buuclass);
					}
				}
			}
			
			Set<BuuClass> newClasses = offer.getBuuClasses();
			
			newClasses.removeAll(deleteClasses);
			
			offer.setBuuClasses(newClasses);
			
			this.offerDao.update(offer);
		}
		
		if(admins.size() > 0) {
			
			this.adminDao.deleteAdministrators(admins);
		}
		
		this.classDao.deleteBuuClasses(buuClasses);
	}

	@Override
	public List<BuuClass> fetchMajorsData(int facultyKey) {
		
		return this.classDao.getBuuClassesByClassTypeAndFatherKey("1", facultyKey);
	}

	@Override
	public List<BuuClass> fetchClassData(int majorKey) {

		return this.classDao.getBuuClassesByClassTypeAndFatherKey("2", majorKey);
	}

	@Override
	public List<BuuClass> fetchBuuClassesDataByLoginAccounts(
			String[] loginAccounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuuClass getBuuClassByClassKey(int classKey) {
		
		return (BuuClass) this.classDao.get(classKey);
	}

	@Override
	public int getBuuClassSize(int classKey) {
		
		List<Student> students = this.studentDao.getStudentsByClassInfo(classKey);
		
		return students.size();
	}

	@Override
	public BuuClass getBuuClassByName(String name) {
		
		return this.getBuuClassByName(name);
	}
}
