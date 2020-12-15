package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.Genders;

public class GenderRequest {
  private List<Genders> gender;
  private String transactionType;
public List<Genders> getGender() {
	return gender;
}
public void setGender(List<Genders> gender) {
	this.gender = gender;
}
public String getTransactionType() {
	return transactionType;
}
public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
}
@Override
public String toString() {
	return "GenderRequest [gender=" + gender + ", transactionType=" + transactionType + "]";
}



}
