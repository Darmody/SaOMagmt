package com.darmody.buumanagementsystem.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 2013.8.23
 * @author Caihuanyu
 * @content 学生实体类
 * @version 2.0
 */

@Entity
@Table(name="TD_STUDENT_INFO")
public class Student implements Serializable{

	private static final long serialVersionUID = 449474523932065302L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PK_stuKey", nullable=false, unique=true, length=5)
	private int PK_stuKey;		//id
	
	@Column(name="name", nullable=false, length=15)
	private String name;		//姓名

	@Column(name="stuCode", nullable=false, unique=true, length=13)
	private String stuCode;    	//学号
	
	@Column(name="thnic", nullable=false, length=18)
	private String thnic;	//民族

	@Column(name="schoolRoll", nullable=false, length=8)
	private String schoolRoll;	//学籍

	@Column(name="schoolingYear", nullable=false, length=10)
	private String schoolingYear;	//学制
	
	@Column(name="grade", nullable=false, length=7)
	private String grade;	//年级

	@Column(name="gender", nullable=false, length=2)
	private String gender;		//性别

	@Column(name="IDNum", nullable=false, length=20)
	private String IDNum;	//身份证号

	@Column(name="birthday", nullable=false, length=18)
	private String birthday;	//生日

	@Column(name="dormitoryCode", nullable=false, length=10)
	private String dormitoryCode;		//宿舍号

	@ManyToOne(fetch=FetchType.EAGER, targetEntity=BuuClass.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name="facultyInfo", nullable=false)
	private BuuClass facultyInfo;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=BuuClass.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name="majorInfo", nullable=false)
	private BuuClass majorInfo;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=BuuClass.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name="classInfo", nullable=false)
	private BuuClass classInfo;
	
	@Column(name="password", nullable=false, length=12)
	private String password;

	@Column(name="counselor", nullable=false, length=15)
	private String counselor;
	
	@Column(name="phone", nullable=false, length=11)
	private String phone;
	
	public Student() {
	
		super();
	}

	public Student(int pK_stuKey, String name, String stuCode, String thnic,
			String schoolRoll, String schoolingYear, String grade,
			String gender, String iDNum, String birthday, String dormitoryCode,
			BuuClass facultyInfo, BuuClass majorInfo, BuuClass classInfo,
			String password, String counselor, String phone) {
		super();
		PK_stuKey = pK_stuKey;
		this.name = name;
		this.stuCode = stuCode;
		this.thnic = thnic;
		this.schoolRoll = schoolRoll;
		this.schoolingYear = schoolingYear;
		this.grade = grade;
		this.gender = gender;
		IDNum = iDNum;
		this.birthday = birthday;
		this.dormitoryCode = dormitoryCode;
		this.facultyInfo = facultyInfo;
		this.majorInfo = majorInfo;
		this.classInfo = classInfo;
		this.password = password;
		this.counselor = counselor;
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getPK_stuKey() {
		return PK_stuKey;
	}

	public void setPK_stuKey(int pK_stuKey) {
		PK_stuKey = pK_stuKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStuCode() {
		return stuCode;
	}

	public void setStuCode(String stuCode) {
		this.stuCode = stuCode;
	}

	public String getThnic() {
		return thnic;
	}

	public void setThnic(String thnic) {
		this.thnic = thnic;
	}

	public String getSchoolRoll() {
		return schoolRoll;
	}

	public void setSchoolRoll(String schoolRoll) {
		this.schoolRoll = schoolRoll;
	}

	public String getSchoolingYear() {
		return schoolingYear;
	}

	public void setSchoolingYear(String schoolingYear) {
		this.schoolingYear = schoolingYear;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIDNum() {
		return IDNum;
	}

	public void setIDNum(String iDNum) {
		IDNum = iDNum;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDormitoryCode() {
		return dormitoryCode;
	}

	public void setDormitoryCode(String dormitoryCode) {
		this.dormitoryCode = dormitoryCode;
	}

	public BuuClass getFacultyInfo() {
		return facultyInfo;
	}

	public void setFacultyInfo(BuuClass facultyInfo) {
		this.facultyInfo = facultyInfo;
	}

	public BuuClass getMajorInfo() {
		return majorInfo;
	}

	public void setMajorInfo(BuuClass majorInfo) {
		this.majorInfo = majorInfo;
	}

	public BuuClass getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(BuuClass classInfo) {
		this.classInfo = classInfo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCounselor() {
		return counselor;
	}

	public void setCounselor(String counselor) {
		this.counselor = counselor;
	}
}
