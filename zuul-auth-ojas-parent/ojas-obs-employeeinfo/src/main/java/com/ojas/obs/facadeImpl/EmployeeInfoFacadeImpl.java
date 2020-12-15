package com.ojas.obs.facadeImpl;


import static com.ojas.obs.constants.URLconstants.GETALL;
import static com.ojas.obs.constants.URLconstants.GETALLINFO;
import static com.ojas.obs.constants.URLconstants.GETBYID;
import static com.ojas.obs.constants.URLconstants.GETREPORTIES;
import static com.ojas.obs.constants.UserConstants.DELETE;
import static com.ojas.obs.constants.UserConstants.FAILED;
import static com.ojas.obs.constants.UserConstants.GETEMAILID;
import static com.ojas.obs.constants.UserConstants.PICUPDATE;
import static com.ojas.obs.constants.UserConstants.SAVE;
import static com.ojas.obs.constants.UserConstants.SUCCESS;
import static com.ojas.obs.constants.UserConstants.UPDATE;
import static com.ojas.obs.constants.URLconstants.GETSKILLSBYEMPINFO;
import static com.ojas.obs.constants.UserConstants.UPDATESTATUS;

import java.sql.SQLException;
import java.util.ArrayList;
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

import com.ojas.obs.dao.EmployeeInfoDao;
import com.ojas.obs.facade.EmployeeInfoFacade;
import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.model.EmployeeSkills;
import com.ojas.obs.request.EmployeeInfoRequest;
import com.ojas.obs.response.EmployeeInfoResponse;

/**
 * 
 * @author sdileep
 *
 */
