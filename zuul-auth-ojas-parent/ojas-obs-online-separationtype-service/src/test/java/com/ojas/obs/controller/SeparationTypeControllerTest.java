package com.ojas.obs.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.ojas.obs.facade.SeparationTypeFacade;
import com.ojas.obs.main.*;
import com.ojas.obs.model.SeparationType;




import com.ojas.obs.request.SeparationTypeRequest;
import com.ojas.obs.response.SeparationTypeResponse;
import com.ojas.obs.utility.ErrorResponse;

public class SeparationTypeControllerTest {

	@InjectMocks
	SeparationTypeController separationTypeController;

	@Mock
	SeparationTypeFacade separationTypeFacade;

	@Spy
	SeparationTypeRequest separationTypeRequest;
	@Spy
	SeparationTypeResponse separationTypeResponse;
	@Spy
	ErrorResponse errorResponse;
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<Object>(HttpStatus.OK);
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	SeparationTypeRequest separationTypeRequests;

	@Before
	public void init() throws Exception {
		separationTypeController = new SeparationTypeController();
		separationTypeFacade = mock(SeparationTypeFacade.class);
		setCollabarator(separationTypeController, "separationTypeFacade", separationTypeFacade);
	}

	private void setCollabarator(SeparationTypeController separationTypeController2, String string,
			SeparationTypeFacade separationTypeFacade2) throws Exception {
		Field field;
		field = separationTypeController2.getClass().getDeclaredField(string);
		field.setAccessible(true);
		field.set(separationTypeController2, separationTypeFacade2);
	}

	public SeparationTypeRequest separationTypeRequest() {
		List<SeparationType> l = new ArrayList<>();
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		SeparationType sep = new SeparationType();
		sep.setSeparationType("java");
		sep.setSeparationTypeId(1);
		l.add(sep);
		separationTypeRequest.setSeparationType(l);
		separationTypeRequest.setTransactionType("save");
		return separationTypeRequest;

	}

