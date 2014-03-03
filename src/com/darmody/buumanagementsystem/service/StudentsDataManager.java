package com.darmody.buumanagementsystem.service;

import java.io.File;
import java.util.List;

import com.darmody.buumanagementsystem.entity.Student;

/**
 * 2013.7.18
 * @author Caihuanyu
 * @content 学生数据管理服务层接口
 */

public interface StudentsDataManager {

	/**
	 * @description 添加新的学生数据
	 * @param newStudent 新的学生对象
	 */
	public int addNewStudent(Student newStudent);
	
	/**
	 * @description 通过键值删除学生数据
	 * @param PK_stuKey 待删除的学生对象的键值
	 * @return 删除成功返回true
	 */
	public boolean deleteStudent(Integer PK_stuKey);
	
	/**
	 * @description 通过实体信息删除实体
	 * @param student 需被删除的实体
	 * @return 删除成功返回true
	 */
	public boolean deleteStudent(Student student);
	
	/**
	 * @description 删除指定的所有实体
	 * @param students 待被删除的所有实体
	 * @return 删除成功返回true
	 */
	public boolean cleanStudentsData(List<Student> students);
	
	/**
	 * @description 更新学生数据
	 * @param student 被更新的学生对象
	 * @return 更新成功返回true
	 */
	public boolean updateStudentData(Student student);
	
	/**
	 * @description 获取所有学生的数据
	 * @return 获取所有学生的数据
	 */
	public List<Student> fetchStudentsData();
	
	/**
	 * @description 获取所有学生的数据并分页
	 * @return 获取所有学生的数据
	 */
	public List<Student> fetchStudentsData(int firstResult, int maxResult);	
	
	/**
	 * @description 根据学生的指定信息检索学生数据
	 * @param studentsInfo 检索的学生信息
	 * @return 获取即所得到的学生数据
	 */
	public List<Student> fetchStudentSData(Student studentsInfo);
	
	/**
	 * @description 根据学生的指定信息检索学生数据并分页
	 * @param studentsInfo 检索的学生信息
	 * @return 获取即所得到的学生数据
	 */
	public List<Student> fetchStudentSData(Student studentsInfo, int firstResult, int maxResult);
	
	/**
	 * 通过学号获取学生对象
	 * @param stuCode 学生的学号
	 * @return 查询得到的学生对象
	 */
	public Student fetchStudentData(String stuCode);
	
	/**
	 * 通过key获取学生对象
	 * @param key 学生的key值
	 * @return 查询得到的学生对象
	 */
	public Student fetchStudentData(int key);
	
	
	/**
	 * 通过excel文件批量导入学生数据
	 * @param excelFile 记录学生数据的excel文件
	 * @throws Exception 
	 */
	public void bulkAddStudentsDataByExcelFile(File excelFile) throws Exception;
	
	/**
	 * 通过学号及密码登录
	 * @param stuCode 学号
	 * @param password 密码
	 * @return 登陆成功返回true
	 */
	public boolean loginValidate(String stuCode, String password);
}
