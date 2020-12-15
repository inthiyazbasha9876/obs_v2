package com.obs.psa.customercontractinfocontrollertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.obs.psa.customercontactinfo.constants.ErrorResponse;
import com.obs.psa.customercontactinfo.controller.CustomerContactInfoController;
import com.obs.psa.customercontactinfo.facade.CustomerContactInfoFacade;
import com.obs.psa.customercontactinfo.facadeimpl.CustomerContactInfoFacadeImpl;
import com.obs.psa.customercontactinfo.model.CustomerContactInfo;
import com.obs.psa.customercontactinfo.request.CustomerContactInfoRequest;
import com.obs.psa.customercontactinfo.response.CustomerContactInfoResponse;

public class CustomerContactInfoControllerTest {

	@InjectMocks
	CustomerContactInfoController customerContactInfoController;

	@Mock
	CustomerContactInfoFacade customerContactInfoFacadeImpl;

	@Spy
	CustomerContactInfoRequest customerContactInfoRequest;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	CustomerContactInfoResponse customerContactInfoResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(customerContactInfoResponse, HttpStatus.OK);

	@Spy
	CustomerContactInfo customerContactInfo;

	@Before
	public void init() throws Exception {
		customerContactInfoController = new CustomerContactInfoController();
		customerContactInfoFacadeImpl = mock(CustomerContactInfoFacadeImpl.class);
		setCollaborator(customerContactInfoController, "customerContactInfoFacadeImpl", customerContactInfoFacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<CustomerContactInfo> getCustomerContactInfoList() {
		
		List<CustomerContactInfo> customerContactInfo = new ArrayList<CustomerContactInfo>();
		CustomerContactInfo customerContactInfolist = new CustomerContactInfo();
		customerContactInfolist.setContactId(1);
		customerContactInfolist.setContactName("TCS");
		customerContactInfolist.setCustomerId(1001);
		customerContactInfolist.setDepartment("Java");
		customerContactInfolist.setDesignation("Java");
		customerContactInfolist.setDoa("2008-01-11");
		customerContactInfolist.setDob("1993-01-11");
		customerContactInfolist.setPersonalEmail("tcs@gmail.com");
		customerContactInfolist.setOfficialEmail("tataconsultancyservice@gmail.com");
		customerContactInfolist.setPermanentMobileNumber((long) 987654546);
		customerContactInfolist.setAlternateMobileNumber((long) 927253555);
		customerContactInfolist.setAddress1("hyd");
		customerContactInfolist.setAddress2("hyd");
		customerContactInfolist.setAddress3("hyd");
		customerContactInfolist.setBdm("kishore");
		customerContactInfolist.setPincode(515201);
		customerContactInfolist.setStatus(true);

		CustomerContactInfo customerContactInfo1 = new CustomerContactInfo();
		customerContactInfo1.setContactId(2);
		customerContactInfo1.setContactName("CTS");
		customerContactInfo1.setCustomerId(1002);
		customerContactInfo1.setDepartment("Java");
		customerContactInfo1.setDesignation("Java");
		customerContactInfo1.setDoa("2008-01-11");
		customerContactInfo1.setDob("1993-01-11");
		customerContactInfo1.setPersonalEmail("cts@gmail.com");
		customerContactInfo1.setOfficialEmail("cognizant@gmail.com");
		customerContactInfo1.setPermanentMobileNumber((long) 980654546);
		customerContactInfo1.setAlternateMobileNumber((long) 927250555);
		customerContactInfo1.setAddress1("hyd");
		customerContactInfo1.setAddress2("hyd");
		customerContactInfo1.setAddress3("hyd");
		customerContactInfo1.setBdm("kishore");
		customerContactInfo1.setPincode(515201);
		customerContactInfo1.setStatus(true);

		customerContactInfo.add(customerContactInfolist);
		customerContactInfo.add(customerContactInfo1);
		return customerContactInfo;
	}

	@Test
	public void deliverylocationRequestNullTest() throws SQLException, IOException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType(null);
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setservice = customerContactInfoController
				.setCustomerContactInfo(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException, IOException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();
		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType("save");
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setservice = customerContactInfoController
				.setCustomerContactInfo(customerContactInfoRequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException, IOException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();
		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType("update");
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setBus = customerContactInfoController.setCustomerContactInfo(customerContactInfoRequest,
				request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException, IOException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();
		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType("delete");
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setBus = customerContactInfoController.setCustomerContactInfo(customerContactInfoRequest,
				request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		
		customerContactInfo.setContactName("TCS");
		customerContactInfo.setCustomerId(1001);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("tcs@gmail.com");
		customerContactInfo.setOfficialEmail("tataconsultancyservice@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 987654546);
		customerContactInfo.setAlternateMobileNumber((long) 927253555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(true);

		List<CustomerContactInfo> cusContactInfos = new ArrayList<CustomerContactInfo>();
		cusContactInfos.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(cusContactInfos);
		customerContactInfoRequest.setTransactionType("save");

		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setCustomerContactInfo = customerContactInfoController
				.setCustomerContactInfo(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setCustomerContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		
		customerContactInfo.setContactName("TCS");
		customerContactInfo.setCustomerId(1001);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("tcs@gmail.com");
		customerContactInfo.setOfficialEmail("tataconsultancyservice@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 987654546);
		customerContactInfo.setAlternateMobileNumber((long) 927253555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(false);

		List<CustomerContactInfo> customerContactInfos = new ArrayList<CustomerContactInfo>();
		customerContactInfos.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(customerContactInfos);
		customerContactInfoRequest.setTransactionType("save");
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenThrow(RuntimeException.class);

		ResponseEntity<Object> setContactInfo = customerContactInfoController
				.setCustomerContactInfo(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		customerContactInfo.setContactName("TCS");
		customerContactInfo.setCustomerId(1001);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("tcs@gmail.com");
		customerContactInfo.setOfficialEmail("tataconsultancyservice@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 987654546);
		customerContactInfo.setAlternateMobileNumber((long) 927253555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(false);

		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);
		customerContactInfoRequest.setTransactionType("save");
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(successResponse);

		ResponseEntity<Object> setContactInfo = customerContactInfoController
				.setCustomerContactInfo(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setContactInfo.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		
		customerContactInfo.setContactName("sss");
		customerContactInfo.setCustomerId(1001);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("tcs@gmail.com");
		customerContactInfo.setOfficialEmail("tataconsultancyservice@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 987654546);
		customerContactInfo.setAlternateMobileNumber((long) 927253555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(false);

		List<CustomerContactInfo> contactInfos = new ArrayList<CustomerContactInfo>();
		contactInfos.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfos);
		customerContactInfoRequest.setTransactionType("update");
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(successResponse);

		ResponseEntity<Object> setContactInfo = customerContactInfoController
				.setCustomerContactInfo(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setContactInfo.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();
		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		customerContactInfo.setContactName("sss");
		customerContactInfo.setCustomerId(1001);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("tcs@gmail.com");
		customerContactInfo.setOfficialEmail("tataconsultancyservice@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 987654546);
		customerContactInfo.setAlternateMobileNumber((long) 927253555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(false);

		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);
		customerContactInfoRequest.setTransactionType("delete");
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
		.thenReturn(successResponse);

		ResponseEntity<Object> setContactInfo = customerContactInfoController
				.setCustomerContactInfo(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType(null);
		when(customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setservice = customerContactInfoController
				.getCustomerContactInfoDetails(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType("getById");

		customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId();

		when(customerContactInfoFacadeImpl.getCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(successResponse);
		ResponseEntity<Object> setservice = customerContactInfoController
				.getCustomerContactInfoDetails(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType("getById");

		customerContactInfoRequest.getCustomerContactInfo().get(0).setContactId(null);

		when(customerContactInfoFacadeImpl.getCustomerContactInfo(customerContactInfoRequest))
				.thenReturn(failureResponse);
		ResponseEntity<Object> setservice = customerContactInfoController
				.getCustomerContactInfoDetails(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
		customerContactInfoRequest.setTransactionType("getAll");
		when(customerContactInfoFacadeImpl.getCustomerContactInfo(customerContactInfoRequest))
				.thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = customerContactInfoController
				.getCustomerContactInfoDetails(customerContactInfoRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}
