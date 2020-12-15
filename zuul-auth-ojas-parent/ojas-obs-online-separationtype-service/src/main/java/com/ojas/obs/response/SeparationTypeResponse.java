package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.SeparationType;


/**
 * 
 * @author nsrikanth
 *
 */
public class SeparationTypeResponse {
	 
    //private SeparationType separationType;
	
	private String message;
	private String statusCode;
	private List<SeparationType> separationTypeList;
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
	public List<SeparationType> getSeparationTypeList() {
		return separationTypeList;
	}
	public void setSeparationTypeList(List<SeparationType> separationTypeList) {
		this.separationTypeList = separationTypeList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((separationTypeList == null) ? 0 : separationTypeList.hashCode());
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
		SeparationTypeResponse other = (SeparationTypeResponse) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (separationTypeList == null) {
			if (other.separationTypeList != null)
				return false;
		} else if (!separationTypeList.equals(other.separationTypeList))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SeparationTypeResponse [message=" + message + ", statusCode=" + statusCode + ", separationTypeList="
				+ separationTypeList + "]";
	}

	
	

}
	

