package com.ojas.obs.facadeTest;

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

import com.ojas.obs.facadeimpl.DocumentStageFacadeImpl;
import com.ojas.obs.model.DocumentStage;
import com.ojas.obs.repositories.DocumentStageRepository;
import com.ojas.obs.request.DocumentstageRequest;
import com.ojas.obs.response.DocumentStageResponse;
import com.ojas.obs.response.ErrorResponse;

public class DocumentStageFacadeImplTest {     
	@InjectMocks
	DocumentStageFacadeImpl docfacadeimpl;
	
	@Mock
	DocumentStageRepository documentStageRepo;
	
	@Mock
	DocumentstageRequest  docfacadeImpl;
	
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
		docfacadeimpl=new DocumentStageFacadeImpl();
		documentStageRepo = mock(DocumentStageRepository.class);
		setCollaborator(docfacadeimpl, "documentStageRepo", documentStageRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<DocumentStage> getServicetypeList() {
		List<DocumentStage> statuslist = new ArrayList<DocumentStage>();
		DocumentStage statusdatalist = new DocumentStage(); 
		statusdatalist.setDocumentstageId(1);
		statusdatalist.setDocumentstage("clientodc");
		
		
		DocumentStage statusdatalist1 = new DocumentStage();
		statusdatalist1.setDocumentstageId(2);
		statusdatalist1.setDocumentstage("ojasodc");
		
		statuslist.add(statusdatalist);
		statuslist.add(statusdatalist1);
		return statuslist;
	}
	 
	
	@Test
	public void testSaveError() throws SQLException 
	{	
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();	
		sarstatusreq.setTransactionType("save");
		
		List<DocumentStage> statuslist = new ArrayList<DocumentStage>();
		DocumentStage statusdatalist = new DocumentStage(); 
		statusdatalist.setDocumentstageId(1);
		statusdatalist.setDocumentstage("clientodc");
		statusdatalist.setStatus(true);
		statuslist.add(null);
		statuslist.isEmpty();
		sarstatusreq.setDoucumentStageList(statuslist);
		when(documentStageRepo.save(statusdatalist)).thenReturn(statusdatalist);		
		ResponseEntity<Object> saveStatus = docfacadeimpl.saveDetails(sarstatusreq);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	@Test
	public void testSave() throws SQLException 
	{	
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();	
		sarstatusreq.setTransactionType("save");
		
		List<DocumentStage> statuslist = new ArrayList<DocumentStage>();
		DocumentStage statusdatalist = new DocumentStage(); 
		statusdatalist.setDocumentstageId(1);
		statusdatalist.setDocumentstage("clientodc");
		statusdatalist.setStatus(true);
		statuslist.add(statusdatalist);
		//statuslist.isEmpty();
		sarstatusreq.setDoucumentStageList(statuslist);
		when(documentStageRepo.saveAll(statuslist)).thenReturn(statuslist);		
		ResponseEntity<Object> saveStatus = docfacadeimpl.saveDetails(sarstatusreq);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();
		
		sarstatusreq.setTransactionType("update");
		
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());
		
		DocumentStage sarstatus2 = new DocumentStage();
		sarstatus2.setDocumentstageId(1);
		sarstatus2.setDocumentstage("cat");
		
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setDoucumentStageList(sarstatus);

		Integer id=sarstatusreq.getDoucumentStageList().get(0).getDocumentstageId();
		
		when(documentStageRepo.findById(id)).thenReturn(Optional.of(sarstatus2));
			
		ResponseEntity<Object> saveStatus = docfacadeimpl.saveDetails(sarstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();
		
		sarstatusreq.setTransactionType("update");
		
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());
		
		DocumentStage sarstatus2 = new DocumentStage();
		sarstatus2.setDocumentstageId(null);
		sarstatus2.setDocumentstage("cat");
		
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setDoucumentStageList(sarstatus);

		Integer id=sarstatusreq.getDoucumentStageList().get(0).getDocumentstageId();
		
		when(documentStageRepo.findById(id)).thenReturn(Optional.of(sarstatus2));
			
		ResponseEntity<Object> saveStatus = docfacadeimpl.saveDetails(sarstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	@Test
	public void testdeleteSucess() throws SQLException {
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();	
		sarstatusreq.setTransactionType("delete");	
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());	
		DocumentStage sarstatus2 = new DocumentStage();
		sarstatus2.setDocumentstageId(5);
		sarstatus2.setDocumentstage(null);
		sarstatus2.setStatus(false);	
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setDoucumentStageList(sarstatus);
		Integer id=sarstatusreq.getDoucumentStageList().get(0).getDocumentstageId();	
		
		when(documentStageRepo.getOne(id)).thenReturn(sarstatus2);
		when(documentStageRepo.save(sarstatus2)).thenReturn(sarstatus2);			
		ResponseEntity<Object> saveStatus = docfacadeimpl.saveDetails(sarstatusreq);		
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();	
		sarstatusreq.setTransactionType("delete");	
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());	
		DocumentStage sarstatus2 = new DocumentStage();
		sarstatus2.setDocumentstageId(null);
		sarstatus2.setDocumentstage(null);
		sarstatus2.setStatus(false);	
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setDoucumentStageList(sarstatus);
		Integer id=sarstatusreq.getDoucumentStageList().get(0).getDocumentstageId();	
		
		when(documentStageRepo.getOne(id)).thenReturn(sarstatus2);
		when(documentStageRepo.save(sarstatus2)).thenReturn(sarstatus2);			
		ResponseEntity<Object> saveStatus = docfacadeimpl.saveDetails(sarstatusreq);		
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();
		
		sarstatusreq.setTransactionType("ss");
		
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());
		
		DocumentStage sarstaus2 = new DocumentStage();

		when(documentStageRepo.save(sarstaus2)).thenReturn(sarstaus2);	
		
		ResponseEntity<Object> saveStatus = docfacadeimpl.saveDetails(sarstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
	
	@Test 
	public void getAllSuccess() throws SQLException 
	{
		
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();
		
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());
		
		sarstatusreq.setTransactionType("getAll");
	
		DocumentStage servicetype2 = new DocumentStage();
	
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		sarstatus.add(servicetype2);
		sarstatusreq.setDoucumentStageList(sarstatus);

		when(documentStageRepo.findAll()).thenReturn(sarstatus);	
		
		ResponseEntity<Object> saveStatus = docfacadeimpl.getDetails(sarstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllError() throws SQLException {
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		DocumentStage doc= new DocumentStage();
		doc.setDocumentstageId(null);
		doc.setDocumentstage(null);
		doc.setStatus(false);
		sarstatus.isEmpty();
		sarstatusreq.setTransactionType("GetAll");
		sarstatusreq.setDoucumentStageList(sarstatus);
		when(documentStageRepo.findAll()).thenReturn(sarstatus);
		ResponseEntity<Object> saveStatus = docfacadeimpl.getDetails(sarstatusreq);		
		HttpStatus statusCode = saveStatus.getStatusCode();		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();
		
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());
	
		DocumentStage servicetype2 = new DocumentStage();
		servicetype2.setDocumentstageId(1);
		
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		sarstatus.add(servicetype2);
		sarstatusreq.setDoucumentStageList(sarstatus);
    	
		sarstatusreq.setTransactionType("getById");
    	Integer id=sarstatus.get(0).getDocumentstageId();

		when(documentStageRepo.findAll()).thenReturn(sarstatus);	
		
		ResponseEntity<Object> saveStatus = docfacadeimpl.getDetails(sarstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		DocumentstageRequest sarstatusreq = new DocumentstageRequest();
		
		sarstatusreq.setDoucumentStageList(this.getServicetypeList());
		
		DocumentStage sarstatus2 = new DocumentStage();
		sarstatus2.setDocumentstageId(1);
		
		List<DocumentStage> sarstatus  = new ArrayList<DocumentStage>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setDoucumentStageList(sarstatus);
    	
		sarstatusreq.setTransactionType("getById");
    	Integer id=sarstatus.get(0).getDocumentstageId();
    	
		when(documentStageRepo.findById(id)).thenReturn(Optional.of(sarstatus2));
		sarstatus.add(sarstatus2);
		
		ResponseEntity<Object> saveStatus = docfacadeimpl.getDetails(sarstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	
	
	
	
	}
}

