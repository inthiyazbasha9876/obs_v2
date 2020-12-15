package com.ojas.obs.facade;

import static com.ojas.obs.constants.ResignationConstants.GETALL;
import static com.ojas.obs.constants.ResignationConstants.GETBYID;
import static com.ojas.obs.constants.ResignationConstants.SAVE;
import static com.ojas.obs.constants.ResignationConstants.UPDATE;
import static com.ojas.obs.constants.ResignationConstants.UPDATESTATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.ResignationDaoInterface;
import com.ojas.obs.errormessage.ErrorResponse;
import com.ojas.obs.model.BasicInfoModel;
import com.ojas.obs.model.Resignation;
import com.ojas.obs.request.ResignationRequest;
import com.ojas.obs.response.ResignationResponse;

@Service
public class ResignationFacadeImpl implements ResignationFacadeInterface {

	@Autowired
	ResignationDaoInterface resignationDaoInterface;

	@Autowired
	private Environment environment;
	ResignationResponse resp = null;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	List<String> managerMailIdList1 = new ArrayList<>();

	@Override
	public ResponseEntity<Object> setResignation(ResignationRequest resignationRequest) {

		List<BasicInfoModel> basicInfoList = resignationDaoInterface
				.getByBasicInfoEmpId(resignationRequest.getResignation().get(0).getEmployeeId());

		logger.info("This is basic info list based on resignation Email id");

		String reportingManagerEmpId = basicInfoList.get(0).getReportingManager();// use manager employee id

		managerMailIdList1 = resignationDaoInterface.getByContactId1(reportingManagerEmpId,
				resignationRequest.getResignation().get(0).getEmployeeId());
		logger.info("in facade Email id getting :");
		resignationRequest.getResignation().get(0).setEmailId1(managerMailIdList1.get(0));
		resignationRequest.getResignation().get(0).setEmailId2(managerMailIdList1.get(1));

		try {

			if (resignationRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				boolean n = resignationDaoInterface.saveResignation(resignationRequest);

				if (n) {
					resp = new ResignationResponse();
					resp.setMessage("Successfully record added");
					resp.setStatusCode("200");
					sendMail(resignationRequest.getResignation().get(0).getLeavingReason());
					return new ResponseEntity<>(resp, HttpStatus.OK);

				} else {
					resp = new ResignationResponse();
					resp.setMessage("record not saved");
					resp.setStatusCode("200");
					return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
				}
			}
			if (resignationRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
				boolean n = resignationDaoInterface.updateResignation(resignationRequest);
				List<Resignation> resignation = resignationRequest.getResignation();
				if (n) {
					resp = new ResignationResponse();
					resp.setMessage("Successfully record updated");
					resp.setStatusCode("200");
					resp.setResignationList(resignation);
					sendMail(resignationRequest.getResignation().get(0).getLeavingReason());
					return new ResponseEntity<>(resp, HttpStatus.OK);

				} else {
					resp = new ResignationResponse();
					resp.setMessage("record not updated");
					resp.setStatusCode("200");
					return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
				}
			}
			if(resignationRequest.getTransactionType().equalsIgnoreCase(UPDATESTATE)) {
				boolean n = resignationDaoInterface.updateStateResignation(resignationRequest);
				List<Resignation> resignation = resignationRequest.getResignation();
				if(n) {
					resp = new ResignationResponse();
					resp.setMessage("Successfully state record updated");
					resp.setStatusCode("200");
					resp.setResignationList(resignation);
					sendMail(resignationRequest.getResignation().get(0).getLeavingReason());
					return new ResponseEntity<>(resp, HttpStatus.OK);
				}
			}
					
		} catch (DuplicateKeyException e) {

			ErrorResponse error = new ErrorResponse();

			error.setStatusMessage(e.getMessage());
			error.setMessage("Duplicates are not allowed.");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

		} catch (Exception exception) {

			ErrorResponse error = new ErrorResponse();

			error.setStatusMessage(exception.getMessage());
			error.setMessage("Exception Occuerd");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		return null;

	}

	@Override
	public ResponseEntity<Object> getResignation(ResignationRequest resignationRequest) {

		try {
			resp = new ResignationResponse();
			logger.info("in facade class");
			if (resignationRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
				List<Resignation> list = resignationDaoInterface.getAll(resignationRequest);

				logger.info("list of records in facade -------------");
				if (null == list || list.isEmpty()) {
					resp.setMessage("no records found");
					resp.setStatusCode(HttpStatus.NO_CONTENT.toString());
					resp.setResignationList(list);
					logger.info("list of records in facade");
					return new ResponseEntity<>(resp, HttpStatus.OK);
				} else {
					resp.setMessage("success");
					resp.setStatusCode("200");
					resp.setResignationList(list);
					logger.info("list of records in facade success");
					return new ResponseEntity<>(resp, HttpStatus.OK);
				}
			}

			if (resignationRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
				List<Resignation> list = null;

				if (resignationRequest.getResignation().get(0).getEmployeeId() != null
						||!(resignationRequest.getResignation().get(0).getEmployeeId().isEmpty())) {
					list = resignationDaoInterface.getById(resignationRequest.getResignation().get(0).getEmployeeId());
				} else {
					list = resignationDaoInterface
							.getByEmpId(resignationRequest.getResignation().get(0).getEmployeeId());
				}

				if (null == list || list.isEmpty()) {
					resp.setMessage("no records found");
					resp.setStatusCode(HttpStatus.NO_CONTENT.toString());
					resp.setResignationList(list);
					return new ResponseEntity<>(resp, HttpStatus.OK);
				} else {
					resp.setMessage("success");
					resp.setStatusCode("200");
					resp.setResignationList(list);
					return new ResponseEntity<>(resp, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Experience occured");
			error.setStatusMessage(e.getMessage());
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		return null;
	}
	
	private void sendMail(String reason) throws MessagingException {
		Properties prop = new Properties();
		prop.put(environment.getProperty("smtphost"), environment.getProperty("mailType"));
		prop.put(environment.getProperty("mailport"), environment.getProperty("mailportNo"));
		prop.put(environment.getProperty("mailauth"), environment.getProperty("mailauthboolean"));
		prop.put(environment.getProperty("mailsmtpstarttls"),
				environment.getProperty("mailstarttlsenable")); // TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(environment.getProperty("mailusername"),
						environment.getProperty("mailpassword"));
			}
		});

			String str = "";
			for (String string : managerMailIdList1) {

				str = str + string + ",";
			}
			str = str + environment.getProperty("it-mail");
			logger.info(str);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(environment.getProperty("mailusername")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(str)

			);
			message.setSubject("Testing OBS-HRMS");
			message.setText("Dear OBS Team," + "\n\n Test for OBS-HRMS resignation module - Please ignore"
					+ reason);

			Transport.send(message);

			logger.info("Done");
	}

}
