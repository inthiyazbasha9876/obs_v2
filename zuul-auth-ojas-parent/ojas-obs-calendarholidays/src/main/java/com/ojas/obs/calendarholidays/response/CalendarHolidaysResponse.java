package com.ojas.obs.calendarholidays.response;

import java.util.List;

import com.ojas.obs.calendarholidays.model.CalendarHolidays;

public class CalendarHolidaysResponse {

	private String message;
	private String statusCode;
	private List<CalendarHolidays> holidayslist;
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
	public List<CalendarHolidays> getHolidayslist() {
		return holidayslist;
	}
	public void setHolidayslist(List<CalendarHolidays> holidayslist) {
		this.holidayslist = holidayslist;
	}
	@Override
	public String toString() {
		return "CalendarHolidaysResponse [message=" + message + ", statusCode=" + statusCode + ", holidayslist="
				+ holidayslist + "]";
	}
	
}
