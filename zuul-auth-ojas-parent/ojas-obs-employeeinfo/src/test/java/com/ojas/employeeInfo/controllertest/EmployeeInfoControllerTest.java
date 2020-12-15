package com.ojas.employeeInfo.controllertest;

import static org.junit.Assert.assertEquals;
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

import com.ojas.obs.controller.EmployeeInfoController;
import com.ojas.obs.errorResponse.ErrorResponse;
import com.ojas.obs.facade.EmployeeInfoFacade;
import com.ojas.obs.facadeImpl.EmployeeInfoFacadeImpl;
import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.request.EmployeeInfoRequest;
import com.ojas.obs.response.EmployeeInfoResponse;

public class EmployeeInfoControllerTest {
	@InjectMocks
	EmployeeInfoController empInfoController;
	@Mock
	EmployeeInfoFacadeImpl empInfoFacadeImpl;
	@Mock
	EmployeeInfoFacade empInfoFacade;
	@Spy
	EmployeeInfoRequest empInfoRequest;
	@Spy
	EmployeeInfoResponse empInfoResponse;
	@Spy
	ErrorResponse errorResponse;
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(empInfoResponse, HttpStatus.OK);
	@Spy
	EmployeeInfo empInfo;

	@Before
	public void init() throws Exception {
		empInfoController = new EmployeeInfoController();
		empInfoFacadeImpl = mock(EmployeeInfoFacadeImpl.class);
		setCollaborator(empInfoController, "employeeInfoFacade", empInfoFacadeImpl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<EmployeeInfo> getEmpInfoList() {
		List<EmployeeInfo> employeeInfos = new ArrayList<EmployeeInfo>();
		EmployeeInfo employeeInfo = new EmployeeInfo();
		employeeInfo.setFirstname("abc");
		employeeInfo.setMiddlename("def");
		employeeInfo.setLastname("ghi");
		employeeInfo.setStatus("Bench");
		employeeInfo.setFlag(true);
		employeeInfo.setGender("Male");
		employeeInfo.setDob("123456");
		//employeeInfo.setStatusDate("11-07-2019");
		employeeInfo.setTitle("Manager");
		employeeInfo.setEmployeeId("123");
		//employeeInfo.setSbu(2);
		employeeInfo.setCreatedBy("123");
		employeeInfo.setUpdatedBy("123");
		employeeInfo.setEmail("pa@oajs-it.con");
		employeeInfo.setPersonalMobileNo("54545454545");
		employeeInfos.add(employeeInfo);
		return employeeInfos;
	}

	@Test
	public void setEmpInfoNullCheckRequest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		// EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		when(empInfoFacadeImpl.setEmployeeInfo(empInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(empInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckList() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoSave() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setEmployeeInfo(getEmpInfoList());
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setEmpInfoEmptyCheckFirstName() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setFirstname("");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		errorResponse = new ErrorResponse();
		empInfoResponse = new EmployeeInfoResponse();
		list.get(0).hashCode();
		employeeInfoRequest.hashCode();
		errorResponse.hashCode();
		empInfoResponse.hashCode();
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckFirstName() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setFirstname(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoEmptyCheckMiddleName() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setMiddlename("");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckMiddleName() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setMiddlename(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoEmptyCheckLastName() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setLastname("");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckLastName() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setLastname(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckTitle() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setTitle(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckStatus() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setStatus(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckGender() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setGender(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoEmptyCheckDob() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setDob("");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckDob() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setDob(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoEmptyCheckEmployeeId() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setEmployeeId("");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckEmployeeId() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setEmployeeId(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoUpdate() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setId(12);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("update");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckUpdateeId() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setId(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("update");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoDelete() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setId(12);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("delete");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setEmpInfoNullCheckDeleteId() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setId(null);
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("delete");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmpInfoCatch() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setEmployeeId("kj");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setEmpInfoDupCatch() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setEmployeeId("fsdg");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		Throwable cause = new Throwable();
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenThrow(new DuplicateKeyException(null, cause));
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setEmpInfoSQLCatch() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> list = getEmpInfoList();
		list.get(0).setEmployeeId("fdasfasd");
		employeeInfoRequest.setEmployeeInfo(list);
		employeeInfoRequest.setTransactionType("save");
		when(empInfoFacadeImpl.setEmployeeInfo(employeeInfoRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> setEmp = empInfoController.setEmployeeInfo(employeeInfoRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getEmpInfoNullCheckRequest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(empInfoFacadeImpl.getAllEmployeeDetails(empInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> getEmp = empInfoController.getEmpDetails(empInfoRequest, request, response);
		HttpStatus statusCode = getEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getEmpInfoNullCheckType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType(null);
		when(empInfoFacadeImpl.getAllEmployeeDetails(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> getEmp = empInfoController.getEmpDetails(employeeInfoRequest, request, response);
		HttpStatus statusCode = getEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getEmpInfoEmptyCheckType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("");
		when(empInfoFacadeImpl.getAllEmployeeDetails(employeeInfoRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> getEmp = empInfoController.getEmpDetails(employeeInfoRequest, request, response);
		HttpStatus statusCode = getEmp.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getEmpInfo() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("getAll");
		when(empInfoFacadeImpl.getAllEmployeeDetails(employeeInfoRequest)).thenReturn(successResponse);
		ResponseEntity<Object> getEmp = empInfoController.getEmpDetails(employeeInfoRequest, request, response);
		HttpStatus statusCode = getEmp.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getEmpInfoSQLCatch() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("getAll");
		when(empInfoFacadeImpl.getAllEmployeeDetails(employeeInfoRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> getEmp = empInfoController.getEmpDetails(employeeInfoRequest, request, response);
		HttpStatus statusCode = getEmp.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getEmpInfoCatch() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("getAll");
		when(empInfoFacadeImpl.getAllEmployeeDetails(employeeInfoRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> getEmp = empInfoController.getEmpDetails(employeeInfoRequest, request, response);
		HttpStatus statusCode = getEmp.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
}
