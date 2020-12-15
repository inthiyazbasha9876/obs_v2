package com.ojas.obs.calendarholidays.controller;
import static com.ojas.obs.calendarholidays.constants.Constants.DELETE;
import static com.ojas.obs.calendarholidays.constants.Constants.GETBYID;
import static com.ojas.obs.calendarholidays.constants.Constants.SAVE;
import static com.ojas.obs.calendarholidays.constants.Constants.UPDATE;
import static com.ojas.obs.calendarholidays.constants.Constants.SET;
import static com.ojas.obs.calendarholidays.constants.Constants.GET;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.calendarholidays.facadeimpl.CalendarHolidaysFacadeImpl;
import com.ojas.obs.calendarholidays.model.CalendarHolidays;
import com.ojas.obs.calendarholidays.request.CalendarHolidaysRequest;
import com.ojas.obs.calendarholidays.response.ErrorResponse;

@RestController
//@RequestMapping("/holidays")
public class CalendarHolidaysController {
	@Autowired
	private CalendarHolidaysFacadeImpl holidaysfacadeImpl;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> saveHolidays(@RequestBody CalendarHolidaysRequest holidaysRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into controller");

		List<CalendarHolidays> calendarList = holidaysRequestObject.getHolidayslist();
		try {
			if (holidaysRequestObject == null || (holidaysRequestObject.getHolidayslist() == null)
					|| holidaysRequestObject.getTransactionType() == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("NULLVALUE");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (CalendarHolidays calendar : calendarList) {
				if (holidaysRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
						&& calendar.getHolidayDate().isEmpty() && calendar.getHolidayName().isEmpty()) {
					ErrorResponse response = new ErrorResponse();
					response.setStatusCode("422");
					response.setStatusMessage("NULLVALUE");
					return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if ((holidaysRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						|| holidaysRequestObject.getTransactionType().equalsIgnoreCase(DELETE))
						&& calendar.getHolidayId() == null)

				{
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage("NULLVALUE");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return holidaysfacadeImpl.saveHolidays(holidaysRequestObject);

		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getHolidays(@RequestBody CalendarHolidaysRequest holidaysRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		try {
			if (holidaysRequestObject == null || holidaysRequestObject.getHolidayslist() == null
					|| holidaysRequestObject.getHolidayslist().isEmpty()
					|| holidaysRequestObject.getTransactionType() == null
					|| holidaysRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (holidaysRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& holidaysRequestObject.getHolidayslist().get(0).getHolidayId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("calendar holidays must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return holidaysfacadeImpl.getHolidays(holidaysRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}

}
