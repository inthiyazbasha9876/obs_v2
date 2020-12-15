package com.ojas.obs.psa.model;

public class EmpInfo {
	private String employeeId;
	private String empName;
	private String officialEmail;
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getOfficialEmail() {
		return officialEmail;
	}
	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}
	
	@Override
	public String toString() {
		return "EmpInfo [employeeId=" + employeeId + ", empName=" + empName + ", officialEmail=" + officialEmail + "]";
	}
		
		
		
}
