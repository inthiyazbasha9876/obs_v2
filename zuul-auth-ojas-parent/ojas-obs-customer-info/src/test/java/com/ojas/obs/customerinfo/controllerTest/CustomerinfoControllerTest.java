package com.ojas.obs.customerinfo.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.sql.SQLException;
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

import com.ojas.obs.customerinfo.controller.CustomerinfoController;
import com.ojas.obs.customerinfo.facade.CustomerinfoFacade;
import com.ojas.obs.customerinfo.facadeimpl.CustomerinfoFacadeImpl;
import com.ojas.obs.customerinfo.model.ShippingAddress;
import com.ojas.obs.customerinfo.model.ContactInfo;
import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.model.CustomerGst;
import com.ojas.obs.customerinfo.request.CustomerinfoRequest;
import com.ojas.obs.customerinfo.response.CustomerinfoResponse;
import com.ojas.obs.customerinfo.response.ErrorResponse;




public class CustomerinfoControllerTest
{
	@InjectMocks
	CustomerinfoController customerinfocontroller;
	
	@Mock
	CustomerinfoFacade customerinfoFacade;
	
	@Spy
	CustomerinfoRequest customerinforeq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	CustomerinfoResponse  customerinforesponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(customerinforesponse, HttpStatus.OK);
	
	@Spy
	Customer customer;

	@Before
	public void init() throws Exception 
	{
		customerinfocontroller=new CustomerinfoController();
		customerinfoFacade = mock(CustomerinfoFacadeImpl.class);
		setCollaborator(customerinfocontroller, "customerinfoFacade", customerinfoFacade);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<Customer> getCustomerList() {
		List<Customer> cuslist = new ArrayList<Customer>();
		
		Customer custdatalist = new Customer();
		custdatalist.setCustomerId(1);
		custdatalist.setCustomerName("clientodc");
		
		
		Customer custdatalist1 = new Customer();
		custdatalist1.setCustomerId(2);
		custdatalist1.setCustomerName("ojasodc");
		
		cuslist.add(custdatalist);
		cuslist.add(custdatalist1);
		
		return cuslist;
	}
	
	
	@Test
	public void CustomerListRequestNullTest() throws SQLException, IOException
	{
		HttpServletRequest request = null;
		
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    	
    	//customerinforequest.setCustomerList(this.getCustomerList());
    	
    	customerinforequest.setTransactionType(null);
    	
		when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(failureResponse);
		
		ResponseEntity<Object> setservice = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		
		HttpStatus unitCode = setservice.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void customerinfoRequestsaveTest1() throws SQLException, IOException
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    	
    	//customerinforequest.setCustomerList(this.getCustomerList());
    	
    	customerinforequest.setTransactionType("save");
    	
		when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(failureResponse);
		
		ResponseEntity<Object> setservice = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		
		HttpStatus status = setservice.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void customerinfoRequestsaveTest() throws SQLException, IOException
	{
		HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class); 
        
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    	
    	//customerinforequest.setCustomerList(this.getCustomerList());
    	
    	customerinforequest.setTransactionType("save");
    	
		when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(failureResponse);
		
		ResponseEntity<Object> setservice = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		
		HttpStatus status = setservice.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void customerinfoRequestupdateTest() throws SQLException, IOException
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    	
    	customerinforequest.setTransactionType("update");
    	
    	when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(failureResponse);
    	
		ResponseEntity<Object> setBus = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void customerinfoRequestdeleteTest() throws SQLException, IOException
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    	
    	customerinforequest.setTransactionType("delete");
    	
    	when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(failureResponse);
    	
		ResponseEntity<Object> setBus = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception , IOException
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    		
    	Customer cus = new Customer();
    	cus.setCustomerName("any cato");
    	cus.setCustomerstatus(true);
    	
    	List<Customer> deliverylocation  = new ArrayList<Customer>();
    	deliverylocation.add(cus);
    	
    	//customerinforequest.setCustomerList(deliverylocation);
    	customerinforequest.setTransactionType("gg");
    	
    	when(customerinfoFacade.setCustomer(customerinforequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
		
	}
	
	
	@Test
	public void setExceptionTest() throws Exception , IOException
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    		
    	Customer cus = new Customer();
    	cus.setCustomerName("any cato");
    	cus.setCustomerstatus(false);
    	
    	List<Customer> deliverylocation  = new ArrayList<Customer>();
    	deliverylocation.add(cus);
    	
    	//customerinforequest.setCustomerList(deliverylocation);
    	customerinforequest.setTransactionType("gg");
    	
    	when(customerinfoFacade.setCustomer(customerinforequest)).thenThrow(RuntimeException.class);   
		ResponseEntity<Object> setBus = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
		
		
	}
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    		
    	Customer cus = new Customer();
    	cus.setCustomerName("any cato");
    	cus.setCustomerstatus(false);
    	
    	List<Customer> deliverylocation  = new ArrayList<Customer>();
    	deliverylocation.add(cus);
    	
    	//customerinforequest.setCustomerList(deliverylocation);
    	customerinforequest.setTransactionType("gg");
    	
    	when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    		
    	Customer cus = new Customer();
    	cus.setCustomerName("any cato");
    	cus.setCustomerstatus(false);
    	
    	List<Customer> deliverylocation  = new ArrayList<Customer>();
    	deliverylocation.add(cus);
    	
    	//customerinforequest.setCustomerList(deliverylocation);
    	customerinforequest.setTransactionType("kk");
    	
    	when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    		
    	Customer cus = new Customer();
    	cus.setCustomerName("any cato");
    	cus.setCustomerstatus(false);
    	
    	List<Customer> deliverylocation  = new ArrayList<Customer>();
    	deliverylocation.add(cus);
    	
    	//customerinforequest.setCustomerList(deliverylocation);
    	customerinforequest.setTransactionType("kkj");
    	
    	when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = customerinfocontroller.setCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	//gettestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException, IOException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    	
    	//customerinforequest.setCustomerList(this.getCustomerList());
    	customerinforequest.setTransactionType(null);
    	
		when(customerinfoFacade.setCustomer(customerinforequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = customerinfocontroller.getCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void getByIdcheck() throws SQLException, IOException, URISyntaxException
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    
    	//customerinforequest.setCustomerList(this.getCustomerList());
    	customerinforequest.setTransactionType("getById");
		
    	//customerinforequest.getCustomergstList().getCustomer().getCustomerId();
		
		when(customerinfoFacade.getCustomerinfo(customerinforequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = customerinfocontroller.getCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	

	@Test
	public void getByIdsuccesscheck() throws SQLException, IOException, URISyntaxException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
    
    	//customerinforequest.setCustomerList(this.getCustomerList());
    	customerinforequest.setTransactionType("get");
		
    	customerinforequest.getCustomerList();
		
		when(customerinfoFacade.getCustomerinfo(customerinforequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = customerinfocontroller.getCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void getExceptionTest() throws Exception 
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	CustomerinfoRequest customerinforequest=new CustomerinfoRequest();
  
    	//customerinforequest.setCustomerList(this.getCustomerList());
    	customerinforequest.setTransactionType("getAll");	
    	when(customerinfoFacade.getCustomerinfo(customerinforequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = customerinfocontroller.getCustomerinfo(customerinforequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
}
