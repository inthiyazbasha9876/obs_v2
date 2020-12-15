package com.ojas.obs.model;

public class ContactInfoModel {


	private String email;
	
	private String empId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "ContactInfoModel [email=" + email + ", empId=" + empId + "]";
	}

	
}
