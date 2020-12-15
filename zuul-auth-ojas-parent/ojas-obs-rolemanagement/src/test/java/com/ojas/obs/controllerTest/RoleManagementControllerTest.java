package com.ojas.obs.controllerTest;

import static org.junit.Assert.*;
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

import com.ojas.obs.controller.RoleManagementController;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.RoleManagementFacade;
import com.ojas.obs.model.RoleManagement;
import com.ojas.obs.request.RoleManagementRequest;
import com.ojas.obs.response.RoleManagementResponse;

public class RoleManagementControllerTest {
	@Mock
	RoleManagementFacade roleManagementFacade;

	@InjectMocks
	private RoleManagementController roleManagementController;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	RoleManagementRequest roleManagementRequest;

	@Spy
	RoleManagementResponse roleManagementResponse;

	@Spy
	List<RoleManagement> roleManagemenList;

	@Spy
	RoleManagement roleManagement;

	@Before
	public void init() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		roleManagementController = new RoleManagementController();
		roleManagementFacade = mock(RoleManagementFacade.class);
		setCollaborator(roleManagementController, "roleManagementFacade", roleManagementFacade);
	}

	private void setCollaborator(Object object, String name, Object service) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		Field field;
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);

			field.set(object, service);
	}

	public RoleManagementRequest roleManagementRequest() {
		roleManagementRequest = new RoleManagementRequest();
		RoleManagement roleManagement1 = new RoleManagement();
		roleManagement1.setId(1);
		roleManagement1.setRoleName("admin");
		RoleManagement roleManagement2 = new RoleManagement();
		roleManagement2.setId(2);
		roleManagement2.setRoleName("user");
		List<RoleManagement> roleManagementList = new ArrayList<>();
		roleManagementList.add(roleManagement1);
		roleManagementList.add(roleManagement2);
		roleManagementRequest.setTransactionType("save");
		roleManagementRequest.setRoleManagement(roleManagementList);
		return roleManagementRequest;
	}

	@Test
	public void setRolemanagementSuccess() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setRolemanagementNameNullTest() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
		RoleManagement roleManagement = new RoleManagement();
		roleManagement.setRoleName(null);
		roleManagementList.add(roleManagement);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setRolemanagementNameEmptyTest() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
		RoleManagement roleManagement = new RoleManagement();
		roleManagement.setRoleName("");
		roleManagementList.add(roleManagement);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setRolemanagementTransactionNullTest() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType(null);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setRolemanagementTestUpdateCondition() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
		RoleManagement roleManagement = new RoleManagement();
		roleManagement.setId(null);
		roleManagement.setRoleName("admin");
		roleManagementList.add(roleManagement);
		roleManagementRequest.setTransactionType("update");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setRolemanagementTestDeleteCondition() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
		RoleManagement roleManagement = new RoleManagement();
		roleManagement.setId(null);
		roleManagement.setRoleName("admin");
		roleManagementList.add(roleManagement);
		roleManagementRequest.setTransactionType("delete");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setRolemanagementTestDeleteConditionFail() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
		RoleManagement roleManagement = new RoleManagement();
		roleManagement.setId(123);
		roleManagement.setRoleName("admin");
		roleManagementList.add(roleManagement);
		roleManagementRequest.setTransactionType("delete");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setRolemanagementExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.getRoleManagement(roleManagementRequest)).thenThrow(SQLException.class);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setRolemanagementObjectNullCheckTest() throws SQLException {
		roleManagementRequest = new RoleManagementRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		roleManagementRequest.setTransactionType("save");
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setRolemanagementDupException() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Throwable cause = new Throwable();
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenThrow(new DuplicateKeyException(null, cause));
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setRolemanagementSQLException() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setRolemanagementException() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.setRoleManagement(roleManagementRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> saveRole = roleManagementController.setRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getRolemanagementSuccess() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		roleManagementRequest = new RoleManagementRequest();
		roleManagementRequest.setTransactionType("getAll");
		when(roleManagementFacade.getRoleManagement(roleManagementRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveRole = roleManagementController.getRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getByIdRolemanagementSuccess() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		roleManagementRequest = new RoleManagementRequest();
		roleManagementRequest.setTransactionType("getById");
		when(roleManagementFacade.getRoleManagement(roleManagementRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> saveRole = roleManagementController.getRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getRolemanagementTransactionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		roleManagementRequest = roleManagementRequest();
		when(roleManagementFacade.getRoleManagement(roleManagementRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> saveRole = roleManagementController.getRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getRolemanagementExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(roleManagementFacade.getRoleManagement(roleManagementRequest)).thenThrow(SQLException.class);
		ResponseEntity<Object> saveRole = roleManagementController.getRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getRolemanagementSQLException() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		roleManagementRequest = new RoleManagementRequest();
		roleManagementRequest.setTransactionType("getAll");
		when(roleManagementFacade.getRoleManagement(roleManagementRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> saveRole = roleManagementController.getRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getRolemanagementException() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		roleManagementRequest = new RoleManagementRequest();
		roleManagementRequest.setTransactionType("getAll");
		when(roleManagementFacade.getRoleManagement(roleManagementRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> saveRole = roleManagementController.getRoleManagement(roleManagementRequest, request,
				response);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

}