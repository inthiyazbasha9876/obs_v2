package com.ojas.obs.title.controllerTest;

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
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.controller.TitleController;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.TitleFacade;
import com.ojas.obs.model.Model;
import com.ojas.request.Request;
import com.ojas.response.Response;

public class ControllerTest {

	@Mock
	TitleFacade titleFacade;

	@InjectMocks
	private TitleController titleController;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	Request titleRequest;

	@Spy
	Response titleResponse;

	@Spy
	List<Model> titleList;

	@Spy
	Model title;

	@Before
	public void init() {
		titleController = new TitleController();
		titleFacade = mock(TitleFacade.class);
		setCollaborator(titleController, "facade", titleFacade);
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

	public Request titleRequest() {
		titleRequest = new Request();
		Model title = new Model();
		title.setId(1);
		title.setEmployeeId("wser");
		title.setFlag(true);
		title.setRole(23);
		title.setTitle("abcd");
		title.setCreatedby("abds");
		title.setCreatedby("mnb");
		title.setCreateddate(new Timestamp(21101998));
		List<Model> List = new ArrayList<>();
		List.add(title);
		titleRequest.setTransactionType("save");
		titleRequest.setModel(List);
		return titleRequest;
	}

	@Test
	public void setSuccess() throws Exception {
		titleRequest = titleRequest();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.setTitle(titleRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void setNullTest() throws Exception {
		titleRequest = titleRequest();
		List<Model> List = titleRequest.getModel();
		Model title = new Model();
		title.setEmployeeId(null);
		title.setRole(null);
		title.setTitle(null);
		List.add(title);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.setTitle(titleRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setTransactionNullTest() throws Exception {
		titleRequest = titleRequest();
		titleRequest.setTransactionType("");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.setTitle(titleRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setUpdateCondition() throws Exception {
		titleRequest = titleRequest();
		Model title = new Model();
		title.setUpdatedby("aaa");
		title.setId(null);
		title.setRole(3);
		title.setTitle("false");
		title.setUpdateddate(new Timestamp(21101998));
		titleRequest.setTransactionType("update");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.setTitle(titleRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setTestDeleteCondition() throws Exception {
		titleRequest = titleRequest();
		List<Model> List = titleRequest.getModel();
		Model title = new Model();
		title.setId(null);
		List.add(title);
		titleRequest.setTransactionType("delete");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.setTitle(titleRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setTestDeleteConditionFail() throws Exception {
		titleRequest = titleRequest();
		List<Model> List = titleRequest.getModel();
		Model title = new Model();
		title.setId(123);
		List.add(title);
		titleRequest.setTransactionType("delete");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.setTitle(titleRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.getTitle(titleRequest)).thenThrow(SQLException.class);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setObjectNullCheckTest() throws Exception {
		titleRequest = new Request();
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		titleRequest.setTransactionType("save");
		when(titleFacade.setTitle(titleRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> save = titleController.setEmployeeBUDetails(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getSuccess() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		titleRequest = new Request();
		titleRequest.setTransactionType("getall");
		when(titleFacade.getTitle(titleRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> save = titleController.getTitle(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		titleRequest = new Request();
		titleRequest.setTransactionType("getbyid");
		when(titleFacade.getTitle(titleRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> save = titleController.getTitle(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getTransactionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		titleRequest = titleRequest();
		when(titleFacade.getTitle(titleRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> save = titleController.getTitle(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(titleFacade.getTitle(titleRequest)).thenThrow(SQLException.class);
		ResponseEntity<Object> save = titleController.getTitle(titleRequest, request, response);
		HttpStatus statusCode = save.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
}
