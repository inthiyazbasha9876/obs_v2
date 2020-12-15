package com.ojas.obs.title.facadeTest;

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

import com.ojas.obs.dao.TitleDao;
import com.ojas.obs.dao.TitleDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.TitleFacade;
import com.ojas.obs.model.Model;
import com.ojas.request.Request;
import com.ojas.response.Response;

public class FacadeTest {

	@Mock
	TitleDao titleDao;

	@Mock
	TitleDaoImpl titleDaoImpl;

	@InjectMocks
	private TitleFacade titleFacade;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	Request request;

	@Spy
	Response response;

	@Spy
	List<Model> List;

	@Spy
	Model title;

	@Spy
	List<Model> titleList = new ArrayList<Model>();
	
	@Before
	public void init() {
		titleList.add(title);
		titleFacade = new TitleFacade();
		titleDaoImpl = mock(TitleDaoImpl.class);
		setCollaborator(titleFacade, "titleDao", titleDaoImpl);
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
	
	public Request request() {
		request = new Request();
		Model title = new Model();
		title.setId(1);
		title.setEmployeeId("wser");
		title.setFlag(true);
		title.setRole(23);
		title.setTitle("abcd");
		title.setCreatedby("abds");
		title.setCreatedby("mnb");
		title.setUpdatedby("323");
		title.setUpdateddate(new Timestamp(21101998));
		title.setCreateddate(new Timestamp(21101998));
		Model title2 = new Model();
		title2.setId(1);
		title2.setEmployeeId("wser");
		title2.setFlag(true);
		title2.setRole(23);
		title2.setTitle("abcd");
		title2.setCreatedby("abds");
		title2.setCreatedby("mnb");
		title2.setUpdatedby("323");
		title2.setUpdateddate(new Timestamp(21101998));
		title2.setCreateddate(new Timestamp(21101998));

		List<Model> List = new ArrayList<>();
		List.add(title2);
		List.add(title);
		request.setTransactionType("save");
		request.setModel(List);
		return request;
	}
	
	@Test
	public void setSaveSuccess() throws SQLException {
		request = request();
		when(titleDaoImpl.saveTitle(request)).thenReturn(true);
		ResponseEntity<Object> saveBu = titleFacade.setTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void setSaveFail() throws SQLException {
		request = request();
		when(titleDaoImpl.saveTitle(request)).thenReturn(false);
		ResponseEntity<Object> saveBu = titleFacade.setTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateSuccess() throws SQLException {
		request = request();
		request.setTransactionType("update");
		when(titleDaoImpl.updateTitle(request)).thenReturn(true);
		ResponseEntity<Object> saveBu = titleFacade.setTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void setUpdateFail() throws SQLException {
		request = request();
		request.setTransactionType("update");
		when(titleDaoImpl.updateTitle(request)).thenReturn(false);
		ResponseEntity<Object> saveBu = titleFacade.setTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void getSuccess() throws SQLException {
		request = request();
		request.setTransactionType("getAll");
		when(titleDaoImpl.getAllTitle(request)).thenReturn(titleList);
		ResponseEntity<Object> saveBu = titleFacade.getTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {
		request = request();
		request.setTransactionType("getbyid");
		when(titleDaoImpl.getById(request)).thenReturn(titleList);
		ResponseEntity<Object> saveBu = titleFacade.getTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getNullList() throws SQLException {
		request = request();
		request.setTransactionType("getall");
		when(titleDaoImpl.getAllTitle(request)).thenReturn(null);
		ResponseEntity<Object> saveBu = titleFacade.getTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getEmptyList() throws SQLException {
	request = request();
		request.setTransactionType("getAll");
		when(titleDaoImpl.getAllTitle(request)).thenReturn(Collections.emptyList());
		ResponseEntity<Object> saveBu = titleFacade.getTitle(request);
		HttpStatus statusCode = saveBu.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
}
