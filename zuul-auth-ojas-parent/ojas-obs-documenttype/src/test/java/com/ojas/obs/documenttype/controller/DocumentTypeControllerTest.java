package com.ojas.obs.documenttype.controller;

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

import com.ojas.obs.documenttype.controller.DocumentTypeController;
import com.ojas.obs.documenttype.facadeimpl.DocumentTypeFacadeImpl;
import com.ojas.obs.documenttype.model.DocumentType;
import com.ojas.obs.documenttype.request.DocumentTypeRequest;
import com.ojas.obs.documenttype.response.DocumentTypeResponse;
import com.ojas.obs.documenttype.response.ErrorResponse;

public class DocumentTypeControllerTest {

	@InjectMocks
	DocumentTypeController documenttypecontroller;

	@Mock
	DocumentTypeFacadeImpl documenttypeFacadeImpl;

	@Spy
	DocumentTypeRequest documenttyperreq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	DocumentTypeResponse actionOwnerResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(actionOwnerResponse, HttpStatus.OK);

	@Spy
	DocumentType documenttype;

	@Before
	public void init() throws Exception {
		documenttypecontroller = new DocumentTypeController();
		documenttypeFacadeImpl = mock(DocumentTypeFacadeImpl.class);
		setCollaborator(documenttypecontroller, "documenttypeFacadeImpl", documenttypeFacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<DocumentType> getDocumenttypeList() {
		List<DocumentType> documenttypeList = new ArrayList<DocumentType>();
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(1);
		document.setDocumenttype("clientodc");

        DocumentType document1 = new DocumentType();
		document1.setDocumenttypeId(2);
		document1.setDocumenttype("codc");

		documenttypeList.add(document);
		documenttypeList.add(document1);
		return documenttypeList;
	}

	@Test
	public void documentTypeRequestNullTest() throws SQLException 
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType(null);
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = documenttypecontroller.saveDocumentType(documenttyperreq, request,response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException 
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
		documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType("save");
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = documenttypecontroller.saveDocumentType(documenttyperreq, request,response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException 
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType("update");
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = documenttypecontroller.saveDocumentType(documenttyperreq, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType("delete");
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = documenttypecontroller.saveDocumentType(documenttyperreq, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        DocumentType type = new DocumentType();
		type.setDocumenttype("any cato");
		type.setStatus(true);
        List<DocumentType> list = new ArrayList<DocumentType>();
		list.add(type);
		documenttyperreq.setDocumenttypelist(list);
		documenttyperreq.setTransactionType("save");
        when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = documenttypecontroller.saveDocumentType(documenttyperreq, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception 
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        DocumentType type = new DocumentType();
		type.setDocumenttype("any cato");
		type.setStatus(false);
        List<DocumentType> list = new ArrayList<DocumentType>();
		list.add(type);
		documenttyperreq.setDocumenttypelist(list);
		documenttyperreq.setTransactionType("save");
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenThrow(RuntimeException.class);
		ResponseEntity<Object> setBus = documenttypecontroller.saveDocumentType(documenttyperreq, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setsavesucces() throws Exception
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        DocumentType actionOwner = new DocumentType();
		actionOwner.setDocumenttype("data");
		actionOwner.setStatus(false);
      	List<DocumentType> list = new ArrayList<DocumentType>();
		list.add(actionOwner);
		documenttyperreq.setDocumenttypelist(list);
		documenttyperreq.setTransactionType("save");
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(successResponse);
        ResponseEntity<Object> setBus = documenttypecontroller.saveDocumentType(documenttyperreq, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void setupdateFail() throws Exception
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        DocumentType actionOwner = new DocumentType();
		actionOwner.setDocumenttype("data");
		actionOwner.setStatus(false);
        List<DocumentType> list = new ArrayList<DocumentType>();
		list.add(actionOwner);
		documenttyperreq.setDocumenttypelist(list);
		documenttyperreq.setTransactionType("s");
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(successResponse);
        ResponseEntity<Object> setBus = documenttypecontroller.saveDocumentType(documenttyperreq, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
    	DocumentType actionOwner = new DocumentType();
		actionOwner.setDocumenttype("ss");
		actionOwner.setStatus(false);
      	List<DocumentType> list = new ArrayList<DocumentType>();
		list.add(actionOwner);
		documenttyperreq.setDocumenttypelist(list);
		documenttyperreq.setTransactionType("delete");
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(successResponse);
    	ResponseEntity<Object> setBus = documenttypecontroller.saveDocumentType(documenttyperreq, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
    	documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType(null);
		when(documenttypeFacadeImpl.saveDocumentType(documenttyperreq)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = documenttypecontroller.getDocumenttype(documenttyperreq, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
        documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType("getById");
        documenttyperreq.getDocumenttypelist().get(0).getDocumenttypeId();
    	when(documenttypeFacadeImpl.getDocumentType(documenttyperreq)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = documenttypecontroller.getDocumenttype(documenttyperreq, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
    	documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType("getById");
    	documenttyperreq.getDocumenttypelist().get(0).setDocumenttypeId(null);
        when(documenttypeFacadeImpl.getDocumentType(documenttyperreq)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = documenttypecontroller.getDocumenttype(documenttyperreq, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception
	{
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		DocumentTypeRequest documenttyperreq = new DocumentTypeRequest();
    	documenttyperreq.setDocumenttypelist(this.getDocumenttypeList());
		documenttyperreq.setTransactionType("getAll");
		when(documenttypeFacadeImpl.getDocumentType(documenttyperreq)).thenThrow(RuntimeException.class);
    	ResponseEntity<Object> setBus = documenttypecontroller.getDocumenttype(documenttyperreq, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}
