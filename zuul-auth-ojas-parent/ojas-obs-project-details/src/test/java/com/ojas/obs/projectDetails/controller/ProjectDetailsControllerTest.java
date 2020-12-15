package com.ojas.obs.projectDetails.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ojas.obs.projectDetails.Exception.ProjectDetailsException;
import com.ojas.obs.projectDetails.facade.ProjectDetailsFacade;
import com.ojas.obs.projectDetails.facadeImpl.ProjectDetailsFacadeImpl;
import com.ojas.obs.projectDetails.model.ErrorResponse;
import com.ojas.obs.projectDetails.model.ProjectDetails;
import com.ojas.obs.projectDetails.request.ProjectDetailsRequest;
import com.ojas.obs.projectDetails.response.ProjectDetailsResponse;

public class ProjectDetailsControllerTest {

	@Mock
	ProjectDetailsFacade projectDetailsFacade;
	@Mock
	ProjectDetailsFacadeImpl projectDetailsFacadeImpl;

	@InjectMocks
	ProjectDetailsController projectDetailsController;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	ProjectDetailsRequest projectDetailsRequest = new ProjectDetailsRequest();

	@Spy
	ProjectDetailsResponse projectDetailsResponse = new ProjectDetailsResponse();

	@Spy
	List<ProjectDetails> projectDetailsList = new ArrayList<>();

	@Before
	public void init() throws SecurityException, Exception {
		projectDetailsController = new ProjectDetailsController();

		projectDetailsFacadeImpl = mock(ProjectDetailsFacadeImpl.class);
		setCollaborator(projectDetailsController, "projectDetailsFacade", projectDetailsFacadeImpl);
	}

	private void setCollaborator(ProjectDetailsController projectDetailsController, String projectDetailsFacade,
			ProjectDetailsFacadeImpl projectDetailsFacadeImpl) throws Exception, SecurityException {
		Field field;
			field = projectDetailsController.getClass().getDeclaredField(projectDetailsFacade);
			field.setAccessible(true);
			field.set(projectDetailsController, projectDetailsFacadeImpl);
	}

//	public projectDetailsRequest> projectDetailsList() {
//
//		projectDetailsRequest = new ProjectDetailsRequest();
//		List<ProjectDetails> list = new ArrayList<ProjectDetails>();
//		ProjectDetails projectDetails = new ProjectDetails();
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//		projectDetails.setContractId(2);
//		projectDetails.setRateId(3);
//		projectDetails.setStartDate(new Timestamp(dateFormat.parse("2019-02-28").getTime()));
//		projectDetails.setEndDate(new Timestamp(dateFormat.parse("2019-03-28").getTime()));
//		projectDetails.setBillingId(987);
//		projectDetails.setEmployeeId("877");
//		projectDetails.setProjectTechStack("project");
//		projectDetails.setCustomer("ojas");
//		projectDetails.setLocation("hyd");
//		projectDetails.setGstApplicable(true);
//		projectDetails.setProjectType("running");
//		projectDetails.setProjectStatus("internal");
//		projectDetails.setBdmContact("emp2");
//		projectDetails.setInternal(true);
//		projectDetails.setFlag(true);
//		projectDetails.setCreatedBy("877");
//		projectDetails.setUpdatedBy("877");
//		list.add(projectDetails);
//		projectDetailsRequest.setProjectDetailsList(list);
//		projectDetailsRequest.setTransactionType("save");
//
//		return projectDetailsRequest;
//
//	}
	public ProjectDetailsRequest projectDetailsRequest() throws ParseException {

		projectDetailsRequest = new ProjectDetailsRequest();
		List<ProjectDetails> list = new ArrayList<ProjectDetails>();
		ProjectDetails projectDetails = new ProjectDetails();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		//String strDate = dateFormat.format(dateFormat);  
		
		projectDetails.setContractId(2);
		projectDetails.setRateId(3);
		//projectDetails.setStartDate(new Date(dateFormat.parse("2019-02-28").getTime()));
		projectDetails.setStartDate(new String("2019-02-28"));
		//projectDetails.setEndDate(new Date(dateFormat.parse("2019-03-28").getTime()));
		projectDetails.setEndDate(new String("2019-03-28"));
		projectDetails.setBillingId(987);
		projectDetails.setEmployeeId("877");
		projectDetails.setProjectTechStack("project");
		projectDetails.setCustomer("ojas");
		projectDetails.setLocation("hyd");
		projectDetails.setGstApplicable(true);
		projectDetails.setProjectType("running");
		projectDetails.setProjectStatus("internal");
		projectDetails.setBdmContact("emp2");
		projectDetails.setInternal(true);
		projectDetails.setFlag(true);
		projectDetails.setCreatedBy("877");
		projectDetails.setUpdatedBy("877");
		list.add(projectDetails);
		projectDetailsRequest.setProjectDetailsList(list);
		projectDetailsRequest.setTransactionType("save");

		return projectDetailsRequest;

	}

