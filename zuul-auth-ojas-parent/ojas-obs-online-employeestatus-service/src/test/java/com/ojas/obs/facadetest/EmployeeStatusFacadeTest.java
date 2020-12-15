package com.ojas.obs.facadetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.EmployeeStatusDao;
import com.ojas.obs.daoimpl.EmployeeStatusDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.EmployeeStatusFacade;
import com.ojas.obs.facadeimpl.EmployeeStatusFacadeImpl;
import com.ojas.obs.model.EmployeeStatus;
import com.ojas.obs.request.EmployeeStatusRequest;
import com.ojas.obs.response.EmployeeStatusResponse;

public class EmployeeStatusFacadeTest {

	@InjectMocks
	EmployeeStatusFacadeImpl empStatusFacadeImpl;
	@Mock
	EmployeeStatusFacade empStatusFacade;
	@Mock
	EmployeeStatusDao empStatusDao;
	@Mock
	EmployeeStatusDaoImpl empStatusDaoImpl;
	@Spy
	EmployeeStatus empStatus;
	@Spy
	EmployeeStatusRequest empstatusRequest;
	@Spy
	EmployeeStatusResponse empstatusResponse;
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Before
	public void init() throws Exception {
		empStatusFacadeImpl = new EmployeeStatusFacadeImpl();
		empStatusDaoImpl = mock(EmployeeStatusDaoImpl.class);
		setCollaborator(empStatusFacadeImpl, "employeeStatusDao", empStatusDaoImpl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	@Test
	public void testSave() throws SQLException {
		EmployeeStatusRequest empstatusRequest = new EmployeeStatusRequest();
		empstatusRequest.setTransactionType("save");
		when(empStatusDaoImpl.saveEmployeeStatus(empstatusRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.setEmployeeStatus(empstatusRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testSaveNegative() throws SQLException {
		EmployeeStatusRequest empstatusRequest = new EmployeeStatusRequest();
		empstatusRequest.setTransactionType("save");
		when(empStatusDaoImpl.saveEmployeeStatus(empstatusRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.setEmployeeStatus(empstatusRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testUpdate() throws SQLException {
		EmployeeStatusRequest empstatusReques = new EmployeeStatusRequest();
		empstatusReques.setTransactionType("update");
		when(empStatusDaoImpl.updateEmployeeStatus(empstatusReques)).thenReturn(true);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.setEmployeeStatus(empstatusReques);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testUpdateNegative() throws SQLException {
		EmployeeStatusRequest empstatusReques = new EmployeeStatusRequest();
		empstatusReques.setTransactionType("update");
		when(empStatusDaoImpl.updateEmployeeStatus(empstatusReques)).thenReturn(false);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.setEmployeeStatus(empstatusReques);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	/*
	 * @Test public void testDelete() { EmployeeStatusRequest empstatusReques = new
	 * EmployeeStatusRequest(); empstatusReques.setTransactionType("delete");
	 * when(empStatusDaoImpl.deleteEmployeeStatus(empstatusReques)).thenReturn(true)
	 * ; ResponseEntity<Object> saveStatus =
	 * empStatusFacadeImpl.setEmployeeStatus(empstatusReques); HttpStatus statusCode
	 * = saveStatus.getStatusCode(); assertEquals(HttpStatus.OK, statusCode); }
	 * 
	 * @Test public void testDeleteNegative() { EmployeeStatusRequest
	 * empstatusReques = new EmployeeStatusRequest();
	 * empstatusReques.setTransactionType("delete");
	 * when(empStatusDaoImpl.deleteEmployeeStatus(empstatusReques)).thenReturn(false
	 * ); ResponseEntity<Object> saveStatus =
	 * empStatusFacadeImpl.setEmployeeStatus(empstatusReques); HttpStatus statusCode
	 * = saveStatus.getStatusCode(); assertEquals(HttpStatus.CONFLICT, statusCode);
	 * }
	 */
	@Test
	public void testSetNullCheck() throws SQLException {
		EmployeeStatusRequest empstatusReques = new EmployeeStatusRequest();
		empstatusReques.setTransactionType("");
		when(empStatusDaoImpl.saveEmployeeStatus(empstatusReques)).thenReturn(false);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.setEmployeeStatus(empstatusReques);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testGet() throws SQLException {
		List<EmployeeStatus> employeeStatusList = new ArrayList<EmployeeStatus>();
		EmployeeStatus status = new EmployeeStatus();
		status.setId(1);
		employeeStatusList.add(status);
		EmployeeStatusRequest empstatusReques = new EmployeeStatusRequest();
		empstatusReques.setTransactionType("getall");
		when(empStatusDaoImpl.getAllStatus()).thenReturn(employeeStatusList);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.getEmployeeStatus(empstatusReques);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetNegative() throws SQLException {
		List<EmployeeStatus> employeeStatusList = new ArrayList<EmployeeStatus>();
		EmployeeStatusRequest empstatusReques = new EmployeeStatusRequest();
		empstatusReques.setTransactionType("getall");
		when(empStatusDaoImpl.getAllStatus()).thenReturn(employeeStatusList);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.getEmployeeStatus(empstatusReques);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testGetById() throws SQLException {
		List<EmployeeStatus> employeeStatusList = new ArrayList<EmployeeStatus>();
		EmployeeStatus status = new EmployeeStatus();
		status.setId(1);
		employeeStatusList.add(status);
		List<EmployeeStatus> empStatusList = new ArrayList<EmployeeStatus>();
		EmployeeStatus response = new EmployeeStatus();
		response.setStatus("Active");
		empStatusList.add(response);
		EmployeeStatusRequest empstatusReques = new EmployeeStatusRequest();
		empstatusReques.setTransactionType("getbyid");
		empstatusReques.setEmployeeStatus(employeeStatusList);
		when(empStatusDaoImpl.getById(anyInt())).thenReturn(empStatusList);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.getEmployeeStatus(empstatusReques);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetByIdNegative() throws SQLException {
		List<EmployeeStatus> employeeStatusList = new ArrayList<EmployeeStatus>();
		EmployeeStatusRequest empstatusReques = new EmployeeStatusRequest();
		empstatusReques.setTransactionType("getall");
		when(empStatusDaoImpl.getAllStatus()).thenReturn(employeeStatusList);
		ResponseEntity<Object> saveStatus = empStatusFacadeImpl.getEmployeeStatus(empstatusReques);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

}
