package com.ojas.obs.modelresponse;

import java.util.List;


import com.ojas.obs.model.EmployeeEducation;

/**
 * 
 * @author mpraneethguptha
 *
 */
public class EmployeeEducationResponse {
    private List<EmployeeEducation> listCourse;
    private String statusCode;
	private String message;
	public List<EmployeeEducation> getListCourse() {
		return listCourse;
	}
	public void setListCourse(List<EmployeeEducation> listCourse) {
		this.listCourse = listCourse;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listCourse == null) ? 0 : listCourse.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeEducationResponse other = (EmployeeEducationResponse) obj;
		if (listCourse == null) {
			if (other.listCourse != null)
				return false;
		} else if (!listCourse.equals(other.listCourse)) {
			return false;}
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message)) {
			return false;}
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode)) {
			return false;}
		return true;
	}

}
