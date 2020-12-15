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

import com.ojas.obs.billingtype.facadeimpl.BillingTypeFacadeImpl;
import com.ojas.obs.billingtype.model.BillingType;
import com.ojas.obs.billingtype.repository.BillingTypeRepository;
import com.ojas.obs.billingtype.request.BillingTypeRequest;
import com.ojas.obs.billingtype.response.BillingTypeResponse;
import com.ojas.obs.billingtype.response.ErrorResponse;

public class BillingTypeFacadeImplTest {

	@InjectMocks
	BillingTypeFacadeImpl impl;
	@Mock
	BillingTypeRepository billingTypeRepository;;
	@Spy
	BillingType billingType;
	@Spy
	BillingTypeRequest request;
	@Spy
	BillingTypeResponse response;
	@Spy
	ErrorResponse errorResponse;

	@Before
	public void init() throws Exception {
		impl = new BillingTypeFacadeImpl();
		billingTypeRepository = mock(BillingTypeRepository.class);
		setCollaborator(impl, "billingTypeRepository", billingTypeRepository);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<BillingType> getBillingList() {
		List<BillingType> list = new ArrayList<BillingType>();
		BillingType BillingType = new BillingType();
		BillingType.setId(1);
		BillingType.setName("kusuma");
		list.add(BillingType);
		return list;
	}

	@Test
	public void setTransaction() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setTransactionType("save");
		request.setBillingList(getBillingList());
		BillingType BillingType = new BillingType();
		BillingType.setId(2);
		when(billingTypeRepository.save(request.getBillingList().get(0))).thenReturn(BillingType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setTransactionNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setTransactionType("save");
		request.setBillingList(getBillingList());
		BillingType BillingType = new BillingType();
		BillingType.setId(null);
		when(billingTypeRepository.save(request.getBillingList().get(0))).thenReturn(BillingType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateNotNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setTransactionType("update");
		request.setBillingList(getBillingList());
		BillingType BillingType = new BillingType();
		BillingType.setId(3);
		when(billingTypeRepository.getOne(1)).thenReturn(BillingType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setTransactionType("update");
		request.setBillingList(getBillingList());
		BillingType BillingType = new BillingType();
		BillingType.setId(null);
		when(billingTypeRepository.getOne(1)).thenReturn(BillingType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setTransactionType("delete");
		request.setBillingList(getBillingList());
		BillingType BillingType = new BillingType();
		BillingType.setStatus(false);
		when(billingTypeRepository.getOne(1)).thenReturn(BillingType);
		when(billingTypeRepository.save(BillingType)).thenReturn(request.getBillingList().get(0));
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setDeleteNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setTransactionType("delete");
		request.setBillingList(getBillingList());
		BillingType BillingType = new BillingType();
		BillingType.setStatus(getBillingList().isEmpty());
		when(billingTypeRepository.getOne(1)).thenReturn(BillingType);
		// when(billingTypeRepository.save(BillingType)).thenReturn(request.getBillingList().get(0));
		when(billingTypeRepository.save(BillingType)).thenReturn(BillingType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotAcceptable() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setTransactionType("");
		request.setBillingList(getBillingList());
		BillingType BillingType = new BillingType();
		BillingType.setStatus(getBillingList().isEmpty());
		when(billingTypeRepository.getOne(1)).thenReturn(BillingType);
		when(billingTypeRepository.save(BillingType)).thenReturn(BillingType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE,statusCode);
	}

	@Test
	public void setGetAllNotNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingList());
		request.setTransactionType("getall");
		BillingType BillingType = new BillingType();
		BillingType.setStatus(false);
		when(billingTypeRepository.findAll()).thenReturn(request.getBillingList());
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetAllNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingList());
		request.setTransactionType("getall");
		request.getBillingList().get(0).setId(1);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdNotNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingList());
		request.setTransactionType("getbyid");
		BillingType BillingType = new BillingType();
		BillingType.setId(1);
		when(billingTypeRepository.getOne(1)).thenReturn(BillingType);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetByIdNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingList());
		request.setTransactionType("getbyid");
		BillingType billingType = new BillingType();
		billingType.setId(null);
		when(billingTypeRepository.getOne(1)).thenReturn(billingType);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdMethodNull() throws DuplicateKeyException, Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingList());
		request.setTransactionType("");
		BillingType BillingType = new BillingType();
		when(billingTypeRepository.getOne(1)).thenReturn(BillingType);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE,status);
	}
}