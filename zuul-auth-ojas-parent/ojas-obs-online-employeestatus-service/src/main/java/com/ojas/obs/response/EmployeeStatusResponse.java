package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.EmployeeStatus;

/**
 * 
 * @author Manohar
 *
 */
public class EmployeeStatusResponse {
	private String message;
	private String statusCode;
	private List<EmployeeStatus> employeeStatusList;

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

	public List<EmployeeStatus> getEmployeeStatusList() {
		return employeeStatusList;
	}

	public void setEmployeeStatusList(List<EmployeeStatus> employeeStatusList) {
		this.employeeStatusList = employeeStatusList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeStatusList == null) ? 0 : employeeStatusList.hashCode());
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
		EmployeeStatusResponse other = (EmployeeStatusResponse) obj;
		if (employeeStatusList == null) {
			if (other.employeeStatusList != null)
				return false;
		} else if (!employeeStatusList.equals(other.employeeStatusList))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeStatusResponse [message=" + message + ", statusCode=" + statusCode + ", employeeStatusList="
				+ employeeStatusList + "]";
	}

}
