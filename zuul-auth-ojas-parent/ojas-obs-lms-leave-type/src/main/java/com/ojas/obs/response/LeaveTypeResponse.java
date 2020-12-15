package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.LeaveType;

public class LeaveTypeResponse {
	private String message;
	private String statusCode;
	private List<LeaveType> leaveTypeList;

	public List<LeaveType> getLeaveTypeList() {
		return leaveTypeList;
	}

	public void setLeaveTypeList(List<LeaveType> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
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

	

}
