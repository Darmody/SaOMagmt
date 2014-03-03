package com.darmody.buumanagementsystem.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 2013.7.25 15:55
 * @author Caihuanyu
 * @content 管理员实体
 */

@Entity
@Table(name="TD_ADMIN_INFO")
public class Administrator implements Serializable{

	private static final long serialVersionUID = 5629312391548697723L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PK_adminKey", nullable=false, unique=true, length=5)
	private int PK_adminKey;
	
	@Column(name="loginAccount", nullable=false, unique=true, length=15)
	private String loginAccount;
	
	@Column(name="password", nullable=false, length=20)
	private String password;
	
	@Column(name="adminType", nullable=false, length=2)
	private String adminType;

	@Column(name="name", nullable=false, unique=false,length=15)
	private String name;	
	
	@Column(name="phone", nullable=true, unique=false, length=15)
	private String phone;
	
	@Column(name="email", nullable=true, unique=false, length=40)
	private String email;
	
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=BuuClass.class, cascade=CascadeType.REFRESH, mappedBy="admins")
	private List<BuuClass> chargedClasses;
	
	public Administrator() {

		super();
	
	}

	public Administrator(int pK_adminKey, String loginAccount, String password,
			String adminType, String name, String phone, String email) {
		
		super();
		PK_adminKey = pK_adminKey;
		this.loginAccount = loginAccount;
		this.password = password;
		this.adminType = adminType;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	/*
	 * Getter and Setter
	 */
	
	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPK_adminKey() {
		return PK_adminKey;
	}

	public void setPK_adminKey(int pK_adminKey) {
		PK_adminKey = pK_adminKey;
	}

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<BuuClass> getChargedClasses() {
		return chargedClasses;
	}

	public void setChargedClasses(List<BuuClass> chargedClasses) {
		this.chargedClasses = chargedClasses;
	}
}
