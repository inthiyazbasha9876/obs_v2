package com.ojas.obs.tms.request;

import java.util.Set;

import com.ojas.obs.tms.model.Record;
import com.ojas.obs.tms.model.TimeSheet;
import com.ojas.obs.tms.model.TimesheetStatus;

public class TmsRequest {
	private String transactionType;
	private Set<Record> recordsList;
	private TimeSheet sheet;
	private TimesheetStatus status;
	
	
	public TimesheetStatus getStatus() {
		return status;
	}
	public void setStatus(TimesheetStatus status) {
		this.status = status;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Set<Record> getRecordsList() {
		return recordsList;
	}
	public void setRecordsList(Set<Record> recordsList) {
		this.recordsList = recordsList;
	}
	public TimeSheet getSheet() {
		return sheet;
	}
	public void setSheet(TimeSheet sheet) {
		this.sheet = sheet;
	}
	@Override
	public String toString() {
		return "TmsRequest [transactionType=" + transactionType + ", recordsList=" + recordsList + ", sheet=" + sheet
				+ ", status=" + status + "]";
	}
	
}
