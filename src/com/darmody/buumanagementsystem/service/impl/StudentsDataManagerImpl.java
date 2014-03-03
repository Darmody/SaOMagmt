package com.darmody.buumanagementsystem.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.darmody.buumanagementsystem.dao.impl.BuuClassDaoImpl;
import com.darmody.buumanagementsystem.dao.impl.StudentDaoImpl;
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.entity.Student;
import com.darmody.buumanagementsystem.service.StudentsDataManager;
import com.darmody.buumanagementsystem.util.Constraint;

/**
 * 2013.7.18
 * @author Caihuanyu
 * @content 学生数据管理服务接口实现类
 */

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class StudentsDataManagerImpl implements StudentsDataManager{

	@Resource
	private StudentDaoImpl studentDao;
	
	@Resource
	private BuuClassDaoImpl buuclassDao; 
	
	@Override
	public int addNewStudent(Student newStudent) {
		
		int key = 0;
		
		key = studentDao.create(newStudent);
		
		return key;
	}

	@Override
	public boolean deleteStudent(Integer PK_stuKey) {
		
		this.studentDao.delete(PK_stuKey);
		
		return true;
	}

	@Override
	public boolean updateStudentData(Student student) {
					
		this.studentDao.update(student);
		
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true,timeout=10)
	public List<Student> fetchStudentsData() {
	
		return this.studentDao.getStudents();
		
	}

	@Override
	public boolean deleteStudent(Student student) {
			
		this.studentDao.delete(student);
		
		return true;
	}

	@Override
	public boolean cleanStudentsData(List<Student> students) {
		
		this.studentDao.deleteStudents(students);
		
		return true;		
	}

	@Override
	public List<Student> fetchStudentSData(Student studentsInfo) {

		return this.studentDao.searchStudents(studentsInfo);
	}

	@Override
	public void bulkAddStudentsDataByExcelFile(File excelFile) throws Exception {
		
		List<Student> students = new ArrayList<Student>();
		
		Workbook workbook = Workbook.getWorkbook(excelFile);
		
		Sheet sheet = workbook.getSheet("学生数据");
		
		for(int i = 1; i < sheet.getRows(); i++) {
			
			Cell[] cells = sheet.getRow(i);
			
			Student student = new Student();
			
			if(cells[0].getContents() == null || cells[0].getContents().equals("")) {
				
				break;
			}
			
			student.setStuCode(cells[0].getContents());
			
			student.setName(cells[1].getContents());
			
			student.setGender(cells[2].getContents());
			
			student.setThnic(cells[3].getContents());
			
			BuuClass buuClass = this.buuclassDao.getBuuClassByName(cells[4].getContents());
			
			student.setClassInfo(buuClass);
			
			buuClass = this.buuclassDao.get(buuClass.getFatherClassKey());
			
			student.setMajorInfo(buuClass);
			
			buuClass = this.buuclassDao.get(buuClass.getFatherClassKey());
			
			student.setFacultyInfo(buuClass);
			
			student.setGrade(cells[5].getContents());
			
			student.setCounselor(cells[6].getContents());
			
			student.setSchoolingYear(cells[7].getContents());
			
			student.setSchoolRoll(cells[8].getContents());
			
			student.setIDNum(cells[9].getContents());
			
			student.setBirthday(cells[10].getContents());
			
			student.setDormitoryCode(cells[11].getContents());
			
			student.setPassword(Constraint.DEFAULT_PASSWORD);
			
			student.setPhone(cells[12].getContents());
			
			students.add(student);
		}
		
		if(students.size() == 0) {

			workbook.close();
			
			return;
		}
		
		this.studentDao.saveOrUpdateAll(students);
		
		workbook.close();
		
	}

	@Override
	public Student fetchStudentData(String stuCode) {
		
		return this.studentDao.getStudentByStuCode(stuCode);
	}

	@Override
	public boolean loginValidate(String stuCode, String password) {
		
		Student student = this.studentDao.getStudentByStuCode(stuCode);
		
		if(student != null && student.getPassword().equals(password)) {
			
			return true;
		}
		
		return false;
	}

	@Override
	public List<Student> fetchStudentsData(int firstResult, int maxResult) {
		
		return this.studentDao.getStudentsByPagination(firstResult, maxResult);
	}

	@Override
	public List<Student> fetchStudentSData(Student studentsInfo,
			int firstResult, int maxResult) {
		
		return this.studentDao.searchStudentsByPagination(studentsInfo, firstResult, maxResult);
	}

	@Override
	public Student fetchStudentData(int key) {
		
		return this.studentDao.get(key);
	}
}
