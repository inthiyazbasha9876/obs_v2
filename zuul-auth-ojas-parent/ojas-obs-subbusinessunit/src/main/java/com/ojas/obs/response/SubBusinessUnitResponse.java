package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.SubBusinessUnit;

/**
 * 
 * @author asuneel
 *
 */
public class SubBusinessUnitResponse {

	private List<SubBusinessUnit> subBusinessUnitList;
	private String message;
	private String statusCode;
	private List<String> sbuHeads;
	
	public List<String> getSbuHeads() {
		return sbuHeads;
	}
	public void setSbuHeads(List<String> sbuHeads) {
		this.sbuHeads = sbuHeads;
	}
	public List<SubBusinessUnit> getSubBusinessUnitList() {
		return subBusinessUnitList;
	}
	public void setSubBusinessUnitList(List<SubBusinessUnit> subBusinessUnitList) {
		this.subBusinessUnitList = subBusinessUnitList;
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
		return "SubBusinessUnitResponse [subBusinessUnitList=" + subBusinessUnitList + ", message=" + message
				+ ", statusCode=" + statusCode + ", sbuHeads=" + sbuHeads + "]";
	}
	
	
}
