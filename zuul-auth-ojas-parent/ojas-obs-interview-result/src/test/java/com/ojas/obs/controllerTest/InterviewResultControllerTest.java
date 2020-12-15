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

import com.ojas.obs.controller.InterviewResultController;
import com.ojas.obs.facadeimpl.InterviewResultFacadeImpl;
import com.ojas.obs.model.InterviewResult;
import com.ojas.obs.request.InterviewResultRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.InterviewResultResponse;



public class InterviewResultControllerTest {   

	@InjectMocks
	InterviewResultController resultcontroller;
	
	@Mock
	InterviewResultFacadeImpl interviewfacadeImpl;
	
	@Spy
	InterviewResultRequest resultreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	InterviewResultResponse resultresponse; 
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(resultresponse, HttpStatus.OK);
	
	@Spy
	InterviewResult interviewresult;
	
	@Before
	public void init() throws Exception 
	{
		resultcontroller=new InterviewResultController();
		interviewfacadeImpl = mock(InterviewResultFacadeImpl.class); 
		setCollaborator(resultcontroller, "interviewfacadeImpl", interviewfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field; 
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<InterviewResult> getInterviewResults() {
		List<InterviewResult> resultlist = new ArrayList<InterviewResult>();
		InterviewResult resultdatalist = new InterviewResult();
		resultdatalist.setInterviewresultId(1);
		resultdatalist.setInterviewResult("clientodc");
		
		
		InterviewResult resultdatalist1 = new InterviewResult();
		resultdatalist1.setInterviewresultId(2);
		resultdatalist1.setInterviewResult("ojasodc");
		
		resultlist.add(resultdatalist);
		resultlist.add(resultdatalist1);
		return resultlist;
	} 
	@Test
	public void intervieModeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    	
    	resultrequest.setInterviewresultList(this.getInterviewResults());
    	resultrequest.setTransactionType(null);
		when(interviewfacadeImpl.saveDetails(resultrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    	resultrequest.setInterviewresultList(this.getInterviewResults());
    	resultrequest.setTransactionType("update");
		when(interviewfacadeImpl.saveDetails(resultrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    	resultrequest.setInterviewresultList(this.getInterviewResults());
    	resultrequest.setTransactionType("delete");
		when(interviewfacadeImpl.saveDetails(resultrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    		
    	InterviewResult result2 = new InterviewResult();
    	result2.setInterviewResult("any cato");
    	result2.setStatus(true);
    	
    	List<InterviewResult> result  = new ArrayList<InterviewResult>();
    	result.add(result2);
    	resultrequest.setInterviewresultList(result);
    	resultrequest.setTransactionType("save");	
    	
    	when(interviewfacadeImpl.saveDetails(resultrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    		
    	InterviewResult result2 = new InterviewResult();
    	result2.setInterviewResult("any cato");
    	result2.setStatus(false);
    	
    	List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
    	interviewresult.add(result2);
    	resultrequest.setInterviewresultList(interviewresult);
    	resultrequest.setTransactionType("save");	
    	when(interviewfacadeImpl.saveDetails(resultrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    		
    	InterviewResult result2 = new InterviewResult();
    	result2.setInterviewResult("data");
    	result2.setStatus(false);
    	 
    	List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
    	interviewresult.add(result2);
    	resultrequest.setInterviewresultList(interviewresult);
    	resultrequest.setTransactionType("save");		
    	when(interviewfacadeImpl.saveDetails(resultrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
		
    	InterviewResult result2 = new InterviewResult();
    	result2.setInterviewResult("data");
    	result2.setStatus(false);
    	
    	List<InterviewResult> resultstatus  = new ArrayList<InterviewResult>();
    	resultstatus.add(result2);
    	resultrequest.setInterviewresultList(resultstatus);
    	resultrequest.setTransactionType("update");	
    	when(interviewfacadeImpl.saveDetails(resultrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    		
    	InterviewResult result2 = new InterviewResult();
    	result2.setInterviewResult("ss");
    	result2.setStatus(false);
    	
    	List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
    	interviewresult.add(result2);
    	resultrequest.setInterviewresultList(interviewresult);
    	resultrequest.setTransactionType("delete");	
    	when(interviewfacadeImpl.saveDetails(resultrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = resultcontroller.saveDetails(resultrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    	
    	resultrequest.setInterviewresultList(this.getInterviewResults());
    	resultrequest.setTransactionType(null);
		when(interviewfacadeImpl.saveDetails(resultrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = resultcontroller.getDetails(resultrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    
    	resultrequest.setInterviewresultList(this.getInterviewResults());
    	resultrequest.setTransactionType("getById");
		
    	resultrequest.getInterviewresultList().get(0).getInterviewresultId();
		
		when(interviewfacadeImpl.getDetails(resultrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = resultcontroller.getDetails(resultrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
    
    	resultrequest.setInterviewresultList(this.getInterviewResults());
    	resultrequest.setTransactionType("getById");
		
    	resultrequest.getInterviewresultList().get(0).setInterviewresultId(null);
		
		when(interviewfacadeImpl.getDetails(resultrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = resultcontroller.getDetails(resultrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	 
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	InterviewResultRequest resultrequest=new InterviewResultRequest();
  
    	resultrequest.setInterviewresultList(this.getInterviewResults());
    	resultrequest.setTransactionType("getAll");	
    	when(interviewfacadeImpl.getDetails(resultrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = resultcontroller.getDetails(resultrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
}


