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

import com.ojas.obs.controller.SarStatusController;
import com.ojas.obs.facade.SarStatusFacade;
import com.ojas.obs.facadeimpl.SarStatusFacadeImpl;
import com.ojas.obs.model.SarStatus;
import com.ojas.obs.request.SarStatusRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.SarStatusResponse;

public class SarStatusControllerTest {

	@InjectMocks
	SarStatusController sarstatuscontroller;

	@Mock
	SarStatusFacade sarfacadeImpl;

	@Spy
	SarStatusRequest sarstatusreq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	SarStatusResponse sarstatusresponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(sarstatusresponse, HttpStatus.OK);

	@Spy
	SarStatus sarstatus;

	@Before
	public void init() throws Exception {
		sarstatuscontroller = new SarStatusController();
		sarfacadeImpl = mock(SarStatusFacadeImpl.class);
		setCollaborator(sarstatuscontroller, "sarfacadeImpl", sarfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<SarStatus> getSarStatusList() {
		List<SarStatus> statuslist = new ArrayList<SarStatus>();
		SarStatus statusdatalist = new SarStatus();
		statusdatalist.setSarstatusId(1);
		// servicedatalist.setSarStatus("clientodc");
		SarStatus sardatalist1 = new SarStatus();
		sardatalist1.setSarstatusId(2);
		// sardatalist1.setSarStatus("ojasodc");
		statuslist.add(statusdatalist);
		statuslist.add(sardatalist1);
		return statuslist;
	}

	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();
		List<SarStatus> sarlist = new ArrayList<SarStatus>();
		SarStatus sardatalist1 = new SarStatus();
		sardatalist1.setSarstatusId(null);
		sardatalist1.setSarStatus(null);
		sardatalist1.setStatus(null);
		sarlist.add(sardatalist1);
		sarrequest.setSarstatusList(null);
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();
		sarrequest.setSarstatusList(this.getSarStatusList());
		sarrequest.setTransactionType("save");
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();
		sarrequest.setSarstatusList(this.getSarStatusList());
		sarrequest.setTransactionType("update");
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();
		sarrequest.setSarstatusList(this.getSarStatusList());
		sarrequest.setTransactionType("delete");
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		SarStatus sarstatus2 = new SarStatus();
		// servicetype2.setServiceType("any cato");
		sarstatus2.setStatus(true);

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarrequest.setSarstatusList(sarstatus);
		sarrequest.setTransactionType("save");

		when(sarfacadeImpl.saveDetails(sarrequest)).thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		SarStatus sarstatus2 = new SarStatus();
		// servicetype2.setServiceType("any cato");
		sarstatus2.setStatus(false);

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarrequest.setSarstatusList(sarstatus);
		sarrequest.setTransactionType("save");
		when(sarfacadeImpl.saveDetails(sarrequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		SarStatus sarstatus = new SarStatus();
		// servicetype2.setServiceType("data");
		sarstatus.setStatus(false);

		List<SarStatus> locationtype1 = new ArrayList<SarStatus>();
		locationtype1.add(sarstatus);
		sarrequest.setSarstatusList(locationtype1);
		sarrequest.setTransactionType("save");
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		SarStatus sarstatus2 = new SarStatus();
		// servicetype2.setServiceType("data");
		sarstatus2.setStatus(false);

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarrequest.setSarstatusList(sarstatus);
		sarrequest.setTransactionType("update");
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		SarStatus sarstatus2 = new SarStatus();
		// servicetype2.setServiceType("ss");
		sarstatus2.setStatus(false);

		List<SarStatus> locationtype = new ArrayList<SarStatus>();
		locationtype.add(sarstatus2);
		sarrequest.setSarstatusList(locationtype);
		sarrequest.setTransactionType("delete");
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = sarstatuscontroller.saveDetails(sarrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		sarrequest.setSarstatusList(this.getSarStatusList());
		sarrequest.setTransactionType(null);
		when(sarfacadeImpl.saveDetails(sarrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = sarstatuscontroller.getDetails(sarrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		sarrequest.setSarstatusList(this.getSarStatusList());
		sarrequest.setTransactionType("getById");

		sarrequest.getSarstatusList().get(0).getSarstatusId();

		when(sarfacadeImpl.getDetails(sarrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = sarstatuscontroller.getDetails(sarrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		sarrequest.setSarstatusList(this.getSarStatusList());
		sarrequest.setTransactionType("getById");

		sarrequest.getSarstatusList().get(0).setSarstatusId(null);

		when(sarfacadeImpl.getDetails(sarrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = sarstatuscontroller.getDetails(sarrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SarStatusRequest sarrequest = new SarStatusRequest();

		sarrequest.setSarstatusList(this.getSarStatusList());
		sarrequest.setTransactionType("getAll");
		when(sarfacadeImpl.getDetails(sarrequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = sarstatuscontroller.getDetails(sarrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}
