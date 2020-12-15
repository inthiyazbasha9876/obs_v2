package com.ojas.obs.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.controller.ServicecategoryController;
import com.ojas.obs.facade.ServicecategoryFacade;
import com.ojas.obs.facadeimpl.ServicecategoryFacadeImpl;
import com.ojas.obs.model.ServiceCategory;
import com.ojas.obs.request.ServicecategoryRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.ServicecategoryResponse;

public class ServicecategoryControllerTest 
{

	@InjectMocks
	ServicecategoryController servicecategorycontroller;
	
	@Mock
	ServicecategoryFacade cmsfacadeImpl;
	
	@Spy
	ServicecategoryRequest servicecategortrequest;
	
	@Spy
	ErrorResponse errorresponse=new ErrorResponse();
	
	@Spy
	ServicecategoryResponse servicecategoryresponse = new ServicecategoryResponse();
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(servicecategoryresponse, HttpStatus.OK);
	
	@Spy
	ServiceCategory servicecategory;
	
	@Before
	public void init() throws Exception 
	{
		servicecategorycontroller=new ServicecategoryController();
		cmsfacadeImpl = mock(ServicecategoryFacadeImpl.class);
		setCollaborator(servicecategorycontroller, "cmsfacadeImpl", cmsfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<ServiceCategory> getservicecategoryList() {
		List<ServiceCategory> servicelist = new ArrayList<ServiceCategory>();
		ServiceCategory servicedatalist = new ServiceCategory();
		servicedatalist.setServiceategoryId(1);
		servicedatalist.setServiceategoryName("Dev");
		
		
		ServiceCategory servicedatalist1 = new ServiceCategory();
		servicedatalist1.setServiceategoryId(2);
		servicedatalist1.setServiceategoryName("Testing");
		
		servicelist.add(servicedatalist);
		servicelist.add(servicedatalist1);
		return servicelist;
	}

	@Test
	public void servicecategoryRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
		ServicecategoryRequest servicecategortrequest1 = null;
    	servicecategortrequest.setServicecategoryList(this.getservicecategoryList());
		servicecategortrequest.setTransactionType(null);
		when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    	servicecategortrequest.setServicecategoryList(this.getservicecategoryList());
		servicecategortrequest.setTransactionType("save");
		when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    	servicecategortrequest.setServicecategoryList(getservicecategoryList());
    	servicecategortrequest.setTransactionType("update");
		when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    	servicecategortrequest.setServicecategoryList(getservicecategoryList());
    	servicecategortrequest.setTransactionType("delete");
		when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    		
    	ServiceCategory serviceCategory2 = new ServiceCategory();
    	serviceCategory2.setServiceategoryName("any cato");
    	serviceCategory2.setServiceStatus(true);
    	List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategortrequest.setServicecategoryList(serviceCategory);
    	servicecategortrequest.setTransactionType("save");	
    	
    	when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    		
    	ServiceCategory serviceCategory2 = new ServiceCategory();
    	serviceCategory2.setServiceategoryName("data");
    	serviceCategory2.setServiceStatus(false);
    	List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategortrequest.setServicecategoryList(serviceCategory);
    	servicecategortrequest.setTransactionType("save");	
    	when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    		
    	ServiceCategory serviceCategory2 = new ServiceCategory();
    	serviceCategory2.setServiceategoryName("data");
    	serviceCategory2.setServiceStatus(false);
    	List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategortrequest.setServicecategoryList(serviceCategory);
    	servicecategortrequest.setTransactionType("save");	
    	when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    		
    	ServiceCategory serviceCategory2 = new ServiceCategory();
    	serviceCategory2.setServiceategoryName("data");
    	serviceCategory2.setServiceStatus(false);
    	List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategortrequest.setServicecategoryList(serviceCategory);
    	servicecategortrequest.setTransactionType("update");	
    	when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    		
    	ServiceCategory serviceCategory2 = new ServiceCategory();
    	serviceCategory2.setServiceategoryId(1);
    	serviceCategory2.setServiceStatus(false);
    	List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategortrequest.setServicecategoryList(serviceCategory);
    	servicecategortrequest.setTransactionType("delete");	
    	when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = servicecategorycontroller.saveDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	//get testcases
	
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
		ServicecategoryRequest servicecategortrequest1 = null;
    	servicecategortrequest.setServicecategoryList(this.getservicecategoryList());
		servicecategortrequest.setTransactionType(null);
		when(cmsfacadeImpl.saveDetails(servicecategortrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicecategorycontroller.getDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    	
		//ServicecategoryRequest servicecategortrequest1 = null;
    	
    	servicecategortrequest.setServicecategoryList(this.getservicecategoryList());
		servicecategortrequest.setTransactionType("getById");
		
		servicecategortrequest.getServicecategoryList().get(0).getServiceategoryId();
		
		when(cmsfacadeImpl.getDetails(servicecategortrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = servicecategorycontroller.getDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
    	
		//ServicecategoryRequest servicecategortrequest1 = null;
    	
    	servicecategortrequest.setServicecategoryList(this.getservicecategoryList());
		servicecategortrequest.setTransactionType("getById");
		
		servicecategortrequest.getServicecategoryList().get(0).setServiceategoryId(null);
		
		when(cmsfacadeImpl.getDetails(servicecategortrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicecategorycontroller.getDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServicecategoryRequest servicecategortrequest=new ServicecategoryRequest();
  
    	servicecategortrequest.setServicecategoryList(this.getservicecategoryList());
    	servicecategortrequest.setTransactionType("getAll");	
    	when(cmsfacadeImpl.getDetails(servicecategortrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = servicecategorycontroller.getDetails(servicecategortrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
}
