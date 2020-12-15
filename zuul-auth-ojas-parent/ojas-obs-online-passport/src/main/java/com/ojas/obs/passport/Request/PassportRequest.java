package com.ojas.obs.passport.Request;

import java.util.List;

import com.ojas.obs.passport.model.Passport;

//Request Pojo Class
public class PassportRequest {
	private List<Passport> passportList; // This fied is used to send model class request
	private String transaactionType;

	public List<Passport> getPassportList() {
		return passportList;
	}

	public void setPassportList(List<Passport> passportList) {
		this.passportList = passportList;
	}

	public String getTransaactionType() {
		return transaactionType;
	}

	public void setTransaactionType(String transaactionType) {
		this.transaactionType = transaactionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((passportList == null) ? 0 : passportList.hashCode());

		result = prime * result + ((transaactionType == null) ? 0 : transaactionType.hashCode());
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
		PassportRequest other = (PassportRequest) obj;
		if (transaactionType == null) {
			if (other.transaactionType != null)
				return false;
		} else if (!transaactionType.equals(other.transaactionType))
			return false;
		return true;
	}

}
