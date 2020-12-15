package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.InterviewResult;

public class InterviewResultRequest {
	private List<InterviewResult> interviewresultList;
	private String transactionType;

	public List<InterviewResult> getInterviewresultList() {
		return interviewresultList;
	}

	public void setInterviewresultList(List<InterviewResult> interviewresultList) {
		this.interviewresultList = interviewresultList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "InterviewResultRequest [interviewresultList=" + interviewresultList + ", transactionType="
				+ transactionType + "]";
	}
}
