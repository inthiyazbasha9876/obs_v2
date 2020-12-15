package com.ojas.obs.facadeTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.EmployeeBUDao;
import com.ojas.obs.dao.EmployeeBUDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.EmpoyeeBUFacade;
import com.ojas.obs.model.EmployeeBUDetails;
import com.ojas.obs.request.EmployeeBUDetailsRequest;
import com.ojas.obs.response.EmployeeBUDeatailsResponse;

public class FacadeTest {

	@Mock
	EmployeeBUDao buDao;

	@Mock
	EmployeeBUDaoImpl buDaoImpl;

	@InjectMocks
	private EmpoyeeBUFacade buFacade;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	EmployeeBUDetailsRequest buRequest;

	@Spy
	EmployeeBUDeatailsResponse buResponse;

	@Spy
	List<EmployeeBUDetails> List;

	@Spy
	EmployeeBUDetails bu;

	@Spy
	List<EmployeeBUDetails> buList = new ArrayList<EmployeeBUDetails>();

	@Before
	public void init() {
		buList.add(bu);
		buFacade = new EmpoyeeBUFacade();
		buDaoImpl = mock(EmployeeBUDaoImpl.class);
		setCollaborator(buFacade, "employeebuDao", buDaoImpl);
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
		EmployeeBUDetails bu = new EmployeeBUDetails();
		bu.setId(1);
		bu.setStatus("admin");
		bu.setEmployeeId("abc");
		bu.setFlag(true);
		bu.setSbu(3);
		bu.setCreatedby("mnb");
		bu.setCreateddate(new Timestamp(21101998));
		EmployeeBUDetails bu2 = new EmployeeBUDetails();
		bu.setId(2);
		bu.setStatus("adm");
		bu.setEmployeeId("abcf");
		bu.setFlag(true);
		bu.setSbu(3);
		bu.setCreatedby("mnbc");
		bu.setCreateddate(new Timestamp(21101998));

		List<EmployeeBUDetails> List = new ArrayList<>();
		List.add(bu2);
		List.add(bu);
		buRequest.setTransactionType("save");
		buRequest.setEmployeeBUDeatils(List);
		return buRequest;
	}

	@Test
	public void setSaveSuccess() throws SQLException {
		buRequest = buRequest();
		when(buDaoImpl.saveEmployeebu(buRequest)).thenReturn(true);
		ResponseEntity<Object> saveBu = buFacade.setEmployeeBU(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setSaveFail() throws SQLException {
		buRequest = buRequest();
		when(buDaoImpl.saveEmployeebu(buRequest)).thenReturn(false);
		ResponseEntity<Object> saveBu = buFacade.setEmployeeBU(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateSuccess() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("update");
		when(buDaoImpl.updateEmployeebu(buRequest)).thenReturn(true);
		ResponseEntity<Object> saveBu = buFacade.setEmployeeBU(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateFail() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("update");
		when(buDaoImpl.updateEmployeebu(buRequest)).thenReturn(false);
		ResponseEntity<Object> saveBu = buFacade.setEmployeeBU(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void setDeleteSuccess() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("delete");
		when(buDaoImpl.deleteEmployeeRecord(1)).thenReturn(true);
		ResponseEntity<Object> saveBu = buFacade.setEmployeeBU(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteFail() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("delete");
		when(buDaoImpl.deleteEmployeeRecord(1)).thenReturn(false);
		ResponseEntity<Object> saveBu = buFacade.setEmployeeBU(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

//	@Test
//	public void setRolemanagementEmptyTransaction() throws SQLException {
//		buRequest = buRequest();
//		buRequest.setTransactionType("");
//		when(buDaoImpl.updateEmployeebu(buRequest)).thenReturn(true);
//		ResponseEntity<Object> saveRole = buFacade.setEmployeeBU(buRequest);
//		HttpStatus statusCode = saveRole.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}

	@Test
	public void getSuccess() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("getAll");
		when(buDaoImpl.getAllEmployeebu(buRequest)).thenReturn(buList);
		ResponseEntity<Object> saveBu = buFacade.getEmployeeBUDetails(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("getById");
		when(buDaoImpl.getById(buRequest)).thenReturn(buList);
		ResponseEntity<Object> saveBu = buFacade.getEmployeeBUDetails(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getNullList() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("getAll");
		when(buDaoImpl.getAllEmployeebu(buRequest)).thenReturn(null);
		ResponseEntity<Object> saveBu = buFacade.getEmployeeBUDetails(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getEmptyList() throws SQLException {
		buRequest = buRequest();
		buRequest.setTransactionType("getAll");
		when(buDaoImpl.getAllEmployeebu(buRequest)).thenReturn(Collections.emptyList());
		ResponseEntity<Object> saveBu = buFacade.getEmployeeBUDetails(buRequest);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

}
