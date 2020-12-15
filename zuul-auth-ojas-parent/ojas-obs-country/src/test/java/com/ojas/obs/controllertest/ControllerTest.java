package com.ojas.obs.controllertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.country.controller.CountryController;
import com.ojas.obs.country.facadeImpl.CountryFacadeImpl;
import com.ojas.obs.country.model.Country;
import com.ojas.obs.country.request.CountryRequest;
import com.ojas.obs.country.response.CountryResponse;
import com.ojas.obs.country.response.ErrorResponse;

public class ControllerTest {

	@InjectMocks
	CountryController controller;
	@Mock
	CountryFacadeImpl impl;
	@Spy
	CountryRequest request;
	@Spy
	CountryResponse response = new CountryResponse();
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<Object>(response, HttpStatus.OK);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	Country country;

	@Before
	public void init() throws Exception {
		controller = new CountryController();
		impl = mock(CountryFacadeImpl.class);
		setCollaborator(controller, "countryFacadeImpl", impl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<Country> getCountryList() {
		List<Country> list = new ArrayList<Country>();
		Country country = new Country();
		country.setId(1);
		country.setCountryname("UK");
		list.add(country);
		return list;
	}

	@Test
	public void setCountryNullList() throws DuplicateKeyException {
		request = new CountryRequest();
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setCountryTypeNullType() throws NullPointerException {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		// request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTransactionEmpty() {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void countryStatusNull() {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.getCountrylist().get(0).setStatus(null);
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void countryNameEmpty() {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.getCountrylist().get(0).setCountryname("UK");
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNull() {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.getCountrylist().get(0).setId(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNulll() {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.getCountrylist().get(0).setStatus(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteIdNull() {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.getCountrylist().get(0).setId(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteNameNull() {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.getCountrylist().get(0).setStatus(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setCountrySuccessList() throws Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("save");
		request.getCountrylist().get(0).setStatus(true);
		request.getCountrylist().get(0).setCountryname("UK");
		when(impl.saveDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setCountry_DuplicateKeyException() throws Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("save");
		request.getCountrylist().get(0).setStatus(true);
		request.getCountrylist().get(0).setCountryname("UK");
		when(impl.saveDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setCountry_Exception() throws Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("save");
		request.getCountrylist().get(0).setStatus(true);
		request.getCountrylist().get(0).setCountryname("UK");
		when(impl.saveDetails(request)).thenThrow(new Exception());
		ResponseEntity<Object> responseEntity = controller.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void getExceptionNull() throws Exception {
		CountryRequest request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = controller.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getByIdEmpty() throws Exception {
		CountryRequest request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = controller.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdNull() {
		CountryRequest request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("getbyid");
		request.getCountrylist().get(0).setId(null);
		ResponseEntity<Object> responseEntity = controller.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getAllDetails() {
		CountryRequest request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("getbyid");
		request.getCountrylist().get(0).setId(1);
		ResponseEntity<Object> responseEntity = controller.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void set_DuplicateKeyException() throws Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountryList());
		request.setTransactionType("getbyid");
		request.getCountrylist().get(0).setStatus(true);
		request.getCountrylist().get(0).setCountryname("UK");
		when(impl.getAllDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = controller.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

//	@Test
//	public void set_Exception() throws Exception {
//		request = new CountryRequest();
//		request.setCountrylist(getCountryList());
//		request.setTransactionType("getall");
//		request.getCountrylist().get(0).setStatus(true);
//		request.getCountrylist().get(0).setCountryname("UK");
//		when(impl.getAllDetails(request)).thenThrow(new Exception());
//		ResponseEntity<Object> responseEntity = controller.getAllIssues(request);
//		HttpStatus status = responseEntity.getStatusCode();
//		assertEquals(HttpStatus.CONFLICT, status);
//	}

}
