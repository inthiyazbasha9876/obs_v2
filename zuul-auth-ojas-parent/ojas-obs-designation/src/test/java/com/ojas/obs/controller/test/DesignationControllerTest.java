package com.ojas.obs.controller.test;

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
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
//import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.model.Designation;
import com.ojas.main.OjasObsDesignationApplicationTests;
import com.ojas.obs.controller.DesignationController;
import com.ojas.obs.facade.DesignationFacade;
import com.ojas.obs.request.DesignationRequest;
import com.ojas.obs.response.DesignationResponse;
import com.ojas.obs.utility.ErrorResponse;

//@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootConfiguration
public class DesignationControllerTest extends OjasObsDesignationApplicationTests { 
 
	@Mock
	DesignationFacade designationFacade;

	@InjectMocks
	private DesignationController designationController;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	DesignationRequest dRequest = new DesignationRequest();
	@Spy
	DesignationResponse dResponse;
	@Spy
	List<Designation> designationDetails = new ArrayList<>();
	@Spy
	DesignationRequest designationRequest;
	@Spy
	DesignationRequest designationRequest1 = null;
	@Spy
	Designation designation;

	@Before
	public void init() throws Exception {
		designationController = new DesignationController();
		designationFacade = mock(DesignationFacade.class);
		setCollabarator(designationController, "designationFacade", designationFacade);

	}

	public void setCollabarator(DesignationController designationController2, String name,
			DesignationFacade designationFacade2) throws Exception {
		Field field;
			field = designationController2.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(designationController2, designationFacade2);
		} 

	public DesignationRequest designationRequest() {
		List<Designation> l = new ArrayList<>();
		dRequest = new DesignationRequest();
		Designation des = new Designation();
		// des.setId(1);
		des.setDesignation("java");
		l.add(des);
		dRequest.setDesignation(l);
		dRequest.setTransactionType("save");
		return dRequest;
	}

	
	@SuppressWarnings("deprecation")
	@Test
	public void setDesignation() throws SQLException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.setDesignation(anyObject())).thenReturn(sucessResponse);

		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest(), request,
				response);
		HttpStatus status = setDesignation.getStatusCode();

		assertEquals(HttpStatus.OK, status);

	}

	
	@Test
	public void setDesignationListNullCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DesignationRequest designationRequest = new DesignationRequest();
//		List<Designation> desList = new ArrayList<Designation>();
//		designationRequest.setDesignation(desList);
		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest, request,
				response);
		HttpStatus status = setDesignation.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
		// when(designationFacade.setDesignation(designationRequest).thenReturn(failureResponse));
	}

	@Test
	public void setDesignationListEmptyCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setDesignation(Collections.emptyList());
