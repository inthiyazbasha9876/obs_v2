package com.ojas.obs.calendarholidays.request;

import java.util.List;

import com.ojas.obs.calendarholidays.model.CalendarHolidays;

public class CalendarHolidaysRequest {

	private List<CalendarHolidays> holidayslist;
	private String transactionType;
	public List<CalendarHolidays> getHolidayslist() {
		return holidayslist;
	}
	public void setHolidayslist(List<CalendarHolidays> holidayslist) {
		this.holidayslist = holidayslist;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "CalendarHolidaysRequest [holidayslist=" + holidayslist + ", transactionType=" + transactionType + "]";
	}
	
}
