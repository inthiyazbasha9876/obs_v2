package com.ojas.obs.regforgot.facadeimpl;

import static com.ojas.obs.regforgot.constants.UserConstants.FAILED;
import static com.ojas.obs.regforgot.constants.UserConstants.SENDMAIL;
import static com.ojas.obs.regforgot.constants.UserConstants.UPDATE;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.ojas.obs.regforgot.dao.ForgotPasswordDao;
import com.ojas.obs.regforgot.facade.ForgotPasswordFacade;
import com.ojas.obs.regforgot.model.ForgotPassword;
import com.ojas.obs.regforgot.request.ForgotPasswordRequest;
import com.ojas.obs.regforgot.response.ForgotPasswordResponse;

@Service
public class ForgotPasswordFacadeImpl implements ForgotPasswordFacade {
	@Autowired
	private ForgotPasswordDao forgotPasswordDao;
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	@Autowired
	private Environment env;
	private Random random = new SecureRandom();
	Logger logger = Logger.getLogger(this.getClass());
	ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse();
	
	@Override
	public ResponseEntity<Object> setForgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws SQLException, MailException {
		logger.debug("inside setEmployee method in facade : " + forgotPasswordRequest);
		if (forgotPasswordRequest.getTransactionType().equalsIgnoreCase(SENDMAIL)) {

			ForgotPassword forgotPassword = forgotPasswordRequest.getForgotPassword();
			String mailId = forgotPasswordDao.getMailId(forgotPassword.getEmployeeId());
			if (mailId != null) {
				Integer otp = 100000 + random.nextInt(900000);
				logger.info("Generated otp");
				LocalDateTime expTime = LocalDateTime.now().plusMinutes(15);
				logger.debug("expTime is : " + expTime);
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(env.getProperty("spring.mail.username"));
				System.out.println("From mail Id : "+mailMessage.getFrom());
				mailMessage.setTo(mailId);
				mailMessage.setSubject("OTP to reset your password");
				mailMessage.setText("Your OTP to reset your password is : " + otp
						+ " . Click on the link to reset your password "+ env.getProperty("resetUrl") +". This OTP will be expired in 15 minutes.");
				javaMailSender.send(mailMessage);
				boolean send = forgotPasswordDao.saveOtp(otp, expTime, forgotPassword.getEmployeeId());

				if (!send) {
					logger.error("Failed to send mail");
					forgotPasswordResponse.setMessage(FAILED);
					forgotPasswordResponse.setStatusCode("409");
					return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.CONFLICT);
				}
				logger.debug("Mail sent : " + send);
				forgotPasswordResponse = new ForgotPasswordResponse();
				forgotPasswordResponse.setStatusCode("200");
				forgotPasswordResponse.setMessage("OTP sent to your registered mail id!");
				return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.OK);
			}
			logger.error("Invalid employee id");
			forgotPasswordResponse.setMessage(FAILED);
			forgotPasswordResponse.setStatusCode("409");
			forgotPasswordResponse.setStatusMessage("Invalid employee id");
			return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.CONFLICT);
		}
		if (forgotPasswordRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			ForgotPassword forgotRequest = forgotPasswordRequest.getForgotPassword();
			ForgotPassword forgotResponse = forgotPasswordDao.getForgotData(forgotRequest.getEmployeeId());
			if (null == forgotResponse.getOtp()) {
				logger.error("Oops! There is no request to reset password");
				forgotPasswordResponse.setStatusCode("409");
				forgotPasswordResponse.setMessage("Oops! There is no request to reset password!");
				return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.CONFLICT);
			}
			Timestamp now = new Timestamp(new Date().getTime());
			if (!now.before(forgotResponse.getExpTime())) {
				logger.error("Oops! Your OTP is expaired!");
				forgotPasswordResponse.setStatusCode("409");
				forgotPasswordResponse.setMessage("Oops! Your OTP is expaired!");
				return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.CONFLICT);
			}
			if (!forgotRequest.getOtp().equals(forgotResponse.getOtp())) {
				logger.error("Oops! Invalid OTP entered!");
				forgotPasswordResponse.setStatusCode("409");
				forgotPasswordResponse.setMessage("Oops! Invalid OTP entered!");
				return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.CONFLICT);
			}
			logger.info("OTP matched");
			boolean update = forgotPasswordDao.update(forgotRequest);
			String mailId = forgotPasswordDao.getMailId(forgotRequest.getEmployeeId());
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			System.out.println("From mail Id : "+mailMessage.getFrom());
			mailMessage.setTo(mailId);
			mailMessage.setSubject("Password has been changed successfully");
			mailMessage.setText("Your password changed successfully as per your request. Click the link to login "+ env.getProperty("siteUrl") +" .");
			javaMailSender.send(mailMessage);
			//forgotPasswordDao.deleteForgotData(forgotRequest.getEmployeeId());
			logger.debug("Reset password : " + update);
			if (!update) {
				logger.error("Failed to reset password");
				forgotPasswordResponse.setStatusCode("409");
				forgotPasswordResponse.setMessage(FAILED);
				return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.CONFLICT);
			}
			forgotPasswordResponse.setStatusCode("200");
			forgotPasswordResponse.setMessage("Holla! Your password has changed successfully!");
			return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.OK);
		}
		return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.CONFLICT);
	}
}