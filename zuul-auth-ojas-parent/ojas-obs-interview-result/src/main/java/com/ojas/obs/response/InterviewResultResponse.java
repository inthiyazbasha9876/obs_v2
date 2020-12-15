package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.InterviewResult;

public class InterviewResultResponse {
	private String message;
	private String statusCode;
	private List<InterviewResult> interviewresultList;

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

	public List<InterviewResult> getInterviewresultList() {
		return interviewresultList;
	}

	public void setInterviewresultList(List<InterviewResult> interviewresultList) {
		this.interviewresultList = interviewresultList;
	}

	@Override
	public String toString() {
		return "InterviewResultResponse [message=" + message + ", statusCode=" + statusCode + ", interviewresultList="
				+ interviewresultList + "]";
	}
}