	@SuppressWarnings("deprecation")
	@Test
	public void setSeparation() throws SQLException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(separationTypeFacade.setSeparationTypeDetails(anyObject())).thenReturn(sucessResponse);
		separationTypeController.setSeparationType(separationTypeRequest(), request, response);

	}

	@Test
	public void setSeparationTypeListNullCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
	}

	@Test
	public void setSeparationTypeEmptytest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setSeparationType(Collections.emptyList());
		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
	}

	@Test
	public void setSeparationTypeListCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		List<SeparationType> sepList = new ArrayList<SeparationType>();
		separationTypeRequest.setSeparationType(sepList);
		// when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> separationType = separationTypeController.setSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = separationType.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
	}

	@Test
	public void setIDNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("update");
		SeparationType separationType = new SeparationType();
		separationType.setSeparationTypeId(null);
		List<SeparationType> separationTypelist = new ArrayList<>();
		separationTypelist.add(separationType);
		separationTypeRequest.setSeparationType(separationTypelist);
		// when(designationFacade.setDesignation(designationRequest)).thenReturn(failureResponse);

		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);

	}

	@Test
	public void setIDDNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("delete");
		SeparationType separationType = new SeparationType();
		separationType.setSeparationTypeId(null);
		List<SeparationType> separationTypelist = new ArrayList<>();
		separationTypelist.add(separationType);
		separationTypeRequest.setSeparationType(separationTypelist);
		// when(designationFacade.setDesignation(designationRequest)).thenReturn(failureResponse);

		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);

	}

	@Test
	public void setIDnotNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		//separationTypeRequest.setTransactionType("update");
		//SeparationType separationType = new SeparationType();
		//separationType.setSeparationTypeId(123);
		//List<SeparationType> separationTypelist = new ArrayList<>();
		//separationTypelist.add(separationType);
		//separationTypeRequest.setSeparationType(separationTypelist);
		when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest)).thenReturn(failureResponse);

		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}


	@Test
	public void setIDnotNull1() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("update");
		SeparationType separationType = new SeparationType();
		separationType.setSeparationTypeId(null);
		separationType.setSeparationType("abc");
		List<SeparationType> separationTypelist = new ArrayList<>();
		separationTypelist.add(separationType);
		separationTypeRequest.setSeparationType(separationTypelist);
		when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest)).thenReturn(sucessResponse);

		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setExceptionSeparationType() throws SQLException {
		
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		//separationTypeRequest = separationTypeRequest();
		when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest())).thenThrow(new RuntimeException());
		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest(), request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		 assertEquals(HttpStatus.CONFLICT, status);
		/*
		 * HttpServletRequest request = null; HttpServletResponse response = null;
		 * 
		 * // SeparationTypeRequest separationTypeRequest = separationTypeRequest();
		 * 
		 * separationTypeRequest.setTransactionType("save"); SeparationType
		 * separationType = new SeparationType();
		 * separationType.setSeparationType("abc");; List<SeparationType>
		 * separationTypelist = new ArrayList<>();
		 * separationTypelist.add(separationType);
		 * separationTypeRequest.setSeparationType(separationTypelist);
		 * 
		 * when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest())).
		 * thenThrow(new RuntimeException()); ResponseEntity<Object> setSeparationType =
		 * separationTypeController.setSeparationType(separationTypeRequest(), request,
		 * response); HttpStatus status = setSeparationType.getStatusCode();
		 * assertEquals(HttpStatus.CONFLICT, status);
		 */

	}

	@Test
	public void setDuplicateExceptionSeparationType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest()))
				.thenThrow(new DuplicateKeyException(null));
		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest(),
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);

	}

	@Test
	public void setSQLExceptionSeparationType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		// SeparationTypeRequest separationTypeRequest = separationTypeRequest();
		/*
		 * separationTypeRequest.setTransactionType("save"); SeparationType
		 * separationType = new SeparationType();
		 * separationType.setSeparationType("abc");; List<SeparationType>
		 * separationTypelist = new ArrayList<>();
		 * separationTypelist.add(separationType);
		 * separationTypeRequest.setSeparationType(separationTypelist);
		 */
		when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest())).thenThrow(SQLException.class);
		ResponseEntity<Object> setSeparationType = separationTypeController.setSeparationType(separationTypeRequest(),
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);

	}

	@Test
	public void getSeparationType() throws SQLException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		// SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		when(separationTypeFacade.getSeparationType(separationTypeRequest)).thenReturn(sucessResponse);

		separationTypeController.getAllSeparationType(separationTypeRequest(), request, response);

	}

	@Test
	public void getSeparationTypeNullCheck() throws SQLException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;

		ResponseEntity<Object> getSeparationType = separationTypeController.getAllSeparationType(null, request,
				response);
		HttpStatus status = getSeparationType.getStatusCode();

		assertNotEquals(HttpStatus.OK, status);

	}

	@Test
	public void getSeparationTypeTransNullCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		separationTypeRequest = null;
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("sessionId");

		// List<SeparationType> distignationlist = new ArrayList<>();
		// distignationlist.add(separationType);
		// separationTypeRequest.setSeparationType(distignationlist );
		when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest)).thenReturn(failureResponse);

		separationTypeController.getAllSeparationType(separationTypeRequest, request, response);

	}

	@Test
	public void getSQLExceptionSeparationType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		SeparationTypeRequest separationTypeRequest = separationTypeRequest();
		separationTypeRequest.setTransactionType("getall");
		when(separationTypeFacade.getSeparationType(separationTypeRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> setSeparationType = separationTypeController.getAllSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);

	}

	@Test
	public void getgetExceptionSeparationType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		SeparationTypeRequest separationTypeRequest = separationTypeRequest();
		separationTypeRequest.setTransactionType("getall");
		when(separationTypeFacade.getSeparationType(separationTypeRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setSeparationType = separationTypeController.getAllSeparationType(separationTypeRequest,
				request, response);
		HttpStatus status = setSeparationType.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);

	}

	/*
	 * @Test public void setSeparationTypeTransactionNullTest() throws SQLException
	 * { HttpServletRequest request = null; HttpServletResponse response = null;
	 * SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
	 * separationTypeRequest.setTransactionType(null);
	 * //when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest)).
	 * thenReturn(failureResponse); ResponseEntity<Object> separationType =
	 * separationTypeController.setSeparationType(separationTypeRequest, request,
	 * response); HttpStatus statusCode = separationType.getStatusCode();
	 * 
	 * assertNotEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); }
	 */

}

/*
 * @Test public void setSeparationTypeNullCheck() throws SQLException {
 * HttpServletRequest httpServletRequest = null; HttpServletResponse
 * httpServletResponse = null; separationTypeRequest = new
 * SeparationTypeRequest(); List<SeparationType> separationList = new
 * ArrayList<SeparationType>(); SeparationType sepType = new SeparationType();
 * SeparationType sepType1 = new SeparationType();
 * //sepType.setSeparationType("qqq"); separationList.add(sepType);
 * separationList.add(sepType1);
 * separationTypeRequest.setTransactionType("save");
 * separationTypeRequest.setSeparationType(separationList);
 * when(separationTypeFacade.setSeparationTypeDetails(separationTypeRequest)).
 * thenReturn(failureResponse); ResponseEntity<Object> response =
 * separationTypeController.setSeparationType(separationTypeRequest,
 * httpServletRequest, httpServletResponse); HttpStatus status =
 * response.getStatusCode(); assertNotEquals(HttpStatus.OK, status); }
 */
