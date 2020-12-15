package com.ojas.obs.calendarholidays.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.calendarholidays.request.CalendarHolidaysRequest;

@Service
public interface CalendarHolidaysFacade {
	public ResponseEntity<Object> saveHolidays(CalendarHolidaysRequest holidaysRequestObject);
	public ResponseEntity<Object> getHolidays(CalendarHolidaysRequest holidaysRequestObject);
}
