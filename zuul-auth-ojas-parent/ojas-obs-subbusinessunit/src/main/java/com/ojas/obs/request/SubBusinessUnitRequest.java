package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.SubBusinessUnit;

/**
 * 
 * @author asuneel
 *
 */
public class SubBusinessUnitRequest {

	private List<SubBusinessUnit> subBusinessUnitModel;
	private String transactionType;

	public List<SubBusinessUnit> getSubBusinessUnitModel() {
		return subBusinessUnitModel;
	}

	public void setSubBusinessUnitModel(List<SubBusinessUnit> subBusinessUnitModel) {
		this.subBusinessUnitModel = subBusinessUnitModel;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "SubBusinessUnitRequest [subBusinessUnitModel=" + subBusinessUnitModel + ", transactionType="
				+ transactionType + "]";
	}

	

}