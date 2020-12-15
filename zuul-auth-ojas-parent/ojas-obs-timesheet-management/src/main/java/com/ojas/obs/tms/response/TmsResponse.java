package com.ojas.obs.tms.response;

import java.util.List;

import com.ojas.obs.tms.model.Record;
import com.ojas.obs.tms.model.TimeSheet;

public class TmsResponse {
	private String message;
	private String statusCode;
	private List<TimeSheet> timesheetList;
	private List<Record> recordList;
	
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
	public List<TimeSheet> getTimesheetList() {
		return timesheetList;
	}
	public void setTimesheetList(List<TimeSheet> timesheetList) {
		this.timesheetList = timesheetList;
	}
	public List<Record> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<Record> recordList) {
		this.recordList = recordList;
	}
	
	@Override
	public String toString() {
		return "TMSResponse [message=" + message + ", statusCode=" + statusCode + ", timesheetList=" + timesheetList
				+ ", recordList=" + recordList + "]";
	}
	
}
