package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.model.EmployeeSkills;

/**
 * 
 * @author sdileep
 *
 */
public class EmployeeInfoRequest {

	private List<EmployeeInfo> employeeInfo;
	private EmployeeSkills employeeskills;
	private String transactionType;

	public List<EmployeeInfo> getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(List<EmployeeInfo> employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	

	public EmployeeSkills getEmployeeskills() {
		return employeeskills;
	}

	public void setEmployeeskills(EmployeeSkills employeeskills) {
		this.employeeskills = employeeskills;
	}
	@Override
	public String toString() {
		return "EmployeeInfoRequest [employeeInfo=" + employeeInfo + ", transactionType=" + transactionType + "]";
	}

}