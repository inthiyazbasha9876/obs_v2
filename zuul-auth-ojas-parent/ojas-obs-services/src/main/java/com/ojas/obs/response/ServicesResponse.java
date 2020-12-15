package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.Services;

public class ServicesResponse{
	private List<Services> servicesList;
	private String message;
	private String  statusCode;
	
	public List<Services> getServicesList() {
		return servicesList;
	}
	public void setServicesList(List<Services> servicesList) {
		this.servicesList = servicesList;
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
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((servicesList == null) ? 0 : servicesList.hashCode());
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
		ServicesResponse other = (ServicesResponse) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (servicesList == null) {
			if (other.servicesList != null)
				return false;
		} else if (!servicesList.equals(other.servicesList))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}
	
	

}
