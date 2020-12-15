package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.DependentDetails;

public class DependentDetailsResponse {
	private List<DependentDetails> getDependentDetailsList;
	private String statusCode;
	private String message;
	
	public List<DependentDetails> getGetDependentDetailsList() {
		return getDependentDetailsList;
	}
	public void setGetDependentDetailsList(List<DependentDetails> getDependentDetailsList) {
		this.getDependentDetailsList = getDependentDetailsList;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "DependentDetailsResponse [getDependentDetailsList=" + getDependentDetailsList + ", statusCode="
				+ statusCode + ", message=" + message + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDependentDetailsList == null) ? 0 : getDependentDetailsList.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DependentDetailsResponse other = (DependentDetailsResponse) obj;
		if (getDependentDetailsList == null) {
			if (other.getDependentDetailsList != null)
				return false;
		} else if (!getDependentDetailsList.equals(other.getDependentDetailsList))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}
	
	


}