@Service
public class EmployeeInfoFacadeImpl implements EmployeeInfoFacade {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%&*";

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EmployeeInfoDao employeeInfoDao;
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	@Autowired
	private Environment env;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.facade.EmployeeInfoFacade#saveEmployeeInfo(com.ojas.obs.request.
	 * EmployeeInfoRequest)
	 */

	@Override
	public ResponseEntity<Object> setEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException {

		logger.debug("inside saveEmployee method : " + employeeInfoRequest);
		EmployeeInfoResponse empInfoResponse = null;

		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			empInfoResponse = new EmployeeInfoResponse();
			employeeInfoRequest.getEmployeeInfo().get(0).setPassword(randomAlphaNumeric());
			boolean saveEmployee = employeeInfoDao.saveEmployeeInfo(employeeInfoRequest);
			logger.debug("employeefacadeImpl employee saved : " + saveEmployee);
			if (!saveEmployee) {
				empInfoResponse.setStatusCode("409");
				empInfoResponse.setMessage(FAILED);
				return new ResponseEntity<>(empInfoResponse, HttpStatus.CONFLICT);
			}
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			EmployeeInfo info = employeeInfoRequest.getEmployeeInfo().get(0);
			String empMail = info.getEmail();
			String mngrId = info.getReportingManager();
			String mngrMail = employeeInfoDao.getMngrMail(mngrId);
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(empMail);
			mailMessage.setCc(mngrMail);
			mailMessage.setSubject("Welcome to Ojas family");
			mailMessage.setText("Dear " + info.getFirstname()
					+ "! \n\n \tPlease login to OBS and update your onboarding formalities. \n" + "\nurl : "
					+ env.getProperty("obsUrl") + "\nUsername : " + info.getEmployeeId() + "\nPassword : "
					+ info.getPassword());
			javaMailSender.send(mailMessage);
			empInfoResponse.setMessage("Successfully record added");
			empInfoResponse.setStatusCode("200");
			return new ResponseEntity<>(empInfoResponse, HttpStatus.OK);
		}

		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			empInfoResponse = new EmployeeInfoResponse();

			boolean updateEmployee = employeeInfoDao.updateEmployeeInfo(employeeInfoRequest);
			logger.info("employeefacadeImpl employee updated : " + updateEmployee);
			if (!updateEmployee) {
				empInfoResponse.setMessage(FAILED);
				empInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(empInfoResponse, HttpStatus.CONFLICT);
			}
			empInfoResponse.setStatusCode("200");
			empInfoResponse.setMessage("Successfully record updated");
			return new ResponseEntity<>(empInfoResponse, HttpStatus.OK);
		}
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			empInfoResponse = new EmployeeInfoResponse();
			boolean deleteEmployeeRecord = employeeInfoDao.deleteEmployeeInfo(employeeInfoRequest);
			logger.debug("inside  delete condition");
			if (!deleteEmployeeRecord) {
				empInfoResponse.setMessage(FAILED);
				empInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(empInfoResponse, HttpStatus.CONFLICT);
			}
			empInfoResponse.setStatusCode("200");
			empInfoResponse.setMessage("Successfully record deleted");
			return new ResponseEntity<>(empInfoResponse, HttpStatus.OK);
		}
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(PICUPDATE)) {
			empInfoResponse = new EmployeeInfoResponse();
			boolean deleteEmployeeRecord = employeeInfoDao.picUpload(employeeInfoRequest.getEmployeeInfo().get(0));
			logger.debug("inside  delete condition : " + deleteEmployeeRecord);
			if (!deleteEmployeeRecord) {
				empInfoResponse.setMessage(FAILED);
				empInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(empInfoResponse, HttpStatus.CONFLICT);
			}
			empInfoResponse.setStatusCode("200");
			empInfoResponse.setMessage("Profile pic updated successfully!");
			return new ResponseEntity<>(empInfoResponse, HttpStatus.OK);

		}
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(UPDATESTATUS)) {
			empInfoResponse = new EmployeeInfoResponse();
			EmployeeInfo info = employeeInfoRequest.getEmployeeInfo().get(0);
			info.setFlag(true);
			if (info.getStatus().equalsIgnoreCase("Absconded") || info.getStatus().equalsIgnoreCase("Terminated")
					|| info.getStatus().equalsIgnoreCase("Inactive")) {
				info.setFlag(false);
			}
			boolean deleteEmployeeRecord = employeeInfoDao.updateStatus(info);
			logger.debug("inside  delete condition : " + deleteEmployeeRecord);
			if (!deleteEmployeeRecord) {
				empInfoResponse.setMessage(FAILED);
				empInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(empInfoResponse, HttpStatus.CONFLICT);
			}
			empInfoResponse.setStatusCode("200");
			empInfoResponse.setMessage("Employee status updated successfully!");
			return new ResponseEntity<>(empInfoResponse, HttpStatus.OK);

		}
		return new ResponseEntity<>(empInfoResponse, HttpStatus.CONFLICT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.facade.EmployeeInfoFacade#getAllEmployeeDetails(com.ojas.obs.
	 * request.EmployeeInfoRequest)
	 */

	@Override
	public ResponseEntity<Object> getAllEmployeeDetails(EmployeeInfoRequest employeeInfoRequest) throws SQLException {

		logger.debug("Inside getAllEmployeeDetails in EmployeeInfoFacade.");
		EmployeeInfoResponse employeeResponse = new EmployeeInfoResponse();
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			logger.debug("Inside getall in facade");
			List<EmployeeInfo> allEmployeeDetails = employeeInfoDao.getAllEmployeeDetails(employeeInfoRequest);
			logger.info("Inside getall in facade all employeedetails : " + allEmployeeDetails);
			if (allEmployeeDetails != null) {
				employeeResponse.setEmployeeInfo(allEmployeeDetails);
				employeeResponse.setMessage(SUCCESS);
				employeeResponse.setStatusCode("200");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
			employeeResponse.setEmployeeInfo(new ArrayList<>());
			employeeResponse.setStatusCode("200");
			employeeResponse.setMessage("No records found");
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		}
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(GETALLINFO)) {
			logger.debug("Inside getall in facade");
			List<EmployeeInfo> allEmployeeDetails = employeeInfoDao.getAllEmployeeInfos();
			logger.info("Inside getall in facade all employeedetails : " + allEmployeeDetails);
			if (allEmployeeDetails != null) {
				employeeResponse.setEmployeeInfo(allEmployeeDetails);
				employeeResponse.setMessage(SUCCESS);
				employeeResponse.setStatusCode("200");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
			employeeResponse.setEmployeeInfo(new ArrayList<>());
			employeeResponse.setStatusCode("200");
			employeeResponse.setMessage("No records found");
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		}
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			logger.debug("Inside getbyid in facade");
			List<EmployeeInfo> allEmployeeDetails = employeeInfoDao.getById(employeeInfoRequest);
			logger.info("Fetched employee list : " + allEmployeeDetails);
			employeeResponse.setEmployeeInfo(allEmployeeDetails);
			employeeResponse.setMessage(SUCCESS);
			employeeResponse.setStatusCode("200");
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		}
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(GETREPORTIES)) {
			logger.debug("Inside getbyid in facade");
			List<EmployeeInfo> allEmployeeDetails = employeeInfoDao.getReporties(employeeInfoRequest);
			logger.info("Fetched employee list : " + allEmployeeDetails);
			employeeResponse.setEmployeeInfo(allEmployeeDetails);
			employeeResponse.setMessage(SUCCESS);
			employeeResponse.setStatusCode("200");
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		}
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(GETSKILLSBYEMPINFO)) {
			List<EmployeeSkills> getskills = employeeInfoDao.getSkillsByEmpInfo(employeeInfoRequest.getEmployeeskills().getSkills());
		
			if (!getskills.isEmpty()) {
				employeeResponse = new EmployeeInfoResponse();
				employeeResponse.setEmplist(getskills);
				employeeResponse.setMessage("success");
				employeeResponse.setStatusCode("200");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
		}

		List<String> emailIDs = null;
		if (employeeInfoRequest.getTransactionType().equalsIgnoreCase(GETEMAILID)) {
			logger.debug("Inside get email id in facade");
			EmployeeInfo info = employeeInfoRequest.getEmployeeInfo().get(0);
			emailIDs = employeeInfoDao.getEmails(info.getEmployeeId(), info.getReportingManager());
			logger.debug("Fetched mail id's : " + emailIDs.toString());
			employeeResponse.setList(emailIDs);
			employeeResponse.setMessage(SUCCESS);
			employeeResponse.setStatusCode("200");
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		}
		return new ResponseEntity<>(employeeResponse, HttpStatus.CONFLICT);

	}

	public static String randomAlphaNumeric() {
		int count = 8;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}


}
