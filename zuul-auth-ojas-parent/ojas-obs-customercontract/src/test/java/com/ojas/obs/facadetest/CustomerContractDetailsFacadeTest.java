package com.ojas.obs.facadetest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.Before;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.facadeimpl.CustomerContractDetailsFacadeImpl;
import com.ojas.obs.model.CustomerContractDetails;
import com.ojas.obs.repository.CustomerContractDetailsRepository;
import com.ojas.obs.request.CustomerContractDetailsRequest;
import com.ojas.obs.response.CustomerContractDetailsErrorResponse;
import com.ojas.obs.response.CustomerContractDetailsResponse;

public class CustomerContractDetailsFacadeTest {

	@InjectMocks
	CustomerContractDetailsFacadeImpl customerContractFacadeImpl;

	@Mock
	CustomerContractDetailsRepository CustomerContractRepo;
 
	@Spy
	CustomerContractDetailsRequest customerContractRequest;
 
	@Spy
	CustomerContractDetailsResponse customerContractResponse;

	@Spy
	CustomerContractDetailsErrorResponse customerContractErrorResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(customerContractErrorResponse,
			HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(customerContractErrorResponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> success = new ResponseEntity<>(customerContractResponse, HttpStatus.OK);

	@Spy
	CustomerContractDetails customerContractDetails;

//	@Before
	@BeforeEach
	public void init() throws Exception {

		customerContractFacadeImpl = new CustomerContractDetailsFacadeImpl();
		CustomerContractRepo = mock(CustomerContractDetailsRepository.class);
		setCollaborator(customerContractFacadeImpl, "customerContractDetailsRepo", CustomerContractRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<CustomerContractDetails> getCustomerContractDetails() {
		ArrayList<CustomerContractDetails> list = new ArrayList<>();
		CustomerContractDetails ccd = new CustomerContractDetails();
		Date date = new Date();
		ccd.setContractid(101);
		ccd.setContractname("TCS");
		ccd.setDescription("Hello hi..");
		ccd.setStartdate(date);
		ccd.setEnddate(date);
		ccd.setServicetype("perm");
		ccd.setDeliverylocation("1");
		ccd.setCreatedby("saritha");
		ccd.setContractvalue(80000.83);
		ccd.setContractowner("OJAS");
		ccd.setStatus(true);
		list.add(ccd);
		return list;
	}

//	@Test
//	public void testSaveCheckError() throws Exception {
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setTransactiontype("save");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		CustomerContractDetails contractDetails = new CustomerContractDetails();
//		when(CustomerContractRepo.save(contractDetails)).thenReturn(contractDetails);
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
//				.setCustomerContractDetails(customerContractRequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
//		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
// 	} 

//	@Test
//	public void testSaveSuccess() throws Exception {
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setTransactiontype("save");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		CustomerContractDetails ccd = new CustomerContractDetails();
//		ccd.setContractname("TCS");
//		ccd.setDescription("Hello hi..");
//		ArrayList<CustomerContractDetails> list = new ArrayList<>();
//		list.add(ccd);
//		customerContractRequest.setCustomercontractdetailslist(list);
//		when(CustomerContractRepo.save(ccd)).thenReturn(ccd);
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
//				.setCustomerContractDetails(customerContractRequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.OK, statusCode);
//		Assertions.assertEquals(HttpStatus.OK, statusCode);
//	}

//	@Test
//	public void testupdatesuccesscheck() throws SQLException, Exception {
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setTransactiontype("update");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		CustomerContractDetails ccd = new CustomerContractDetails();
//		ccd.setContractid(101);
//		ccd.setContractname("TCS");
//		ArrayList<CustomerContractDetails> list = new ArrayList<>();
//		list.add(ccd);
//		customerContractRequest.setCustomercontractdetailslist(list);
//		Integer id = customerContractRequest.getCustomercontractdetailslist().get(0).getContractid();
//		when(CustomerContractRepo.findById(id)).thenReturn(Optional.of(ccd));
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
//				.setCustomerContractDetails(customerContractRequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.OK, statusCode);
//		Assertions.assertEquals(HttpStatus.OK, statusCode);
//	}

//	@Test
//	public void testupdateErrorcheck() throws SQLException, Exception {
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setTransactiontype("update");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		CustomerContractDetails ccd = new CustomerContractDetails();
//		ccd.setContractid(null);
//		ccd.setContractname("TCS");
//		ArrayList<CustomerContractDetails> list = new ArrayList<>();
//		list.add(ccd);
//		customerContractRequest.setCustomercontractdetailslist(list);
//		Integer id = customerContractRequest.getCustomercontractdetailslist().get(0).getContractid();
//		when(CustomerContractRepo.findById(id)).thenReturn(Optional.of(ccd));
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
//				.setCustomerContractDetails(customerContractRequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
//		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
//	}

//	@Test
//	public void testdeletesuccesscheck() throws Exception {
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setTransactiontype("delete");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		CustomerContractDetails ccd = new CustomerContractDetails();
//		ccd.setContractid(101);
//		ccd.setContractname("TCS");
//		ArrayList<CustomerContractDetails> list = new ArrayList<>();
//		list.add(ccd);
//		customerContractRequest.setCustomercontractdetailslist(list);
//		Integer id = customerContractRequest.getCustomercontractdetailslist().get(0).getContractid();
//		when(CustomerContractRepo.getOne(id)).thenReturn(ccd);
//		ccd.setStatus(ccd.getStatus() == null);
//		when(CustomerContractRepo.save(ccd)).thenReturn(ccd);
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
//				.setCustomerContractDetails(customerContractRequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.OK, statusCode);
//		Assertions.assertEquals(HttpStatus.OK, statusCode);
//	}

	@Test
	public void testdeleteErrorcheck() throws Exception {
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype("delete");
		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
		CustomerContractDetails ccd = new CustomerContractDetails();
		ccd.setContractid(null);
		ccd.setContractname(null);
		ccd.setStatus(null);
		ArrayList<CustomerContractDetails> list = new ArrayList<>();
		list.add(ccd);
		customerContractRequest.setCustomercontractdetailslist(list);
		Integer id = customerContractRequest.getCustomercontractdetailslist().get(0).getContractid();
		when(CustomerContractRepo.getOne(id)).thenReturn(ccd);
		ccd.setStatus(ccd.getStatus() != null);
		when(CustomerContractRepo.save(ccd)).thenReturn(null);
		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
				.setCustomerContractDetails(customerContractRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	// get test cases
	
//	@Test
//	public void getAllSuccess() throws SQLException, Exception {
//
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		customerContractRequest.setTransactiontype("getAll");
//		CustomerContractDetails details = new CustomerContractDetails();
//		List<CustomerContractDetails> list = new ArrayList<>();
//		list.add(details);
//		customerContractRequest.setCustomercontractdetailslist(list);
//		when(CustomerContractRepo.findAll()).thenReturn(list);
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
//				.getCustomerContractDetails(customerContractRequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.OK, statusCode);
//		Assertions.assertEquals(HttpStatus.OK, statusCode);
//	}

//	@Test
//	public void getAllSuccessError() throws SQLException, Exception {
//
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		customerContractRequest.setTransactiontype("getAll");
//		CustomerContractDetails details = new CustomerContractDetails();
//		List<CustomerContractDetails> list = new ArrayList<>();
//		list.add(details);
//		customerContractRequest.setCustomercontractdetailslist(list);
//		when(CustomerContractRepo.findAll()).thenReturn(null);
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl
//				.getCustomerContractDetails(customerContractRequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.CONFLICT, statusCode);
//		Assertions.assertEquals(HttpStatus.CONFLICT, statusCode);
//	}	
	
	
//	@Test
//	public void getByIdError() throws SQLException, Exception 
//	{
//		
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		CustomerContractDetails details = new CustomerContractDetails();
//		details.setContractid(101);		
//		List<CustomerContractDetails> list  = new ArrayList<>();
//		list.add(details);
//		customerContractRequest.setCustomercontractdetailslist(list);
//		customerContractRequest.setTransactiontype("getById");
//    	Integer id=customerContractRequest.getCustomercontractdetailslist().get(0).getContractid();
//		when(CustomerContractRepo.findAll()).thenReturn(list);	
//		ResponseEntity<Object> saveStatus = customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest);		
//		HttpStatus statusCode = saveStatus.getStatusCode();
////		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
//		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
//	}
	
	@Test
	public void getByIdSuccess() throws SQLException, Exception 
	{
		
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
		CustomerContractDetails details = new CustomerContractDetails();
		details.setContractid(101);		
		List<CustomerContractDetails> list  = new ArrayList<>();
		list.add(details);
		customerContractRequest.setCustomercontractdetailslist(list);
		customerContractRequest.setTransactiontype("getById");
    	Integer id=customerContractRequest.getCustomercontractdetailslist().get(0).getContractid();
		when(CustomerContractRepo.findById(id)).thenReturn(Optional.of(details));	
		ResponseEntity<Object> saveStatus = customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest);		
		HttpStatus statusCode = saveStatus.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
		Assertions.assertEquals(HttpStatus.OK, statusCode);
	}
}
