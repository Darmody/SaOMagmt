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
 * @content 报盘答案对象，封装一封提交的报盘答案信息
 */

@Entity
@Table(name="TD_OFFER_ANSWER_INFO")
public class OfferAnswer implements Serializable{

	private static final long serialVersionUID = -1087013379451613267L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PK_answerKey", nullable=false, unique=true, length=5)
	private int PK_answerKey;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="answerRecord", nullable=false, unique=false)
	private String answerRecord;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=OfferFlow.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name="belongedFlow", nullable=false)
	private OfferFlow belongedFlow;
	
	@Column(name="submitter", nullable=false, length=20)
	private String submitter;
	
	public OfferAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OfferAnswer(int pK_answerKey, String answerRecord,
			OfferFlow belongedFlow, String submitter) {
		super();
		PK_answerKey = pK_answerKey;
		this.answerRecord = answerRecord;
		this.belongedFlow = belongedFlow;
		this.submitter = submitter;
	}

	/*
	 * Getter and Setter
	 */
	
	public int getPK_answerKey() {
		return PK_answerKey;
	}

	public void setPK_answerKey(int pK_answerKey) {
		PK_answerKey = pK_answerKey;
	}

	public String getAnswerRecord() {
		return answerRecord;
	}

	public void setAnswerRecord(String answerRecord) {
		this.answerRecord = answerRecord;
	}

	public OfferFlow getBelongedFlow() {
		return belongedFlow;
	}

	public void setBelongedFlow(OfferFlow belongedFlow) {
		this.belongedFlow = belongedFlow;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
}
