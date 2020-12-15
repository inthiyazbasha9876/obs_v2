package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constant.UserConstants.DELETE;
import static com.ojas.obs.constant.UserConstants.FAILED;
import static com.ojas.obs.constant.UserConstants.GETALL;
import static com.ojas.obs.constant.UserConstants.GETBYCUSTOMERID;
import static com.ojas.obs.constant.UserConstants.GETBYID;
import static com.ojas.obs.constant.UserConstants.GETFILE;
import static com.ojas.obs.constant.UserConstants.PROVIDEVALIDID;
import static com.ojas.obs.constant.UserConstants.RECORDSNOTFOUND;
import static com.ojas.obs.constant.UserConstants.SAVE;
import static com.ojas.obs.constant.UserConstants.STATUSUPDATE;
import static com.ojas.obs.constant.UserConstants.SUCCESS;
import static com.ojas.obs.constant.UserConstants.UPDATE;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ojas.obs.facade.CustomerContractDetailsFacade;
import com.ojas.obs.model.CustomerContractDetails;
import com.ojas.obs.repository.CustomerContractDetailsRepository;
import com.ojas.obs.request.CustomerContractDetailsRequest;
import com.ojas.obs.response.CustomerContractDetailsResponse;

@Service
public class CustomerContractDetailsFacadeImpl implements CustomerContractDetailsFacade {

	@Autowired
	private CustomerContractDetailsRepository customerContractDetailsRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Environment env;

