package com.ojas.obs.model;

public class Skill {
	
	private Integer id;
	private String skill_name;
	private boolean status;
	
	public Skill() {
		super();
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSkill_name() {
		return skill_name;
	}
	public void setSkill_name(String skill_name) {
		this.skill_name = skill_name;
	}
	@Override
	public String toString() {
		return "Skill [id=" + id + ", skill_name=" + skill_name + ", status=" + status + "]";
	}
	
	
	
	
	
	

}
