package com.ojas.obs.controller;

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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.facade.EmployeeContactFacade;
import com.ojas.obs.facadeimpl.EmployeeContactFacadeImpl;
import com.ojas.obs.model.EmployeeContactInfo;
import com.ojas.obs.requests.EmployeeContactInfoRequest;
import com.ojas.obs.response.EmployeeContactInfoResponse;
import com.ojas.obs.response.ErrorResponse;

public class EmployeeContactInfoControllerTest {

	@InjectMocks
	private EmployeeContactInfoController employeeContactInfoController;

	@Mock
	EmployeeContactFacade employeeStatusFacade;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<Object>(HttpStatus.OK);

	@Spy
	EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();

	@Spy
	EmployeeContactInfoResponse employeeContactInfoResponse = new EmployeeContactInfoResponse();

	@Spy
	List<EmployeeContactInfo> employeeContactInfolist = new ArrayList<>();

	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		employeeContactInfoController = new EmployeeContactInfoController();
		employeeStatusFacade = mock(EmployeeContactFacadeImpl.class);

		// MockitoAnnotations.initMocks(This.class);
		setCollaborator(employeeContactInfoController, "empFacade", employeeStatusFacade);

	}

	private void setCollaborator(Object object, String name, Object service) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field field;

			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, service);

		
	}

	public List<EmployeeContactInfo> getEmployeeContactInfo() {

		List<EmployeeContactInfo> list = new ArrayList<>();

		EmployeeContactInfo employeeContactInfo = new EmployeeContactInfo();
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

		employeeContactInfo.setId(1);
		employeeContactInfo.setAlternateMobileNo("9876543210");
		employeeContactInfo.setCurrentAddressLine1("Raidurgam");
		employeeContactInfo.setCurrentAddressLine2("Police Line-2");
		employeeContactInfo.setCurrentCity("Hyderbad");
		employeeContactInfo.setCurrentState("AP");
		employeeContactInfo.setCurrentPin(500001);
		employeeContactInfo.setPermanentAddressLine1("Hyderbad");
		employeeContactInfo.setEmpId("19201");
		employeeContactInfo.setCreatedBy("SaiKrishna");
		employeeContactInfo.setUpdatedBy("Krishna");
		employeeContactInfo.setCreatedDate(timestamp);
		employeeContactInfo.setUpdatedDate(timestamp);
		employeeContactInfo.setFlag(true);

		list.add(employeeContactInfo);
		return list;

	}

	public List<EmployeeContactInfo> getEmployeeContactInfo1() {

		List<EmployeeContactInfo> list = new ArrayList<>();

		EmployeeContactInfo employeeContactInfo = new EmployeeContactInfo();
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

		employeeContactInfo.setId(null);
		employeeContactInfo.setAlternateMobileNo("9876543210");
		employeeContactInfo.setCurrentAddressLine1("Raidurgam");
		employeeContactInfo.setCurrentAddressLine2("Police Line-2");
		employeeContactInfo.setCurrentCity("Hyderbad");
		employeeContactInfo.setCurrentState("TS");
		employeeContactInfo.setCurrentPin(500001);
		employeeContactInfo.setPermanentAddressLine1("Hyderbad");
		employeeContactInfo.setEmpId("19201");
		employeeContactInfo.setCreatedBy("SaiKrishna");
		employeeContactInfo.setUpdatedBy("Krishna");
		employeeContactInfo.setCreatedDate(timestamp);
		employeeContactInfo.setUpdatedDate(timestamp);
		employeeContactInfo.setFlag(true);

		list.add(employeeContactInfo);
		return list;
	}

