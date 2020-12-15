package com.ojas.obs.facade;

import static com.ojas.obs.utility.Constants.ACCEPT;
import static com.ojas.obs.utility.Constants.DECLINE;
import static com.ojas.obs.utility.Constants.DELETE;
import static com.ojas.obs.utility.Constants.GETALL;
import static com.ojas.obs.utility.Constants.GETBYID;
import static com.ojas.obs.utility.Constants.MESSAGE;
import static com.ojas.obs.utility.Constants.PENDING;
import static com.ojas.obs.utility.Constants.SAVE;
import static com.ojas.obs.utility.Constants.SAVE_SUCCESS_MESSAGE;
import static com.ojas.obs.utility.Constants.UPDATE_AADHAR_IMG;
import static com.ojas.obs.utility.Constants.UPDATE_AADHAR_STATUS;
import static com.ojas.obs.utility.Constants.UPDATE_PAN_IMG;
import static com.ojas.obs.utility.Constants.UPDATE_PAN_STATUS;
import static com.ojas.obs.utility.Constants.UPDATE_PASSPORT_IMG;
import static com.ojas.obs.utility.Constants.UPDATE_PASSPORT_STATUS;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.KyeDao;
import com.ojas.obs.model.KYE;
import com.ojas.obs.request.KYERequest;
import com.ojas.obs.response.KyeResponse;
import com.ojas.obs.utility.ErrorResponse;

@Service
public class KyeFacadeImpl implements KyeFacade {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private KyeDao kyeDao;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	Environment env;


