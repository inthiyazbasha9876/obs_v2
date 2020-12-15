package com.ojas.obs.model;

public class RoleModel {

	
	private int id;
	private String employeeId;
	private int role_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	@Override
	public String toString() {
		return "RoleModel [id=" + id + ", employeeId=" + employeeId + ", role_id=" + role_id + "]";
	}
	
}
