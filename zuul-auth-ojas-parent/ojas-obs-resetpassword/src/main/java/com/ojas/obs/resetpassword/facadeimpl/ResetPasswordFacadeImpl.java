package com.ojas.obs.resetpassword.facadeimpl;

import static com.ojas.obs.resetpassword.constants.UserConstants.FAILED;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ojas.obs.resetpassword.dao.ResetPasswordDao;
import com.ojas.obs.resetpassword.facade.ResetPasswordFacade;
import com.ojas.obs.resetpassword.model.ResetPassword;
import com.ojas.obs.resetpassword.request.ResetPasswordRequest;
import com.ojas.obs.resetpassword.response.ResetPasswordResponse;

@Service
public class ResetPasswordFacadeImpl implements ResetPasswordFacade {
	@Autowired
	private ResetPasswordDao resetPasswordDao;
	@Autowired
	private PasswordEncoder passwordEncode;
	@Autowired
	private Environment env;
	@Autowired
	private JavaMailSender javaMailSender;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> setPassword(ResetPasswordRequest resetPasswordRequest)
			throws SQLException, DuplicateKeyException {
		logger.debug("inside password facade " + resetPasswordRequest);
		ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();

		ResetPassword resetPassword = resetPasswordRequest.getPwd();
		String employeeId = resetPasswordRequest.getPwd().getEmployeeId();
		String pass = resetPasswordDao.getPassword(employeeId);
		if (this.passwordEncode.matches(resetPassword.getCurruntPassword(), pass)) {
			logger.debug("Password matched");
			String encode = this.passwordEncode.encode(resetPassword.getNewPassword());
			boolean updatePassword = resetPasswordDao.updatePassword(encode, resetPassword.getUpdatedBy(), employeeId);
			String mailId = resetPasswordDao.getMailId(resetPassword.getEmployeeId());
			if (updatePassword) {
				sendEmail(mailId, "Password changed successfully",
						"Hi,\n\n \tYour OBS password has been changed successfully.");
				resetPasswordResponse.setMessage("Password updated successfully");
				resetPasswordResponse.setStatusCode("200");
				return new ResponseEntity<>(resetPasswordResponse, HttpStatus.OK);
			}
			resetPasswordResponse.setMessage("Failed to update password!");
			resetPasswordResponse.setStatusCode("409");
			return new ResponseEntity<>(resetPasswordResponse, HttpStatus.CONFLICT);
		}
		logger.debug("Password didn't match!");
		resetPasswordResponse.setMessage("Password didn't match! Please enter a valid password!");
		resetPasswordResponse.setStatusCode("409");
		return new ResponseEntity<>(resetPasswordResponse, HttpStatus.CONFLICT);

	}

	@Override
	public ResponseEntity<Object> savePassword(ResetPasswordRequest resetPasswordRequest)
			throws SQLException, AddressException, MessagingException {
		logger.debug("inside password facade " + resetPasswordRequest);
		ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
		Timestamp updatedOn = new Timestamp(new Date().getTime());
		resetPasswordRequest.getPwd().setUpdatedOn(updatedOn);
		ResetPassword resetPassword = resetPasswordRequest.getPwd();
		String encode = this.passwordEncode.encode(resetPassword.getNewPassword());
		String employeeId = resetPasswordRequest.getPwd().getEmployeeId();

		boolean savePassword = resetPasswordDao.savePassword(encode, updatedOn, employeeId);
		String mailId = resetPasswordDao.getMailId(resetPassword.getEmployeeId());

		if (savePassword) {
			resetPasswordResponse.setMessage("Successfully record saved");
			resetPasswordResponse.setStatusCode("200");
			sendEmail(mailId, "New password generated", "Hi, \nNew password has been generated successfully.");
			logger.debug("Reset password : " + savePassword);

			return new ResponseEntity<>(resetPasswordResponse, HttpStatus.OK);
		} else {
			resetPasswordResponse.setMessage(FAILED);
			return new ResponseEntity<>(resetPasswordResponse, HttpStatus.CONFLICT);
		}
	}

	public void sendEmail(String toEmail, String subject, String body) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(body + " \n\n\n\nNote: This is an auto-generated mail from OBS. Please do not reply.");
		javaMailSender.send(mailMessage);

		/*
		 * MimeMessage msg = new MimeMessage(session); msg.setFrom(new
		 * InternetAddress("support-obs@ojas-it.com"));
		 * msg.setReplyTo(InternetAddress.parse("saritha.ummani@ojas-it.com", false));
		 * msg.setSubject(subject, "UTF-8"); msg.setText(body, "UTF-8");
		 * msg.setSentDate(new Date()); msg.setRecipients(Message.RecipientType.TO,
		 * InternetAddress.parse(toEmail, false));
		 * System.out.println("Message is ready"); Transport.send(msg);
		 * 
		 * System.out.println("EMail Sent Successfully!!");
		 */

	}

	/*
	 * public void emailConf() throws AddressException, MessagingException { final
	 * String fromEmail = "support-obs@ojas-it.com"; final String password =
	 * "Ojas1525"; final String toEmail = "saritha.ummani@ojas-it.com";
	 * 
	 * System.out.println("TLSEmail Start"); Properties props = new Properties();
	 * props.put("mail.smtp.host", "smtp.office365.com");
	 * props.put("mail.smtp.port", "587"); props.put("mail.smtp.auth", "true");
	 * props.put("mail.smtp.starttls.enable", "true");
	 * props.put("mail.smtp.ssl.trust", "smtp.office365.com");
	 * 
	 * Authenticator auth = new Authenticator() {
	 * 
	 * protected PasswordAuthentication getPasswordAuthentication() { return new
	 * PasswordAuthentication(fromEmail, password); } }; Session session =
	 * Session.getInstance(props, auth);
	 * 
	 * //sendEmail(session, toEmail, "generate password",
	 * "your password was changed");
	 * 
	 * }
	 */
}
