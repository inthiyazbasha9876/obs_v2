package com.ojas.obs.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.facade.EmployeeEducationFacade;
import com.ojas.obs.model.EmployeeEducation;
import com.ojas.obs.modelrequest.EmployeeEducationRequest;
import com.ojas.obs.modelresponse.EmployeeEducationResponse;

@SpringBootConfiguration
public class EmployeeEducationControllerTest {

	@InjectMocks
	private EducationTypeController educationTypeController;

	@Mock
	EmployeeEducationFacade employeeEducationFacade;

	@Spy
	Error err = new Error();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();

	@Spy
	List<EmployeeEducationResponse> listResponse = new ArrayList<EmployeeEducationResponse>();

	@Spy
	EmployeeEducationRequest employeeEducationRequest = new EmployeeEducationRequest();

	@Spy
	List<EmployeeEducation> listReq = new ArrayList<EmployeeEducation>();

	@Before
	public void beforeTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		educationTypeController = new EducationTypeController();
		employeeEducationFacade = mock(EmployeeEducationFacade.class);
		setCollabarator(educationTypeController, "employeeEducationFacade", employeeEducationFacade);
	}

	public void setCollabarator(Object object, String name, Object collabarator)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, collabarator);
	}

	public EmployeeEducationRequest employeeEducationRequest() {

		EmployeeEducation employeeEducation = new EmployeeEducation();

		employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("save");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		return employeeEducationRequest;

	}

	/*
	 * @Test public void saveEmpDetails() throws SQLException { HttpServletRequest
	 * httpReq=null; HttpServletResponse httpResp=null;
	 * 
	 * 
	 * when(employeeEducationFacade.setEmployeeEducationInfo(
	 * employeeEducationRequest())).thenReturn(successEntity);
	 * ResponseEntity<Object> setResponseEntity =
	 * educationTypeController.setEmployeeEductaionInfo(employeeEducationRequest(),
	 * httpReq, httpResp); HttpStatus httpStatus =
	 * setResponseEntity.getStatusCode(); assertEquals(HttpStatus.OK, httpStatus); }
	 */

	@Test
	public void saveNullDetails() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		employeeEducation.setEducationType(null);
		employeeEducation.setId(null);
		listReq.add(null);
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("save");
		employeeEducationRequest.setListEmployeeEducations(null);

		when(employeeEducationFacade.setEmployeeEducationInfo(employeeEducationRequest)).thenReturn(responseEntity);
		ResponseEntity<Object> setResponseEntity = educationTypeController
				.setEmployeeEductaionInfo(employeeEducationRequest, httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@Test
	public void deleteUpdateNullDetails() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		employeeEducation.setEducationType(null);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("delete");
		listReq.add(employeeEducation);
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationFacade.setEmployeeEducationInfo(employeeEducationRequest)).thenReturn(responseEntity);
		ResponseEntity<Object> setResponseEntity = educationTypeController
				.setEmployeeEductaionInfo(employeeEducationRequest, httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@Test
	public void deleteNullDetails() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		employeeEducation.setId(1);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("delet");
		listReq.add(employeeEducation);
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationFacade.setEmployeeEducationInfo(employeeEducationRequest)).thenReturn(responseEntity);
		ResponseEntity<Object> setResponseEntity = educationTypeController
				.setEmployeeEductaionInfo(employeeEducationRequest, httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void ExceptionDuplicateTest() throws SQLException {

		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;
		when(employeeEducationFacade.setEmployeeEducationInfo(anyObject()))
				.thenThrow(new DuplicateKeyException(null, new RuntimeException()));
		ResponseEntity<Object> setResponseEntity = educationTypeController
				.setEmployeeEductaionInfo(employeeEducationRequest(), httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void ExceptionSQlTest() throws SQLException {

		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		when(employeeEducationFacade.setEmployeeEducationInfo(anyObject()))
				.thenThrow(new SQLException(null, new Throwable()));
		ResponseEntity<Object> setResponseEntity = educationTypeController
				.setEmployeeEductaionInfo(employeeEducationRequest(), httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void ExceptionTest() throws SQLException {

		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		when(employeeEducationFacade.setEmployeeEducationInfo(anyObject())).thenThrow(new RuntimeException());
		ResponseEntity<Object> setResponseEntity = educationTypeController
				.setEmployeeEductaionInfo(employeeEducationRequest(), httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	/// get transaction type
	@Test
	public void getNullTranDetails() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		listReq.add(employeeEducation);
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("get");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationFacade.getEmployeeEducationInfo(employeeEducationRequest)).thenReturn(responseEntity);
		ResponseEntity<Object> setResponseEntity = educationTypeController.getEductionDetails(employeeEducationRequest,
				httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@Test
	public void getNullDetails() throws SQLException {

		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		when(employeeEducationFacade.getEmployeeEducationInfo(null)).thenReturn(responseEntity);
		ResponseEntity<Object> setResponseEntity = educationTypeController.getEductionDetails(null, httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getExceptionTest() throws SQLException {
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;

		when(employeeEducationFacade.getEmployeeEducationInfo(anyObject())).thenThrow(new RuntimeException());
		ResponseEntity<Object> setResponseEntity = educationTypeController.getEductionDetails(employeeEducationRequest,
				httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getSQLException() throws SQLException {
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;
		EmployeeEducation employeeEducation = new EmployeeEducation();
		listReq.add(employeeEducation);
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getAll");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationFacade.getEmployeeEducationInfo(anyObject()))
				.thenThrow(new SQLException(null, new Throwable()));
		ResponseEntity<Object> setResponseEntity = educationTypeController.getEductionDetails(employeeEducationRequest,
				httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getDupliException() throws SQLException {
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;
		EmployeeEducation employeeEducation = new EmployeeEducation();
		listReq.add(employeeEducation);
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getAll");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationFacade.getEmployeeEducationInfo(anyObject()))
				.thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setResponseEntity = educationTypeController.getEductionDetails(employeeEducationRequest,
				httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getEducationType() throws SQLException {
		HttpServletRequest httpReq = null;
		HttpServletResponse httpResp = null;
		EmployeeEducation employeeEducation = new EmployeeEducation();
		listReq.add(employeeEducation);
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getAll");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationFacade.getEmployeeEducationInfo(anyObject())).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = educationTypeController.getEductionDetails(employeeEducationRequest,
				httpReq, httpResp);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}
}
