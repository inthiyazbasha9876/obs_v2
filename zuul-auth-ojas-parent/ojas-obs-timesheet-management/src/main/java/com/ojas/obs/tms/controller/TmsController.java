package com.ojas.obs.tms.controller;

import static com.ojas.obs.tms.constants.UrlConstants.GET;
import static com.ojas.obs.tms.constants.UrlConstants.SET;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYEMP;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYEMPID;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYID;
import static com.ojas.obs.tms.constants.UtilConstants.GETFILE;
import static com.ojas.obs.tms.constants.UtilConstants.GETDATES;
import static com.ojas.obs.tms.constants.UtilConstants.INVALID;
import static com.ojas.obs.tms.constants.UtilConstants.RECORDUPDATE;
import static com.ojas.obs.tms.constants.UtilConstants.SAVE;
import static com.ojas.obs.tms.constants.UtilConstants.SUBMIT;
import static com.ojas.obs.tms.constants.UtilConstants.UPDATE;
import static com.ojas.obs.tms.constants.UtilConstants.GETHOURS;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYPROJECT;

import java.lang.reflect.Field;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ojas.obs.tms.facade.TmsFacade;
import com.ojas.obs.tms.model.Record;
import com.ojas.obs.tms.model.TimeSheet;
import com.ojas.obs.tms.model.TimesheetStatus;
import com.ojas.obs.tms.request.TmsRequest;
import com.ojas.obs.tms.response.ErrorResponse;

/**
 * @author Manohar
 *
 */

//@RequestMapping("/obs/tms")
@Controller
public class TmsController {
	@Autowired
	private TmsFacade tmsFacade;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setTimeSheet(@RequestBody TmsRequest request, HttpServletRequest req,
			HttpServletResponse res) {
		if (request == null || request.getTransactionType() == null || request.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(INVALID);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			if (request.getTransactionType().equalsIgnoreCase(SUBMIT) && request.getSheet() != null) {
				TimeSheet sheet = request.getSheet();
				TimesheetStatus status = request.getStatus();
				String nullMsg = validateSheet(sheet);
				if (nullMsg != null) {
					logger.error(nullMsg);
					ErrorResponse error = new ErrorResponse();
					error.setMessage(nullMsg);
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				String nullRecords = validateRecords(request);
				if (nullRecords != null) {
					logger.error(nullRecords);
					ErrorResponse error = new ErrorResponse();
					error.setMessage(nullRecords);
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}

				if (status == null || status.getSubmissionState() == null) {
					logger.error("Submission status is null");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("Submission status must not be null");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				return tmsFacade.setTimesheet(request);
			}
			if ((request.getTransactionType().equalsIgnoreCase(SAVE)
					|| request.getTransactionType().equalsIgnoreCase(RECORDUPDATE))
					&& request.getRecordsList() != null) {
				String nullRecords = validateRecords(request);
				if (nullRecords != null) {
					logger.error(nullRecords);
					ErrorResponse error = new ErrorResponse();
					error.setMessage(nullRecords);
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				return tmsFacade.setTimesheet(request);
			}

			if (request.getTransactionType().equalsIgnoreCase(UPDATE) || request.getStatus() == null
					|| request.getStatus().getSubmissionState() == null || request.getStatus().getStatusId() == null) {
				return tmsFacade.setTimesheet(request);
			}
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(INVALID);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getTimeSheet(@RequestBody TmsRequest request, HttpServletRequest req,
			HttpServletResponse res) {
		if (request == null || request.getTransactionType() == null || request.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(INVALID);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			TimeSheet sheet = request.getSheet();
			if (((request.getTransactionType().equalsIgnoreCase(GETBYID)
					|| request.getTransactionType().equalsIgnoreCase(GETFILE)) && sheet.getTimeSheetID() == null)
					|| ((request.getTransactionType().equalsIgnoreCase(GETBYEMP)
							|| request.getTransactionType().equalsIgnoreCase(GETDATES))
							&& sheet.getEmployeeId() == null)
					|| (request.getTransactionType().equalsIgnoreCase(GETBYEMPID) && sheet.getEmployeeId() == null
							&& sheet.getTimeSheetStartDate() == null)
					|| (request.getTransactionType().equalsIgnoreCase(GETHOURS)
							&& (request.getSheet().getProjectId() == null || request.getSheet().getStartDate() == null
									|| request.getSheet().getEndDate() == null))
					|| (request.getTransactionType().equalsIgnoreCase(GETBYPROJECT)
							&& (request.getSheet().getProjectId() == null || request.getSheet().getEmployeeId() == null
									|| request.getSheet().getStartDate() == null
									|| request.getSheet().getEndDate() == null))) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Timesheet ID must not be null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return tmsFacade.getTimesheet(request);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	private String validateSheet(TimeSheet sheet) throws IllegalAccessException {
		String msg = null;
		for (Field f : sheet.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.getName().equalsIgnoreCase("timesheetStatus") || f.getName().equalsIgnoreCase("record")
					|| f.getName().equalsIgnoreCase("filePath") || f.getName().equalsIgnoreCase("attachment")
					|| f.getName().equalsIgnoreCase("fileName") || f.getName().equalsIgnoreCase("projectId")
					|| f.getName().equalsIgnoreCase("startDate") || f.getName().equalsIgnoreCase("endDate")) {
				continue;
			}
			Object obj = f.get(sheet);
			if (obj == null || obj.toString().trim().isEmpty()) {
				msg = f.getName() + " value is empty!";
				return msg;
			}
		}
		return msg;
	}

	private String validateRecords(TmsRequest request) throws IllegalAccessException {
		String msg = null;
		Set<Record> records = request.getRecordsList();
		for (Record rec : records) {
			for (Field f : rec.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.getName().equalsIgnoreCase("sheet") || f.getName().equalsIgnoreCase("employeeId")
						|| f.getName().equalsIgnoreCase("hours")
						|| (request.getTransactionType().equalsIgnoreCase(SAVE)
								&& (f.getName().equalsIgnoreCase("recordId")
										|| f.getName().equalsIgnoreCase("hoursLogged")
										|| f.getName().equalsIgnoreCase("hours")))) {
					continue;
				}
				Object obj = f.get(rec);
				if (obj == null || obj.toString().trim().isEmpty()) {
					msg = f.getName() + " value is empty!";
					return msg;
				}
			}
		}

		return msg;
	}
}
