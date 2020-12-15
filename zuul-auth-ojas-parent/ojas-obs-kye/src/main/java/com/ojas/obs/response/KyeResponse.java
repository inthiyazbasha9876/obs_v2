package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.KYE;

/**
 * 
 * @author tshiva
 *
 */
public class KyeResponse {
	private List<KYE> kyeList;
	private String message;
	private String statusCode;
	
	public List<KYE> getKyeList() {
		return kyeList;
	}
	public void setKyeList(List<KYE> kyeList) {
		this.kyeList = kyeList;
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
	public String toString() {
		return "KyeResponse [kyeList=" + kyeList + ", message=" + message + ", statusCode=" + statusCode + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kyeList == null) ? 0 : kyeList.hashCode());
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
		KyeResponse other = (KyeResponse) obj;
		if (kyeList == null) {
			if (other.kyeList != null)
				return false;
		} else if (!kyeList.equals(other.kyeList))
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