	private static String sql = "Select officialEmail from obs_employeeinfo where employee_id in(";
	private final Logger logger = Logger.getLogger(this.getClass());

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<Object> setCustomerContractDetails(
			CustomerContractDetailsRequest customerContractDetailsRequest) throws SQLException, IOException {
		CustomerContractDetailsResponse response = new CustomerContractDetailsResponse();
		logger.debug("request data into facade" + customerContractDetailsRequest);

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(SAVE)) {
			logger.debug("request data into facade save" + customerContractDetailsRequest);
			response = new CustomerContractDetailsResponse();

			List<CustomerContractDetails> contractlist = customerContractDetailsRequest
					.getCustomercontractdetailslist();

			List<CustomerContractDetails> contractDetails = customerContractDetailsRepo.saveAll(contractlist);

			if (contractDetails != null && contractDetails.get(0).getCustomerid() != null) {

				logger.debug("save method success");
				List<String> empIds = Arrays.asList(contractlist.get(0).getBuHead(), contractlist.get(0).getSbuHead());
				sendMail(empIds, "New contract added", "New contract was added and document has been uploaded");
				response.setStatusCode("200");
				response.setMessage("Contract saved successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		}

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(UPDATE)) {
			logger.debug("request data into facade update" + customerContractDetailsRequest);

			List<CustomerContractDetails> contractdetails = customerContractDetailsRequest
					.getCustomercontractdetailslist();
			Optional<CustomerContractDetails> findById = customerContractDetailsRepo
					.findById(contractdetails.get(0).getContractid());
			if (findById.isPresent() && findById.get().getContractid() != null) {

				customerContractDetailsRepo.saveAll(contractdetails);
				List<String> empIds = Arrays.asList(contractdetails.get(0).getBuHead(),
						contractdetails.get(0).getSbuHead());
				sendMail(empIds, "New contract added", "New contract was added and document has been updated");
				response.setStatusCode("200");
				response.setMessage("Contract updated successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		}

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(DELETE)) {
			response = new CustomerContractDetailsResponse();
			logger.debug("request data into facade delete " + customerContractDetailsRequest);

			List<CustomerContractDetails> contractdetails = customerContractDetailsRequest
					.getCustomercontractdetailslist();
			Integer contractid = contractdetails.get(0).getContractid();
			CustomerContractDetails deletestatus = customerContractDetailsRepo.getOne(contractid);
			deletestatus.setStatus(!deletestatus.getStatus());
			CustomerContractDetails saveContractdetails = customerContractDetailsRepo.save(deletestatus);

			if (saveContractdetails != null) {
				response.setStatusCode("200");
				response.setMessage("Contract deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(STATUSUPDATE)) {
			List<CustomerContractDetails> contractlist = customerContractDetailsRequest
					.getCustomercontractdetailslist();

			response = new CustomerContractDetailsResponse();
			CustomerContractDetails getcontid = customerContractDetailsRepo.getOne(contractlist.get(0).getContractid());
			if (getcontid.getContractid() != null) {

				getcontid.setBuStatus(contractlist.get(0).getBuStatus());
				getcontid.setFinanceStatus(contractlist.get(0).getFinanceStatus());
				getcontid.setComment(contractlist.get(0).getComment());
				CustomerContractDetails statusupdate = customerContractDetailsRepo.save(getcontid);

				if (statusupdate.getContractid() != null && (statusupdate.getBuStatus().equalsIgnoreCase("approved")
						&& statusupdate.getFinanceStatus().equalsIgnoreCase("pending"))) {

					List<String> empIds = Arrays.asList(getcontid.getBuHead(), getcontid.getSbuHead());

					sendMail(empIds, "Contract Status ",
							" Business Unit Team contract have been " + getcontid.getBuStatus().toLowerCase());
					logger.info("Contract have been " + contractlist.get(0).getBuStatus());
					response.setMessage("Contract have been " + contractlist.get(0).getBuStatus());
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);

				} else if (statusupdate.getContractid() != null
						&& (statusupdate.getBuStatus().equalsIgnoreCase("rejected")
								&& statusupdate.getFinanceStatus().equalsIgnoreCase("pending"))) {

					List<String> empIds = Arrays.asList(getcontid.getBuHead(), getcontid.getSbuHead());

					sendMail(empIds, "Contract Status ",
							"Business Unit Team contract have been " + getcontid.getBuStatus().toLowerCase());
					logger.info("Contract have been " + contractlist.get(0).getBuStatus());
					response.setMessage("Contract have been " + contractlist.get(0).getBuStatus());
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);

				} else if (statusupdate.getContractid() != null
						&& (statusupdate.getBuStatus().equalsIgnoreCase("approved")
								&& statusupdate.getFinanceStatus().equalsIgnoreCase("approved"))) {

					List<String> empIds = Arrays.asList(getcontid.getBuHead(), getcontid.getSbuHead());

					sendMail(empIds, "Contract Status ",
							"Finance Team contract have been " + getcontid.getFinanceStatus().toLowerCase());
					logger.info("Contract have been " + contractlist.get(0).getFinanceStatus());
					response.setMessage("Contract have been " + contractlist.get(0).getFinanceStatus());
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else if (statusupdate.getContractid() != null
						&& (statusupdate.getFinanceStatus().equalsIgnoreCase("rejected")
								&& statusupdate.getBuStatus().equalsIgnoreCase("approved"))) {

					List<String> empIds = Arrays.asList(getcontid.getBuHead(), getcontid.getSbuHead());
					sendMail(empIds, "Contract Status ",
							"Finance Team contract have been " + getcontid.getFinanceStatus().toLowerCase());
					logger.info("Contract have been " + contractlist.get(0).getFinanceStatus());
					response.setMessage("Contract have been " + contractlist.get(0).getFinanceStatus());
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			}

		}

		response.setStatusCode("422");
		response.setMessage(FAILED);
		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);

	}

	@Override
	public ResponseEntity<Object> getCustomerContractDetails(
			CustomerContractDetailsRequest customerContractDetailsRequest) throws SQLException, IOException {

		CustomerContractDetailsResponse response = new CustomerContractDetailsResponse();
		logger.debug(" getAll customer details");
		List<CustomerContractDetails> list = customerContractDetailsRequest.getCustomercontractdetailslist();

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(GETALL)) {
			List<CustomerContractDetails> findAll = customerContractDetailsRepo.getCustomercontractdetailslist();
			logger.info("inside getall in facade customer contract details" + findAll);
			if (findAll == null) {
				response = new CustomerContractDetailsResponse();
				response.setCustomercontractdetailslist(new ArrayList<CustomerContractDetails>());
				response.setMessage(RECORDSNOTFOUND);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			response = new CustomerContractDetailsResponse();
			response.setCustomercontractdetailslist(findAll);
			response.setMessage(SUCCESS);
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(GETBYID)
				&& list.get(0).getContractid() != null) {
			logger.debug("recived data into facade getbyid constract details" + customerContractDetailsRequest);
			ArrayList<CustomerContractDetails> listcontractDetails = new ArrayList<>();

			if (list.get(0).getContractid() != null) {
				Integer id = list.get(0).getContractid();
//				CustomerContractDetails getById = customerContractDetailsRepo.findById(id)
//						.orElse(new CustomerContractDetails());
				
				List<CustomerContractDetails> getById = customerContractDetailsRepo.getByContractid(id);
				logger.info("inside getbyid in facade customer contact details" + getById);
				listcontractDetails.addAll(getById);

//				if (getById != null && getById.getContractid() != null)
					if (getById != null )
				{
					response = new CustomerContractDetailsResponse();
					response.setCustomercontractdetailslist(listcontractDetails);
					response.setStatusCode("200");
					response.setMessage(SUCCESS);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			}

		}

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(GETBYCUSTOMERID)
				&& list.get(0).getCustomerid() != null) {
			logger.debug("recived data into facade getbycustomerid constract details" + customerContractDetailsRequest);

			if (list.get(0).getCustomerid() != null) {
				Integer custid = list.get(0).getCustomerid();
				List<CustomerContractDetails> byCustomerid = customerContractDetailsRepo.getByCustomerid(custid);
				logger.info("inside getbyid in facade customer contact details" + byCustomerid);

				if (!byCustomerid.isEmpty()) {
					response = new CustomerContractDetailsResponse();
					response.setCustomercontractdetailslist(byCustomerid);
					response.setStatusCode("200");
					response.setMessage(SUCCESS);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			}

		}

		if (customerContractDetailsRequest.getTransactiontype().equalsIgnoreCase(GETFILE)
				&& list.get(0).getContractid() != null) {

			Integer id = list.get(0).getContractid();

			String doc = customerContractDetailsRepo.getDocument(id);
			logger.info("inside getbyid in facade customer contract details" + doc);

			if (doc != null) {
				response = new CustomerContractDetailsResponse();
				response.setDocument(doc);
				response.setStatusCode("200");
				response.setMessage(SUCCESS);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		}

		response = new CustomerContractDetailsResponse();
		response.setStatusCode("422");
		response.setMessage(PROVIDEVALIDID);
		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	// mail

	public Boolean sendMail(List<String> empIds, String subject, String body) throws IOException {
		Boolean sent = false;
		StringBuilder queryBuilder = new StringBuilder(sql);
		Object[] ids = new Object[empIds.size()];
		for (int i = 0; i < empIds.size(); i++) {
			ids[i] = empIds.get(i);
			queryBuilder.append(" ?");
			if (i != empIds.size() - 1)
				queryBuilder.append(",");
		}
		queryBuilder.append(")");
		String query = queryBuilder.toString();
		jdbcTemplate = new JdbcTemplate(getDataSource());
		List<String> emialIds = jdbcTemplate.queryForList(query, ids, String.class);
		emialIds.add(env.getProperty("financemail"));
		emialIds.add(env.getProperty("hremail"));
		String[] to = emialIds.stream().toArray(String[]::new);
		if (emialIds != null) {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(to);
			mailMessage.setSubject(subject);
			mailMessage.setText(body);
			javaMailSender.send(mailMessage);
			sent = true;
		}
		return sent;
	}

	DataSource getDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(env.getProperty("db.url"));
		driverManagerDataSource.setUsername(env.getProperty("spring.datasource.username"));
		driverManagerDataSource.setPassword(env.getProperty("spring.datasource.password"));
		driverManagerDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		return driverManagerDataSource;
	}
}
