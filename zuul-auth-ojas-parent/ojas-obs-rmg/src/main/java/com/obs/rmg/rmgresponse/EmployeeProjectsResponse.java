package com.obs.rmg.rmgresponse;

import java.util.List;

import com.obs.rmg.rmgmodel.EmployeeProjects;
import com.obs.rmg.rmgmodel.EmployeeSkills;
import com.obs.rmg.rmgmodel.ProjectList;

public class EmployeeProjectsResponse
{
	private String message;
	private String statusCode;
	private List<EmployeeProjects> employeeprojects;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public List<EmployeeProjects> getEmployeeprojects() {
		return employeeprojects;
	}
	public void setEmployeeprojects(List<EmployeeProjects> employeeprojects) {
		this.employeeprojects = employeeprojects;
	}
	@Override
	public String toString() {
		return "EmployeeProjectsResponse [message=" + message + ", statusCode=" + statusCode + ", employeeprojects="
				+ employeeprojects + "]";
	}
	
}
