package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.SarStatus;

public class SarStatusResponse {
	private String message;
	private String statusCode;
	private List<SarStatus> sarstatusList;

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

	public List<SarStatus> getSarstatusList() {
		return sarstatusList;
	}

	public void setSarstatusList(List<SarStatus> sarstatusList) {
		this.sarstatusList = sarstatusList;
	}

	@Override
	public String toString() {
		return "SarStatusResponse [message=" + message + ", statusCode=" + statusCode + ", sarstatusList="
				+ sarstatusList + "]";
	}

}
