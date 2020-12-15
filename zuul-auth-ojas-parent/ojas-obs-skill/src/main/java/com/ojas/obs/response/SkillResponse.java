package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.Skill;

public class SkillResponse {
	
	private List<Skill> listOfSkill;
   private String statusCode;
	private String message;
	public List<Skill> getListOfSkill() {
		return listOfSkill;
	}
	public void setListOfSkill(List<Skill> listOfSkill) {
		this.listOfSkill = listOfSkill;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
