package com.obs.rmg.rmgresponse;

import java.util.List;

import com.obs.rmg.rmgmodel.HoursList;

public class EmployeeHoursResponse
{
	private String message;
	private String statusCode;
	private List<HoursList> hourslist;
	
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
	public List<HoursList> getHourslist() {
		return hourslist;
	}
	public void setHourslist(List<HoursList> hourslist) {
		this.hourslist = hourslist;
	}
	
}
