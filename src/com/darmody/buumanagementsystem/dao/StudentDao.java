package com.darmody.buumanagementsystem.dao;

import java.util.List;

import com.darmody.buumanagementsystem.entity.Student;

/**
 * 2013.7.17
 * @author Caihuanyu
 * @content 学生数据操作接口
 */

public interface StudentDao extends Dao{

	/**
	 * @return 返回所有学生数据
	 */
	public List<Student> getStudents();
	
	/**
	 * 删除指定的所有学生数据
	 * @param studnets 待删除的学生
	 */
	public void deleteStudents(List<Student> studnets);
	
	/**
	 * 根据检索条件搜索学生数据
	 * @param studentInfo 检索的条件
	 * @return 检索得到的数据
	 */
	public List<Student> searchStudents(Student studentInfo);
	
	/**
	 * 通过学号获取学生
	 * @param stuCode 学号
	 * @return 查询得到的对象
	 */
	public Student getStudentByStuCode(String stuCode);
	
	/**
	 * 通过班级key值获取该班级下的所有学生
	 * @param classKey 班级的key值
	 * @return 返回查询得到的结果
	 */
	public List<Student> getStudentsByClassInfo(int classKey);
	
	/**
	 * 通过专业key值获取该专业下的所有学生
	 * @param classKey 班级的key值
	 * @return 返回查询得到的结果
	 */
	public List<Student> getStudentsByMajorInfo(int classKey);
	
	/**
	 * 通过系key值获取该系下的所有学生
	 * @param classKey 班级的key值
	 * @return 返回查询得到的结果
	 */
	public List<Student> getStudentsByFacultyInfo(int classKey);
	
	
	/**
	 * 分页返回学生数据
	 * @param firstResult 取得第一页的位置
	 * @param maxResult 页的大小
	 * @return 返回查询得到的结果
	 */
	public List<Student> getStudentsByPagination(int firstResult, int maxResult);
	
	public List<Student> searchStudentsByPagination(Student studentInfo, int firstResult, int maxResult);
}
