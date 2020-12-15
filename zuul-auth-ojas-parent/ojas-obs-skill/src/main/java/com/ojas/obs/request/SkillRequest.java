package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.Skill;

public class SkillRequest {

	
	private List<Skill> listOfSkill;
	private String transactionType;
	
	public SkillRequest() {
		super();
	}
	public List<Skill> getListOfSkill() {
		return listOfSkill;
	}
	public void setListOfSkill(List<Skill> listOfSkill) {
		this.listOfSkill = listOfSkill;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "SkillRequest [listOfSkill=" + listOfSkill + ", transactionType=" + transactionType + "]";
	}
	
	
		
}
