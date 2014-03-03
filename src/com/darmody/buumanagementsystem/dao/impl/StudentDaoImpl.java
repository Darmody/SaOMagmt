package com.darmody.buumanagementsystem.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.darmody.buumanagementsystem.dao.StudentDao;
import com.darmody.buumanagementsystem.entity.Student;

/**
 * 2013.7.17
 * @author Darmody
 * @content 学生数据操作实现类
 */

@Repository
public class StudentDaoImpl extends DaoImpl implements StudentDao{
	
	@Override
	public List<Student> getStudents() {
		
		return this.getResultList(Student.class, "", null, (Object[])null);
		
	}

	@Override
	public Student get(Integer pk) {
		
		return this.get(Student.class, (Serializable)pk);
	}

	@Override
	public void deleteStudents(List<Student> students) {
		
		for(int i = 0; i < students.size(); i++) {
			
			this.delete(students.get(i).getPK_stuKey());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> searchStudents(Student studentInfo) {
	
	//	String thinc = studentInfo.getThnic();
		
		String thincSQL = "";
		
//		if(!thinc.equals("*")) {
//			
//			if(thinc.equals("%汉族%")) {
//			
//				thincSQL = "p.thinc like '%汉族%' and ";
//		
//			} else {
//				
//				thincSQL = " p.thinc like '%汉族%' and ";
//			}
//		}
		
		String hql = "from Student p where ";
		
		if(!studentInfo.getName().equals("*")) {
			
			hql += "p.name like '%" + studentInfo.getName() + "%' and ";
		}
		
		if(!studentInfo.getStuCode().equals("*")) {
			
			hql += "p.stuCode like '%" + studentInfo.getStuCode() + "%' and ";
		}
		
//		if(!studentInfo.getSchoolRoll().equals("*")) {
//			
//			hql += "p.schoolRoll like '%" + studentInfo.getSchoolRoll() + "%' and ";
//		}
//
//		if(!studentInfo.getSchoolingYear().equals("*")) {
//			
//			hql += "p.schoolingYear like '%" + studentInfo.getSchoolingYear() + "%' and ";
//		}
		
//		if(!studentInfo.getGrade().equals("*")) {
//			
//			hql += "p.grade like '%" + studentInfo.getGrade() + "%' and ";
//		}
		
		if(!studentInfo.getGender().equals("*")) {
			
			hql += "p.gender like '%" + studentInfo.getGender() + "%' and ";
		}

//		if(!studentInfo.getIDNum().equals("*")) {
//			
//			hql += "p.IDNum like '%" + studentInfo.getIDNum() + "%' and ";
//		}
//		if(!studentInfo.getBirthday().equals("*")) {
//			
//			hql += "p.birthday like '%" + studentInfo.getBirthday() + "%' and ";
//		}
//		if(!studentInfo.getDormitoryCode().equals("*")) {
//			
//			hql += "p.dormitoryCode like '%" + studentInfo.getDormitoryCode() + "%' and ";
//		}
		
		if(studentInfo.getFacultyInfo() != null && studentInfo.getFacultyInfo().getClassName() != null 
				&& !studentInfo.getFacultyInfo().getClassName().equals("")) {
			
			hql += "p.facultyInfo like '%" + studentInfo.getFacultyInfo().getPK_classKey() + "%' and ";
		}
		
		if(studentInfo.getMajorInfo() != null && studentInfo.getMajorInfo().getClassName() != null 
				&& !studentInfo.getMajorInfo().getClassName().equals("")) {
			
			hql += "p.majorInfo like '%" + studentInfo.getMajorInfo().getPK_classKey() + "%' and ";
		}
		
		if(studentInfo.getClassInfo() != null && studentInfo.getClassInfo().getClassName() != null 
				&& !studentInfo.getClassInfo().getClassName().equals("")) {
			
			hql += "p.classInfo like '%" + studentInfo.getClassInfo().getPK_classKey() + "%' and ";
		}
		
		hql += thincSQL;
		
		if(hql.endsWith(" and ")) {
			
			hql = hql.substring(0, hql.length() - 4);
		
		}
		
		if(hql.endsWith(" where ")) {
			
			hql = hql.substring(0, hql.length() - 6);
		}
		
		hql += "order by p.stuCode";
		
		return this.find(hql);
		
	}

	@Override
	public Student getStudentByStuCode(String stuCode) {
		
		String hql = "from Student p where p.stuCode = " + stuCode;
		
		@SuppressWarnings("unchecked")
		List<Student> students = this.find(hql);
		
		if(students != null && students.size() == 1) {
			
			return students.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsByClassInfo(int classKey) {
		
		String hql = "from Student p where p.classInfo = " + classKey;
		
		return this.find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsByPagination(final int firstResult, final int maxResult) {
		
		final String hql = "from Student s order by s.stuCode asc";
		
		List<Student> list = super.executeFind(new HibernateCallback<Object>() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(hql);
				
				List<Student> result = query.setFirstResult(firstResult).setMaxResults(maxResult).list();
							
				return result;		
			}
		});
			
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> searchStudentsByPagination(Student studentInfo,
			final int firstResult, final int maxResult) {		
		
		//	String thinc = studentInfo.getThnic();
			
			String thincSQL = "";
			
//			if(!thinc.equals("*")) {
//				
//				if(thinc.equals("%汉族%")) {
//				
//					thincSQL = "p.thinc like '%汉族%' and ";
//			
//				} else {
//					
//					thincSQL = " p.thinc like '%汉族%' and ";
//				}
//			}
			
			String hql = "from Student p where ";
			
			if(!studentInfo.getName().equals("*")) {
				
				hql += "p.name like '%" + studentInfo.getName() + "%' and ";
			}
			
			if(!studentInfo.getStuCode().equals("*")) {
				
				hql += "p.stuCode like '%" + studentInfo.getStuCode() + "%' and ";
			}
			
//			if(!studentInfo.getSchoolRoll().equals("*")) {
//				
//				hql += "p.schoolRoll like '%" + studentInfo.getSchoolRoll() + "%' and ";
//			}
	//
//			if(!studentInfo.getSchoolingYear().equals("*")) {
//				
//				hql += "p.schoolingYear like '%" + studentInfo.getSchoolingYear() + "%' and ";
//			}
			
//			if(!studentInfo.getGrade().equals("*")) {
//				
//				hql += "p.grade like '%" + studentInfo.getGrade() + "%' and ";
//			}
			
			if(!studentInfo.getGender().equals("*")) {
				
				hql += "p.gender like '%" + studentInfo.getGender() + "%' and ";
			}

//			if(!studentInfo.getIDNum().equals("*")) {
//				
//				hql += "p.IDNum like '%" + studentInfo.getIDNum() + "%' and ";
//			}
//			if(!studentInfo.getBirthday().equals("*")) {
//				
//				hql += "p.birthday like '%" + studentInfo.getBirthday() + "%' and ";
//			}
//			if(!studentInfo.getDormitoryCode().equals("*")) {
//				
//				hql += "p.dormitoryCode like '%" + studentInfo.getDormitoryCode() + "%' and ";
//			}
			
			if(studentInfo.getFacultyInfo() != null && studentInfo.getFacultyInfo().getClassName() != null 
					&& !studentInfo.getFacultyInfo().getClassName().equals("")) {
				
				hql += "p.facultyInfo like '%" + studentInfo.getFacultyInfo().getPK_classKey() + "%' and ";
			}
			
			if(studentInfo.getMajorInfo() != null && studentInfo.getMajorInfo().getClassName() != null 
					&& !studentInfo.getMajorInfo().getClassName().equals("")) {
				
				hql += "p.majorInfo like '%" + studentInfo.getMajorInfo().getPK_classKey() + "%' and ";
			}
			
			if(studentInfo.getClassInfo() != null && studentInfo.getClassInfo().getClassName() != null 
					&& !studentInfo.getClassInfo().getClassName().equals("")) {
				
				hql += "p.classInfo like '%" + studentInfo.getClassInfo().getPK_classKey() + "%' and ";
			}
			
			hql += thincSQL;
			
			if(hql.endsWith(" and ")) {
				
				hql = hql.substring(0, hql.length() - 4);
			
			}
			
			if(hql.endsWith(" where ")) {
				
				hql = hql.substring(0, hql.length() - 6);
			}
			
			hql += "order by p.stuCode asc";

			final String queryStr = hql;
			
			List<Student> list = super.executeFind(new HibernateCallback<Object>() {
				
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					
					Query query = session.createQuery(queryStr);
					
					List<Student> result = query.setFirstResult(firstResult).setMaxResults(maxResult).list();
								
					return result;		
				}
			});
				
			return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsByMajorInfo(int majorKey) {
	
		String hql = "from Student p where p.majorInfo = " + majorKey;
		
		return this.find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsByFacultyInfo(int classKey) {
		
		String hql = "from Student p where p.facultyInfo = " + classKey;
		
		return this.find(hql);
	}
}
