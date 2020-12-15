package com.obs.lms.request;

import com.obs.lms.model.LeaveBalance;
import com.obs.lms.model.LeaveInfo;

public class LmsRequest {
	private LeaveInfo leaveInfo;
	private LeaveBalance leaveBalance;
	private String transationType;

	public LeaveInfo getLeaveInfo() {
		return leaveInfo;
	}

	public void setLeaveInfo(LeaveInfo leaveInfo) {
		this.leaveInfo = leaveInfo;
	}

	public LeaveBalance getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(LeaveBalance leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public String getTransationType() {
		return transationType;
	}

	public void setTransationType(String transationType) {
		this.transationType = transationType;
	}

}