//	List<Designation> desList = new ArrayList<Designation>();
//	designationRequest.setDesignation(desList);
		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest, request,
				response);
		HttpStatus status = setDesignation.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);
		// when(designationFacade.setDesignation(designationRequest).thenReturn(failureResponse));
	}
	/*
	 * @Test public void setDesignationListCheck() throws SQLException {
	 * HttpServletRequest request = null; HttpServletResponse response = null;
	 * DesignationRequest designationRequest = new DesignationRequest();
	 * List<Designation> desList = new ArrayList<Designation>();
	 * designationRequest.setDesignation(desList);
	 * 
	 * ResponseEntity<Object> setDesignation =
	 * designationController.setDesignation(designationRequest, request, response);
	 * HttpStatus status = setDesignation.getStatusCode();
	 * assertNotEquals(HttpStatus.OK, status); //
	 * when(designationFacade.setDesignation(designationRequest).thenReturn(
	 * failureResponse)); }
	 * 
	 * @Test public void setDesignationTransactionType() throws SQLException {
	 * HttpServletRequest request = null; HttpServletResponse response = null;
	 * DesignationRequest designationRequest = new DesignationRequest();
	 * designationRequest.setDesignation(Collections.emptyList()); //
	 * List<Designation> desList = new ArrayList<Designation>();
	 * designationRequest.setTransactionType("update"); //
	 * designationRequest.setDesignation(desList);
	 * when(designationFacade.setDesignation(designationRequest)).thenReturn(
	 * failureResponse);
	 * 
	 * ResponseEntity<Object> setDesignation =
	 * designationController.setDesignation(designationRequest, request, response);
	 * HttpStatus status = setDesignation.getStatusCode();
	 * assertNotEquals(HttpStatus.OK, status); //
	 * when(designationFacade.setDesignation(designationRequest).thenReturn(
	 * failureResponse)); }
	 */

	@Test
	public void setIDNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("update");
		Designation designation = new Designation();
		designation.setId(null);
		List<Designation> distignationlist = new ArrayList<>();
		distignationlist.add(designation);
		designationRequest.setDesignation(distignationlist);
		// when(designationFacade.setDesignation(designationRequest)).thenReturn(failureResponse);

		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest, request,
				response);
		HttpStatus status = setDesignation.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);

	}

	@Test
	public void setIDDNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("delete");
		Designation designation = new Designation();
		designation.setId(null);
		List<Designation> distignationlist = new ArrayList<>();
		distignationlist.add(designation);
		designationRequest.setDesignation(distignationlist);
		// when(designationFacade.setDesignation(designationRequest)).thenReturn(failureResponse);

		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest, request,
				response);
		HttpStatus status = setDesignation.getStatusCode();
		assertNotEquals(HttpStatus.OK, status);

	}

	@Test
	public void setIDnotNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("update");
		Designation designation = new Designation();
		designation.setId(123);
		List<Designation> distignationlist = new ArrayList<>();
		distignationlist.add(designation);
		designationRequest.setDesignation(distignationlist);
		when(designationFacade.setDesignation(designationRequest)).thenReturn(sucessResponse);

		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest, request,
				response);
		HttpStatus status = setDesignation.getStatusCode();
		assertEquals(HttpStatus.OK, status);

	}
	
	@Test
	public void setIDNull2() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("update");
		Designation designation = new Designation();
		designation.setId(null);
		designation.setDesignation("Java");
		List<Designation> distignationlist = new ArrayList<>();
		distignationlist.add(designation);
		designationRequest.setDesignation(distignationlist);
		when(designationFacade.setDesignation(designationRequest)).thenReturn(sucessResponse);

		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest, request,
				response);
		HttpStatus status = setDesignation.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}
	@SuppressWarnings("deprecation")
	@Test
	public void setDuplicateExceptionDesignation() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.setDesignation(anyObject())).thenThrow(new DuplicateKeyException(null));
		List<Designation> l = new ArrayList<>();
		dRequest = new DesignationRequest();
		Designation des = new Designation();
		// des.setId(1);
		des.setDesignation("java");
		des.setDesignation("java");
		l.add(des);
		dRequest.setDesignation(l);
		dRequest.setTransactionType("save");
		

		ResponseEntity<Object> setDesignation = designationController.setDesignation(dRequest, request,	response);
		HttpStatus status = setDesignation.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, status);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void setSQLExceptionDesignation() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.setDesignation(anyObject())).thenThrow(new SQLException());

		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest(), request,	response);
		HttpStatus status = setDesignation.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, status);

	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void setExceptionDesignation() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.setDesignation(anyObject())).thenThrow(new RuntimeException());

		ResponseEntity<Object> setDesignation = designationController.setDesignation(designationRequest(), request,	response);
		HttpStatus status = setDesignation.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, status);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void getDesignation() throws SQLException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.getDesignation(anyObject())).thenReturn(sucessResponse);

		ResponseEntity<Object> getDesignation = designationController.getDesignation(designationRequest(), request,
				response);
		HttpStatus status = getDesignation.getStatusCode();

		assertEquals(HttpStatus.OK, status);

	}

	@Test
	public void getDesignationNullCheck() throws SQLException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		// when(designationFacade.setDesignation(anyObject())).thenReturn(sucessResponse);
