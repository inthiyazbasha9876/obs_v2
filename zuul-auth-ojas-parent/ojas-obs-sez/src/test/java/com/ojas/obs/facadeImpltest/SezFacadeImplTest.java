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

import com.ojas.obs.sez.facadeimpl.SezFacadeImpl;
import com.ojas.obs.sez.model.Sez;
import com.ojas.obs.sez.repositories.SezRepository;
import com.ojas.obs.sez.request.SezRequest;
import com.ojas.obs.sez.response.ErrorResponse;
import com.ojas.obs.sez.response.SezResponse;

public class SezFacadeImplTest {

	@InjectMocks
	SezFacadeImpl impl;
	@Mock
	SezRepository sezDao;
	@Spy
	Sez sez;
	@Spy
	SezRequest request;
	@Spy
	SezResponse response;
	@Spy
	ErrorResponse errorResponse;

	@Before
	public void init() throws Exception {
		impl = new SezFacadeImpl();
		sezDao = mock(SezRepository.class);
		setCollaborator(impl, "sezDao", sezDao);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<Sez> getSezlist() {
		List<Sez> list = new ArrayList<Sez>();
		Sez sez = new Sez();
		sez.setId(1);
		sez.setName("kusuma");
		list.add(sez);
		return list;
	}

	@Test
	public void setTransaction() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setTransactionType("save");
		request.setSezlist(getSezlist());
		Sez sez = new Sez();
		sez.setId(2);
		when(sezDao.save(request.getSezlist().get(0))).thenReturn(sez);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setTransactionNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setTransactionType("save");
		request.setSezlist(getSezlist());
		Sez sez = new Sez();
		sez.setId(null);
		when(sezDao.save(request.getSezlist().get(0))).thenReturn(sez);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateNotNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setTransactionType("update");
		request.setSezlist(getSezlist());
		Sez sez = new Sez();
		sez.setId(3);
		when(sezDao.getOne(1)).thenReturn(sez);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setTransactionType("update");
		request.setSezlist(getSezlist());
		Sez sez = new Sez();
		sez.setId(null);
		when(sezDao.getOne(1)).thenReturn(sez);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setTransactionType("delete");
		request.setSezlist(getSezlist());
		Sez sez = new Sez();
		sez.setStatus(false);
		when(sezDao.getOne(1)).thenReturn(sez);
		when(sezDao.save(sez)).thenReturn(request.getSezlist().get(0));
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setDeleteNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setTransactionType("delete");
		request.setSezlist(getSezlist());
		Sez sez = new Sez();
		sez.setStatus(getSezlist().isEmpty());
		when(sezDao.getOne(1)).thenReturn(sez);
		// when(sezDao.save(sez)).thenReturn(request.getSezlist().get(0));
		when(sezDao.save(sez)).thenReturn(sez);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotAcceptable() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setTransactionType("");
		request.setSezlist(getSezlist());
		Sez sez = new Sez();
		sez.setStatus(getSezlist().isEmpty());
		when(sezDao.getOne(1)).thenReturn(sez);
		when(sezDao.save(sez)).thenReturn(sez);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE,statusCode);
	}

	@Test
	public void setGetAllNotNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getall");
		Sez sez = new Sez();
		sez.setStatus(false);
		when(sezDao.findAll()).thenReturn(request.getSezlist());
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetAllNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getall");
		request.getSezlist().get(0).setId(1);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdNotNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getbyid");
		Sez sez = new Sez();
		sez.setId(1);
		when(sezDao.getOne(1)).thenReturn(sez);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetByIdNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getbyid");
		Sez sez = new Sez();
		sez.setId(null);
		when(sezDao.getOne(1)).thenReturn(sez);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdMethodNull() throws DuplicateKeyException, Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("");
		Sez sez = new Sez();
		when(sezDao.getOne(1)).thenReturn(sez);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE,status);
	}
}