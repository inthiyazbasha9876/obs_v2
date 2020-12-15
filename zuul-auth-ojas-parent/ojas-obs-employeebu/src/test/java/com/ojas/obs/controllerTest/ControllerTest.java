package com.ojas.obs.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import com.ojas.obs.controller.EmployeeBUController;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.EmpoyeeBUFacade;
import com.ojas.obs.model.EmployeeBUDetails;
import com.ojas.obs.request.EmployeeBUDetailsRequest;
import com.ojas.obs.response.EmployeeBUDeatailsResponse;

public class ControllerTest {

	@Mock
	EmpoyeeBUFacade buFacade;

	@InjectMocks
	private EmployeeBUController buController;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	EmployeeBUDetailsRequest buRequest;

	@Spy
	EmployeeBUDeatailsResponse buResponse;

	@Spy
	List<EmployeeBUDetails> buList;

	@Spy
	EmployeeBUDetails bu;

	@Before
	public void init() {
		buController = new EmployeeBUController();
		buFacade = mock(EmpoyeeBUFacade.class);
		setCollaborator(buController, "employeebuFacade", buFacade);
	}

	private void setCollaborator(Object object, String name, Object service) {

		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);

			field.set(object, service);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public EmployeeBUDetailsRequest buRequest() {
		buRequest = new EmployeeBUDetailsRequest();
		EmployeeBUDetails bu1 = new EmployeeBUDetails();
		bu1.setId(1);
		bu1.setStatus("admin");
		bu1.setEmployeeId("abc");
		bu1.setFlag(true);
		bu1.setSbu(3);
		bu1.setCreatedby("mnb");
		bu1.setCreateddate(new Timestamp(21101998));
//		bu1.setUpdatedby("ngfd");
//		bu1.setUpdateddate(new Timestamp(21101998));
		List<EmployeeBUDetails> List = new ArrayList<>();
		List.add(bu1);
		buRequest.setTransactionType("save");
		buRequest.setEmployeeBUDeatils(List);
		return buRequest;
	}

	@Test
	public void setBuSuccess() throws Exception {
		buRequest = buRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.setEmployeeBU(buRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveBu = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setBuNullTest() throws Exception {
		buRequest = buRequest();
		List<EmployeeBUDetails> List = buRequest.getEmployeeBUDeatils();
		EmployeeBUDetails bu = new EmployeeBUDetails();
		bu.setEmployeeId(null);
		bu.setSbu(null);
		List.add(bu);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.setEmployeeBU(buRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveBu = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBuTransactionNullTest() throws Exception {
		buRequest = buRequest();
		buRequest.setTransactionType("");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.setEmployeeBU(buRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveBu = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBuUpdateCondition() throws Exception {
		buRequest = buRequest();
		EmployeeBUDetails bu = new EmployeeBUDetails();
		bu.setUpdatedby("aaa");
		bu.setId(null);
		bu.setSbu(3);
		bu.setStatus("false");
		bu.setUpdateddate(new Timestamp(21101998));
		buRequest.setTransactionType("update");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.setEmployeeBU(buRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveBu = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setTestDeleteCondition() throws Exception {
		buRequest = buRequest();
		List<EmployeeBUDetails> List = buRequest.getEmployeeBUDeatils();
		EmployeeBUDetails bu = new EmployeeBUDetails();
		bu.setId(null);
		List.add(bu);
		buRequest.setTransactionType("delete");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.setEmployeeBU(buRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setTestDeleteConditionFail() throws Exception {
		buRequest = buRequest();
		List<EmployeeBUDetails> List = buRequest.getEmployeeBUDeatils();
		EmployeeBUDetails bu = new EmployeeBUDetails();
		bu.setId(123);
		List.add(bu);
		buRequest.setTransactionType("delete");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.setEmployeeBU(buRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveRole = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setBuExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.getEmployeeBUDetails(buRequest)).thenThrow(SQLException.class);
		ResponseEntity<Object> saveRole = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setbuObjectNullCheckTest() throws Exception {
		buRequest = new EmployeeBUDetailsRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		buRequest.setTransactionType("save");
		when(buFacade.setEmployeeBU(buRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveBu = buController.setEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getBuSuccess() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		buRequest = new EmployeeBUDetailsRequest();
		buRequest.setTransactionType("getall");
		when(buFacade.getEmployeeBUDetails(buRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveBu = buController.getEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdSuccess() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		buRequest = new EmployeeBUDetailsRequest();
		buRequest.setTransactionType("getbyid");
		when(buFacade.getEmployeeBUDetails(buRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveBu = buController.getEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getTransactionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		buRequest = buRequest();
		when(buFacade.getEmployeeBUDetails(buRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveBu = buController.getEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(buFacade.getEmployeeBUDetails(buRequest)).thenThrow(SQLException.class);
		ResponseEntity<Object> saveBu = buController.getEmployeeBUDetails(buRequest, request, response);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

}
