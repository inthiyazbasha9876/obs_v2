package com.ojas.obs.emp_edu.facadeImpl;

import static com.ojas.obs.emp_edu.utility.Constants.DELETE;
import static com.ojas.obs.emp_edu.utility.Constants.ID_NULL;
import static com.ojas.obs.emp_edu.utility.Constants.PENDING;
import static com.ojas.obs.emp_edu.utility.Constants.PENDING_MESSAGE;
import static com.ojas.obs.emp_edu.utility.Constants.SAVE;
import static com.ojas.obs.emp_edu.utility.Constants.SAVE_SUCCESS_MESSAGE;
import static com.ojas.obs.emp_edu.utility.Constants.STATUS_UPDATE;
import static com.ojas.obs.emp_edu.utility.Constants.TRANSACTIONTYPE_NULL;
import static com.ojas.obs.emp_edu.utility.Constants.UPDATE;
import static com.ojas.obs.emp_edu.utility.Constants.UPDATE_FAILED_MESSAGE;
import static com.ojas.obs.emp_edu.utility.Constants.VALID_TRANSACTIONTYPE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ojas.obs.emp_edu.dao.EmployeeEducationDetailsDao;
import com.ojas.obs.emp_edu.facade.EmployeeEducationFacade;
import com.ojas.obs.emp_edu.model.EmployeeEducationDetails;
import com.ojas.obs.emp_edu.model.EmployeeEducationDetailsRequest;
import com.ojas.obs.emp_edu.model.EmployeeEducationResponse;
import com.ojas.obs.emp_edu.model.ErrorResponse;

/**
 * @author vraghuram
 *
 */
