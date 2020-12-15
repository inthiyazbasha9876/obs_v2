package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.KYE;

/**
 * 
 * @author tshiva
 *
 */
public class KYERequest {
	private List<KYE> kye;
	private String transactionType;
	
	public List<KYE> getKye() {
		return kye;
	}
	public void setKye(List<KYE> kye) {
		this.kye = kye;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "KYERequest [kye=" + kye + ", transactionType=" + transactionType + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kye == null) ? 0 : kye.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
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
		KYERequest other = (KYERequest) obj;
		if (kye == null) {
			if (other.kye != null)
				return false;
		} else if (!kye.equals(other.kye))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

	

}