package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.Designation;



/**
 * 
 * @author nsrikanth
 *
 */
public class DesignationResponse {

	private List<Designation> listDesignation;
	private String message;
	private String statusCode;
	public List<Designation> getListDesignation() {
		return listDesignation;
	}
	public void setListDesignation(List<Designation> listDesignation) {
		this.listDesignation = listDesignation;
	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listDesignation == null) ? 0 : listDesignation.hashCode());
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
		DesignationResponse other = (DesignationResponse) obj;
		if (listDesignation == null) {
			if (other.listDesignation != null)
				return false;
		} else if (!listDesignation.equals(other.listDesignation))
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
	@Override
	public String toString() {
		return "DesignationResponse [listDesignation=" + listDesignation + ", message=" + message + ", statusCode="
				+ statusCode + "]";
	}
	

}
