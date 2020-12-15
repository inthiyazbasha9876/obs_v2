package com.ojas.obs.calendarholidays.facadeimpl;

import static com.ojas.obs.calendarholidays.constants.Constants.DELETE;
import static com.ojas.obs.calendarholidays.constants.Constants.UPDATE;
import static com.ojas.obs.calendarholidays.constants.Constants.SAVE;
import static com.ojas.obs.calendarholidays.constants.Constants.GETBYID;
import static com.ojas.obs.calendarholidays.constants.Constants.GETALL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.calendarholidays.facade.CalendarHolidaysFacade;
import com.ojas.obs.calendarholidays.model.CalendarHolidays;
import com.ojas.obs.calendarholidays.repository.CalendarHolidaysRepository;
import com.ojas.obs.calendarholidays.request.CalendarHolidaysRequest;
import com.ojas.obs.calendarholidays.response.CalendarHolidaysResponse;

@Service
public class CalendarHolidaysFacadeImpl implements CalendarHolidaysFacade{
 @Autowired
 private CalendarHolidaysRepository holidaysRepo;
 private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
	@Override
	public ResponseEntity<Object> saveHolidays(CalendarHolidaysRequest holidaysRequestObject) {
		CalendarHolidaysResponse response=null;
		logger.debug("request coming to the facade");
		
		if(holidaysRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new CalendarHolidaysResponse();

			List<CalendarHolidays> holidays = holidaysRequestObject.getHolidayslist();

			for (CalendarHolidays calender : holidays) {
				CalendarHolidays save = holidaysRepo.save(calender);

				if (save.getHolidayId() != null) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("holiday saved successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("save method");
					response.setStatusCode("422");
					response.setMessage("failed to save");
					return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}	
		if (holidaysRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new CalendarHolidaysResponse();
			List<CalendarHolidays> holidays = holidaysRequestObject.getHolidayslist();
			CalendarHolidays calendar = holidays.get(0);
			Integer id = calendar.getHolidayId();
			Optional<CalendarHolidays> findById = holidaysRepo.findById(id);

			if (findById.isPresent() &&findById.get().getHolidayId() != null) {
				holidaysRepo.save(calendar);
				response.setStatusCode("200");
				response.setMessage("holiday updated successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("409");
			response.setMessage("failed to update");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (holidaysRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new CalendarHolidaysResponse();
			Integer id = holidaysRequestObject.getHolidayslist().get(0).getHolidayId();
			CalendarHolidays status = holidaysRepo.getOne(id);
			status.setStatus(!status.getStatus());
			CalendarHolidays data = holidaysRepo.save(status);

			if (data.getHolidayId() != null) {
				response.setStatusCode("200");
				response.setMessage("holiday deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("409");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getHolidays(CalendarHolidaysRequest holidaysRequestObject) {
		List<CalendarHolidays> list = holidaysRequestObject.getHolidayslist();
		logger.debug(" getAll holidays details");
		CalendarHolidaysResponse response = null;
		List<CalendarHolidays> getAll = null;

		if (holidaysRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = holidaysRepo.findAll();
			if (getAll.isEmpty()) {
				response = new CalendarHolidaysResponse();
				response.setHolidayslist(new ArrayList<CalendarHolidays>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			response = new CalendarHolidaysResponse();
			response.setHolidayslist(getAll);
			response.setMessage("success");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (holidaysRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getHolidayId() != null) {

			for (CalendarHolidays details : list) {

				if (details.getHolidayId() != null) {
					Integer id = holidaysRequestObject.getHolidayslist().get(0).getHolidayId();

					ArrayList<CalendarHolidays> holidayslist = new ArrayList<>();

					CalendarHolidays getById = holidaysRepo.findById(id).orElse(new CalendarHolidays());

					holidayslist.add(getById);

					if (getById != null && getById.getHolidayId() != null) {
						response = new CalendarHolidaysResponse();
						response.setHolidayslist(holidayslist);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
					response = new CalendarHolidaysResponse();
					response.setStatusCode("409");
					response.setMessage("please provide valid id");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}