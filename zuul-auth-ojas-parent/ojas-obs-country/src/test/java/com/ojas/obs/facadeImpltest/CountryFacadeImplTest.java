package com.ojas.obs.facadeImpltest;

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

import com.ojas.obs.country.facadeImpl.CountryFacadeImpl;
import com.ojas.obs.country.model.Country;
import com.ojas.obs.country.repositories.CountryRepository;
import com.ojas.obs.country.request.CountryRequest;
import com.ojas.obs.country.response.CountryResponse;
import com.ojas.obs.country.response.ErrorResponse;

public class CountryFacadeImplTest {

	@InjectMocks
	CountryFacadeImpl impl;
	@Mock
	CountryRepository countryRepository;;
	@Spy
	Country country;
	@Spy
	CountryRequest request;
	@Spy
	CountryResponse response;
	@Spy
	ErrorResponse errorResponse;

	@Before
	public void init() throws Exception {
		impl = new CountryFacadeImpl();
		countryRepository = mock(CountryRepository.class);
		setCollaborator(impl, "countryDao", countryRepository);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<Country> getCountrylist() {
		List<Country> list = new ArrayList<Country>();
		Country Country = new Country();
		Country.setId(1);
		Country.setCountryname("UK");
		list.add(Country);
		return list;
	}

	@Test
	public void setTransaction() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setTransactionType("save");
		request.setCountrylist(getCountrylist());
		Country Country = new Country();
		Country.setId(2);
		when(countryRepository.save(request.getCountrylist().get(0))).thenReturn(Country);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setTransactionNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setTransactionType("save");
		request.setCountrylist(getCountrylist());
		Country Country = new Country();
		Country.setId(null);
		when(countryRepository.save(request.getCountrylist().get(0))).thenReturn(Country);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateNotNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setTransactionType("update");
		request.setCountrylist(getCountrylist());
		Country Country = new Country();
		Country.setId(3);
		when(countryRepository.getOne(1)).thenReturn(Country);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setTransactionType("update");
		request.setCountrylist(getCountrylist());
		Country Country = new Country();
		Country.setId(null);
		when(countryRepository.getOne(1)).thenReturn(Country);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setTransactionType("delete");
		request.setCountrylist(getCountrylist());
		Country Country = new Country();
		Country.setStatus(false);
		when(countryRepository.getOne(1)).thenReturn(Country);
		when(countryRepository.save(Country)).thenReturn(request.getCountrylist().get(0));
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setDeleteNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setTransactionType("delete");
		request.setCountrylist(getCountrylist());
		Country Country = new Country();
		Country.setStatus(getCountrylist().isEmpty());
		when(countryRepository.getOne(1)).thenReturn(Country);
		// when(CountryRepository.save(Country)).thenReturn(request.getCountrylist().get(0));
		when(countryRepository.save(Country)).thenReturn(Country);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotAcceptable() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setTransactionType("");
		request.setCountrylist(getCountrylist());
		Country Country = new Country();
		Country.setStatus(getCountrylist().isEmpty());
		when(countryRepository.getOne(1)).thenReturn(Country);
		when(countryRepository.save(Country)).thenReturn(Country);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		equals(statusCode);
	}

	@Test
	public void setGetAllNotNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountrylist());
		request.setTransactionType("getall");
		Country Country = new Country();
		Country.setStatus(false);
		when(countryRepository.findAll()).thenReturn(request.getCountrylist());
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetAllNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountrylist());
		request.setTransactionType("getall");
		request.getCountrylist().get(0).setId(1);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdNotNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountrylist());
		request.setTransactionType("getbyid");
		Country Country = new Country();
		Country.setId(1);
		when(countryRepository.getOne(1)).thenReturn(Country);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetByIdNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountrylist());
		request.setTransactionType("getbyid");
		Country Country = new Country();
		when(countryRepository.getOne(null)).thenReturn(Country);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdMethodNull() throws DuplicateKeyException, Exception {
		request = new CountryRequest();
		request.setCountrylist(getCountrylist());
		request.setTransactionType("");
		Country Country = new Country();
		when(countryRepository.getOne(1)).thenReturn(Country);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		equals(status);
	}
}