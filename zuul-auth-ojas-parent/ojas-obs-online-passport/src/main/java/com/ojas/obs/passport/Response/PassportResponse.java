package com.ojas.obs.passport.Response;

import java.util.List;

import com.ojas.obs.passport.model.Passport;

//Request Pojo Class
public class PassportResponse {

	private List<Passport> passportList; // This field is used to display the list of Model Class;
	private String statusCode;
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Passport> getPassportList() {
		return passportList;
	}

	public void setPassportList(List<Passport> passportList) {
		this.passportList = passportList;
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
		
		result = prime * result + ((passportList == null) ? 0 : passportList.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		PassportResponse other = (PassportResponse) obj;
		
		if (passportList == null) {
			if (other.passportList != null)
				return false;
		} else if (!passportList.equals(other.passportList))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

}