	@Test
	public void testSetProjectDetails()
			throws SQLException, ProjectDetailsException, ParseException, JsonProcessingException {

		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();

		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(projectDetailsFacadeImpl.setProjectDetails(projectDetailsRequestObject))
				.thenReturn(projectDetailsResponse);

		ResponseEntity<Object> setProjectDetails = projectDetailsController
				.setProjectDetails(projectDetailsRequestObject, request, response);
		HttpStatus status = setProjectDetails.getStatusCode();

		assertEquals(HttpStatus.OK, status);

	}

//	@Test
//	public void testGetProjectDetails() throws ParseException, SQLException {
//		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
//
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//
//		projectDetailsRequestObject.setTransactionType("getAll");
//		projectDetailsRequestObject.setProjectDetailsList(null);
//
//		when(projectDetailsFacadeImpl.getProjectDetails(projectDetailsRequestObject))
//				.thenReturn(projectDetailsResponse);
//		ResponseEntity<Object> getProjectDetails = projectDetailsController
//				.getProjectDetails(projectDetailsRequestObject, request, response);
//		HttpStatus status = getProjectDetails.getStatusCode();
//
//		assertEquals(HttpStatus.OK, status);
//
//	}

//	@Test(expected = ProjectDetailsException.class)
//	public void setProjectDetailsObjectNullCheck()
//			throws SQLException, ProjectDetailsException, ParseException, InvalidFormatException {
//		HttpServletRequest servletRequest = null;
//		HttpServletResponse servletResponse = null;
//		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
//		projectDetailsRequestObject = null;
//		projectDetailsController.setProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
//
//	}

