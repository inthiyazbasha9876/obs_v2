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

import com.ojas.obs.contractcompany.controller.ContractCompanyController;
import com.ojas.obs.contractcompany.facadeimpl.ContractCompanyFacadeImpl;
import com.ojas.obs.contractcompany.model.ContractCompany;
import com.ojas.obs.contractcompany.request.ContractCompanyRequest;
import com.ojas.obs.contractcompany.response.ContractCompanyResponse;
import com.ojas.obs.contractcompany.response.ErrorResponse;

public class ContractCompanyControllerTest {

	@InjectMocks
	ContractCompanyController contractCompanyController;
	@Mock
	ContractCompanyFacadeImpl impl;
	@Spy
	ContractCompanyRequest request;
	@Spy
	ContractCompanyResponse response = new ContractCompanyResponse();
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<Object>(response, HttpStatus.OK);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ContractCompany company;

	@Before
	public void init() throws Exception {
		contractCompanyController = new ContractCompanyController();
		impl = mock(ContractCompanyFacadeImpl.class);
		setCollaborator(contractCompanyController, "companyFacadeImpl", impl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ContractCompany> getContractCompanyList() {
		List<ContractCompany> list = new ArrayList<ContractCompany>();
		ContractCompany company = new ContractCompany();
		company.setId(1);
		company.setCompanyName("UK");
		list.add(company);
		return list;
	}

	@Test
	public void setContractCompanyNullList() throws DuplicateKeyException {
		request = new ContractCompanyRequest();
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setContractCompanyTypeNullType() throws NullPointerException {
		request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		// request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTransactionEmpty() {
		request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

//	@Test
//	public void ContractCompanyStatusNull() {
//		request = new ContractCompanyRequest();
//		request.setCompanyList(getContractCompanyList());
//		request.getCompanyList().get(0).setStatus(null);
//		request.setTransactionType("save");
//		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
//		HttpStatus status = responseEntity.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
//	}

	@Test
	public void ContractCompanyNameEmpty() {
		
		List<ContractCompany> list = new ArrayList<ContractCompany>();
		ContractCompany company = new ContractCompany();
		company.setId(1);
		company.setCompanyName("");
		list.add(company);
		
		request = new ContractCompanyRequest();
		request.setCompanyList(list);
		//request.getCompanyList().get(0).setCompanyName("UK");
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNull() {
		request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.getCompanyList().get(0).setId(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNulll() {
		
		List<ContractCompany> list = new ArrayList<ContractCompany>();
		ContractCompany company = new ContractCompany();
		company.setId(null);
		list.add(company);
		request = new ContractCompanyRequest();
		request.setCompanyList(list);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteIdNull() {
		request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.getCompanyList().get(0).setId(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteNameNull() {
		
		List<ContractCompany> list = new ArrayList<ContractCompany>();
		ContractCompany company = new ContractCompany();
		company.setId(null);
		company.setCompanyName("UK");
		list.add(company);
		request = new ContractCompanyRequest();
		request.setCompanyList(list);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

//	@Test
//	public void setContractCompanySuccessList() throws Exception {
//		request = new ContractCompanyRequest();
//		request.setCompanyList(getContractCompanyList());
//		request.setTransactionType("save");
//		request.getCompanyList().get(0).setStatus(true);
//		request.getCompanyList().get(0).setCompanyName("UK");
//		when(impl.saveDetails(request)).thenReturn(successResponse);
//		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
//		HttpStatus status = responseEntity.getStatusCode();
//		assertEquals(HttpStatus.OK, status);
//	}

	@Test
	public void setContractCompany_DuplicateKeyException() throws Exception {
		ContractCompany con=new ContractCompany();
		con.setCompanyName("UK");
		con.setCompanyName("UK");
		ArrayList<ContractCompany> clist=new ArrayList<>();
		clist.add(con);
		request = new ContractCompanyRequest();
		request.setCompanyList(clist);
		DuplicateKeyException duplicateKeyException= new DuplicateKeyException(null);
		when(impl.saveDetails(request)).thenThrow(duplicateKeyException);
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setContractCompany_Exception() throws Exception {
		request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType("save");
		request.getCompanyList().get(0).setStatus(true);
		request.getCompanyList().get(0).setCompanyName("UK");
		when(impl.saveDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = contractCompanyController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void getExceptionNull() throws Exception {
		ContractCompanyRequest request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = contractCompanyController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getByIdEmpty() throws Exception {
		ContractCompanyRequest request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = contractCompanyController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdNull() {
		ContractCompanyRequest request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType("getbyid");
		request.getCompanyList().get(0).setId(null);
		ResponseEntity<Object> responseEntity = contractCompanyController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getAllDetails() throws DuplicateKeyException, Exception {
		ContractCompanyRequest request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType("getbyid");
		request.getCompanyList().get(0).setId(1);
		when(impl.getAllDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = contractCompanyController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void set_DuplicateKeyException() throws Exception {
		request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType("getbyid");
		request.getCompanyList().get(0).setStatus(true);
		request.getCompanyList().get(0).setCompanyName("UK");
		when(impl.getAllDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = contractCompanyController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void set_Exception() throws Exception {
		request = new ContractCompanyRequest();
		request.setCompanyList(getContractCompanyList());
		request.setTransactionType("getall");
		request.getCompanyList().get(0).setStatus(true);
		request.getCompanyList().get(0).setCompanyName("UK");
		when(impl.getAllDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = contractCompanyController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

}
