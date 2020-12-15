package com.ojas.obs.model;

import java.util.List;

public class EmployeeSkillInfoResponse {

	private List<EmployeeSkillInfo> getSkillInfoList;
	private int totalCount;
	private String statusCode;
	private String statusMessage;

	public List<EmployeeSkillInfo> getGetSkillInfoList() {
		return getSkillInfoList;
	}

	public void setGetSkillInfoList(List<EmployeeSkillInfo> getSkillInfoList) {
		this.getSkillInfoList = getSkillInfoList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "EmployeeSkillInfoResponse [getSkillInfoList=" + getSkillInfoList + ", totalCount=" + totalCount
				+ ", statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}

	
	

}
