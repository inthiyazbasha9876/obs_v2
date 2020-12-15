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

import com.ojas.obs.contractcompany.facadeimpl.ContractCompanyFacadeImpl;
import com.ojas.obs.contractcompany.model.ContractCompany;
import com.ojas.obs.contractcompany.repository.ContractCompanyRepository;
import com.ojas.obs.contractcompany.request.ContractCompanyRequest;
import com.ojas.obs.contractcompany.response.ContractCompanyResponse;
import com.ojas.obs.contractcompany.response.ErrorResponse;

public class ContractCompanyFacadeImplTest {

	@InjectMocks
	ContractCompanyFacadeImpl impl;
	@Mock
	ContractCompanyRepository companyRepository;;
	@Spy
	ContractCompany contractCompany;
	@Spy
	ContractCompanyRequest request;
	@Spy
	ContractCompanyResponse response;
	@Spy
	ErrorResponse errorResponse;

	@Before
	public void init() throws Exception {
		impl = new ContractCompanyFacadeImpl();
		companyRepository = mock(ContractCompanyRepository.class);
		setCollaborator(impl, "contractCompanyRepository", companyRepository);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ContractCompany> getCompanyList() {
		List<ContractCompany> list = new ArrayList<ContractCompany>();
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setId(1);
		ContractCompany.setCompanyName("UK");
		list.add(ContractCompany);
		return list;
	}

	@Test
	public void setTransaction() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setTransactionType("save");
		request.setCompanyList(getCompanyList());
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setId(2);
		when(companyRepository.save(request.getCompanyList().get(0))).thenReturn(ContractCompany);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setTransactionNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setTransactionType("save");
		request.setCompanyList(getCompanyList());
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setId(null);
		when(companyRepository.save(request.getCompanyList().get(0))).thenReturn(ContractCompany);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateNotNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setTransactionType("update");
		request.setCompanyList(getCompanyList());
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setId(3);
		when(companyRepository.getOne(1)).thenReturn(ContractCompany);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setTransactionType("update");
		request.setCompanyList(getCompanyList());
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setId(null);
		when(companyRepository.getOne(1)).thenReturn(ContractCompany);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setTransactionType("delete");
		request.setCompanyList(getCompanyList());
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setStatus(false);
		when(companyRepository.getOne(1)).thenReturn(ContractCompany);
		when(companyRepository.save(ContractCompany)).thenReturn(request.getCompanyList().get(0));
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setDeleteNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setTransactionType("delete");
		request.setCompanyList(getCompanyList());
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setStatus(getCompanyList().isEmpty());
		when(companyRepository.getOne(1)).thenReturn(ContractCompany);
		// when(ContractCompanyRepository.save(ContractCompany)).thenReturn(request.getCompanyList().get(0));
		when(companyRepository.save(ContractCompany)).thenReturn(ContractCompany);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotAcceptable() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setTransactionType("");
		request.setCompanyList(getCompanyList());
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setStatus(getCompanyList().isEmpty());
		when(companyRepository.getOne(1)).thenReturn(ContractCompany);
		when(companyRepository.save(ContractCompany)).thenReturn(ContractCompany);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus status = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, status);
	}

	@Test
	public void setGetAllNotNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setCompanyList(getCompanyList());
		request.setTransactionType("getall");
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setStatus(false);
		when(companyRepository.findAll()).thenReturn(request.getCompanyList());
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetAllNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setCompanyList(getCompanyList());
		request.setTransactionType("getall");
		request.getCompanyList().get(0).setId(1);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdNotNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setCompanyList(getCompanyList());
		request.setTransactionType("getbyid");
		ContractCompany ContractCompany = new ContractCompany();
		ContractCompany.setId(1);
		when(companyRepository.getOne(1)).thenReturn(ContractCompany);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetByIdNull() throws DuplicateKeyException, Exception {
		ContractCompany con=new ContractCompany();
		con.setId(null);
		ArrayList<ContractCompany> clist=new ArrayList<>();
		clist.add(con);
		request = new ContractCompanyRequest();
		request.setCompanyList(clist);
		request.setTransactionType("getbyid");
		when(companyRepository.getOne(null)).thenReturn(con);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdMethodNull() throws DuplicateKeyException, Exception {
		request = new ContractCompanyRequest();
		request.setCompanyList(getCompanyList());
		request.setTransactionType("");
		ContractCompany ContractCompany = new ContractCompany();
		when(companyRepository.getOne(1)).thenReturn(ContractCompany);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, status);
	}
}