@Component
public class EmployeeEducationDetailsFacadeImpl implements EmployeeEducationFacade {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Autowired
	EmployeeEducationDetailsDao employeeEducationDetailsDaoImpl;
	ResponseEntity<Object> responseEntity = null;

// set cluster transaction for EmployeeEducationDetails
	@Override
	public ResponseEntity<Object> setEmployeeEducationDetails(EmployeeEducationDetailsRequest emplEduDetailsRequestObj)
			throws SQLException {
		if (null != emplEduDetailsRequestObj.getTransactionType()) {
			if (emplEduDetailsRequestObj.getTransactionType().equalsIgnoreCase(SAVE)) {
				return this.saveTransaction(emplEduDetailsRequestObj);
			} else if (emplEduDetailsRequestObj.getTransactionType().equalsIgnoreCase(UPDATE)) {
				return this.updateTransaction(emplEduDetailsRequestObj);
			} else if (emplEduDetailsRequestObj.getTransactionType().equalsIgnoreCase(DELETE)) {
				return this.deleteTransaction(emplEduDetailsRequestObj);
			} else if (emplEduDetailsRequestObj.getTransactionType().equalsIgnoreCase(STATUS_UPDATE)) {
				return this.statusUpdateTransaction(emplEduDetailsRequestObj);
			} else {
				ErrorResponse error = new ErrorResponse();
				error.setStatuscode("422");
				error.setStatusMessage(VALID_TRANSACTIONTYPE);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("422");
			error.setStatusMessage(TRANSACTIONTYPE_NULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	// get cluster transaction for EmployeeEducationDetails
	@Override
	public ResponseEntity<Object> getEmployeeEducationDetails(EmployeeEducationDetailsRequest emplEduDetailsRequestObj)
			throws SQLException {

		EmployeeEducationResponse employeeEducationResponse = null;

		if (emplEduDetailsRequestObj.getTransactionType().equalsIgnoreCase("getAll")) {

			employeeEducationResponse = new EmployeeEducationResponse();
			List<EmployeeEducationDetails> allEducationDetails = employeeEducationDetailsDaoImpl
					.getEmployeeEducationDetails();

			if (allEducationDetails == null || allEducationDetails.isEmpty()) {
				employeeEducationResponse.setEmployeeEducationDetailsList(new ArrayList<>());
				employeeEducationResponse.setMessage("No records found");
				employeeEducationResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
			} else {
				employeeEducationResponse.setMessage("success");
				employeeEducationResponse.setStatusCode("200");
				employeeEducationResponse.setEmployeeEducationDetailsList(allEducationDetails);
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			}
		}

		if (emplEduDetailsRequestObj.getTransactionType().equalsIgnoreCase("getById")) {
			employeeEducationResponse = new EmployeeEducationResponse();
			List<EmployeeEducationDetails> list = null;
			String empId = emplEduDetailsRequestObj.getEmployeeEducationDetailsList().get(0).getEmployeeId();
			Integer id = emplEduDetailsRequestObj.getEmployeeEducationDetailsList().get(0).getId();
			if (id != null)
				list = employeeEducationDetailsDaoImpl.getEmployeeEducationDetailsById(emplEduDetailsRequestObj);
			else if (empId != null)
				list = employeeEducationDetailsDaoImpl.getDetailByEmpId(emplEduDetailsRequestObj);

			if (list == null) {
				employeeEducationResponse.setMessage("No record Present");
				employeeEducationResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
			} else {
				employeeEducationResponse.setMessage("success");
				employeeEducationResponse.setStatusCode("200");
				employeeEducationResponse.setEmployeeEducationDetailsList(list);
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			}
		}
		return null;
	}

	// id validations for update and delete and getbyid
	public List<EmployeeEducationDetails> getIdValidation(
			EmployeeEducationDetailsRequest employeeEducationDetailsRequest) {
		List<EmployeeEducationDetails> employeeEducationDetailslist = new ArrayList<>();
		for (EmployeeEducationDetails employeeEducationDetails : employeeEducationDetailsRequest
				.getEmployeeEducationDetailsList()) {
			if (null == employeeEducationDetails.getId()) {
				employeeEducationDetailslist.add(employeeEducationDetails);
			}
		}
		return employeeEducationDetailslist;
	}

	public List<EmployeeEducationDetails> getEmpIdValidation(
			EmployeeEducationDetailsRequest employeeEducationDetailsRequest) {
		List<EmployeeEducationDetails> employeeEducationDetailslist = new ArrayList<>();
		for (EmployeeEducationDetails employeeEducationDetails : employeeEducationDetailsRequest
				.getEmployeeEducationDetailsList()) {
			if (null == employeeEducationDetails.getEmployeeId()) {
				employeeEducationDetailslist.add(employeeEducationDetails);
			}
		}
		return employeeEducationDetailslist;
	}

	public ResponseEntity<Object> saveTransaction(EmployeeEducationDetailsRequest emplEduDetailsRequestObj)
			throws SQLException {
		for (EmployeeEducationDetails employeeEducationDetails : emplEduDetailsRequestObj
				.getEmployeeEducationDetailsList()) {
			employeeEducationDetails.setFlag(true);
		}
		int[] saveEmployeeEducationDetails = employeeEducationDetailsDaoImpl
				.saveEmployeeEducationDetails(emplEduDetailsRequestObj);

		int b = 0;
		for (int a : saveEmployeeEducationDetails) {
			if (a > 0) {
				b++;
			}
		}
		if (b == emplEduDetailsRequestObj.getEmployeeEducationDetailsList().size()) {
			String status = emplEduDetailsRequestObj.getEmployeeEducationDetailsList().get(0).getStatus();
			if (status.equalsIgnoreCase(PENDING)) {

				SimpleMailMessage mailMessage = new SimpleMailMessage();
				List<EmployeeEducationDetails> employeeEducationDetailsList = emplEduDetailsRequestObj
						.getEmployeeEducationDetailsList();
				employeeEducationDetailsList.get(0).getEmployeeId();
				String mailId = employeeEducationDetailsDaoImpl
						.getMailId(employeeEducationDetailsList.get(0).getEmployeeId());
				mailMessage.setFrom(env.getProperty("spring.mail.username"));
				mailMessage.setTo(mailId);
				mailMessage.setCc(env.getProperty("spring.mail.username"));
				mailMessage.setSubject("Welcome to Ojas Family");
				mailMessage
						.setText("HI this is your Employee Id [" + employeeEducationDetailsList.get(0).getEmployeeId()
								+ "] your educational documents are uploaded succefully");
				javaMailSender.send(mailMessage);
			}
			EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
			employeeEducationResponse.setStatusCode("200");
			employeeEducationResponse.setMessage(SAVE_SUCCESS_MESSAGE);
			return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
		} else {
			EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
			employeeEducationResponse.setStatusCode("409");
			employeeEducationResponse.setMessage("Employee Education Details has not saved with id is"
					+ emplEduDetailsRequestObj.getEmployeeEducationDetailsList().get(b + 1));
			return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
		}

	}

	public ResponseEntity<Object> updateTransaction(EmployeeEducationDetailsRequest emplEduDetailsRequestObj)
			throws SQLException {
		String msg = "Welcome to Ojas Family";
		String message = null;
		List<EmployeeEducationDetails> list = new ArrayList<>();
		List<EmployeeEducationDetails> idValidation = this.getIdValidation(emplEduDetailsRequestObj);
		if (idValidation.isEmpty()) {
			int[] updateEmployeeEducationDetails = employeeEducationDetailsDaoImpl
					.updateEmployeeEducationDetails(emplEduDetailsRequestObj);

			for (int a : updateEmployeeEducationDetails) {
				if (a <= 0) {
					list.add(emplEduDetailsRequestObj.getEmployeeEducationDetailsList().get(a));
				}
			}

			if (list.isEmpty()) {

				List<EmployeeEducationDetails> employeeEducationDetailsList = emplEduDetailsRequestObj
						.getEmployeeEducationDetailsList();
				String employeeId = employeeEducationDetailsList.get(0).getEmployeeId();
				String status = employeeEducationDetailsList.get(0).getStatus();
				String image = employeeEducationDetailsList.get(0).getImage();

				if (status.equalsIgnoreCase(PENDING) && image != null) {

					SimpleMailMessage mailMessage = new SimpleMailMessage();
					String mailId = employeeEducationDetailsDaoImpl.getMailId(employeeId);
					mailMessage.setFrom(env.getProperty("spring.mail.username"));
					mailMessage.setTo(mailId);
					mailMessage.setCc(env.getProperty("spring.mail.username"));
					mailMessage.setSubject(msg);
					mailMessage.setText("Hi ThiS is your Employee Id [" + employeeId
							+ "] your educational documents has been updated successfully.");
					javaMailSender.send(mailMessage);
					message = PENDING_MESSAGE;
				}

				EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
				employeeEducationResponse.setEmployeeEducationDetailsList(list);
				employeeEducationResponse.setStatusCode("200");
				employeeEducationResponse.setMessage(message);
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			} else {
				EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
				employeeEducationResponse.setEmployeeEducationDetailsList(list);
				employeeEducationResponse.setStatusCode("409");
				employeeEducationResponse.setMessage(UPDATE_FAILED_MESSAGE);
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.UNPROCESSABLE_ENTITY);

			}
		} else {
			EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
			employeeEducationResponse.setEmployeeEducationDetailsList(idValidation);
			employeeEducationResponse.setStatusCode("422");
			employeeEducationResponse.setMessage(ID_NULL);
			return new ResponseEntity<>(employeeEducationResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	// status update Tx

	public ResponseEntity<Object> statusUpdateTransaction(EmployeeEducationDetailsRequest emplEduDetailsRequestObj)
			throws SQLException {
		String msg = "Welcome to Ojas Family";
		List<EmployeeEducationDetails> list = new ArrayList<>();
		List<EmployeeEducationDetails> idValidation = this.getIdValidation(emplEduDetailsRequestObj);
		if (idValidation.isEmpty()) {
			int[] updateEmployeeEducationDetails = employeeEducationDetailsDaoImpl
					.statusUpdate(emplEduDetailsRequestObj);

			for (int a : updateEmployeeEducationDetails) {
				if (a <= 0) {
					list.add(emplEduDetailsRequestObj.getEmployeeEducationDetailsList().get(a));
				}
			}

			if (list.isEmpty()) {

				List<EmployeeEducationDetails> employeeEducationDetailsList = emplEduDetailsRequestObj
						.getEmployeeEducationDetailsList();
				String employeeId = employeeEducationDetailsList.get(0).getEmployeeId();
				String status = employeeEducationDetailsList.get(0).getStatus();
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				String mailId = employeeEducationDetailsDaoImpl.getMailId(employeeId);
				mailMessage.setFrom(env.getProperty("spring.mail.username"));
				mailMessage.setTo(mailId);
				mailMessage.setCc();
				mailMessage.setSubject(msg);
				mailMessage
						.setText("Hi this is Your employee Id [" + employeeId + "] your documents have been " + status);
				javaMailSender.send(mailMessage);
				EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
				employeeEducationResponse.setEmployeeEducationDetailsList(list);
				employeeEducationResponse.setStatusCode("200");
				employeeEducationResponse.setMessage("your documents have been " + status);
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			} else {
				EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
				employeeEducationResponse.setEmployeeEducationDetailsList(list);
				employeeEducationResponse.setStatusCode("409");
				employeeEducationResponse.setMessage(UPDATE_FAILED_MESSAGE);
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.UNPROCESSABLE_ENTITY);

			}
		} else {
			EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
			employeeEducationResponse.setEmployeeEducationDetailsList(idValidation);
			employeeEducationResponse.setStatusCode("422");
			employeeEducationResponse.setMessage(ID_NULL);
			return new ResponseEntity<>(employeeEducationResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	// delete transaction for EmployeeEducationDetails
	public ResponseEntity<Object> deleteTransaction(EmployeeEducationDetailsRequest emplEduDetailsRequestObj)
			throws SQLException {
		List<EmployeeEducationDetails> list = new ArrayList<>();
		List<EmployeeEducationDetails> idValidation = this.getIdValidation(emplEduDetailsRequestObj);
		if (idValidation.isEmpty()) {
			int[] updateEmployeeEducationDetails = employeeEducationDetailsDaoImpl
					.deleteEmployeeEducationDetails(emplEduDetailsRequestObj);

			for (int a : updateEmployeeEducationDetails) {
				if (a <= 0) {
					list.add(emplEduDetailsRequestObj.getEmployeeEducationDetailsList().get(a));
				}
			}
			if (list.isEmpty()) {
				EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
				employeeEducationResponse.setStatusCode("200");
				employeeEducationResponse.setMessage("Employee Education Details have been deleted");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			} else {
				EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
				employeeEducationResponse.setEmployeeEducationDetailsList(list);
				employeeEducationResponse.setStatusCode("409");
				employeeEducationResponse.setMessage("Employee Education Details has not deleted  ");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.UNPROCESSABLE_ENTITY);

			}
		} else {
			EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();
			employeeEducationResponse.setEmployeeEducationDetailsList(idValidation);
			employeeEducationResponse.setStatusCode("422");
			employeeEducationResponse.setMessage(ID_NULL);
			return new ResponseEntity<>(employeeEducationResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
