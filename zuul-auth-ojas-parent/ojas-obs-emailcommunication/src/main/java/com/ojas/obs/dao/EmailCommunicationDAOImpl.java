package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ojas.obs.constants.UserConstants.INSERT_EMAIL;
import static com.ojas.obs.constants.UserConstants.UPDATE_EMAIL;
import static com.ojas.obs.constants.UserConstants.SELECT_EMAIL;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.Email;
import com.ojas.obs.request.EmailCommunicationRequest;

@Repository
public class EmailCommunicationDAOImpl implements EmailCommunicationDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Boolean saveEmail(EmailCommunicationRequest emailCommunicationRequest) throws SQLException {
		boolean b = false;
		int[] save;

		List<Email> emailList = emailCommunicationRequest.getEmail();
		List<Object[]> list = new ArrayList<>();
		for (Email email : emailList) {
			Object[] bus = new Object[] { email.getToAddress(),email.getFromAddress(), email.getEmailAddress()};
			list.add(bus);
		}
		save = jdbcTemplate.batchUpdate(INSERT_EMAIL, list);
		if (save.length > 0) {
			b = true;

		}

		return b;
	}

	@Override
	public Boolean updateEmail(EmailCommunicationRequest emailCommunicationRequest) throws SQLException {
		boolean b = false;

		List<Email> emailList = emailCommunicationRequest.getEmail();

		int[] update;
		List<Object[]> list = new ArrayList<>();
		for (Email email : emailList) {
			Object[] bus = new Object[] {  email.getToAddress(), email.getFromAddress(),email.getEmailAddress()};
			list.add(bus);
		}
		update = jdbcTemplate.batchUpdate(UPDATE_EMAIL, list);

		if (update.length > 0) {
			b = true;
		}

		return b;
	}

	@Override
	public List<Email> getAllListDetails(EmailCommunicationRequest request) throws SQLException {
System.out.println("in dao class");
		logger.debug("Inside getAllEducationDetails DAO .***");
		List<Email> list = jdbcTemplate.query(SELECT_EMAIL, new BeanPropertyRowMapper<Email>(Email.class));
		return list;

	}

}
