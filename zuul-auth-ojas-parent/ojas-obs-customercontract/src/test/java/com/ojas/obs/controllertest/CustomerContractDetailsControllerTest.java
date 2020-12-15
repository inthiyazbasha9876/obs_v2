
package com.ojas.obs.controllertest;

//import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.Before;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.controller.CustomerContractDetailsController;
import com.ojas.obs.facadeimpl.CustomerContractDetailsFacadeImpl;
import com.ojas.obs.model.CustomerContractDetails;
import com.ojas.obs.request.CustomerContractDetailsRequest;
import com.ojas.obs.response.CustomerContractDetailsErrorResponse;
import com.ojas.obs.response.CustomerContractDetailsResponse;

public class CustomerContractDetailsControllerTest { 

	@InjectMocks
	CustomerContractDetailsController customerContractdController;

	@Mock
	CustomerContractDetailsFacadeImpl customerContractFacadeImpl;

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

		customerContractdController = new CustomerContractDetailsController();
		customerContractFacadeImpl = mock(CustomerContractDetailsFacadeImpl.class);
		setCollaborator(customerContractdController, "customerContractDetailsFacade", customerContractFacadeImpl);

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
		ccd.setContractvalue(80000.0008563);
		ccd.setContractowner("OJAS");
		ccd.setStatus(true);
		list.add(ccd);
		return list;

	}

	// set method

	@Test
	public void setCustomerContractNullCheckReq() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		when(customerContractFacadeImpl.setCustomerContractDetails(customerContractRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setcontract = customerContractdController
				.setCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assertions.assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setTxNullCheckReq() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype(null);
		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
		when(customerContractFacadeImpl.setCustomerContractDetails(customerContractRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setcontract = customerContractdController
				.setCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setEmptyTxCheckReq() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype("");
		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
		when(customerContractFacadeImpl.setCustomerContractDetails(customerContractRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setcontract = customerContractdController
				.setCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

//	@Test
//	public void setSaveCustomerContractReq() throws Exception {
//
//		HttpServletRequest req = null;
//		HttpServletResponse res = null;
//		customerContractRequest = new CustomerContractDetailsRequest();
//		customerContractRequest.setTransactiontype("save");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		when(customerContractFacadeImpl.setCustomerContractDetails(customerContractRequest)).thenReturn(success);
//		ResponseEntity<Object> setcontract = customerContractdController
//				.setCustomerContractDetails(customerContractRequest, req, res);
//		HttpStatus statusCode = setcontract.getStatusCode();
////		assertEquals(HttpStatus.OK, statusCode);
//		Assertions.assertEquals(HttpStatus.OK, statusCode);
//
//	}

//	@Test
//	public void setDuplicateKeyExceptionTest() throws Exception {
//		HttpServletRequest req = null;
//		HttpServletResponse res = null;
//		customerContractRequest = new CustomerContractDetailsRequest();
//		CustomerContractDetails customerContractDetails2 = new CustomerContractDetails();
//		customerContractDetails2.setContractname("CTS");
//		customerContractDetails2.setStatus(true);
//		customerContractDetails2.setContractowner("OJAS");
//		List<CustomerContractDetails> arlist = new ArrayList<CustomerContractDetails>();
//		arlist.add(customerContractDetails2);
//		customerContractRequest.setTransactiontype("save");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		when(customerContractFacadeImpl.setCustomerContractDetails(customerContractRequest))
//				.thenThrow(new DuplicateKeyException(null, new Throwable()));
//		ResponseEntity<Object> setcontract = customerContractdController
//				.setCustomerContractDetails(customerContractRequest, req, res);
//		HttpStatus statusCode = setcontract.getStatusCode();
////		assertEquals(HttpStatus.CONFLICT, statusCode);
//		Assertions.assertEquals(HttpStatus.CONFLICT, statusCode);
//	}

//	@Test
//	public void setSQLExceptionTest() throws Exception {
//		HttpServletRequest req = null;
//		HttpServletResponse res = null;
//		customerContractRequest = new CustomerContractDetailsRequest();
//		CustomerContractDetails customerContractDetails2 = new CustomerContractDetails();
//		customerContractDetails2.setContractname("CTS");
//		customerContractDetails2.setStatus(true);
//		customerContractDetails2.setContractowner("OJAS");
//		List<CustomerContractDetails> arlist = new ArrayList<CustomerContractDetails>();
//		arlist.add(customerContractDetails2);
//		customerContractRequest.setTransactiontype("save");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		when(customerContractFacadeImpl.setCustomerContractDetails(customerContractRequest))
//				.thenThrow(new SQLException());
//		ResponseEntity<Object> setcontract = customerContractdController
//				.setCustomerContractDetails(customerContractRequest, req, res);
//		HttpStatus statusCode = setcontract.getStatusCode();
////		assertEquals(HttpStatus.CONFLICT, statusCode);
//		Assertions.assertEquals(HttpStatus.CONFLICT, statusCode);
//	}

//	@Test
//	public void setExceptionTest() throws Exception {
//		HttpServletRequest req = null;
//		HttpServletResponse res = null;
//		customerContractRequest = new CustomerContractDetailsRequest();
//		CustomerContractDetails customerContractDetails2 = new CustomerContractDetails();
//		customerContractDetails2.setContractname("CTS");
//		customerContractDetails2.setStatus(true);
//		customerContractDetails2.setContractowner("OJAS");
//		List<CustomerContractDetails> arlist = new ArrayList<CustomerContractDetails>();
//		arlist.add(customerContractDetails2);
//		customerContractRequest.setTransactiontype("save");
//		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
//		when(customerContractFacadeImpl.setCustomerContractDetails(customerContractRequest))
//				.thenThrow(RuntimeException.class);
//		ResponseEntity<Object> setcontract = customerContractdController
//				.setCustomerContractDetails(customerContractRequest, req, res);
//		HttpStatus statusCode = setcontract.getStatusCode();
////		assertEquals(HttpStatus.CONFLICT, statusCode);
//		Assertions.assertEquals(HttpStatus.CONFLICT, statusCode);
//	}

	// get method

	@Test
	public void getNullCheckReq() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		when(customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setcontract = customerContractdController
				.getCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getEmptyTxCheckReq() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype("");
		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
		when(customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setcontract = customerContractdController
				.getCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getNullTxCheckReq() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype(null);
		customerContractRequest.setCustomercontractdetailslist(getCustomerContractDetails());
		when(customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setcontract = customerContractdController
				.getCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getAllandGetByidCustomerContractReq() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype("getbyid");
		customerContractRequest.setTransactiontype("getall");
		customerContractRequest.setCustomercontractdetailslist(this.getCustomerContractDetails());
		when(customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest)).thenReturn(success);
		ResponseEntity<Object> setcontract = customerContractdController
				.getCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
		Assertions.assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void getSQLExceptionTest() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype("getall");
		customerContractRequest.setCustomercontractdetailslist(this.getCustomerContractDetails());
		when(customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest))
				.thenThrow(new SQLException());
		ResponseEntity<Object> setcontract = customerContractdController
				.getCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.CONFLICT, statusCode);
		Assertions.assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		customerContractRequest = new CustomerContractDetailsRequest();
		customerContractRequest.setTransactiontype("getall");
		customerContractRequest.setCustomercontractdetailslist(this.getCustomerContractDetails());
		when(customerContractFacadeImpl.getCustomerContractDetails(customerContractRequest))
				.thenThrow(RuntimeException.class);
		ResponseEntity<Object> setcontract = customerContractdController
				.getCustomerContractDetails(customerContractRequest, req, res);
		HttpStatus statusCode = setcontract.getStatusCode();
//		assertEquals(HttpStatus.CONFLICT, statusCode);
		Assertions.assertEquals(HttpStatus.CONFLICT, statusCode);
	}

}
