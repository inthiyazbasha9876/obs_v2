package com.ojas.obs.controllertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.controller.EmployeeStatusController;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.EmployeeStatusFacade;
import com.ojas.obs.facadeimpl.EmployeeStatusFacadeImpl;
import com.ojas.obs.model.EmployeeStatus;
import com.ojas.obs.request.EmployeeStatusRequest;
import com.ojas.obs.response.EmployeeStatusResponse;

public class EmployeeStatusControllerTest {
	@InjectMocks
	EmployeeStatusController statusController;
	@Mock
	EmployeeStatusFacadeImpl employeeStatusFacadeImpl;

	@Mock
	EmployeeStatusFacade employeeStatusFacade;

	@Spy
	EmployeeStatusRequest employeeStatusRequest;
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	EmployeeStatusResponse employeeStatusResponse = new EmployeeStatusResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(employeeStatusResponse, HttpStatus.OK);
	@Spy
	EmployeeStatus employeeStatus;

	@Before
	public void init() throws Exception {
		statusController = new EmployeeStatusController();
		employeeStatusFacadeImpl = mock(EmployeeStatusFacadeImpl.class);
		setCollaborator(statusController, "employeeStatusFacade", employeeStatusFacadeImpl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<EmployeeStatus> getEmpStatusList() {
		List<EmployeeStatus> empStatusList = new ArrayList<EmployeeStatus>();
		EmployeeStatus employeeStatus = new EmployeeStatus();
		employeeStatus.setId(1);
		employeeStatus.setStatus("Active");
		empStatusList.add(employeeStatus);
		return empStatusList;
	}

	@Test
	public void setEmployeeStatusNullList() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpStatusNullType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpStatusEmptyType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.setTransactionType("");
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setNullEmpStatus() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		List<EmployeeStatus> empStatusList = new ArrayList<EmployeeStatus>();
		EmployeeStatus empStatus = null;
		empStatusList.add(empStatus);
		employeeStatusRequest.setEmployeeStatus(empStatusList);
		employeeStatusRequest.setTransactionType("save");
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setSaveStatusNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.getEmployeeStatus().get(0).setStatus(null);
		employeeStatusRequest.setTransactionType("save");
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setSaveStatusEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.getEmployeeStatus().get(0).setStatus("");
		employeeStatusRequest.setTransactionType("save");
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setUpdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.setTransactionType("update");
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	//@Test
	public void setUpdateIdNullCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.getEmployeeStatus().get(0).setId(null);
		employeeStatusRequest.setTransactionType("update");
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setUpdateStatusNullCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		EmployeeStatus empStatus = new EmployeeStatus();
		EmployeeStatus empStatus1 = new EmployeeStatus();
		empStatus.setId(4);
		empStatus.setStatus("");
		empStatus1.setId(5);
		List<EmployeeStatus> empStatusList = new ArrayList<EmployeeStatus>();
		empStatusList.add(empStatus);
		empStatusList.add(empStatus1);
		employeeStatusRequest.setEmployeeStatus(empStatusList);
		employeeStatusRequest.setTransactionType("update"); //
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpStatusUpdateStatusIdNullCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		List<EmployeeStatus> employeeStatus = new ArrayList<EmployeeStatus>();
		EmployeeStatus empStatus = new EmployeeStatus();
		EmployeeStatus empStatus1 = new EmployeeStatus();
		empStatus.setId(8);
		empStatus.setStatus("abc");
		// empStatus1.setId(6); //empStatus1.setStatus("def");
		employeeStatus.add(empStatus);
		employeeStatus.add(empStatus1);
		employeeStatusRequest.setEmployeeStatus(employeeStatus);
		employeeStatusRequest.setTransactionType("update");
		// when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmployeeStatusTestCatch() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.setTransactionType("save");
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setEmp = statusController.setEmployeeStatus(employeeStatusRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testSetEmployeeStatusSave() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.setTransactionType("save");
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> saveStatus = statusController.setEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void testSetEmployeeStatusSaveNull() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		List<EmployeeStatus> employeeStatus = getEmpStatusList();
		employeeStatus.get(0).setStatus(null);
		employeeStatusRequest.setEmployeeStatus(employeeStatus);
		employeeStatusRequest.setTransactionType("save");
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> saveStatus = statusController.setEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setSaveStatusEmptyTest() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		List<EmployeeStatus> employeeStatus = getEmpStatusList();
		employeeStatus.get(0).setStatus("");
		employeeStatusRequest.setEmployeeStatus(employeeStatus);
		employeeStatusRequest.setTransactionType("save");
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> saveStatus = statusController.setEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDupCatchTest() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.setTransactionType("save");
		Throwable cause = new Throwable();
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenThrow(new DuplicateKeyException(null, cause));
		ResponseEntity<Object> saveStatus = statusController.setEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	
	@Test
	public void setSQLCatchTest() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.setTransactionType("save");
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> saveStatus = statusController.setEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	
	@Test
	public void setCatchTest() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		employeeStatusRequest.setTransactionType("save");
		when(employeeStatusFacadeImpl.setEmployeeStatus(employeeStatusRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> saveStatus = statusController.setEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	
	@Test
	public void testGetEmployeeStatus() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setTransactionType("getall");
		when(employeeStatusFacadeImpl.getEmployeeStatus(employeeStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> saveStatus = statusController.getEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void testGetEmployeeStatusById() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setTransactionType("getbyid");
		employeeStatusRequest.setEmployeeStatus(getEmpStatusList());
		when(employeeStatusFacadeImpl.getEmployeeStatus(employeeStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> saveStatus = statusController.getEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void testGetEmployeeStatusByIdNull() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setTransactionType("getbyid");
		List<EmployeeStatus> empList = getEmpStatusList();
		empList.get(0).setId(null);
		employeeStatusRequest.setEmployeeStatus(empList);
		when(employeeStatusFacadeImpl.getEmployeeStatus(employeeStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> saveStatus = statusController.getEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void testGetEmployeeStatusTypeEmpty() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setTransactionType("");
		when(employeeStatusFacadeImpl.getEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveStatus = statusController.getEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
	}

	@Test
	public void testGetEmployeeStatusTypeNull() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		// employeeStatusRequest.setTransactionType("");
		when(employeeStatusFacadeImpl.getEmployeeStatus(employeeStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveStatus = statusController.getEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
	}
	
	@Test
	public void testGetSQLCatch() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setTransactionType("getall");
		when(employeeStatusFacadeImpl.getEmployeeStatus(employeeStatusRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> saveStatus = statusController.getEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
	}

	@Test
	public void testGetEmployeeStatusCatch() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		employeeStatusRequest = new EmployeeStatusRequest();
		employeeStatusRequest.setTransactionType("getall");
		when(employeeStatusFacadeImpl.getEmployeeStatus(employeeStatusRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> saveStatus = statusController.getEmployeeStatus(employeeStatusRequest,
				httpServletRequest, httpServletResponse);
		HttpStatus status = saveStatus.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
	}

}
