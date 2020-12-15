package com.ojas.obs.facade;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.EmailCommunicationRequest;

public interface EmailCommunicationFacade {

	ResponseEntity<Object> setEmail(EmailCommunicationRequest emailCommunicationRequest) throws SQLException, AddressException, MessagingException;

	ResponseEntity<Object> getEmail(EmailCommunicationRequest emailCommunicationRequest) throws SQLException;

}
