package com.ojas.obs.facadeImpl;

import static com.ojas.obs.constants.UserConstants.ACCEPTED;
import static com.ojas.obs.constants.UserConstants.ACCEPTED_MESSAGE;
import static com.ojas.obs.constants.UserConstants.DECLINE;
import static com.ojas.obs.constants.UserConstants.DECLINE_MESSAGE;
import static com.ojas.obs.constants.UserConstants.DELETE;
import static com.ojas.obs.constants.UserConstants.FILEUPLOADSTATUS;
import static com.ojas.obs.constants.UserConstants.GETALL;
import static com.ojas.obs.constants.UserConstants.GETBYID;
import static com.ojas.obs.constants.UserConstants.MESSAGE;
import static com.ojas.obs.constants.UserConstants.PENDING;
import static com.ojas.obs.constants.UserConstants.PENDING_MESSAGE;
import static com.ojas.obs.constants.UserConstants.SAVE;
import static com.ojas.obs.constants.UserConstants.SAVE_SUCCESS_MESSAGE;
import static com.ojas.obs.constants.UserConstants.UPDATE;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ojas.obs.dao.ExperienceDao;
import com.ojas.obs.facade.ExperienceFacade;
import com.ojas.obs.model.EmployeeExperienceDetails;
import com.ojas.obs.request.ExperienceRequest;
import com.ojas.obs.response.ExperienceResponse;

@Component
public class ExperienceFacadeImpl implements ExperienceFacade 
{
	@Autowired
	ExperienceDao employeeExperienceDetailsDao;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	Environment env;

	ExperienceResponse experienceResponse = null;
	Logger logger = Logger.getLogger(this.getClass());


