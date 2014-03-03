package com.darmody.buumanagementsystem.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * 2013.8.23 9:06
 * @author Caihuanyu
 * @content 班级实体，包含学院，系别，专业，班级的信息
 */

@Entity
@Table(name="TD_BUUCLASS_INFO")
public class BuuClass {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PK_classKey", nullable=false, unique=true, length=5)
	private int PK_classKey;
	
	@Column(name="className", nullable=false, unique=true, length=30)
	private String className;
	
	@Column(name="classType", nullable=false, unique=false, length=2)
	private String classType;
	
	@Column(name="fatherClassKey", nullable=false, length=5)
	private int fatherClassKey;
	
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=Administrator.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name="admins", nullable=false)
	private List<Administrator> admins;
	
	public BuuClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BuuClass(int pK_classKey, String className, String classType,
			int fatherClassKey, List<Administrator> admins) {
		super();
		PK_classKey = pK_classKey;
		this.className = className;
		this.classType = classType;
		this.fatherClassKey = fatherClassKey;
		this.admins = admins;
	}

	public int getPK_classKey() {
		return PK_classKey;
	}

	public void setPK_classKey(int pK_classKey) {
		PK_classKey = pK_classKey;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public int getFatherClassKey() {
		return fatherClassKey;
	}

	public void setFatherClassKey(int fatherClassKey) {
		this.fatherClassKey = fatherClassKey;
	}

	public List<Administrator> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Administrator> admins) {
		this.admins = admins;
	}
}