//		DesignationRequest designationRequest = new DesignationRequest();

		ResponseEntity<Object> getDesignation = designationController.getDesignation(null, request, response);
		HttpStatus status = getDesignation.getStatusCode();

		assertNotEquals(HttpStatus.OK, status);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void getDesignationNotNullCheck() throws SQLException {

		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.setDesignation(anyObject())).thenReturn(sucessResponse);
		// DesignationRequest designationRequest = new DesignationRequest();

		designationController.getDesignation(designationRequest(), request,
				response);
		// HttpStatus status = getDesignation.getStatusCode();

		// assertEquals(HttpStatus.OK, status);

	}
 
	@SuppressWarnings("deprecation")
	@Test
	public void getSQLExceptionDesignation() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.getDesignation(anyObject())).thenThrow(new SQLException());

		ResponseEntity<Object> getDesignation = designationController.getDesignation(designationRequest(), request,	response);
		HttpStatus status = getDesignation.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, status);

	}
	
	
	
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void getExceptionDesignation() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(designationFacade.getDesignation(anyObject())).thenThrow(new RuntimeException());

		ResponseEntity<Object> getDesignation = designationController.getDesignation(designationRequest(), request,	response);
		HttpStatus status = getDesignation.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, status);

	}
	
	
	
	
	
	
	
	
	
	
	/*
	 * public void setCollabarator(Object object, String name, Object collabarator)
	 * { Field field; try { field = object.getClass().getDeclaredField(name);
	 * field.setAccessible(true); field.set(object, collabarator); } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */
	/*
	 * @Test public void setCatchTest() throws SQLException {
	 * when(designationFacade.setDesignation(designationRequest)).thenReturn(
	 * failureResponse); HttpServletRequest request = null; HttpServletResponse
	 * response = null;
	 * 
	 * ResponseEntity<Object> setDesignation =
	 * designationController.setDesignation(designationRequest1,request,response);
	 * HttpStatus status = setDesignation.getStatusCode();
	 * assertNotEquals(HttpStatus.OK, status);
	 * 
	 * 
	 * }
	 */
}

// 
//package com.ojas.obs.controller;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.mockito.Matchers.anyObject;
//import java.lang.reflect.Field;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.ojas.main.OjasObsDesignationApplicationTests;
//import com.ojas.model.Designation;
//import com.ojas.obs.facade.DesignationFacade;
//import com.ojas.obs.request.DesignationRequest;
//import com.ojas.obs.response.DesignationResponse;
//import com.ojas.utility.ErrorResponse;
//import static org.mockito.Matchers.anyString;
//@SpringBootConfiguration
//
//public class DesignationControllerTest extends OjasObsDesignationApplicationTests {
//
//	@Mock
//	DesignationFacade designationFacade;
//
//	@InjectMocks
//	DesignationController designationController;
//
//	@Spy
//	ErrorResponse err = new ErrorResponse();
//
//	@Spy
//	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
//
//	@Spy
//	ResponseEntity<Object> successEntity = new ResponseEntity<>(err, HttpStatus.OK);
//
//	@Spy
//	DesignationRequest designationReq;
//
//	@Spy
//	List<DesignationResponse> empList = new ArrayList<DesignationResponse>();
//
//	@Spy
//	Designation designation;
//
//	public void setCollabarator(Object object, String name, Object collabarator) {
//		Field field;
//		try {
//			field = object.getClass().getDeclaredField(name);
//			field.setAccessible(true);
//			field.set(object, collabarator);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Before
//	public void init() {
//		designationController = new DesignationController();
//		designationFacade = mock(DesignationFacade.class);
//		setCollabarator(designationController, "designationFacade", designationFacade);
//	}
//
//	public DesignationRequest getDesignationRequest() {
//		designationReq = new DesignationRequest();
//		designation = new Designation();
//		designation.setDesignation("JAVA");
//
//		designationReq.setTransactionType("save");
//		return designationReq;
//	}
//
//	@Test
//	public void testSetDesignation() throws SQLException {
//		HttpServletRequest httpServletRequest = null;
//		HttpServletResponse httpServletResponse = null;
//		when(designationFacade.setDesignation(anyObject())).thenReturn(successEntity);
//		ResponseEntity<Object> setDesignation = designationController.setDesignation(getDesignationRequest(),
//				httpServletRequest, httpServletResponse);
//		assertEquals(HttpStatus.OK, setDesignation.getStatusCode());
//	}
//
//	
//	  @Test public void testGetDesignation() throws SQLException {
//	  HttpServletRequest httpServletRequest = null; HttpServletResponse
//	  httpServletResponse = null;
//	  when(designationFacade.getDesignation(anyObject())).thenReturn( successEntity);
//	  ResponseEntity<Object> designation2 =designationController.getDesignation(getDesignationRequest(),
//	  httpServletRequest, httpServletResponse); HttpStatus statusCode =
//	  designation2.getStatusCode(); assertEquals(HttpStatus.OK, statusCode); }
//	 
//}
