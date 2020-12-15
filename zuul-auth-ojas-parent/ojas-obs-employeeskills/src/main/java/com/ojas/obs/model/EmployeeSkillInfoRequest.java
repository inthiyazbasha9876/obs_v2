package com.ojas.obs.model;

import java.util.List;

public class EmployeeSkillInfoRequest {

	private List<EmployeeSkillInfo> skillInfoModel;
	private String sessionId;
	private int totalCount;
	private String transactionType;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public List<EmployeeSkillInfo> getSkillInfoModel() {
		return skillInfoModel;
	}

	public void setSkillInfoModel(List<EmployeeSkillInfo> skillInfoModel) {
		this.skillInfoModel = skillInfoModel;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public EmployeeSkillInfoRequest() {
		super();
	}

	
	@Override
	public String toString() {
		return "EmployeeSkillInfoRequest [skillInfoModel=" + skillInfoModel + ", sessionId=" + sessionId
				+ ", totalCount=" + totalCount + ", transactionType=" + transactionType + "]";
	}

}
