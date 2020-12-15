package com.ojas.obs.facadeTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.RoleManagementDao;
import com.ojas.obs.daoimpl.RoleManagementDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.RoleManagementFacade;
import com.ojas.obs.model.RoleManagement;
import com.ojas.obs.request.RoleManagementRequest;
import com.ojas.obs.response.RoleManagementResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RoleManagementFacadeTest {

	@Mock
	RoleManagementDao roleManagementDao;
	
	@Mock
	RoleManagementDaoImpl roleManagementDaoImpl;


	@InjectMocks
	private RoleManagementFacade roleManagementFacade;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	RoleManagementRequest roleManagementRequest;

	@Spy
	RoleManagementResponse roleManagementResponse;
	
	@Spy
	List<RoleManagement> roleManagemenList;

	@Spy
	RoleManagement roleManagement;
	
	@Spy
	List<RoleManagement> roleManagementList = new ArrayList<RoleManagement>(); 
	
	@Before
	public void init() {
		roleManagementList.add(roleManagement);
		roleManagementFacade = new RoleManagementFacade();
		roleManagementDaoImpl = mock(RoleManagementDaoImpl.class);
		setCollaborator(roleManagementFacade, "roleManagementDao", roleManagementDaoImpl);
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
	
	public RoleManagementRequest roleManagementRequest() {
		roleManagementRequest = new RoleManagementRequest();
		RoleManagement roleManagement1 = new RoleManagement();
		roleManagement1.setId(1);
		roleManagement1.setRoleName("admin");
		RoleManagement roleManagement2 = new RoleManagement();
		roleManagement2.setId(2);
		roleManagement2.setRoleName("user");
		RoleManagement roleManagement3 = new RoleManagement();
		roleManagement3.setId(3);
		roleManagement3.setRoleName("user");
		List<RoleManagement> roleManagementList = new ArrayList<>();
		roleManagementList.add(roleManagement1);
		roleManagementList.add(roleManagement2);
		roleManagementList.add(roleManagement2);
		roleManagementRequest.setTransactionType("save");
		roleManagementRequest.setRoleManagement(roleManagementList);
		return roleManagementRequest;
	}
	
	@Test
	public void setRolemanagementSaveSuccess() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		when(roleManagementDaoImpl.saveRoleManagement(roleManagementRequest)).thenReturn(true);
		ResponseEntity<Object> saveRole = roleManagementFacade.setRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void setRolemanagementSaveFail() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		when(roleManagementDaoImpl.saveRoleManagement(roleManagementRequest)).thenReturn(false);
		ResponseEntity<Object> saveRole = roleManagementFacade.setRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setRolemanagementUpdateSuccess() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType("update");
		when(roleManagementDaoImpl.updateRoleManagement(roleManagementRequest)).thenReturn(true);
		ResponseEntity<Object> saveRole = roleManagementFacade.setRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void setRolemanagementUpdateFail() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType("update");
		when(roleManagementDaoImpl.updateRoleManagement(roleManagementRequest)).thenReturn(false);
		ResponseEntity<Object> saveRole = roleManagementFacade.setRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setRolemanagementEmptyTransaction() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType("");
		when(roleManagementDaoImpl.updateRoleManagement(roleManagementRequest)).thenReturn(false);
		ResponseEntity<Object> saveRole = roleManagementFacade.setRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getRolemanagementSuccess() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType("getAll");
		when(roleManagementDaoImpl.getAllRollManagements()).thenReturn(roleManagementList);
		ResponseEntity<Object> saveRole = roleManagementFacade.getRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getByIdRolemanagementSuccess() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType("getById");
		when(roleManagementDaoImpl.getByIdRollManagement(1)).thenReturn(roleManagementList);
		ResponseEntity<Object> saveRole = roleManagementFacade.getRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getRolemanagementNullList() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType("getAll");
		when(roleManagementDaoImpl.getAllRollManagements()).thenReturn(null);
		ResponseEntity<Object> saveRole = roleManagementFacade.getRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getRolemanagementEmptyList() throws SQLException {
		roleManagementRequest = roleManagementRequest();
		roleManagementRequest.setTransactionType("getAll");
		when(roleManagementDaoImpl.getAllRollManagements()).thenReturn(Collections.emptyList());
		ResponseEntity<Object> saveRole = roleManagementFacade.getRoleManagement(roleManagementRequest);
		HttpStatus statusCode = saveRole.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

}
