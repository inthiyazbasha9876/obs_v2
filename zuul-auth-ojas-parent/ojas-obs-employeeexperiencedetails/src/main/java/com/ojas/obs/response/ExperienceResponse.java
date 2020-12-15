package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.EmployeeExperienceDetails;

public class ExperienceResponse {
	private List<EmployeeExperienceDetails> employeeExperienceDetails;
	private String statusCode;
	private String message;

	public List<EmployeeExperienceDetails> getEmployeeExperienceDetails() {
		return employeeExperienceDetails;
	}

	public void setEmployeeExperienceDetails(List<EmployeeExperienceDetails> employeeExperienceDetails) {
		this.employeeExperienceDetails = employeeExperienceDetails;
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

	@Override
	public String toString() {
		return "ExperienceResponse [employeeExperienceDetails=" + employeeExperienceDetails + ", statusCode="
				+ statusCode + ", message=" + message + "]";
	}

	
}
