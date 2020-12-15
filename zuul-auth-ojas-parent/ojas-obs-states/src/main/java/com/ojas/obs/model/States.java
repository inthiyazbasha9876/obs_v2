package com.ojas.obs.model;


public class States {
	private int id;
	private String stateName;
	private boolean status;
	
	public States() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "States [id=" + id + ", stateName=" + stateName + ", status=" + status + "]";
	}
	
	
	
}
