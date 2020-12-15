package com.ojas.obs.tms.facadeimpl;

import static com.ojas.obs.tms.constants.UtilConstants.GETALL;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYEMP;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYEMPID;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYID;
import static com.ojas.obs.tms.constants.UtilConstants.GETDATES;
import static com.ojas.obs.tms.constants.UtilConstants.GETFILE;
import static com.ojas.obs.tms.constants.UtilConstants.NORECORDS;
import static com.ojas.obs.tms.constants.UtilConstants.RECORDUPDATE;
import static com.ojas.obs.tms.constants.UtilConstants.SAVE;
import static com.ojas.obs.tms.constants.UtilConstants.SUBMIT;
import static com.ojas.obs.tms.constants.UtilConstants.UPDATE;
import static com.ojas.obs.tms.constants.UtilConstants.GETHOURS;
import static com.ojas.obs.tms.constants.UtilConstants.GETBYPROJECT;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ojas.obs.tms.facade.FeignService;
import com.ojas.obs.tms.facade.TmsFacade;
import com.ojas.obs.tms.model.Record;
import com.ojas.obs.tms.model.TimeSheet;
import com.ojas.obs.tms.model.TimesheetStatus;
import com.ojas.obs.tms.repositoriesimpl.RecordRepositoryImpl;
import com.ojas.obs.tms.repositoriesimpl.StatusRepositoryImpl;
import com.ojas.obs.tms.repositoriesimpl.TimesheetRepositoryImpl;
import com.ojas.obs.tms.request.TmsRequest;
import com.ojas.obs.tms.response.TmsResponse;

import feign.Response;

/**
 * @author Manohar
 *
 */

@Service
public class TmsFacadeImpl implements TmsFacade {

	@Autowired
	private TimesheetRepositoryImpl sheetRepository;
	@Autowired
	private StatusRepositoryImpl statusRepo;
	@Autowired
	private RecordRepositoryImpl recordRepo;
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	@Autowired
	private Environment env;
	@Autowired
	private FeignService service;

	Logger logger = Logger.getLogger(TmsFacadeImpl.class);

