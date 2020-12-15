package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.EmployeeExperience;

public class EmployeeExperienceResponse 
{
	private List<EmployeeExperience> empExperienceList;
	private String StatusCode;
	private String Message;

	public List<EmployeeExperience> getEmpExperienceList() {
		return empExperienceList;
	}

	public void setEmpExperienceList(List<EmployeeExperience> empExperienceList) {
		this.empExperienceList = empExperienceList;
	}

	public String getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
