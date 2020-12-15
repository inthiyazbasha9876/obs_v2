package com.ojas.obs.interviewmode.request;

import java.util.List;

import com.ojas.obs.interviewmode.model.InterviewMode;

public class InterviewModeRequest {

	private List<InterviewMode> interviewmodeList;
	private String transactionType;

	public List<InterviewMode> getInterviewmodeList() {
		return interviewmodeList;
	}

	public void setInterviewmodeList(List<InterviewMode> interviewmodeList) {
		this.interviewmodeList = interviewmodeList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "InterviewModeRequest [interviewmodeList=" + interviewmodeList + ", transactionType=" + transactionType
				+ "]";
	}

}
