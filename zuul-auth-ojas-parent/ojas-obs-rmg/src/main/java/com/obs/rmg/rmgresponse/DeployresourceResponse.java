package com.obs.rmg.rmgresponse;

import java.util.List;

import com.obs.rmg.rmgmodel.EmployeeSkills;

public class DeployresourceResponse {
	private String message;
	private String statusCode;
	private List<EmployeeSkills> employeeskills;

	public List<EmployeeSkills> getEmployeeskills() {
		return employeeskills;
	}

	public void setEmployeeskills(List<EmployeeSkills> employeeskills) {
		this.employeeskills = employeeskills;
	}
	

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

	@Override
	public String toString() {
		return "DeployresourceResponse [message=" + message + ", statusCode=" + statusCode + ", employeeskills="
				+ employeeskills + "]";
	}

	
	
}