	@Override
	public ResponseEntity<Object> setKYE(KYERequest kyeRequest) {
		logger.debug("@kyeRequest in KyeFacadeImpl ::" + kyeRequest);

		ResponseEntity<Object> responseEntity = null;
		KyeResponse kyeResponse = null;
		try {
			if (kyeRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				boolean saveKYE = kyeDao.saveKYE(kyeRequest);
				if (saveKYE) {
					SimpleMailMessage mailMessage = new SimpleMailMessage();
					String mailId = kyeDao.getMailId(kyeRequest.getKye().get(0).getEmployee_Id());
					String employee_Id = kyeRequest.getKye().get(0).getEmployee_Id();
					mailMessage.setFrom(env.getProperty("spring.mail.username"));
					mailMessage.setTo(mailId);
					mailMessage.setCc(env.getProperty("spring.mail.username"));
					mailMessage.setSubject(MESSAGE);
					mailMessage.setText("[" + employee_Id
							+ "] your  KYE documents are saved succefully");
					javaMailSender.send(mailMessage);
					kyeResponse = new KyeResponse();
					kyeResponse.setStatusCode("200");
					kyeResponse.setMessage(SAVE_SUCCESS_MESSAGE);
					return new ResponseEntity<>(kyeResponse, HttpStatus.OK);

				}

				logger.debug("request is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("record not added");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}
			if (kyeRequest.getTransactionType() != null && kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_STATUS)
					&& kyeRequest.getKye().get(0).getAadhar_status().equalsIgnoreCase(PENDING)) {
				boolean updateKYE = kyeDao.updateAadharStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Aadhar Status updated successfully");
					sendMailForAadharStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Aadhar Status not updated");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType() != null
					&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_STATUS)
					&& kyeRequest.getKye().get(0).getAadhar_status().equalsIgnoreCase(ACCEPT)) {
				boolean updateKYE = kyeDao.updateAadharStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Aadhar Status Accepted");
					sendMailForAadharStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Aadhar Status not Accepted");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType() != null
					&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_STATUS)
					&& kyeRequest.getKye().get(0).getAadhar_status().equalsIgnoreCase(DECLINE)) {
				boolean updateKYE = kyeDao.updateAadharStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Aadhar Status Declined");
					sendMailForAadharStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Aadhar Status not Declined");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}
			if (kyeRequest.getTransactionType() != null&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_IMG)) {
				boolean updateKYE = kyeDao.updateAadharKYE(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Aadhar Image Updated Successfully");
					sendMailForImg(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Aadhar not updated");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}
			if (kyeRequest.getTransactionType() != null
					&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PAN_STATUS)
					&& kyeRequest.getKye().get(0).getPan_status().equalsIgnoreCase(PENDING)) {
				boolean updateKYE = kyeDao.updatePanStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("PAN Status updated successfully");
					sendMailForPanStatus(kyeRequest);
					response.setStatusCode("200");
					
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("PAN Status not updated");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType() != null
					&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PAN_STATUS)
					&& kyeRequest.getKye().get(0).getPan_status().equalsIgnoreCase(ACCEPT)) {
				boolean updateKYE = kyeDao.updatePanStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("PAN Status Accepted");
					sendMailForPanStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("PAN Status not Accepted");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType() != null
					&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PAN_STATUS)
					&& kyeRequest.getKye().get(0).getPan_status().equalsIgnoreCase(DECLINE)) {
				boolean updateKYE = kyeDao.updatePanStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("PAN Status Declined");
					sendMailForPanStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("PAN Status not Declined");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType() != null && kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PAN_IMG)) {
				boolean updateKYE = kyeDao.updatePanKYE(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("PAN Image updated successfully");
					sendMailForImg(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("PAN not updated");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType() != null&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_STATUS)
					&& kyeRequest.getKye().get(0).getPassport_status().equalsIgnoreCase(PENDING)) {

				boolean updateKYE = kyeDao.updatePassportStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Passport Status updated successfully");
					sendMailForPassportStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Passport Status not updated");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}
			if (kyeRequest.getTransactionType() != null&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_STATUS)
					&& kyeRequest.getKye().get(0).getPassport_status().equalsIgnoreCase(ACCEPT)) {

				boolean updateKYE = kyeDao.updatePassportStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Passport Status Accepted");
					sendMailForPassportStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Passport Status not Accepted");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}
			if (kyeRequest.getTransactionType() != null&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_STATUS)
					&& kyeRequest.getKye().get(0).getPassport_status().equalsIgnoreCase(DECLINE)) {

				boolean updateKYE = kyeDao.updatePassportStatus(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Passport Status Declined");
					sendMailForPassportStatus(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Passport Status not Declined");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType() != null
					&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_IMG)) {

				boolean updateKYE = kyeDao.updatePassportKYE(kyeRequest);
				if (updateKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("Passport Image Updated Successfully");
					sendMailForImg(kyeRequest);
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Passport not Updated");
				error.setStatusCode("409");
				responseEntity = new ResponseEntity<>(error, HttpStatus.OK);
				return responseEntity;
			}

			if (kyeRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				boolean deleteKYE = kyeDao.deleteKYE(kyeRequest);
				if (deleteKYE) {
					KyeResponse response = new KyeResponse();
					response.setMessage("record deleted successfully");
					response.setStatusCode("200");
					responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
					return responseEntity;
				}
				ErrorResponse error = new ErrorResponse();
				error.setMessage("record not deleted");
				error.setStatusCode("422");
				responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				return responseEntity;
			}
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("exception");
			error.setStatusCode("409");
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
			return responseEntity;
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<Object> getKYE(KYERequest kyeRequest) throws SQLException {
		KyeResponse response = null;

		if (kyeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {

			response = new KyeResponse();
			List<KYE> KyeList = kyeDao.getAllKYE(kyeRequest);

			if (KyeList == null || KyeList.isEmpty()) {
				response.setKyeList(KyeList);
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} else {
				response.setMessage("success");
				response.setStatusCode("200");
				response.setKyeList(KyeList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		if (kyeRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			response = new KyeResponse();
			List<KYE> list = null;
			String empId = kyeRequest.getKye().get(0).getEmployee_Id();
			Integer id = kyeRequest.getKye().get(0).getId();
			if (id != 0)
				list = kyeDao.getById(kyeRequest);
			else if (empId != null)
				list = kyeDao.getByEmpId(kyeRequest);

			if (list == null || list.isEmpty()) {
				response.setMessage("No Records Present");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} else {
				response.setMessage("success");
				response.setStatusCode("200");
				response.setKyeList(list);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private boolean sendMailForPanStatus(KYERequest kyeRequest) throws Exception {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String mailId = kyeDao.getMailId(kyeRequest.getKye().get(0).getEmployee_Id());
		String empId = kyeRequest.getKye().get(0).getEmployee_Id();
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setTo(mailId);
		mailMessage.setSubject("KYE Document Status");

		
		if (kyeRequest.getKye().get(0).getPan_status().equalsIgnoreCase(PENDING)&& kyeRequest.getTransactionType() != null
				&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PAN_STATUS)) {

			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your PAN document accepted.");
		} else if (kyeRequest.getKye().get(0).getPan_status().equalsIgnoreCase(ACCEPT)&& kyeRequest.getTransactionType() != null
				&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PAN_STATUS)) {

			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your PAN Document Updated.");
		} else {
			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your PAN document declined.");
		}

		javaMailSender.send(mailMessage);

		return true;
	}

	private boolean sendMailForAadharStatus(KYERequest kyeRequest) throws Exception {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String mailId = kyeDao.getMailId(kyeRequest.getKye().get(0).getEmployee_Id());
		String empId = kyeRequest.getKye().get(0).getEmployee_Id();
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setTo(mailId);
		mailMessage.setSubject("KYE Document Status");

		if (kyeRequest.getKye().get(0).getAadhar_status().equalsIgnoreCase(PENDING)&& kyeRequest.getTransactionType() != null
				&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_STATUS)) {

			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your Aadhar Document Updated.");
		}

		else if (kyeRequest.getKye().get(0).getAadhar_status().equalsIgnoreCase(ACCEPT)&& kyeRequest.getTransactionType() != null
				&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_STATUS)) {

			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your Aadhar document accepted.");

		} else {
			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your Aadhar document declined.");
		}

		javaMailSender.send(mailMessage);

		return true;

	}
	
	private boolean sendMailForPassportStatus(KYERequest kyeRequest) throws Exception {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String mailId = kyeDao.getMailId(kyeRequest.getKye().get(0).getEmployee_Id());
		String empId = kyeRequest.getKye().get(0).getEmployee_Id();
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setTo(mailId);
		mailMessage.setSubject("KYE Document Status");
		
		if (kyeRequest.getKye().get(0).getPassport_status().equalsIgnoreCase(PENDING) &&  kyeRequest.getTransactionType() != null
				&& kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_STATUS)) {
			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your Passport Document Updated.");
		}else if(kyeRequest.getKye().get(0).getPassport_status().equalsIgnoreCase(ACCEPT)&&kyeRequest.getTransactionType() != null&& 
				kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_STATUS)) {
			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your Passport document accepted.");
		}else {
			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setText("Hi this is your Employee Id [" + empId + "] your Passport document declined.");
		}
		
		javaMailSender.send(mailMessage);

		return true;
	}

	private ResponseEntity<Object> sendMailForImg(KYERequest kyeRequest) throws SQLException {
		KyeResponse response = new KyeResponse();

		if (kyeRequest.getKye().get(0).getAadhar_img() != null || kyeRequest.getKye().get(0).getPan_img() != null
				|| kyeRequest.getKye().get(0).getPassport_img() != null) {

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			String mailId = kyeDao.getMailId(kyeRequest.getKye().get(0).getEmployee_Id());
			
			String empId = kyeRequest.getKye().get(0).getEmployee_Id();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(mailId);
			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			mailMessage.setSubject("KYE Image Status");

			if (kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_IMG)) {
				mailMessage.setText("Hi this is your Employee Id [" + empId + "]  Aadhar Image Updated Successfully .");
			}

			if (kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PAN_IMG)) {
				mailMessage.setText("Hi this is your Employee Id [" + empId + "] PAN Image Updated Successfully.");
			}

			if (kyeRequest.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_IMG)) {
				mailMessage
						.setText("Hi this is your Employee Id [" + empId + "] Passport Image Updated Successfully .");
			}
			javaMailSender.send(mailMessage);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}