	@Test
	public void setProjectDetailsTransactionNullCheck()
			throws ParseException, SQLException, InvalidFormatException, ProjectDetailsException {
		HttpServletRequest servletRequest = null;
		HttpServletResponse servletResponse = null;
		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
		projectDetailsRequestObject.setTransactionType(null);

		ResponseEntity<Object> setProjectDetails = projectDetailsController
				.setProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
		HttpStatus statusCode = setProjectDetails.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setProjectDetailsListNullCheck()
			throws ParseException, SQLException, InvalidFormatException, ProjectDetailsException {
		HttpServletRequest servletRequest = null;
		HttpServletResponse servletResponse = null;
		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
		projectDetailsRequestObject.setTransactionType("save");
		projectDetailsRequestObject.setProjectDetailsList(null);

		ResponseEntity<Object> setProjectDetails = projectDetailsController
				.setProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
		HttpStatus statusCode = setProjectDetails.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setProjectDetailsUpdateIdNullCheck()
			throws ParseException, SQLException, InvalidFormatException, ProjectDetailsException {
		HttpServletRequest servletRequest = null;
		HttpServletResponse servletResponse = null;
		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
		projectDetailsRequestObject.setTransactionType("update");
		projectDetailsRequestObject.getProjectDetailsList().get(0).setId(null);
		ResponseEntity<Object> setProjectDetails = projectDetailsController
				.setProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
		HttpStatus statusCode = setProjectDetails.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setProjectDetailsDeleteIdNullCheck()
			throws ParseException, SQLException, InvalidFormatException, ProjectDetailsException {
		HttpServletRequest servletRequest = null;
		HttpServletResponse servletResponse = null;
		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
		projectDetailsRequestObject.setTransactionType("delete");
		projectDetailsRequestObject.getProjectDetailsList().get(0).setId(null);
		ResponseEntity<Object> setProjectDetails = projectDetailsController
				.setProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
		HttpStatus statusCode = setProjectDetails.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUnexpectedTransactionTyprCheck()
			throws ParseException, SQLException, InvalidFormatException, ProjectDetailsException {
		HttpServletRequest servletRequest = null;
		HttpServletResponse servletResponse = null;
		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
		projectDetailsRequestObject.setTransactionType("rrr");
		projectDetailsRequestObject.getProjectDetailsList().get(0).setId(null);
		ResponseEntity<Object> setProjectDetails = projectDetailsController
				.setProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
		HttpStatus statusCode = setProjectDetails.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}
//	@Test
//	public void setExceptionTest() throws Exception {
//	HttpServletRequest request = null;
//	HttpServletResponse response = null;
//	ProjectDetailsRequest businessUnitsRequest = projectDetailsRequest();
//	businessUnitsRequest.setProjectDetailsList(this.projectDetailsList());
//	businessUnitsRequest.setTransactionType("save");
//	try {
//	when(projectDetailsFacade.setProjectDetails(businessUnitsRequest).thenThrow(new RuntimeException()));
//	}catch (Exception e) {
//		// TODO: handle exception
//		e.printStackTrace();
//	}
//	ResponseEntity<Object> setBus = projectDetailsController.setProjectDetails(businessUnitsRequest, request, response);
//	HttpStatus unitCode = setBus.getStatusCode();
//	assertEquals(HttpStatus.OK, unitCode);
//	}
	@Test
	public void testSetProjectDetailsCatch()
			throws SQLException, ProjectDetailsException, ParseException, JsonProcessingException {

		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();

		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(projectDetailsFacadeImpl.setProjectDetails(projectDetailsRequestObject))
				.thenThrow(new RuntimeException());

		ResponseEntity<Object> setProjectDetails = projectDetailsController
				.setProjectDetails(projectDetailsRequestObject, request, response);
		HttpStatus status = setProjectDetails.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

//	@Test
//	public void getProjectDetailsObjectNullCheck()
//			throws SQLException, ProjectDetailsException, ParseException, InvalidFormatException {
//		HttpServletRequest servletRequest = null;
//		HttpServletResponse servletResponse = null;
//		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
//		projectDetailsRequestObject = null;
//		ResponseEntity<Object> setProjectDetails = projectDetailsController
//				.getProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
//		HttpStatus statusCode = setProjectDetails.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}

//	@Test
//	public void getProjectDetailsTransactionNullCheck()
//			throws ParseException, SQLException, InvalidFormatException, ProjectDetailsException {
//		HttpServletRequest servletRequest = null;
//		HttpServletResponse servletResponse = null;
//		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
//		projectDetailsRequestObject.setTransactionType(null);
//
//		ResponseEntity<Object> setProjectDetails = projectDetailsController
//				.getProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
//		HttpStatus statusCode = setProjectDetails.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}

//	@Test
//	public void getProjectDetailsIdNullCheck()
//			throws ParseException, SQLException, InvalidFormatException, ProjectDetailsException {
//		HttpServletRequest servletRequest = null;
//		HttpServletResponse servletResponse = null;
//		ProjectDetailsRequest projectDetailsRequestObject = projectDetailsRequest();
//		projectDetailsRequestObject.setTransactionType("save");
//		projectDetailsRequestObject.getProjectDetailsList().get(0).setId(null);
//
//		ResponseEntity<Object> setProjectDetails = projectDetailsController
//				.getProjectDetails(projectDetailsRequestObject, servletRequest, servletResponse);
//		HttpStatus statusCode = setProjectDetails.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
	
//	@Test
//	public void testGetProjectDetailsCatch() throws ParseException, SQLException {
//		ProjectDetailsRequest projectDetailsRequestObject = null;
//
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//
//		//projectDetailsRequestObject.setTransactionType("getAll");
//		//projectDetailsRequestObject.setProjectDetailsList(null);
//
//		when(projectDetailsFacadeImpl.getProjectDetails(projectDetailsRequestObject))
//				.thenThrow(new RuntimeException());
//		ResponseEntity<Object> getProjectDetails = projectDetailsController.getProjectDetails(projectDetailsRequestObject, request, response);
//		HttpStatus status = getProjectDetails.getStatusCode();
//
//		assertEquals( HttpStatus.UNPROCESSABLE_ENTITY, status);
//
//	}
//	
	
//	@Test
//	public void PassportSQLExcpTest() throws SQLException {
//		ProjectDetailsRequest passportRequest2 = new ProjectDetailsRequest();
//	passportRequest2.setTransactionType("delete");
//	HttpServletRequest request = null;
//	HttpServletResponse response = null;
//	
//	when(projectDetailsFacadeImpl.getProjectDetails(projectDetailsRequest)).thenThrow(SQLException.class);
//	ResponseEntity<Object> setPassport =
//			projectDetailsController.getProjectDetails(passportRequest2, request, response);
//	System.out.println(setPassport);
//	HttpStatus statusCode = setPassport.getStatusCode();
//	System.out.println(statusCode);
//	assertEquals(HttpStatus.CONFLICT, setPassport.getStatusCode());
//	}


}
