package com.obs.rmg.rmgmodel;

import java.util.List;

public class EmployeeSkills {
	private String employeeId;
	private String empName;
	private String employment_status;
	private List<String> skills;
	private String skillId;
	private float billRate;
	private String startDate;
	private String endDate;
	private String fromDate;
	private String toDate;
	private Object currentCompanySalary;
	private String projectName;
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Object getCurrentCompanySalary() {
		return currentCompanySalary;
	}
	public void setCurrentCompanySalary(Object currentCompanySalary) {
		this.currentCompanySalary = currentCompanySalary;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public float getBillRate() {
		return billRate;
	}
	public void setBillRate(float billRate) {
		this.billRate = billRate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSkillId() {
		return skillId;
	}
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
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
	
	public String getEmployment_status() {
		return employment_status;
	}
	public void setEmployment_status(String employment_status) {
		this.employment_status = employment_status;
	}
	@Override
	public String toString() {
		return "EmployeeSkills [employeeId=" + employeeId + ", empName=" + empName + ", employment_status="
				+ employment_status + ", skills=" + skills + ", skillId=" + skillId + ", billRate=" + billRate
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