	@Override
	public ResponseEntity<Object> setEmployeeExperienceDetails(ExperienceRequest experienceRequestObject)
			throws SQLException 
	{
		List<EmployeeExperienceDetails> employeeExperienceDetailsList = experienceRequestObject
				.getEmployeeExperienceDetails();
		String employee_Id = employeeExperienceDetailsList.get(0).getEmployee_id();
		String message = null;

		for (EmployeeExperienceDetails employeeExperienceDetails : employeeExperienceDetailsList) 
		{
			experienceResponse = new ExperienceResponse();

			if (experienceRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) 
			{
				experienceResponse.setStatusCode("200");
				experienceResponse.setMessage("EmployeeExperienceDetails is  saved");
				
				int saveRes = employeeExperienceDetailsDao.saveEmployeeExperienceDetails(experienceRequestObject);
				logger.debug("received at service by calling the saveEmployeeExperienceDetails is" + saveRes);
				
					if (saveRes > 0 && employeeExperienceDetails.getDocumentsstatus().equalsIgnoreCase(PENDING)) 
					{

						SendMail(experienceRequestObject,employeeExperienceDetails);
						experienceResponse.setStatusCode("200");
						experienceResponse.setMessage(SAVE_SUCCESS_MESSAGE);
						return new ResponseEntity<>(experienceResponse, HttpStatus.OK);
						
					}
		
					else 
					{
						experienceResponse.setStatusCode("422");
						experienceResponse.setMessage("Experience details is not saved");
						return new ResponseEntity<>(experienceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				
			}

		

			if (experienceRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) 
			{
				if (null != employeeExperienceDetails.getId()) {
					int updateEmployeeExperienceDetails = employeeExperienceDetailsDao
							.updateEmployeeExperienceDetails(experienceRequestObject);
					
					logger.debug("received at service by calling the updateEmployeeExperienceDetails is"
							+ updateEmployeeExperienceDetails);

					if (updateEmployeeExperienceDetails > 0) {

						if (employeeExperienceDetails.getDocumentsstatus().equalsIgnoreCase(PENDING)
								&& employeeExperienceDetails.getImage() != null) 
						{
							SendMail(experienceRequestObject,employeeExperienceDetails);
						}
						experienceResponse.setStatusCode("200");
						experienceResponse.setMessage("Experience Details Updated Successfully");
						return new ResponseEntity<>(experienceResponse, HttpStatus.OK);
					}
				} 
				else
				{
					experienceResponse.setStatusCode("422");
					experienceResponse.setMessage("please provide id");
					return new ResponseEntity<>(experienceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
			}

			
			
			if (experienceRequestObject.getTransactionType().equalsIgnoreCase(FILEUPLOADSTATUS)) 
			{
				if (null != employeeExperienceDetails.getEmployee_id()) {
					int fileuploadEmployeeExperienceDetails = employeeExperienceDetailsDao
							.fileuploadEmployeeExperienceDetails(experienceRequestObject);
					
					logger.debug("received at service by calling the updateEmployeeExperienceDetails is"
							+ fileuploadEmployeeExperienceDetails);

					if (fileuploadEmployeeExperienceDetails > 0) {
			
						if (employeeExperienceDetails.getDocumentsstatus().equalsIgnoreCase(ACCEPTED))
						{
							SendMail(experienceRequestObject,employeeExperienceDetails);
						}
						
						if (employeeExperienceDetails.getDocumentsstatus().equalsIgnoreCase(DECLINE)) 
						{

							SendMail(experienceRequestObject,employeeExperienceDetails);
						}
						experienceResponse.setStatusCode("200");
						experienceResponse.setMessage("Experience Details Status updated Successfully");
						return new ResponseEntity<>(experienceResponse, HttpStatus.OK);
					}
				} 
				else 
				{
					experienceResponse.setStatusCode("422");
					experienceResponse.setMessage("please provide the id");
					return new ResponseEntity<>(experienceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
			}

			
			

			if (experienceRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) 
			{
				if (null != employeeExperienceDetails.getId()) 
				{

					int deleteEmployeeExperienceDetails = employeeExperienceDetailsDao
							.deleteEmployeeExperienceDetails(experienceRequestObject);
					experienceResponse.setStatusCode("409");
					experienceResponse.setMessage("sorry EmployeeExperienceDetails is not deactivated");
					logger.debug("received at service by calling the deleteEmployeeExperienceDetails is"
							+ deleteEmployeeExperienceDetails);
					if (deleteEmployeeExperienceDetails > 0) 
					{
						experienceResponse.setStatusCode("200");
						experienceResponse.setMessage("EmployeeExperienceDetails deleted sucesfully");
						return new ResponseEntity<>(experienceResponse, HttpStatus.OK);
					}
				}
				else 
				{
					experienceResponse.setStatusCode("422");
					experienceResponse.setMessage("please provide the id");
					return new ResponseEntity<>(experienceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}

		}
		return null;

	}

	@Override
	public ResponseEntity<Object> getEmployeeExperienceDetails(ExperienceRequest experienceRequestObject)
			throws SQLException 
	{
		List<EmployeeExperienceDetails> employeeExperienceDetailslist = experienceRequestObject
				.getEmployeeExperienceDetails();
		for (EmployeeExperienceDetails employeeExperienceDetails : employeeExperienceDetailslist) {

	
			 experienceResponse = new ExperienceResponse();
			if (experienceRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)) 
			{
				List<EmployeeExperienceDetails> employeeExperienceDetailsListById = null;
				if (employeeExperienceDetails.getId() == null) 
				{
					employeeExperienceDetailsListById = employeeExperienceDetailsDao
							.getByEmpId(employeeExperienceDetails.getEmployee_id());
					
					logger.debug("received at service by calling the getById method is" + employeeExperienceDetailsListById);
					
					if (!employeeExperienceDetailsListById.isEmpty())
					{
						experienceResponse.setStatusCode("200");
						experienceResponse.setMessage("you got employeeExperienceDetails successfully");
						experienceResponse.setEmployeeExperienceDetails(employeeExperienceDetailsListById);
						return new ResponseEntity<>(experienceResponse, HttpStatus.OK);
                          
					}
					else 
					{
						experienceResponse.setStatusCode("422");
						experienceResponse.setMessage("NO Records Found");
						return new ResponseEntity<>(experienceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
					}

				} 
				else 
				{
					employeeExperienceDetailsListById = employeeExperienceDetailsDao
							.getById(employeeExperienceDetails.getId());
					logger.debug(
							"received at service by calling the getById method is" + employeeExperienceDetailsListById);
					if (!employeeExperienceDetailsListById.isEmpty()) {
						experienceResponse.setStatusCode("200");
						experienceResponse.setMessage("you got employeeExperienceDetails successfully");
						experienceResponse.setEmployeeExperienceDetails(employeeExperienceDetailsListById);
						return new ResponseEntity<>(experienceResponse, HttpStatus.OK);

					} else {
						experienceResponse.setStatusCode("422");
						experienceResponse.setMessage("NO Records Found");
						return new ResponseEntity<>(experienceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
					}

				}

				
			}

			
			if (experienceRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
				List<EmployeeExperienceDetails> employeeExperienceDetailsList;
				employeeExperienceDetailsList = employeeExperienceDetailsDao.getAll();
				logger.debug("received at service by calling the getAllmethod is" + employeeExperienceDetailsList);
				if (!employeeExperienceDetailsList.isEmpty()) {
					experienceResponse.setStatusCode("200");
					experienceResponse.setMessage("you got list of employeeExperienceDetails successfully");
					experienceResponse.setEmployeeExperienceDetails(employeeExperienceDetailsList);
					return new ResponseEntity<>(experienceResponse, HttpStatus.OK);

				} else {
					experienceResponse.setStatusCode("409");
					experienceResponse.setEmployeeExperienceDetails(employeeExperienceDetailsList);
					experienceResponse.setMessage("no record found");
					return new ResponseEntity<>(experienceResponse, HttpStatus.CONFLICT);
				}
			}
		}
		return new ResponseEntity<>(experienceResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	}
	
	
	public Boolean  SendMail(ExperienceRequest experienceRequestObject,EmployeeExperienceDetails employeeexperiencedetails) throws SQLException
	{
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String employee_Id=employeeexperiencedetails.getEmployee_id();
		String mailId = employeeExperienceDetailsDao.getMailId(employee_Id);
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setTo(mailId);
		
		if(experienceRequestObject.getTransactionType().equalsIgnoreCase(SAVE) && employeeexperiencedetails.getDocumentsstatus().equalsIgnoreCase(PENDING))
		{
		
		  mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
		  mailMessage.setText("Hi Employee Id [" + employee_Id
					+ "]  Experience documents are uploaded successfully.");
		}
		
		if(experienceRequestObject.getTransactionType().equalsIgnoreCase(UPDATE) && employeeexperiencedetails.getDocumentsstatus().equalsIgnoreCase(PENDING))
		{
		
		  mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
		  mailMessage.setText("Hi Employee Id [" + employee_Id
					+ "]  Experience documents are updated successfully.");
		}
		
		if(experienceRequestObject.getTransactionType().equalsIgnoreCase(UPDATE) && employeeexperiencedetails.getDocumentsstatus().equalsIgnoreCase(PENDING))
		{
		
		  mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
		  mailMessage.setText("Hi Employee Id [" + employee_Id
					+ "]  Experience documents are updated successfully.");
		}
		
		if(experienceRequestObject.getTransactionType().equalsIgnoreCase(FILEUPLOADSTATUS) && employeeexperiencedetails.getDocumentsstatus().equalsIgnoreCase(ACCEPTED))
		{
		
			mailMessage.setText("Hi this is your Employee Id [" + employee_Id
					+ "] your  Experince documents are accepted.");
		}
		if(experienceRequestObject.getTransactionType().equalsIgnoreCase(FILEUPLOADSTATUS) && employeeexperiencedetails.getDocumentsstatus().equalsIgnoreCase(DECLINE))
		{
		
			mailMessage.setText("Hi this is your Employee Id [" + employee_Id
					+ "] your Experince documents are declined.");
		}
		
	
		mailMessage.setSubject(MESSAGE);
	
		javaMailSender.send(mailMessage);
		
		return true;
		
	}
   
}

