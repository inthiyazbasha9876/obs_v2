package com.ojas.obs.controllerTest;

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

import com.ojas.obs.controller.LeaveTypeController;
import com.ojas.obs.facadeimpl.LeaveTypeFacadeImpl;
import com.ojas.obs.model.LeaveType;
import com.ojas.obs.request.LeaveTypeRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.LeaveTypeResponse;

public class LeaveTypeControllerTest {
	@InjectMocks
	LeaveTypeController leavetypecontroller;

	@Mock
	LeaveTypeFacadeImpl leavetypefacadeImpl;

	@Spy
	LeaveTypeRequest leavetypereq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	LeaveTypeResponse leavetyperesponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(leavetyperesponse, HttpStatus.OK);

	@Spy
	LeaveType leavetype;

	@Before
	public void init() throws Exception {
		leavetypecontroller = new LeaveTypeController();
		leavetypefacadeImpl = mock(LeaveTypeFacadeImpl.class);
		setCollaborator(leavetypecontroller, "leavetypefacadeImpl", leavetypefacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<LeaveType> getGeos() {
		List<LeaveType> list = new ArrayList<LeaveType>();
		LeaveType datalist = new LeaveType();
		datalist.setLeaveTypeId(1);
		LeaveType datalist1 = new LeaveType();
		datalist1.setLeaveTypeId(2);
		list.add(datalist);
		list.add(datalist1);
		return list;
	}

	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		List<LeaveType> list = new ArrayList<LeaveType>();
		LeaveType datalist1 = new LeaveType();
		datalist1.setLeaveTypeId(null);
		datalist1.setLeaveTypeName(null);
		datalist1.setStatus(null);
		list.add(datalist1);
		leaverequest.setLeaveTypeList(null);
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		leaverequest.setLeaveTypeList(this.getGeos());
		leaverequest.setTransactionType("save");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		leaverequest.setLeaveTypeList(this.getGeos());
		leaverequest.setTransactionType("update");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		leaverequest.setLeaveTypeList(this.getGeos());
		leaverequest.setTransactionType("delete");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		LeaveType leave2 = new LeaveType();
		leave2.setLeaveTypeName("c");
		LeaveType leave3 = new LeaveType();
		leave3.setLeaveTypeName("c");
		List<LeaveType> leave = new ArrayList<LeaveType>();
		leave.add(leave2);
		leave.add(leave3);
		leaverequest.setLeaveTypeList(leave);
		leaverequest.setTransactionType("save");

		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		LeaveType leave1 = new LeaveType();
		leave1.setStatus(false);
		List<LeaveType> leave2 = new ArrayList<LeaveType>();
		leave2.add(leave1);
		leaverequest.setLeaveTypeList(leave2);
		leaverequest.setTransactionType("save");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenThrow(RuntimeException.class);
		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		LeaveType leave = new LeaveType();
		leave.setStatus(false);
		List<LeaveType> leave1 = new ArrayList<LeaveType>();
		leave1.add(leave);
		leaverequest.setLeaveTypeList(leave1);
		leaverequest.setTransactionType("save");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setsavenegativesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		LeaveType leave = new LeaveType();
		leave.setLeaveTypeName(" ");
		List<LeaveType> leave1 = new ArrayList<LeaveType>();
		leave1.add(leave);
		leave1.isEmpty();
		leaverequest.setLeaveTypeList(leave1);
		leaverequest.setTransactionType("save");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(failureResponse);

		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		LeaveType leave2 = new LeaveType();
		leave2.setStatus(false);
		List<LeaveType> leave = new ArrayList<LeaveType>();
		leave.add(leave2);
		leaverequest.setLeaveTypeList(leave);
		leaverequest.setTransactionType("update");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();
		LeaveType leave2 = new LeaveType();
		leave2.setStatus(false);
		List<LeaveType> leave = new ArrayList<LeaveType>();
		leave.add(leave2);
		leaverequest.setLeaveTypeList(leave);
		leaverequest.setTransactionType("delete");
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = leavetypecontroller.saveDetails(leaverequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();

		leaverequest.setLeaveTypeList(this.getGeos());
		leaverequest.setTransactionType(null);
		when(leavetypefacadeImpl.saveDetails(leaverequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = leavetypecontroller.getDetails(leaverequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();

		leaverequest.setLeaveTypeList(this.getGeos());
		leaverequest.setTransactionType("getById");

		leaverequest.getLeaveTypeList().get(0).getLeaveTypeId();

		when(leavetypefacadeImpl.getDetails(leaverequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = leavetypecontroller.getDetails(leaverequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();

		leaverequest.setLeaveTypeList(this.getGeos());
		leaverequest.setTransactionType("getById");

		leaverequest.getLeaveTypeList().get(0).setLeaveTypeId(null);

		when(leavetypefacadeImpl.getDetails(leaverequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = leavetypecontroller.getDetails(leaverequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LeaveTypeRequest leaverequest = new LeaveTypeRequest();

		leaverequest.setLeaveTypeList(this.getGeos());
		leaverequest.setTransactionType("getAll");
		when(leavetypefacadeImpl.getDetails(leaverequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = leavetypecontroller.getDetails(leaverequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}
