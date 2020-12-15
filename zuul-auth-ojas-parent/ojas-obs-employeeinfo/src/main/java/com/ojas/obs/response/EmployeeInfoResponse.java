package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.model.EmployeeSkills;

/**
 * 
 * @author sdileep
 *
 */
public class EmployeeInfoResponse {
	List<EmployeeInfo> employeeInfo;
	private String message;
	private String statusCode;
	private List<String> list;
	private List<EmployeeSkills> emplist;
	
	
	
	public List<EmployeeSkills> getEmplist() {
		return emplist;
	}

	public void setEmplist(List<EmployeeSkills> emplist) {
		this.emplist = emplist;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<EmployeeInfo> getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(List<EmployeeInfo> employeeInfo) {
		this.employeeInfo = employeeInfo;
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
		return "EmployeeInfoResponse [employeeInfo=" + employeeInfo + ", message=" + message + ", statusCode="
				+ statusCode + "]";
	}

}