//	public List<EmployeeContactInfo> emptyEmployeeContactInfo() {
//		List<EmployeeContactInfo> list = new ArrayList<>();
//
//		EmployeeContactInfo employeeContactInfo = new EmployeeContactInfo();
//		EmployeeContactInfo employeeContactInfo1 = new EmployeeContactInfo();
//
//		list.add(employeeContactInfo);
//		list.add(employeeContactInfo1);
//		return list;
//	}

	// -------Request null test Set------

	@Test
	public void reqNullTestSet() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = null;

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Mockito.lenient().when(employeeStatusFacade.setEmployeeContactInfo(empContactInfoRequest))
				.thenReturn(successEntity);

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

	}

	// -------Get null test ------

	@Test
	public void reqNullTestGet() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = null;

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Mockito.lenient().when(employeeStatusFacade.getEmployeeContactInfo(empContactInfoRequest))
				.thenReturn(successEntity);

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.getEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

	}

	// -----null Tx Test------

	@Test
	public void nullTxTest() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.setTransactionType("");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Mockito.lenient().when(employeeStatusFacade.setEmployeeContactInfo(empContactInfoRequest))
				.thenReturn(successEntity);

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

	}

	/*
	 * @Test public void testSetEmployeeContactInfoInvalidEmail() {
	 * EmployeeContactInfoRequest empContactInfoRequest = new
	 * EmployeeContactInfoRequest();
	 * empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());
	 * 
	 * empContactInfoRequest.setTransactionType("save"); HttpServletRequest request
	 * = null; HttpServletResponse response = null; ResponseEntity<Object>
	 * setEmployeeContactInfo = employeeContactInfoController
	 * .setEmployeeContactInfo(empContactInfoRequest, request, response); HttpStatus
	 * statusCode = setEmployeeContactInfo.getStatusCode();
	 * 
	 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); }
	 * 
	 * @Test public void testSetEmployeeContactInfoEmptyEmail() {
	 * EmployeeContactInfoRequest empContactInfoRequest = new
	 * EmployeeContactInfoRequest();
	 * empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());
	 * 
	 * empContactInfoRequest.setTransactionType("save"); HttpServletRequest request
	 * = null; HttpServletResponse response = null; ResponseEntity<Object>
	 * setEmployeeContactInfo = employeeContactInfoController
	 * .setEmployeeContactInfo(empContactInfoRequest, request, response); HttpStatus
	 * statusCode = setEmployeeContactInfo.getStatusCode();
	 * System.out.println(statusCode);
	 * 
	 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); }
	 */
	@Test
	public void testSetEmployeeContactInfoUpdateId() {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.getEmpInfo().get(0).setId(null);
		empContactInfoRequest.setTransactionType("update");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		System.out.println(statusCode);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testSetEmployeeContactInfoDeleteId() {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.getEmpInfo().get(0).setId(null);
		empContactInfoRequest.setTransactionType("delete");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		System.out.println(statusCode);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testSetEmployeeContactInfoNullTransactionType() {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		// empContactInfoRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		System.out.println(statusCode);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	/*
	 * @Test public void testSetEmployeeContactInfoSave() {
	 * EmployeeContactInfoRequest empContactInfoRequest = new
	 * EmployeeContactInfoRequest();
	 * empContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
	 * 
	 * empContactInfoRequest.setTransactionType("save"); HttpServletRequest request
	 * = null; HttpServletResponse response = null; ResponseEntity<Object>
	 * setEmployeeContactInfo = employeeContactInfoController
	 * .setEmployeeContactInfo(empContactInfoRequest, request, response); HttpStatus
	 * statusCode = setEmployeeContactInfo.getStatusCode();
	 * 
	 * assertEquals(HttpStatus.OK, statusCode); }
	 */

	@Test
	public void setEmployeeContactInfoTestUpdate() throws Exception {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo1());

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		empContactInfoRequest.setTransactionType("update");

		// when(employeeStatusFacade.setEmployeeContactInfo(empContactInfoRequest)).thenThrow(new
		// RuntimeException());

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

	}

	@Test
	public void setEmployeeContactInfoTestDelete() throws Exception {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo1());

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		empContactInfoRequest.setTransactionType("delete");

		// when(employeeStatusFacade.setEmployeeContactInfo(empContactInfoRequest)).thenThrow(new
		// RuntimeException());

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

	}
	

	@Test
	public void getEmployeeContactInfoTestNotEqualGetAll() throws Exception {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		empContactInfoRequest.setTransactionType("getegdg");

		when(employeeStatusFacade.getEmployeeContactInfo(empContactInfoRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.getEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void getEmployeeContactInfoTestGetAll() throws Exception {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		empContactInfoRequest.setTransactionType("getAll");

		when(employeeStatusFacade.getEmployeeContactInfo(empContactInfoRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.getEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);

	}

	
	
	// -----------set DuplicateException test case---------------
	@Test
	public void setDuplicateExceptionTest() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(employeeStatusFacade.setEmployeeContactInfo(empContactInfoRequest))
				.thenThrow(new DuplicateKeyException(""));

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		System.out.println(statusCode);

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	// -----------Set SQLException test case---------------

	@Test
	public void setSQLExceptionTest() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(employeeStatusFacade.setEmployeeContactInfo(empContactInfoRequest)).thenThrow(SQLException.class);

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	// -----------set Exception test case---------------

	@Test
	public void setExceptionTest() throws Exception {
		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		empContactInfoRequest.setTransactionType("save");

		when(employeeStatusFacade.setEmployeeContactInfo(empContactInfoRequest)).thenThrow(new RuntimeException());

		ResponseEntity<Object> setEmployeeContactInfo = employeeContactInfoController
				.setEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	// -----------get DuplicateException test case---------------
	@Test
	public void getDuplicateExceptionTest() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.setTransactionType("getAll");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(employeeStatusFacade.getEmployeeContactInfo(empContactInfoRequest))
				.thenThrow(new DuplicateKeyException(""));

		ResponseEntity<Object> getEmployeeContactInfo = employeeContactInfoController
				.getEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = getEmployeeContactInfo.getStatusCode();
		System.out.println(statusCode);

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	// -----------get SQL Exception test case---------------
	@Test
	public void getSqlExceptionTest() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.setTransactionType("getAll");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(employeeStatusFacade.getEmployeeContactInfo(empContactInfoRequest))
				.thenThrow(SQLException.class);

		ResponseEntity<Object> getEmployeeContactInfo = employeeContactInfoController
				.getEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = getEmployeeContactInfo.getStatusCode();
		System.out.println(statusCode);

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	
	// -----------get Exception test case---------------
	@Test
	public void getExceptionTest() throws Exception {

		EmployeeContactInfoRequest empContactInfoRequest = new EmployeeContactInfoRequest();
		empContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		empContactInfoRequest.setTransactionType("getAll");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(employeeStatusFacade.getEmployeeContactInfo(empContactInfoRequest))
				.thenThrow(new RuntimeException());

		ResponseEntity<Object> getEmployeeContactInfo = employeeContactInfoController
				.getEmployeeContactInfo(empContactInfoRequest, request, response);
		HttpStatus statusCode = getEmployeeContactInfo.getStatusCode();
		System.out.println(statusCode);

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

}
