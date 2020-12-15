package com.ojas.obs.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.EmployeeEducationDao;
import com.ojas.obs.facadeimpl.EmployeeEducationFacadeImpl;
import com.ojas.obs.model.EmployeeEducation;
import com.ojas.obs.modelrequest.EmployeeEducationRequest;
import com.ojas.obs.modelresponse.EmployeeEducationResponse;

@SpringBootConfiguration
public class EmployeeEducationServiceTest {

	@InjectMocks
	private EmployeeEducationFacadeImpl employeeEducationFacadeImpl;

	@Mock
	EmployeeEducationDao employeeEducationDao;

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
		employeeEducationFacadeImpl = new EmployeeEducationFacadeImpl();
		employeeEducationDao = mock(EmployeeEducationDao.class);
		setCollabarator(employeeEducationFacadeImpl, "employeeEducationDao", employeeEducationDao);
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

	@Test
	public void setEducationType() throws SQLException {

		when(employeeEducationDao.saveEmployeeEducation(employeeEducationRequest())).thenReturn(true);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.setEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}

	@Test
	public void setEducationError() throws SQLException {

		when(employeeEducationDao.saveEmployeeEducation(employeeEducationRequest())).thenReturn(false);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.setEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void deleteEducationTypeErr() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();

		employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("delete");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationDao.saveEmployeeEducation(employeeEducationRequest)).thenReturn(false);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.setEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void deleteEducationType() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();

		employeeEducation.setId(1);

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("delete");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationDao.deleteEmployeeEducation(1)).thenReturn(true);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.setEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}

	@Test
	public void updateEducationType() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();

		employeeEducation.setId(1);

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("update");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationDao.updateEmployeeEducation(employeeEducationRequest)).thenReturn(true);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.setEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}

	@Test
	public void updateEducationTypeErr() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();

		employeeEducation.setId(1);

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("update");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationDao.updateEmployeeEducation(null)).thenReturn(true);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.setEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void setEducationTypeErr() throws SQLException {

		EmployeeEducation employeeEducation = new EmployeeEducation();

		employeeEducation.setId(1);

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("sav");
		employeeEducationRequest.setListEmployeeEducations(listReq);

		when(employeeEducationDao.updateEmployeeEducation(null)).thenReturn(true);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.setEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	// get education type

	@Test
	public void getEducationType() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getall");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(employeeEducationDao.getAllEmployeeEducation(employeeEducationRequest)).thenReturn(listReq);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.getEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}

	@Test
	public void getIdEducationType() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();

		// employeeEducation.setEducationType("Java");
		employeeEducation.setId(1);

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getall");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(employeeEducationDao.getAllEmployeeEducation(employeeEducationRequest)).thenReturn(listReq);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.getEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}

	@Test
	public void getNullEducationType() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();

		// employeeEducation.setEducationType("Java");
		// employeeEducation.setId(null);

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getall");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(employeeEducationDao.getAllEmployeeEducation(employeeEducationRequest)).thenReturn(null);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.getEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void getAllRecEducationType() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();

		// employeeEducation.setEducationType("Java");
		employeeEducation.setId(1);

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getall");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(employeeEducationDao.getAllEmployeeEducation(employeeEducationRequest)).thenReturn(null);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.getEmployeeEducationInfo(employeeEducationRequest());
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void getAllRe1cEducationType() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();

		 employeeEducation.setEducationType("Java"); employeeEducation.setId(1);

		listReq.add(employeeEducation);
		List<EmployeeEducation> eduList = null;
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setListEmployeeEducations(listReq);
		employeeEducationRequest.setTransactionType("getall");
		//employeeEducationRequest.setListEmployeeEducations(null);
		when(employeeEducationDao.getAllEmployeeEducation(employeeEducationRequest)).thenReturn(eduList);
		ResponseEntity<Object> respEntity = employeeEducationFacadeImpl
				.getEmployeeEducationInfo(employeeEducationRequest);
		HttpStatus httpStatus = respEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

}
