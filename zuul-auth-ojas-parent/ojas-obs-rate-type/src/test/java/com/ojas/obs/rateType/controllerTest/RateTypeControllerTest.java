package com.ojas.obs.rateType.controllerTest;

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

import com.ojas.obs.rateType.controller.RateTypeController;
import com.ojas.obs.rateType.facade.RateTypeFacade;
import com.ojas.obs.rateType.facadeImpl.RateTypeFacadeImpl;
import com.ojas.obs.rateType.model.RateType;
import com.ojas.obs.rateType.request.RateTypeRequest;
import com.ojas.obs.rateType.response.ErrorResponse;
import com.ojas.obs.rateType.response.RateTypeResponse;




public class RateTypeControllerTest 
{
	@InjectMocks
	RateTypeController ratetypecontroller;
	
	@Mock
	RateTypeFacade rateTypeFacade;
	
	@Spy
	RateTypeRequest reatetypereq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	RateTypeResponse ratetyperesponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(ratetyperesponse, HttpStatus.OK);
	
	@Spy
	RateType  ratetype;
	
	@Before
	public void init() throws Exception 
	{
		ratetypecontroller=new 	RateTypeController();
		rateTypeFacade = mock(RateTypeFacadeImpl.class);
		setCollaborator(ratetypecontroller, "rateTypeFacade", rateTypeFacade);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<RateType> getRateType() {
		List<RateType> ratetypelist = new ArrayList<RateType>();
		RateType ratelist = new RateType();
		ratelist.setId(1);
		ratelist.setRateType("cTA");
		
		
		RateType ratelist1 = new RateType();
		ratelist1.setId(1);
		ratelist1.setRateType("KK");
		
		ratetypelist.add(ratelist);
		ratetypelist.add(ratelist1);
		return ratetypelist;
	}
	
	@Test
	public void RateTypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;	
    	RateTypeRequest reatetypereq=new RateTypeRequest();
    	List<RateType> rateType =new ArrayList<RateType>();
    	RateType rt=new RateType();
    	rt.setId(null);
    	rt.setRateType("good");
    	rt.setStatus(null);
    	rateType.isEmpty();
    	reatetypereq.setTransactionType(null);
    	reatetypereq.setRateType(rateType);
		when(rateTypeFacade.saveRateType(reatetypereq)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = ratetypecontroller.saveRateType(reatetypereq, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void RateTypeRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
         RateTypeRequest reatetypereq=new RateTypeRequest();
    	
    	reatetypereq.setRateType(this.getRateType());
    	reatetypereq.setTransactionType("save");
    	
		when(rateTypeFacade.saveRateType(reatetypereq)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = ratetypecontroller.saveRateType(reatetypereq, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void RateTypeRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	 RateTypeRequest reatetypereq=new RateTypeRequest();
    	 reatetypereq.setRateType(this.getRateType());
    	 reatetypereq.setTransactionType("update");
    	 
		when(rateTypeFacade.saveRateType(reatetypereq)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = ratetypecontroller.saveRateType(reatetypereq, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void RateTypeRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest deliveryrequest=new RateTypeRequest();
    	deliveryrequest.setRateType(this.getRateType());
    	deliveryrequest.setTransactionType("delete");
		when(rateTypeFacade.saveRateType(deliveryrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = ratetypecontroller.saveRateType(deliveryrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
    		
    	RateType Ratetype2 = new RateType();
    	Ratetype2.setRateType("any cato");
    	Ratetype2.setStatus(true);
    	
    	List<RateType> ratetype  = new ArrayList<RateType>();
    	ratetype.add(Ratetype2);
    	ratetyperequest.setRateType(ratetype);
    	ratetyperequest.setTransactionType("save");	
    	
    	when(rateTypeFacade.saveRateType(ratetyperequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = ratetypecontroller.saveRateType(ratetyperequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
		
    	RateType Ratetype2 = new RateType();
    	Ratetype2.setRateType("any cato");
    	Ratetype2.setStatus(false);
    	
    	List<RateType> ratetype  = new ArrayList<RateType>();
    	ratetype.add(Ratetype2);
    	ratetyperequest.setRateType(ratetype);
    	ratetyperequest.setTransactionType("save");	
    	when(rateTypeFacade.saveRateType(ratetyperequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = ratetypecontroller.saveRateType(ratetyperequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
    		
    	RateType Ratetype2 = new RateType();
    	Ratetype2.setRateType("any cato");
    	Ratetype2.setStatus(false);
    	
    	List<RateType> ratetype  = new ArrayList<RateType>();
    	ratetype.add(Ratetype2);
    	ratetyperequest.setRateType(ratetype);
    	ratetyperequest.setTransactionType("save");		
    	when(rateTypeFacade.saveRateType(ratetyperequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = ratetypecontroller.saveRateType(ratetyperequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void setupdateFail() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
		
    	RateType Ratetype2 = new RateType();
    	Ratetype2.setRateType("any cato");
    	Ratetype2.setStatus(false);
    	
    	List<RateType> ratetype  = new ArrayList<RateType>();
    	ratetype.add(Ratetype2);
    	ratetyperequest.setRateType(ratetype);
    	ratetyperequest.setTransactionType("update");	
    	when(rateTypeFacade.saveRateType(ratetyperequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = ratetypecontroller.saveRateType(ratetyperequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
      RateTypeRequest ratetyperequest=new RateTypeRequest();
		
    	RateType Ratetype2 = new RateType();
    	Ratetype2.setRateType("any cato");
    	Ratetype2.setStatus(false);
    	
    	List<RateType> ratetype  = new ArrayList<RateType>();
    	ratetype.add(Ratetype2);
    	ratetyperequest.setRateType(ratetype);
    	ratetyperequest.setTransactionType("delete");	
    	when(rateTypeFacade.saveRateType(ratetyperequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = ratetypecontroller.saveRateType(ratetyperequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	//get testcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
    	
    	ratetyperequest.setRateType(this.getRateType());
    	ratetyperequest.setTransactionType(null);
		when(rateTypeFacade.getCustomerDetails(ratetyperequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = ratetypecontroller.getCustomerDetails(ratetyperequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
    
    	ratetyperequest.setRateType(this.getRateType());
    	ratetyperequest.setTransactionType("getbyid");
		
    	ratetyperequest.getRateType().get(0).getId();
		
		when(rateTypeFacade.getCustomerDetails(ratetyperequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = ratetypecontroller.getCustomerDetails(ratetyperequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
    
    	ratetyperequest.setRateType(this.getRateType());
    	ratetyperequest.setTransactionType("getbyid");
		
    	ratetyperequest.getRateType().get(0).setId(null);
		
		when(rateTypeFacade.getCustomerDetails(ratetyperequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = ratetypecontroller.getCustomerDetails(ratetyperequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	RateTypeRequest ratetyperequest=new RateTypeRequest();
  
    	ratetyperequest.setRateType(this.getRateType());
    	ratetyperequest.setTransactionType("getAll");	
    	when(rateTypeFacade.getCustomerDetails(ratetyperequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = ratetypecontroller.getCustomerDetails(ratetyperequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
}
