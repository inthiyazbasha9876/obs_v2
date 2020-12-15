package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.BankDetails;

/**
 * 
 * @author akrishna
 *
 */
public class BankDetailsResponse {

	private List<BankDetails> listBankDetails;
	private String message;
	private String satusCode;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listBankDetails == null) ? 0 : listBankDetails.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((satusCode == null) ? 0 : satusCode.hashCode());
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
		BankDetailsResponse other = (BankDetailsResponse) obj;
		if (listBankDetails == null) {
			if (other.listBankDetails != null)
				return false;
		} else if (!listBankDetails.equals(other.listBankDetails)) {
			return false;}
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message)) {
			return false;}
		if (satusCode == null) {
			if (other.satusCode != null)
				return false;
		} else if (!satusCode.equals(other.satusCode)) {
			return false;}
		return true;
	}

	@Override
	public String toString() {
		return "BankDetailsResponse [listBankDetails=" + listBankDetails + ", message=" + message + ", satusCode="
				+ satusCode + "]";
	}

	public List<BankDetails> getListBankDetails() {
		return listBankDetails;
	}

	public void setListBankDetails(List<BankDetails> listBankDetails) {
		this.listBankDetails = listBankDetails;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSatusCode() {
		return satusCode;
	}

	public void setSatusCode(String satusCode) {
		this.satusCode = satusCode;
	}

}
