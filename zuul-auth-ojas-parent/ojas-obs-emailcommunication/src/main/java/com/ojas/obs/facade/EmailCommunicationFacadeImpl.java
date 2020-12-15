package com.ojas.obs.facade;

import static com.ojas.obs.constants.UserConstants.FAILED;
import static com.ojas.obs.constants.UserConstants.Mail_Smtp_Auth;
import static com.ojas.obs.constants.UserConstants.Mail_Smtp_Host;
import static com.ojas.obs.constants.UserConstants.Mail_Smtp_Port;
import static com.ojas.obs.constants.UserConstants.Mail_Smtp_Starttls_Enable;
import static com.ojas.obs.constants.UserConstants.SAVE;
import static com.ojas.obs.constants.UserConstants.SUCCESS;
import static com.ojas.obs.constants.UserConstants.UPDATE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.EmailCommunicationDAOImpl;
import com.ojas.obs.model.Email;
import com.ojas.obs.request.EmailCommunicationRequest;
import com.ojas.obs.response.EmailCommunicationResponse;

@Service
public class EmailCommunicationFacadeImpl implements EmailCommunicationFacade {

	@Autowired
	private Environment env;
	/*
	 * @Autowired JavaMailSender javaMailSender;
	 */

	@Autowired
	EmailCommunicationDAOImpl emailCommunicationDAOImpl;

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> setEmail(EmailCommunicationRequest emailCommunicationRequest)
			throws SQLException, AddressException, MessagingException {
		String toAddress = emailCommunicationRequest.getEmail().get(0).getToAddress();

		logger.debug("inside saveEmployeeEducation method : " + emailCommunicationRequest);
		EmailCommunicationResponse emailCommunicationResponse = null;

		if (emailCommunicationRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			emailCommunicationResponse = new EmailCommunicationResponse();
			boolean email = emailCommunicationDAOImpl.saveEmail(emailCommunicationRequest);
			logger.debug("inside  save condition.****** : " + email);
			if (email) {
				System.out.println(toAddress);
				emailCommunicationResponse.setMessage("Successfully record added");
				emailCommunicationResponse.setStatusCode("200");

				Properties props = new Properties();
				props.put(Mail_Smtp_Auth, env.getProperty("Auth"));
				props.put(Mail_Smtp_Starttls_Enable, env.getProperty("Starttlsenable"));
				props.put(Mail_Smtp_Host, env.getProperty("Host"));
				props.put(Mail_Smtp_Port, env.getProperty("Port"));
				
				

				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(env.getProperty("mailusername"),
								env.getProperty("mailpassword"));
						
					}
				});

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(env.getProperty("mailusername")));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
				message.setSubject(env.getProperty("Subject"));
				message.setText(env.getProperty("Text"));
				Transport.send(message);
				System.out.println("Done");

				return new ResponseEntity<>(emailCommunicationResponse, HttpStatus.OK);
			}

			else {
				emailCommunicationResponse.setMessage(FAILED);
				return new ResponseEntity<>(emailCommunicationResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		}
		if (emailCommunicationRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			emailCommunicationRequest = new EmailCommunicationRequest();
			boolean updateEducation = emailCommunicationDAOImpl.updateEmail(emailCommunicationRequest);
			if (updateEducation) {
				emailCommunicationResponse.setMessage("Successfully record updated");
				emailCommunicationResponse.setStatusCode("200");
				return new ResponseEntity<>(emailCommunicationResponse, HttpStatus.OK);
			} else {
				emailCommunicationResponse.setMessage(FAILED);
				return new ResponseEntity<>(emailCommunicationResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}

		return null;

	}

	@Override
	public ResponseEntity<Object> getEmail(EmailCommunicationRequest emailCommunicationRequest) throws SQLException {

		EmailCommunicationResponse emailCommunicationResponse = new EmailCommunicationResponse();
		List<Email> allEmailDetails = null;
		System.out.println("in facade class");
		logger.debug("inside getAllEducationDetails in EmployeeEducationFacde.***");

		allEmailDetails = emailCommunicationDAOImpl.getAllListDetails(emailCommunicationRequest);

		if (allEmailDetails == null) {
			emailCommunicationResponse.setEmailList(new ArrayList<>());
			emailCommunicationResponse.setMessage("No records found");
			emailCommunicationResponse.setStatusCode("409");
		} else {
			emailCommunicationResponse.setEmailList(allEmailDetails);
			emailCommunicationResponse.setMessage(SUCCESS);
		}
		return new ResponseEntity<Object>(emailCommunicationResponse, HttpStatus.OK);

	}
}

/*
 * Properties props = new Properties(); props.put("mail.smtp.auth", "true");
 * props.put("mail.smtp.starttls.enable", "true"); props.put("mail.smtp.host",
 * "outlook.office365.com"); props.put("mail.smtp.port", "587");
 * 
 * 
 * Session session1 = Session.getInstance(props, new javax.mail.Authenticator()
 * { protected PasswordAuthentication getPasswordAuthentication() { return new
 * PasswordAuthentication(env.getProperty("username"),
 * env.getProperty("password")); } });
 * 
 * Message message1 = new MimeMessage(session1); message1.setFrom(new
 * InternetAddress(env.getProperty("username")));
 * message1.setRecipients(Message.RecipientType.TO,
 * InternetAddress.parse(toAddress.toString())); message1.setSubject("Test");
 * message1.setText("HI, This is Test Mail Ignore it.");
 * Transport.send(message1); System.out.println("Done");
 */

/*
 * SimpleMailMessage mailMessage = new SimpleMailMessage();
 * mailMessage.setTo(username); mailMessage.setSubject("Test Email");
 * mailMessage.
 * setText("Test Email from OBS-HRMS to test mass email communication - please ignore"
 * ); javaMailSender.send(mailMessage);
 */

/*
 * Properties prop = new Properties(); prop.put("mail.smtp.host",
 * "smtp.gmail.com"); prop.put("mail.smtp.port", "587");
 * prop.put("mail.smtp.auth", "true"); prop.put("mail.smtp.starttls.enable",
 * "true"); // TLS
 * 
 * Session session = Session.getInstance(prop, new Authenticator() { protected
 * PasswordAuthentication getPasswordAuthentication() {
 * System.out.println("Credentials : " + username + " " + password); return new
 * PasswordAuthentication(username, password); } });
 * System.out.println("Session: " + session); { // // String str =
 * emailCommunicationRequest.getEmail().get(0).getToAddress(); // Message
 * message = new MimeMessage(session); // message.setFrom(new
 * InternetAddress(emailCommunicationRequest.getEmail().get(0).getFromAddress())
 * ); // // message.setRecipients(Message.RecipientType.TO, //
 * InternetAddress.parse( // '"' +
 * resignationRequest.getResignation().get(0).getEmailId1() + ',' + //
 * resignationRequest.getResignation().get(0).getEmailId2() + '') //
 * InternetAddress.parse(str) // // ); message.setSubject("[Testing Gmail TLS");
 * message.setText("Dear Mail Crawler," +
 * "\n\n Please do not spam my email!.........." +
 * emailCommunicationRequest.getEmail().get(0).getEmailAddress());
 * 
 * Transport.send(message);
 * 
 * 
 * 
 * } catch (MessagingException e) { e.printStackTrace(); }
 * 
 */