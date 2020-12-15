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

import com.ojas.obs.controller.EmployeeExperienceController;
import com.ojas.obs.facadeimpl.EmployeeExperienceFacadeImpl;
import com.ojas.obs.model.EmployeeExperience;

import com.ojas.obs.request.EmployeeExperienceRequest;

import com.ojas.obs.response.EmployeeExperienceResponse;
import com.ojas.obs.response.ErrorResponse;

public class EmployeeExperienceControllerTest {
	@InjectMocks 
	EmployeeExperienceController empexpcontroller;
	 
	@Mock
	EmployeeExperienceFacadeImpl empExpFacadeImpl;
	
	@Spy
	EmployeeExperienceRequest empexpreq; 
	
	@Spy
	ErrorResponse errorresponse; 
	
	@Spy
	EmployeeExperienceResponse empexpresponse; 
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(empexpresponse, HttpStatus.OK);
	
	@Spy
	EmployeeExperience empexperience;
	
	@Before
	public void init() throws Exception 
	{
		empexpcontroller=new EmployeeExperienceController(); 
		empExpFacadeImpl = mock(EmployeeExperienceFacadeImpl.class); 
		setCollaborator(empexpcontroller, "empExpFacadeImpl", empExpFacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{ 
		Field field;
		field = object.getClass().getDeclaredField(name); 
		field.setAccessible(true);
		field.set(object, service); 
	}
	
	public List<EmployeeExperience> getEmpExpDetails() {
		List<EmployeeExperience> explist = new ArrayList<EmployeeExperience>();
		EmployeeExperience expdatalist = new EmployeeExperience();
		expdatalist.setEmpExperienceId(1);
		EmployeeExperience expdatalist1 = new EmployeeExperience();
		expdatalist1.setEmpExperienceId(2);
		explist.add(expdatalist);
		explist.add(expdatalist1);
		return explist; 
	}
	
	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		List<EmployeeExperience> explist = new ArrayList<EmployeeExperience>();
		EmployeeExperience expdatalist = new EmployeeExperience();
		expdatalist.setEmpExperienceId(null);
		expdatalist.setEmpExperience(null);
		expdatalist.setStatus(null);
		explist.add(expdatalist);
		exprequest.setEmpExperienceList(null);
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		exprequest.setEmpExperienceList(this.getEmpExpDetails());
		exprequest.setTransactionType("save");
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		exprequest.setEmpExperienceList(this.getEmpExpDetails());
		exprequest.setTransactionType("update");
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
 
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		exprequest.setEmpExperienceList(this.getEmpExpDetails());
		exprequest.setTransactionType("delete");
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setStatus(true);
		List<EmployeeExperience> geo = new ArrayList<EmployeeExperience>();
		geo.add(exp2);
		exprequest.setEmpExperienceList(geo);
		exprequest.setTransactionType("save");

		when(empExpFacadeImpl.setEmpExp(exprequest))
				.thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		EmployeeExperience exp1 = new EmployeeExperience();
		exp1.setStatus(false);
		List<EmployeeExperience> geo2 = new ArrayList<EmployeeExperience>();
		geo2.add(exp1);
		exprequest.setEmpExperienceList(geo2);
		exprequest.setTransactionType("save");
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenThrow(RuntimeException.class);
		ResponseEntity<Object> setBus = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		EmployeeExperience exp = new EmployeeExperience();
		exp.setStatus(false);
		List<EmployeeExperience> geo1 = new ArrayList<EmployeeExperience>();
		geo1.add(exp);
		exprequest.setEmpExperienceList(geo1);
		exprequest.setTransactionType("save");
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		EmployeeExperience exp2 = new EmployeeExperience();	
		exp2.setStatus(false);
		List<EmployeeExperience> geo = new ArrayList<EmployeeExperience>();
		geo.add(exp2);
		exprequest.setEmpExperienceList(geo);
		exprequest.setTransactionType("update");
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();
		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setStatus(false);
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		exp.add(exp2);
		exprequest.setEmpExperienceList(exp);
		exprequest.setTransactionType("delete");
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();

		exprequest.setEmpExperienceList(this.getEmpExpDetails());
		exprequest.setTransactionType(null);
		when(empExpFacadeImpl.setEmpExp(exprequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = empexpcontroller.setEmpExp(exprequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();

		exprequest.setEmpExperienceList(this.getEmpExpDetails());
		exprequest.setTransactionType("getById");

		exprequest.getEmpExperienceList().get(0).getEmpExperienceId();

		when(empExpFacadeImpl.getEmpExp(exprequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = empexpcontroller.getEmpExp(exprequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();

		exprequest.setEmpExperienceList(this.getEmpExpDetails());
		exprequest.setTransactionType("getById");

		exprequest.getEmpExperienceList().get(0).setEmpExperienceId(null);

		when(empExpFacadeImpl.getEmpExp(exprequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = empexpcontroller.getEmpExp(exprequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception { 
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		EmployeeExperienceRequest exprequest = new EmployeeExperienceRequest();

		exprequest.setEmpExperienceList(this.getEmpExpDetails());
		exprequest.setTransactionType("getAll");
		when(empExpFacadeImpl.getEmpExp(exprequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = empexpcontroller.getEmpExp(exprequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}



