package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.DocumentStage;

public class DocumentStageResponse {
	private String message;
	private String statusCode;
	private List<DocumentStage> doucumentStageList;

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

	public List<DocumentStage> getDoucumentStageList() {
		return doucumentStageList;
	}

	public void setDoucumentStageList(List<DocumentStage> doucumentStageList) {
		this.doucumentStageList = doucumentStageList;
	}

	@Override
	public String toString() {
		return "DocumentStageResponse [message=" + message + ", statusCode=" + statusCode + ", doucumentStageList="
				+ doucumentStageList + "]";
	}
}