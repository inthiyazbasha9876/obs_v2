package com.obs.lms.response;

import java.util.List;

import com.obs.lms.model.LeaveBalance;
import com.obs.lms.model.LeaveInfo;

public class LmsResponse {

	private List<LeaveInfo> leaveInfo;
	private LeaveBalance leaveBalList;
	private String message;
	private String statusCode;

	public LeaveBalance getLeaveBalList() {
		return leaveBalList;
	}

	public void setLeaveBalList(LeaveBalance leaveBalList) {
		this.leaveBalList = leaveBalList;
	}

	public List<LeaveInfo> getLeaveInfo() {
		return leaveInfo;
	}

	public void setLeaveInfo(List<LeaveInfo> leaveInfo) {
		this.leaveInfo = leaveInfo;
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
		return "LmsResponse [leaveInfo=" + leaveInfo + ", leaveBalList=" + leaveBalList + ", message=" + message
				+ ", statusCode=" + statusCode + "]";
	}

}
