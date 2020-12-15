package com.obs.lms.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.obs.lms.facade.LmsFacadeImpl;
import com.obs.lms.model.LeaveInfo;
import com.obs.lms.request.LmsRequest;
import com.obs.lms.response.ErrorResponse;
import com.obs.lms.response.LmsResponse;

public class LmsControllerTest {
	@InjectMocks
	private LmsController lmsController;
	@Mock
	private LmsFacadeImpl lmsFacadeImpl;
	@Spy
	LmsRequest lmsRequest =new LmsRequest();
	@Spy
	private LmsResponse lmsResponse;
	@Spy
   LeaveInfo leave =new LeaveInfo();
	@Spy
	private ErrorResponse errorResponse;
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(lmsResponse, HttpStatus.OK);

	@Before
	public void init() throws Exception {
		lmsController = new LmsController();
		lmsFacadeImpl = mock(LmsFacadeImpl.class);
		setCollaborator(lmsController, "lmsFacadeImpl", lmsFacadeImpl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public LeaveInfo getLeaveList() {
		//Date date = new Date(0);
		LeaveInfo leave = new LeaveInfo();
		leave.setAppliedOn("ajay");
		leave.setApplyTo("Radhika");
		leave.setAttachment("djdhjdfj");
		leave.setCcTo("sachin");
		leave.setApplyType("");
		leave.setComment("no");
		leave.setContactDetails((long) 9885511);
		leave.setCountNumOfDays(0.5f);
		leave.setEmpId("19210");
		leave.setFileName("weding");
		leave.setFilePath("c/one/gjt");
		leave.setCountNumOfDays(40.5f);
		leave.setFlag(true);
		leave.setFromDate("2019-12-12");
		leave.setToDate("2019-12-12");
		leave.setStatus("approved");
		leave.setId(5);
		leave.setLeaveReason("i went home");
		leave.setLeaveType("sick leave");
		leave.setSession1("morning");
		leave.setSession2("afternoon");
		leave.setUpdatedOn("bharath");
		leave.setUpdatedBy("suresh");
		return leave;
	}

	@Test
	public void setRequestNull() throws IOException {
		lmsRequest = null;
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> lms = lmsController.setLeave(lmsRequest);
		HttpStatus status = lms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTranationtypeTestNull() throws IOException {
		lmsRequest.setTransationType(null);
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> lms = lmsController.setLeave(lmsRequest);
		HttpStatus status = lms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	  @Test
	  public void setTranationtypeTestEmpty() throws IOException { 
		  lmsRequest.setTransationType("");
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> lms = lmsController.setLeave(lmsRequest);
		HttpStatus status = lms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	  }
	@Test
	public void updateStatusIdFail() throws IOException {
		lmsRequest.setTransationType("updatestatus");
		leave.setId(null);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}
	@Test
	public void updateStatusEmpIdFail() throws IOException {
		lmsRequest.setTransationType("updatestatus");
		leave.setId(5);
		leave.setEmpId(null);leave.setStatus(null);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}
	@Test
	public void updateStatusEmpId() throws IOException {
		lmsRequest.setTransationType("updatestatus");
		leave.setId(5);
		leave.setEmpId("15");leave.setStatus("pending");
		leave.setUpdatedBy("ajay");leave.setUpdatedOn(null);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}


	@Test
	public void updatestatus() throws IOException {
		lmsRequest.setTransationType("updatestatus");
        leave.setId(5);leave.setEmpId("123");leave.setStatus("pending");
        leave.setUpdatedBy("ajay");leave.setUpdatedOn("vijay");
    	lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	
	@Test
	public void updateIdNullTest() throws IOException {
		leave.setId(null);
		lmsRequest.setLeaveInfo(leave);
		lmsRequest.setTransationType("update");

		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}
	@Test
	public void updateIdTest() throws IOException {
		leave.setId(5);
		lmsRequest.setLeaveInfo(leave);
		lmsRequest.setTransationType("update");

		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.OK, status);

	}
	@Test
	public void deleteNullTest() throws IOException {
		leave.setId(null);
		lmsRequest.setLeaveInfo(leave);
		lmsRequest.setTransationType("delete");

		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void saveFailTest() throws IOException {
		lmsRequest.setTransationType("save");
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void setRequObjNull() throws IOException {
		lmsRequest.setLeaveInfo(null);
		lmsRequest.setTransationType("delete");
		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> lms = lmsController.setLeave(lmsRequest);
		HttpStatus status = lms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void successcheck() throws IOException {
		lmsRequest.setLeaveInfo(getLeaveList());
		lmsRequest.setTransationType("save");

		when(lmsFacadeImpl.setLms(lmsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setExceptionTest1() throws IOException {
		lmsRequest.setLeaveInfo(getLeaveList());
		lmsRequest.setTransationType("save");
		lmsRequest.setTransationType("update");
		lmsRequest.setTransationType("delete");

		when(lmsFacadeImpl.setLms(lmsRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setlms = lmsController.setLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	// get

	@Test
	public void getTransationTypeEmpty() throws IOException {
		lmsRequest.setTransationType(null);
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(null);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void getTransationTypeNUll() throws IOException {
		lmsRequest.setTransationType("");
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void getReqNull() throws IOException {
		lmsRequest=null;
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(null);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdTestFail() throws IOException {
		lmsRequest.setTransationType("getbyid");
		leave.setId(null);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void getByIdTest() throws IOException {
		lmsRequest.setTransationType("getbyid");
		leave.setId(5);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void getallleaveinfoTestFail() throws IOException {
		lmsRequest.setTransationType("getallleaveinfo");
		leave.setEmpId(null);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void getallleaveinfoTest() throws IOException {
		lmsRequest.setTransationType("getallleaveinfo");
		leave.setEmpId("125");
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void getFileFail() throws IOException {
		lmsRequest.setTransationType("getfile");
		leave.setId(null);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void getFile() throws IOException {
		lmsRequest.setTransationType("getfile");
		leave.setId(5);
		lmsRequest.setLeaveInfo(leave);
		when(lmsFacadeImpl.getLms(lmsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void getExceptionTest() throws IOException {
		lmsRequest.setLeaveInfo(getLeaveList());
		lmsRequest.setTransationType("getall");

		when(lmsFacadeImpl.getLms(lmsRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setlms = lmsController.getLeave(lmsRequest);
		HttpStatus status = setlms.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

}
