package com.ojas.obs.interviewmode.response;

import java.util.List;

import com.ojas.obs.interviewmode.model.InterviewMode;

public class InterviewModeResponse {
	private String message;
	private String statusCode;
	private List<InterviewMode> interviewmodeList;

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

	public List<InterviewMode> getInterviewmodeList() {
		return interviewmodeList;
	}

	public void setInterviewmodeList(List<InterviewMode> interviewmodeList) {
		this.interviewmodeList = interviewmodeList;
	}

	@Override
	public String toString() {
		return "InterviewModeResponse [message=" + message + ", statusCode=" + statusCode + ", interviewmodeList="
				+ interviewmodeList + "]";
	}

}
