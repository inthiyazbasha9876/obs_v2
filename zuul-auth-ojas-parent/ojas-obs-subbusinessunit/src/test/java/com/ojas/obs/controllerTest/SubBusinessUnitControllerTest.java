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

import com.ojas.obs.controller.SubBusinessUnitController;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.SubBusinessUnitFacade;
import com.ojas.obs.facadeimpl.SubBusinessUnitFacadeImpl;
import com.ojas.obs.model.SubBusinessUnit;
import com.ojas.obs.request.SubBusinessUnitRequest;
import com.ojas.obs.response.SubBusinessUnitResponse;

public class SubBusinessUnitControllerTest {
	@Mock
	SubBusinessUnitFacade subBusinessUnitFacade;
	
	@Mock
	SubBusinessUnitFacadeImpl subBusinessUnitFacadeImpl;

	@InjectMocks
	private SubBusinessUnitController subBusinessUnitController;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	SubBusinessUnitRequest subBusinessUnitRequest;

	@Spy
	SubBusinessUnitResponse subBusinessUnitResponse;

	@Spy
	List<SubBusinessUnit> subBusinessUnitList;

	@Spy
	SubBusinessUnit subBusinessUnit;

	@Before
	public void init() {
		subBusinessUnitController = new SubBusinessUnitController();
		subBusinessUnitFacadeImpl = mock(SubBusinessUnitFacadeImpl.class);
		setCollaborator(subBusinessUnitController, "subBusinessUnitFacade", subBusinessUnitFacadeImpl);
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
	
	public SubBusinessUnitRequest subBusinessUnitRequest() {
		subBusinessUnitRequest = new SubBusinessUnitRequest();
		subBusinessUnit = new SubBusinessUnit();
		subBusinessUnit.setId(1);
		subBusinessUnit.setName("admin");
		subBusinessUnit.setBusinessUnitId("123");
		//subBusinessUnit.setCostCenterId(456);
		List<SubBusinessUnit> list = new ArrayList<>();
		list.add(subBusinessUnit);
		subBusinessUnitRequest.setSubBusinessUnitModel(list);
		subBusinessUnitRequest.setTransactionType("save");
		subBusinessUnitRequest.setSubBusinessUnitModel(list);
		return subBusinessUnitRequest;
	}
	
	@Test
	public void setSubBusinessUnitSuccess() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest.setTransactionType("update");
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitEmptyTransaction() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest.setTransactionType("");
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitNullTransactionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType(null);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitNullListTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setSubBusinessUnitModel(null);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setSubBusinessUnitSaveTransactionTest1() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("save");
		SubBusinessUnit subBusinessUnit = new SubBusinessUnit();
		subBusinessUnit.setName(null);	
		List<SubBusinessUnit> list = new ArrayList<>();
		list.add(subBusinessUnit);
		subBusinessUnitRequest.setSubBusinessUnitModel(list);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitSaveTransactionTest2() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("save");
		subBusinessUnit = new SubBusinessUnit();
		subBusinessUnit.setName("");		
		List<SubBusinessUnit> list = new ArrayList<>();
		list.add(subBusinessUnit);
		subBusinessUnitRequest.setSubBusinessUnitModel(list);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitSaveTransactionTest3() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("save");
		subBusinessUnit = new SubBusinessUnit();
		subBusinessUnit.setName("sunil");
		//subBusinessUnit.setCostCenterId(null);
		List<SubBusinessUnit> list = new ArrayList<>();
		list.add(subBusinessUnit);
		subBusinessUnitRequest.setSubBusinessUnitModel(list);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitSaveTransactionTest4() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("save");
		subBusinessUnit = new SubBusinessUnit();
		subBusinessUnit.setName("sunil");
		//subBusinessUnit.setCostCenterId(123);
		subBusinessUnit.setBusinessUnitId(null);
		List<SubBusinessUnit> list = new ArrayList<>();
		list.add(subBusinessUnit);
		subBusinessUnitRequest.setSubBusinessUnitModel(list);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitUpdateTransactionTest1() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("update");
		subBusinessUnit = new SubBusinessUnit();
		subBusinessUnit.setName("sunil");
		//subBusinessUnit.setCostCenterId(123);
		subBusinessUnit.setBusinessUnitId(null);
		List<SubBusinessUnit> list = new ArrayList<>();
		list.add(subBusinessUnit);
		subBusinessUnitRequest.setSubBusinessUnitModel(list);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitUpdateTransactionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("update");
		subBusinessUnitRequest.getSubBusinessUnitModel().get(0).setId(null);
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitSQLExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setSubBusinessUnitDupExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		Throwable cause = new Throwable();
		when(subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest)).thenThrow(new DuplicateKeyException(null,cause));
		ResponseEntity<Object> saveSBU = subBusinessUnitController.setSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getSubBusinessUnitSuccess() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest.setTransactionType("getAll");
		when(subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> getSBU = subBusinessUnitController.getSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getByIdSubBusinessUnitSuccess() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest.setTransactionType("getById");
		when(subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> getSBU = subBusinessUnitController.getSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getSubBusinessUnitFail() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest.setTransactionType("");
		when(subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> getSBU = subBusinessUnitController.getSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getSubBusinessUnitExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("getAll");
		when(subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> getSBU = subBusinessUnitController.getSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getSubBusinessUnitSQLExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("getAll");
		when(subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> getSBU = subBusinessUnitController.getSubBusinessUnit(subBusinessUnitRequest, request,
				response);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
}
