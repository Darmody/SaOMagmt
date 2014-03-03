package com.darmody.buumanagementsystem.entity.offer;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.darmody.buumanagementsystem.entity.BuuClass;

/**
 * 2013.8.23 
 * @author Caihuanyu
 * @content 报盘对象，封装一个报盘事件的基本信息
 */

@Entity
@Table(name="TD_OFFER_INFO")
public class Offer implements Serializable{

	private static final long serialVersionUID = 7904009702697072839L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PK_offerKey", nullable=false, unique=true, length=5)
	private int PK_offerKey;
	
	@Column(name="offerName", nullable=false, length=30)
	private String offerName;

	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="offerDescription", nullable=false, unique=false)
	private String offerDescription; 
	
	@Column(name="offerStatus", nullable=false)
	private String offerStatus;
	
	@Column(name="offerUrl", nullable=false, unique=false)
	private String offerUrl;
	
	@Column(name="offerStartDate", nullable=false, unique=false)
	private String offerStartDate;
	
	@Column(name="offerCloseDate", nullable=false, unique=false)
	private String offerCloseDate;
	
	@Column(name="offerCreateDate", nullable=false, unique=false)
	private String offerCreateDate;
	
	@Column(name="countLimit", nullable=false, unique=false)
	private int countLimit;

	@Column(name="counFinish", nullable=false, unique=false)
	private int countFinish;
	
	@Column(name="creater", nullable=false, unique=false)
	private String creater;
	
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=BuuClass.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name="buuClasses", nullable=false)
	private Set<BuuClass> buuClasses;
	
	public int getPK_offerKey() {
		return PK_offerKey;
	}

	public Offer() {
		super();
	}

	public Offer(int pK_offerKey, String offerName, String offerDescription,
			String offerStatus, String offerUrl, String offerStartDate,
			String offerCloseDate, String offerCreateDate, int countLimit,
			int countFinish, String creater, Set<BuuClass> buuClasses) {
		super();
		PK_offerKey = pK_offerKey;
		this.offerName = offerName;
		this.offerDescription = offerDescription;
		this.offerStatus = offerStatus;
		this.offerUrl = offerUrl;
		this.offerStartDate = offerStartDate;
		this.offerCloseDate = offerCloseDate;
		this.offerCreateDate = offerCreateDate;
		this.countLimit = countLimit;
		this.countFinish = countFinish;
		this.creater = creater;
		this.buuClasses = buuClasses;
	}

	/*
	 * Getter and Setter
	 */
	
	public void setPK_offerKey(int pK_offerKey) {
		PK_offerKey = pK_offerKey;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

	public String getOfferUrl() {
		return offerUrl;
	}

	public void setOfferUrl(String offerUrl) {
		this.offerUrl = offerUrl;
	}

	public String getOfferStartDate() {
		return offerStartDate;
	}

	public void setOfferStartDate(String offerStartDate) {
		this.offerStartDate = offerStartDate;
	}

	public String getOfferCloseDate() {
		return offerCloseDate;
	}

	public void setOfferCloseDate(String offerCloseDate) {
		this.offerCloseDate = offerCloseDate;
	}

	public int getCountLimit() {
		return countLimit;
	}

	public void setCountLimit(int countLimit) {
		this.countLimit = countLimit;
	}

	public Set<BuuClass> getBuuClasses() {
		return buuClasses;
	}

	public void setBuuClasses(Set<BuuClass> buuClasses) {
		this.buuClasses = buuClasses;
	}

	public int getCountFinish() {
		return countFinish;
	}

	public void setCountFinish(int countFinish) {
		this.countFinish = countFinish;
	}

	public String getOfferCreateDate() {
		return offerCreateDate;
	}

	public void setOfferCreateDate(String offerCreateDate) {
		this.offerCreateDate = offerCreateDate;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
}
