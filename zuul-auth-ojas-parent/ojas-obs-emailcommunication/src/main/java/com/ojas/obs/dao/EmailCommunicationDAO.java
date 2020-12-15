package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.Email;
import com.ojas.obs.request.EmailCommunicationRequest;

public interface EmailCommunicationDAO {

	Boolean saveEmail(EmailCommunicationRequest emailCommunicationRequest) throws SQLException;

	Boolean updateEmail(EmailCommunicationRequest emailCommunicationRequest) throws SQLException;

	List<Email> getAllListDetails(EmailCommunicationRequest request) throws SQLException;

	

}
