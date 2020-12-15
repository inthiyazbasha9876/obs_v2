package com.ojas.obs.daoImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ojas.obs.constants.PropsReaderUtil;
import com.ojas.obs.dao.ExperienceDao;
import com.ojas.obs.model.EmployeeExperienceDetails;
import com.ojas.obs.request.ExperienceRequest;
import com.ojas.obs.rowmapper.ExperienceRowMappers;
import static com.ojas.obs.constants.UserConstants.GETMAILID; 

@Component
public class ExperienceDaoImpl implements ExperienceDao {

	

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	PropsReaderUtil propsReaderUtil;

	// -------------------------SAVE METHOD------------------------------------

	@Override
	public int saveEmployeeExperienceDetails(ExperienceRequest experienceRequestObject) throws SQLException {
		List<EmployeeExperienceDetails> employeeExperienceDetailsList = experienceRequestObject
				.getEmployeeExperienceDetails();
		
		Timestamp stamp = new Timestamp(new java.util.Date().getTime());

	
		

		List<Object[]> inputList = new ArrayList<>();

		for (EmployeeExperienceDetails employeeExperienceDetails : employeeExperienceDetailsList) {

			Object[] params = new Object[] { employeeExperienceDetails.getCompany_name(),
					employeeExperienceDetails.getJoining_date(), employeeExperienceDetails.getExit_date(),
					employeeExperienceDetails.getSalary(), employeeExperienceDetails.getLocation(),
					employeeExperienceDetails.getIs_current_company(), employeeExperienceDetails.getEmployee_id(),
					employeeExperienceDetails.getReference_1_name(),
					employeeExperienceDetails.getReference_1_contact(),
					employeeExperienceDetails.getReference_2_name(),
					employeeExperienceDetails.getReference_2_contact(), employeeExperienceDetails.isFlag(),
					employeeExperienceDetails.getCreated_by(), stamp, 
					employeeExperienceDetails.getImage(), employeeExperienceDetails.getExperience(),
					employeeExperienceDetails.getStatus(), employeeExperienceDetails.getDocumentsstatus() };
			inputList.add(params);

		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(propsReaderUtil.getValue("insert_employee_experience_details"),
				inputList);
		if (batchUpdate.length > 0) {
			return 1;
		}
		return 0;

	}


	
	// -------------------------UPDATE METHOD------------------------------------

	@Override
	public int updateEmployeeExperienceDetails(ExperienceRequest experienceRequestObject) throws SQLException {
		List<EmployeeExperienceDetails> employeeExperienceDetailsList = experienceRequestObject
				.getEmployeeExperienceDetails();
		List<Object[]> inputList = new ArrayList<>();

		Timestamp stamp = new Timestamp(new java.util.Date().getTime());
	

		for (EmployeeExperienceDetails employeeExperienceDetails : employeeExperienceDetailsList) {
			Object[] params = new Object[] { 
					
					employeeExperienceDetails.getEmployee_id(),
				   employeeExperienceDetails.getUpdated_by(),employeeExperienceDetails.getUpdated_date() , 
				    employeeExperienceDetails.getImage(),
					 employeeExperienceDetails.getDocumentsstatus(),employeeExperienceDetails.getId()
					};
			inputList.add(params);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(propsReaderUtil.getValue("update_employee_experience_details"),
				inputList);
		if (batchUpdate.length > 0) {
			return 1;
		}

		return 0;
	}
	
	//fileupload
	
	@Override
	public int fileuploadEmployeeExperienceDetails(ExperienceRequest experienceRequestObject) throws SQLException 
	{
		List<EmployeeExperienceDetails> employeeExperienceDetailsList = experienceRequestObject
				.getEmployeeExperienceDetails();
		List<Object[]> inputList = new ArrayList<>();

		
	

		for (EmployeeExperienceDetails employeeExperienceDetails : employeeExperienceDetailsList) {
			Object[] params = new Object[] { 
					
					employeeExperienceDetails.getDocumentsstatus(),
					employeeExperienceDetails.getEmployee_id()};
			inputList.add(params);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(propsReaderUtil.getValue("fileupload_employee_experience_details"),
				inputList);
		if (batchUpdate.length > 0) {
			return 1;
		}

		return 0;
	}

	// -------------------------DELETE METHOD------------------------------------

	@Override
	public int deleteEmployeeExperienceDetails(ExperienceRequest experienceRequestObject) throws SQLException {
		List<EmployeeExperienceDetails> employeeExperienceDetailsList = experienceRequestObject
				.getEmployeeExperienceDetails();
		List<Object[]> inputList = new ArrayList<>();

		for (EmployeeExperienceDetails employeeExperienceDetails : employeeExperienceDetailsList) {
			Object[] params = new Object[] { employeeExperienceDetails.isFlag(), employeeExperienceDetails.getUpdated_by(),
					employeeExperienceDetails.getId() };
			inputList.add(params);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(propsReaderUtil.getValue("delete_employee_experience_details"),
				inputList);

		if (batchUpdate.length > 0) {
			return 1;
		}
		return 0;
	}

	// -------------------------GETBYID METHOD------------------------------------

	@Override
	public List<EmployeeExperienceDetails> getById(Integer id) throws SQLException {
		Object[] params = new Object[] { id };
		return  jdbcTemplate.query(
				propsReaderUtil.getValue("getById_employee_experience_details"), params, new ExperienceRowMappers());

	

	}
	
	
	// -------------------------GETALL METHOD------------------------------------

	@Override
	public List<EmployeeExperienceDetails> getAll() throws SQLException {

		return jdbcTemplate
				.query(propsReaderUtil.getValue("getAll_employee_experience_details"), new ExperienceRowMappers());



	}

	@Override
	public List<EmployeeExperienceDetails> getByEmpId(String employeeId) throws SQLException {
		Object[] params = new Object[] { employeeId };
		return jdbcTemplate.query(
				propsReaderUtil.getValue("getByEmpId_employee_experience_detailss"), params,
				new ExperienceRowMappers());

		

	}

	public String getMailId(String empId) throws SQLException {
		logger.debug("Inside getMail method in dao");
		String mail = jdbcTemplate.queryForObject(GETMAILID + empId, String.class);
		return mail;
	}

}
