package com.ojas.obs.model;

import java.util.List;

public class EmployeeSkills {
	private String employeeId;
	private String empName;
	private List<String> skills;
	public List<String> getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
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
	@Override
	public String toString() {
		return "EmployeeSkills [employeeId=" + employeeId + ", empName=" + empName + ", skills=" + skills + "]";
	}
}
