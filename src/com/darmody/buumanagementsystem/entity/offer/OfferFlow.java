package com.darmody.buumanagementsystem.entity.offer;

import java.io.Serializable;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 2013.9.5 
 * @author Caihuanyu
 * @content 报盘流程对象，封装报盘的具体流程信息
 */

@Entity
@Table(name="TD_OFFER_FLOW_INFO")
public class OfferFlow implements Serializable{

	private static final long serialVersionUID = 3163578388516339087L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PK_flowKey", nullable=false, unique=true, length=5)
	private int PK_flowKey;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="flowDescription", nullable=false, unique=false)
	private String flowDescription;

	@Column(name="flowNum", nullable=false, unique=false, length=3)
	private String flowNum;
	
	@Column(name="flowType", nullable=false, length=25)
	private String flowType;
	
	@Column(name="flowSpecies", nullable=false, length=1)
	private String flowSpecies;
	
	@Column(name="fatherFlowKey")
	private int fatherFlowKey;

	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Offer.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name="belongedOffer", nullable=false)
	private Offer belongedOffer;

	public OfferFlow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OfferFlow(int pK_flowKey, String flowDescription, String flowNum,
			String flowType, String flowSpecies, int fatherFlowKey,
			Offer belongedOffer) {
		super();
		PK_flowKey = pK_flowKey;
		this.flowDescription = flowDescription;
		this.flowNum = flowNum;
		this.flowType = flowType;
		this.flowSpecies = flowSpecies;
		this.fatherFlowKey = fatherFlowKey;
		this.belongedOffer = belongedOffer;
	}

	/*
	 * Getter and Setter
	 */
	
	public int getPK_flowKey() {
		return PK_flowKey;
	}

	public void setPK_flowKey(int pK_flowKey) {
		PK_flowKey = pK_flowKey;
	}

	public String getFlowDescription() {
		return flowDescription;
	}

	public void setFlowDescription(String flowDescription) {
		this.flowDescription = flowDescription;
	}

	public String getFlowNum() {
		return flowNum;
	}

	public void setFlowNum(String flowNum) {
		this.flowNum = flowNum;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getFlowSpecies() {
		return flowSpecies;
	}

	public void setFlowSpecies(String flowSpecies) {
		this.flowSpecies = flowSpecies;
	}

	public int getFatherFlowKey() {
		return fatherFlowKey;
	}

	public void setFatherFlowKey(int fatherFlow) {
		this.fatherFlowKey = fatherFlow;
	}

	public Offer getBelongedOffer() {
		return belongedOffer;
	}

	public void setBelongedOffer(Offer belongedOffer) {
		this.belongedOffer = belongedOffer;
	}
}
