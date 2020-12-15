package com.ojas.obs.documenttype.facadeimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.documenttype.facade.DocumentTypeFacade;
import com.ojas.obs.documenttype.facadeimpl.DocumentTypeFacadeImpl;
import com.ojas.obs.documenttype.model.DocumentType;
import com.ojas.obs.documenttype.repository.DocumentTypeRepository;
import com.ojas.obs.documenttype.request.DocumentTypeRequest;
import com.ojas.obs.documenttype.response.DocumentTypeResponse;
import com.ojas.obs.documenttype.response.ErrorResponse;

public class DocumentTypeFacadeImplTest {
	@InjectMocks
	DocumentTypeFacadeImpl documenttypeFacadeImpl;

	@Mock
	DocumentTypeRepository documenttypeRepo;

	@Mock
	DocumentTypeFacade documenttypeFacade;

	@Spy
	DocumentTypeRequest documenttypeReq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	DocumentTypeResponse documenttypeResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(documenttypeResponse, HttpStatus.OK);

	@Spy
	DocumentType documenttype;

	@Before
	public void init() throws Exception {
		documenttypeFacadeImpl = new DocumentTypeFacadeImpl();
		documenttypeRepo = mock(DocumentTypeRepository.class);
		setCollaborator(documenttypeFacadeImpl, "documenttypeRepo", documenttypeRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<DocumentType> getCocumenttypeList() {

		List<DocumentType> ownerList = new ArrayList<DocumentType>();
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(1);
		document.setDocumenttype("clientodc");
		DocumentType document1 = new DocumentType();
		document1.setDocumenttypeId(2);
		document1.setDocumenttype("ojasodc");
		ownerList.add(document);
		ownerList.add(document1);
		return ownerList;
	}

	@Test
	public void testSaveError() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setTransactionType("save");
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		List<DocumentType>doclist=new ArrayList<>();
		doclist.add(document);
		documenttypeReq.setDocumenttypelist(doclist);
		when(documenttypeRepo.save(document)).thenReturn(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.saveDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setTransactionType("save");
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(1);
		document.setDocumenttype("cat");
		List<DocumentType> documentlist = new ArrayList<DocumentType>();
		documentlist.add(document);
		documenttypeReq.setDocumenttypelist(documentlist);
		when(documenttypeRepo.save(document)).thenReturn(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.saveDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdatesuccesscheck() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setTransactionType("update");
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(1);
		document.setDocumenttype("cat");
		List<DocumentType> documentlist = new ArrayList<DocumentType>();
		documentlist.add(document);
		documenttypeReq.setDocumenttypelist(documentlist);
		Integer id = documenttypeReq.getDocumenttypelist().get(0).getDocumenttypeId();
		when(documenttypeRepo.getOne(id)).thenReturn(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.saveDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setTransactionType("update");
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(null);
		document.setDocumenttype("cat");
		List<DocumentType> documentList = new ArrayList<DocumentType>();
		documentList.add(document);
		documenttypeReq.setDocumenttypelist(documentList);
		Integer id = documenttypeReq.getDocumenttypelist().get(0).getDocumenttypeId();
		when(documenttypeRepo.getOne(id)).thenReturn(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.saveDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setTransactionType("delete");
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(1);
		document.setDocumenttype("cat");
		List<DocumentType> documentList = new ArrayList<DocumentType>();
		documentList.add(document);
		documenttypeReq.setDocumenttypelist(documentList);
		Integer id = documenttypeReq.getDocumenttypelist().get(0).getDocumenttypeId();
		when(documenttypeRepo.getOne(id)).thenReturn(document);
		document.setStatus(document.getStatus() == null);
		when(documenttypeRepo.save(document)).thenReturn(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.saveDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setTransactionType("delete");
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(null);
		document.setDocumenttype(null);
		document.setStatus(null);
		List<DocumentType> documentList = new ArrayList<DocumentType>();
		documentList.add(document);
		documenttypeReq.setDocumenttypelist(documentList);
		Integer id = documenttypeReq.getDocumenttypelist().get(0).getDocumenttypeId();
		when(documenttypeRepo.getOne(id)).thenReturn(document);
		document.setStatus(document.getStatus() != null);
		when(documenttypeRepo.save(document)).thenReturn(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.saveDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void TestElseError() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setTransactionType("ss");
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		when(documenttypeRepo.save(document)).thenReturn(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.saveDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	// getTestcases

	@Test
	public void getAllSuccess() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		documenttypeReq.setTransactionType("getAll");
		DocumentType document = new DocumentType();
		List<DocumentType> documentList = new ArrayList<DocumentType>();
		documentList.add(document);
		documenttypeReq.setDocumenttypelist(documentList);
		when(documenttypeRepo.findAll()).thenReturn(documentList);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.getDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		documenttypeReq.setTransactionType("getAll");
		DocumentType document = new DocumentType();
		List<DocumentType> documentList = new ArrayList<DocumentType>();
		documentList.add(document);
		documenttypeReq.setDocumenttypelist(documentList);
		when(documenttypeRepo.findAll()).thenReturn(documentList);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.getDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		DocumentTypeRequest documenttypeReq = new DocumentTypeRequest();
		documenttypeReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(1);
		List<DocumentType> documentList = new ArrayList<DocumentType>();
		documentList.add(document);
		documenttypeReq.setDocumenttypelist(documentList);
		documenttypeReq.setTransactionType("getById");
		Integer id = documentList.get(0).getDocumenttypeId();
		when(documenttypeRepo.findAll()).thenReturn(documentList);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.getDocumentType(documenttypeReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {
		
		DocumentTypeRequest actionOwnerReq = new DocumentTypeRequest();
		actionOwnerReq.setDocumenttypelist(this.getCocumenttypeList());
		DocumentType document = new DocumentType();
		document.setDocumenttypeId(1);
		List<DocumentType> documentList = new ArrayList<DocumentType>();
		documentList.add(document);
		actionOwnerReq.setDocumenttypelist(documentList);
		actionOwnerReq.setTransactionType("getById");
		Integer id = documentList.get(0).getDocumenttypeId();
		when(documenttypeRepo.findById(id)).thenReturn(Optional.of(document));
		documentList.add(document);
		ResponseEntity<Object> saveStatus = documenttypeFacadeImpl.getDocumentType(actionOwnerReq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

}
