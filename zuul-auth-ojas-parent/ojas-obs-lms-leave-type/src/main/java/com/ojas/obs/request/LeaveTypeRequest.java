package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.LeaveType;

public class LeaveTypeRequest {
	private List<LeaveType> leaveTypeList;
	private String transactionType;

	public List<LeaveType> getLeaveTypeList() {
		return leaveTypeList;
	}

	public void setLeaveTypeList(List<LeaveType> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
