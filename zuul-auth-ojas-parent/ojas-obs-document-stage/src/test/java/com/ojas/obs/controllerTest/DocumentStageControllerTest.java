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

import com.ojas.obs.controller.DocumentStageController;
import com.ojas.obs.facadeimpl.DocumentStageFacadeImpl;
import com.ojas.obs.model.DocumentStage;
import com.ojas.obs.request.DocumentstageRequest;
import com.ojas.obs.response.DocumentStageResponse;
import com.ojas.obs.response.ErrorResponse;

public class DocumentStageControllerTest {  
 
	@InjectMocks 
	DocumentStageController doccontroller;
	 
	@Mock
	DocumentStageFacadeImpl docfacadeImpl;
	
	@Spy
	DocumentstageRequest docStagereq; 
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	DocumentStageResponse docstageresponse; 
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(docstageresponse, HttpStatus.OK);
	
	@Spy
	DocumentStage docstage;
	
	@Before
	public void init() throws Exception 
	{
		doccontroller=new DocumentStageController(); 
		docfacadeImpl = mock(DocumentStageFacadeImpl.class); 
		setCollaborator(doccontroller, "docfacadeImpl", docfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{ 
		Field field;
		field = object.getClass().getDeclaredField(name); 
		field.setAccessible(true);
		field.set(object, service); 
	}
	
	public List<DocumentStage> getSarStatusList() {
		List<DocumentStage> servicelist = new ArrayList<DocumentStage>();
		DocumentStage servicedatalist = new DocumentStage();
		servicedatalist.setDocumentstageId(1);
		servicedatalist.setDocumentstage("clientodc");
		
		
		DocumentStage sardatalist1 = new DocumentStage();
		sardatalist1.setDocumentstageId(2);
		sardatalist1.setDocumentstage("ojasodc");
		
		servicelist.add(servicedatalist);
		servicelist.add(sardatalist1);
		return servicelist;
	}
	
	
	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest statusrequest=new DocumentstageRequest(); 
    	List<DocumentStage> servicelist = new ArrayList<DocumentStage>();
    	DocumentStage servicedatalist = new DocumentStage();
    	servicedatalist.setDocumentstage(null);
    	servicedatalist.setDocumentstageId(null);
    	servicedatalist.setStatus(null);
    	servicelist.isEmpty();
    	statusrequest.setDoucumentStageList(servicelist);
		when(docfacadeImpl.saveDetails(statusrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = doccontroller.saveDetails(statusrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest servicerequest=new DocumentstageRequest();
    	servicerequest.setDoucumentStageList(this.getSarStatusList());
    	servicerequest.setTransactionType("save");
		when(docfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = doccontroller.saveDetails(servicerequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest servicerequest=new DocumentstageRequest();
    	servicerequest.setDoucumentStageList(this.getSarStatusList());
    	servicerequest.setTransactionType("update");
		when(docfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = doccontroller.saveDetails(servicerequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest servicerequest=new DocumentstageRequest();
    	servicerequest.setDoucumentStageList(this.getSarStatusList());
    	servicerequest.setTransactionType("delete");
		when(docfacadeImpl.saveDetails(servicerequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = doccontroller.saveDetails(servicerequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest servicerequest=new DocumentstageRequest();
    		
    	DocumentStage sarstatus2 = new DocumentStage();
    	sarstatus2.setDocumentstage("any cato");
    	sarstatus2.setStatus(true);
    	
    	List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
    	sarstatus.add(sarstatus2);
    	servicerequest.setDoucumentStageList(sarstatus);
    	servicerequest.setTransactionType("save");	
    	
    	when(docfacadeImpl.saveDetails(servicerequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = doccontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest servicerequest=new DocumentstageRequest();
    		
    	DocumentStage sarstatus2 = new DocumentStage();
    	sarstatus2.setDocumentstage("any cato");
    	sarstatus2.setStatus(false);
    	
    	List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
    	sarstatus.add(sarstatus2);
    	servicerequest.setDoucumentStageList(sarstatus);
    	servicerequest.setTransactionType("save");	
    	when(docfacadeImpl.saveDetails(servicerequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = doccontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest sarstatusrequest=new DocumentstageRequest();
    		
    	DocumentStage sarstatus2 = new DocumentStage();
    	sarstatus2.setDocumentstage("data");
    	sarstatus2.setStatus(false);
    	
    	List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
    	sarstatus.add(sarstatus2);
    	sarstatusrequest.setDoucumentStageList(sarstatus);
    	sarstatusrequest.setTransactionType("save");		
    	when(docfacadeImpl.saveDetails(sarstatusrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = doccontroller.saveDetails(sarstatusrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdateFailTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest servicerequest=new DocumentstageRequest();
		
    	DocumentStage sarstatus2 = new DocumentStage();
    	sarstatus2.setDocumentstage("data");
    	sarstatus2.setStatus(false);
    	
    	List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
    	sarstatus.add(sarstatus2);
    	servicerequest.setDoucumentStageList(sarstatus);
    	servicerequest.setTransactionType("update");	
    	when(docfacadeImpl.saveDetails(servicerequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = doccontroller.saveDetails(servicerequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	
	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest sarstatusrequest=new DocumentstageRequest();
    	
    	sarstatusrequest.setDoucumentStageList(this.getSarStatusList());
    	sarstatusrequest.setTransactionType(null);
		when(docfacadeImpl.saveDetails(sarstatusrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = doccontroller.getDetails(sarstatusrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest sarstatusrequest=new DocumentstageRequest();
    
    	sarstatusrequest.setDoucumentStageList(this.getSarStatusList());
    	sarstatusrequest.setTransactionType("getById");
		
    	sarstatusrequest.getDoucumentStageList().get(0).getDocumentstageId();
		
		when(docfacadeImpl.getDetails(sarstatusrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = doccontroller.getDetails(sarstatusrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest sarstatusrequest=new DocumentstageRequest();
    
    	sarstatusrequest.setDoucumentStageList(this.getSarStatusList());
    	sarstatusrequest.setTransactionType("getById");
		
    	sarstatusrequest.getDoucumentStageList().get(0).setDocumentstageId(null);
		
		when(docfacadeImpl.getDetails(sarstatusrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = doccontroller.getDetails(sarstatusrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DocumentstageRequest sarstatusrequest=new DocumentstageRequest();
  
    	sarstatusrequest.setDoucumentStageList(this.getSarStatusList());
    	sarstatusrequest.setTransactionType("getAll");	
    	when(docfacadeImpl.getDetails(sarstatusrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = doccontroller.getDetails(sarstatusrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
}