	@Override
	public ResponseEntity<Object> setTimesheet(TmsRequest request) throws SQLException, IOException {
		TmsResponse response = new TmsResponse();
		logger.debug("Request in facade : " + request);
		if (request.getTransactionType().equalsIgnoreCase(SUBMIT)) {
			TimeSheet sheet = request.getSheet();
			sheet.setRecord(request.getRecordsList());
			request.getStatus().setSheet(sheet);
			sheet.setTimesheetStatus(request.getStatus());
			uploadFile(sheet);
			boolean saved = sheetRepository.submitTimesheet(sheet);
			if (!saved) {
				response.setMessage("Failed to submit Timesheet");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			sendMail(sheet.getEmployeeId(), sheet.getReportingMngr(), sheet.getTimesheetStatus().getSubmissionState(), sheet.getTimeSheetStartDate(), sheet.getTimesheetStatus().getComment());
			response.setMessage("Timesheet submitted successfully");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (request.getTransactionType().equalsIgnoreCase(SAVE)) {
			TimeSheet sheet = request.getSheet();
			sheet.setRecord(request.getRecordsList());
			request.getStatus().setSheet(sheet);
			sheet.setTimesheetStatus(request.getStatus());
			boolean saved = sheetRepository.saveTimesheet(sheet);
			if (!saved) {
				response.setMessage("Failed to save Timesheet");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			response.setMessage("Record saved successfully");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (request.getTransactionType().equalsIgnoreCase(UPDATE)) {
			TimesheetStatus status = request.getStatus();
			boolean update = statusRepo.updateStatus(status);
			if (!update) {
				response.setMessage("Failed to update status");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			sendMail(request.getSheet().getEmployeeId(), request.getSheet().getReportingMngr(),
					status.getSubmissionState(), request.getSheet().getTimeSheetStartDate(), status.getComment());
			response.setMessage("Status updated successfully");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (request.getTransactionType().equalsIgnoreCase(RECORDUPDATE)) {
			Iterator<Record> itr = request.getRecordsList().iterator();
			Record rec = itr.next();
			boolean update = recordRepo.updateRecord(rec);
			if (!update) {
				response.setMessage("Failed to update record");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			response.setMessage("Record updated successfully");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setMessage("Failed to process your request");
		response.setStatusCode("409");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity<Object> getTimesheet(TmsRequest request)
			throws SQLException, IOException, URISyntaxException {
		List<TimeSheet> fetchedSheets = new ArrayList<>();
		TmsResponse response = new TmsResponse();
		if (request.getTransactionType().equalsIgnoreCase(GETBYID)) {
			fetchedSheets = sheetRepository.getById(request.getSheet().getTimeSheetID());
			return validateResult(fetchedSheets);
		}
		if (request.getTransactionType().equalsIgnoreCase(GETBYEMPID)) {
			fetchedSheets = sheetRepository.getByEmpId(request.getSheet().getEmployeeId(),
					request.getSheet().getTimeSheetStartDate());
			return validateResult(fetchedSheets);

		}
		if (request.getTransactionType().equalsIgnoreCase(GETBYEMP)) {
			fetchedSheets = sheetRepository.getByEmp(request.getSheet().getEmployeeId());
			return validateResult(fetchedSheets);
		}
		if (request.getTransactionType().equalsIgnoreCase(GETDATES)) {
			fetchedSheets = sheetRepository.getByEmpAndStatus(request.getSheet().getEmployeeId());
			return validateResult(fetchedSheets);
		}
		if (request.getTransactionType().equalsIgnoreCase(GETALL)) {
			fetchedSheets = sheetRepository.getTimesheets();
			return validateResult(fetchedSheets);
		}
		if (request.getTransactionType().equalsIgnoreCase(GETHOURS)) {
			List<Record> recList = null;
			recList =  sheetRepository.getHours(request.getSheet().getProjectId(), request.getSheet().getStartDate(), request.getSheet().getEndDate());
			logger.debug("Fetched record : " + recList);
			return validateRecords(recList);
		}
		if (request.getTransactionType().equalsIgnoreCase(GETBYPROJECT)) {
			List<Record> recList = null;
			recList =  sheetRepository.findByProjId(request.getSheet().getProjectId(), request.getSheet().getEmployeeId(), request.getSheet().getStartDate(), request.getSheet().getEndDate());
			logger.debug("Fetched record : " + recList);
			return validateRecords(recList);
		}
		if (request.getTransactionType().equalsIgnoreCase(GETFILE)) {
			String filePath = null;
			filePath = sheetRepository.getFile(request.getSheet().getTimeSheetID());
			if (filePath == null) {
				response.setMessage(NORECORDS);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			byte[] bFile = Files.readAllBytes(new File(filePath).toPath());
			String file = Base64.getEncoder().encodeToString(bFile);
			TimeSheet sheet = new TimeSheet();
			sheet.setAttachment(file);
			fetchedSheets.add(sheet);
		}
		response.setTimesheetList(fetchedSheets);
		response.setMessage("Records fetched successfully");
		response.setStatusCode("200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void uploadFile(TimeSheet sheet) throws IOException {
		if (sheet.getAttachment() != null && !sheet.getAttachment().trim().isEmpty()) {
			byte[] bytes = Base64.getDecoder().decode(sheet.getAttachment());
			String[] filenames = sheet.getFileName().split("\\.");
			Path path = Paths.get(env.getProperty("file_directory") + sheet.getFileName());
			Path savedPath = null;
			int count = 1;
			while (path.toFile().exists()) {
				path = Paths.get(env.getProperty("file_directory") + filenames[0] + count++ + "." + filenames[1]);
			}
			savedPath = Files.write(path, bytes, StandardOpenOption.CREATE);
			sheet.setFilePath(savedPath.toUri().getPath());
		}
	}

	private void sendMail(String empId, String mngr, String status, String start, String reason) throws IOException {
		Response obj = service.getEmployees("{\"employeeInfo\":[{\"reportingManager\":" + mngr + ",\"employeeId\":"
				+ empId + "}],\"transactionType\":\"getEmailId\"}");
		LocalDate end = LocalDate.parse(start).plusDays(6);
		if (obj.body() != null) {
			String data = org.apache.commons.io.IOUtils.toString(obj.body().asReader());
			JsonObject jObj = new JsonParser().parse(data).getAsJsonObject();
			JsonArray jArr = jObj.getAsJsonArray("list");
			String msg = "";
			if ("Pending".equalsIgnoreCase(status))
				msg = env.getProperty("submit");
			if ("Approved".equalsIgnoreCase(status))
				msg = env.getProperty("accept");
			if ("Rejected".equalsIgnoreCase(status))
				msg = "has been rejected due to "+reason+ ". " + env.getProperty("reject");
			if (!jArr.isJsonNull() && (jArr.get(0) != null || jArr.get(1) != null)) {
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(env.getProperty("spring.mail.username"));
				mailMessage.setTo(jArr.get(0).getAsString());
				mailMessage.setCc(jArr.get(1).getAsString());
				mailMessage.setSubject(env.getProperty("subject"));
				mailMessage.setText("Hi!\n\tTimesheet from "+start + " to "+ end+ " " + msg);
				javaMailSender.send(mailMessage);
			}
		}
		obj.close();
	}
	private ResponseEntity<Object> validateRecords(List<Record> result) {
		TmsResponse response = new TmsResponse();
		if(result.isEmpty()) {
			response.setMessage("No records found!");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setMessage("Records fetched successfully!");
		response.setRecordList(result);
		response.setStatusCode("200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	private ResponseEntity<Object> validateResult(List<TimeSheet> resultList) {
		TmsResponse response = new TmsResponse();
		if (resultList.isEmpty()) {
			response.setMessage(NORECORDS);
			response.setStatusCode("409");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		response.setTimesheetList(resultList);
		response.setMessage("Records fetched successfully");
		response.setStatusCode("200");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
