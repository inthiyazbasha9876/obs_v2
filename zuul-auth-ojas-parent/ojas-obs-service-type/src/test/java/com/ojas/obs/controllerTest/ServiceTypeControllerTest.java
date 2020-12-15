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
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.controller.ServiceTypeController;
import com.ojas.obs.facade.ServiceTypeFacade;
import com.ojas.obs.facadeimpl.ServiceTypeFacadeImpl;
import com.ojas.obs.model.ServiceType;
import com.ojas.obs.request.ServiceTypeRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.ServiceTypeResponse;

public class ServiceTypeControllerTest {

	@InjectMocks
	ServiceTypeController servicetypecontroller;
	
	@Mock
	ServiceTypeFacade serfacadeImpl;
	
	@Spy
	ServiceTypeRequest servicetypereq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	ServiceTypeResponse servicetyperesponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(servicetyperesponse, HttpStatus.OK);
	
	@Spy
	ServiceType servicetype;
	
	@Before
	public void init() throws Exception 
	{
		servicetypecontroller=new ServiceTypeController();
		serfacadeImpl = mock(ServiceTypeFacadeImpl.class);
		setCollaborator(servicetypecontroller, "serfacadeImpl", serfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<ServiceType> getServiceTypeList() {
		List<ServiceType> servicelist = new ArrayList<ServiceType>();
		ServiceType servicedatalist = new ServiceType();
		servicedatalist.setId(1);
		//servicedatalist.setServiceType("clientodc");
		
		
		ServiceType servicedatalist1 = new ServiceType();
		servicedatalist1.setId(2);
		//servicedatalist1.setServiceType("ojasodc");
		
		servicelist.add(servicedatalist);
		servicelist.add(servicedatalist1);
		return servicelist;
	}
	
	
	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    	List<ServiceType> servicelist = new ArrayList<ServiceType>();
    	ServiceType servicedatalist1 = new ServiceType();
    	servicedatalist1.setId(null);
    	servicedatalist1.setServiceTypeName(null);
    	servicedatalist1.setStatus(null);
    	servicelist.add(servicedatalist1);
    	servicerequest.setServicetypeList(null);
		when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    	servicerequest.setServicetypeList(this.getServiceTypeList());
    	servicerequest.setTransactionType("save");
		when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	
	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    	servicerequest.setServicetypeList(this.getServiceTypeList());
    	servicerequest.setTransactionType("update");
		when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    	servicerequest.setServicetypeList(this.getServiceTypeList());
    	servicerequest.setTransactionType("delete");
		when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    		
    	ServiceType servicetype2 = new ServiceType();
    	//servicetype2.setServiceType("any cato");
    	servicetype2.setStatus(true);
    	
    	List<ServiceType> servicetype  = new ArrayList<ServiceType>();
    	servicetype.add(servicetype2);
    	servicerequest.setServicetypeList(servicetype);
    	servicerequest.setTransactionType("save");	
    	
    	when(serfacadeImpl.saveDetails(servicerequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    		
    	ServiceType servicetype2 = new ServiceType();
    	//servicetype2.setServiceType("any cato");
    	servicetype2.setStatus(false);
    	
    	List<ServiceType> servicetype  = new ArrayList<ServiceType>();
    	servicetype.add(servicetype2);
    	servicerequest.setServicetypeList(servicetype);
    	servicerequest.setTransactionType("save");	
    	when(serfacadeImpl.saveDetails(servicerequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    		
    	ServiceType servicetype2 = new ServiceType();
    	//servicetype2.setServiceType("data");
    	servicetype2.setStatus(false);
    	
    	List<ServiceType> servicetype  = new ArrayList<ServiceType>();
    	servicetype.add(servicetype2);
    	servicerequest.setServicetypeList(servicetype);
    	servicerequest.setTransactionType("save");		
    	when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
		
    	ServiceType servicetype2 = new ServiceType();
    	//servicetype2.setServiceType("data");
    	servicetype2.setStatus(false);
    	
    	List<ServiceType> servicetype  = new ArrayList<ServiceType>();
    	servicetype.add(servicetype2);
    	servicerequest.setServicetypeList(servicetype);
    	servicerequest.setTransactionType("update");	
    	when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    		
    	ServiceType servicetype2 = new ServiceType();
    	//servicetype2.setServiceType("ss");
    	servicetype2.setStatus(false);
    	
    	List<ServiceType> servicetype  = new ArrayList<ServiceType>();
    	servicetype.add(servicetype2);
    	servicerequest.setServicetypeList(servicetype);
    	servicerequest.setTransactionType("delete");	
    	when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = servicetypecontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    	
    	servicerequest.setServicetypeList(this.getServiceTypeList());
    	servicerequest.setTransactionType(null);
		when(serfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicetypecontroller.getDetails(servicerequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    
    	servicerequest.setServicetypeList(this.getServiceTypeList());
    	servicerequest.setTransactionType("getById");
		
    	servicerequest.getServicetypeList().get(0).getId();
		
		when(serfacadeImpl.getDetails(servicerequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = servicetypecontroller.getDetails(servicerequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
    
    	servicerequest.setServicetypeList(this.getServiceTypeList());
    	servicerequest.setTransactionType("getById");
		
    	servicerequest.getServicetypeList().get(0).setId(null);
		
		when(serfacadeImpl.getDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = servicetypecontroller.getDetails(servicerequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ServiceTypeRequest servicerequest=new ServiceTypeRequest();
  
    	servicerequest.setServicetypeList(this.getServiceTypeList());
    	servicerequest.setTransactionType("getAll");	
    	when(serfacadeImpl.getDetails(servicerequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = servicetypecontroller.getDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
}

