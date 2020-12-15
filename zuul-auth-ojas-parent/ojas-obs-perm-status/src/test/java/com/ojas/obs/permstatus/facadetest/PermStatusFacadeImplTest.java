package com.ojas.obs.permstatus.facadetest;

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

import com.ojas.obs.permstatus.facade.PermStatusFacade;
import com.ojas.obs.permstatus.facadeimpl.PermStatusFacadeImpl;
import com.ojas.obs.permstatus.model.PermStatus;
import com.ojas.obs.permstatus.repository.PermStatusRepository;
import com.ojas.obs.permstatus.request.PermStatusRequest;
import com.ojas.obs.permstatus.response.ErrorResponse;
import com.ojas.obs.permstatus.response.PermStatusResponse;

public class PermStatusFacadeImplTest {
	@InjectMocks
	PermStatusFacadeImpl permStatusFacadeImpl;
	
	@Mock
	PermStatusRepository permStatusRepo;
	
	@Mock
	PermStatusFacade permStatusFacade;
	
	@Spy
	PermStatusRequest PermStatusReq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	PermStatusResponse permStatusResponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(permStatusResponse, HttpStatus.OK);
	
	@Spy
	PermStatus permStatus;
	
	@Before
	public void init() throws Exception 
	{
		permStatusFacadeImpl=new PermStatusFacadeImpl();
		permStatusRepo = mock(PermStatusRepository.class);
		setCollaborator(permStatusFacadeImpl, "permStatusRepo", permStatusRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<PermStatus> getPermStatusList() {
		List<PermStatus> permStatusnList = new ArrayList<PermStatus>();
		
		PermStatus permStatus = new PermStatus();
		permStatus.setPermstatusId(1);
		permStatus.setPermstatus("clientodc");
		
		
		PermStatus permStatusnList1 = new PermStatus();
		permStatusnList1.setPermstatusId(2);
		permStatusnList1.setPermstatus("ojasodc");
		
		permStatusnList.add(permStatus);
		permStatusnList.add(permStatusnList1);
		return permStatusnList;
	}
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		PermStatusRequest permStatusRequest = new PermStatusRequest();
		
		permStatusRequest.setTransactionType("save");
		
		permStatusRequest.setPermStatusList(this.getPermStatusList());
		
		PermStatus permStatus = new PermStatus();
		List<PermStatus> permList = new ArrayList<PermStatus>();
		permList.add(permStatus);
		permStatusRequest.setPermStatusList(permList);


		when(permStatusRepo.save(permStatus)).thenReturn(permStatus);	
		
		ResponseEntity<Object> saveStatus = permStatusFacadeImpl.savePermStatus(permStatusRequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		PermStatusRequest deliverylocationreq = new PermStatusRequest();
		
		deliverylocationreq.setTransactionType("save");
		
		deliverylocationreq.setPermStatusList(this.getPermStatusList());
		
		PermStatus permStatus = new PermStatus();
		permStatus.setPermstatusId(1);
		permStatus.setPermstatus("cat");
		
		List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
		permStatusList.add(permStatus);
		deliverylocationreq.setPermStatusList(permStatusList);
		
		when(permStatusRepo.save(permStatus)).thenReturn(permStatus);	
		
		ResponseEntity<Object> saveStatus = permStatusFacadeImpl.savePermStatus(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
	
		PermStatusRequest permStatusRequest= new PermStatusRequest();
		
		permStatusRequest.setTransactionType("update");
		
		permStatusRequest.setPermStatusList(this.getPermStatusList());
		
		PermStatus permStatus= new PermStatus();
		permStatus.setPermstatusId(1);
		permStatus.setPermstatus("cat");
		
		List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
		permStatusList.add(permStatus);
		permStatusRequest.setPermStatusList(permStatusList);

		Integer id=permStatusRequest.getPermStatusList().get(0).getPermstatusId();
		
		when(permStatusRepo.findById(id)).thenReturn(Optional.of(permStatus));
			
		ResponseEntity<Object> saveStatus = permStatusFacadeImpl.savePermStatus(permStatusRequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		PermStatusRequest permStatusRequest = new PermStatusRequest();
		
		permStatusRequest.setTransactionType("update");
		
		permStatusRequest.setPermStatusList(this.getPermStatusList());
		
		PermStatus permStatus2 = new PermStatus();
		permStatus2.setPermstatusId(null);
		permStatus2.setPermstatus("cat");
		
		List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
		permStatusList.add(permStatus2);
		permStatusRequest.setPermStatusList(permStatusList);

		Integer id=permStatusRequest.getPermStatusList().get(0).getPermstatusId();
		
		when(permStatusRepo.findById(id)).thenReturn(Optional.of(permStatus2));
			
		ResponseEntity<Object> saveStatus = permStatusFacadeImpl.savePermStatus(permStatusRequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		PermStatusRequest permStatusRequest = new PermStatusRequest();
		
		permStatusRequest.setTransactionType("delete");
		
		permStatusRequest.setPermStatusList(this.getPermStatusList());
		
		PermStatus permStatus2 = new PermStatus();	
		permStatus2.setPermstatusId(1);
		permStatus2.setPermstatus("cat");
		
		List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
		permStatusList.add(permStatus2);
		permStatusRequest.setPermStatusList(permStatusList);

		Integer id=permStatusRequest.getPermStatusList().get(0).getPermstatusId();
		
		when(permStatusRepo.getOne(id)).thenReturn(permStatus2);
					
		permStatus2.setStatus(permStatus2.getStatus()==null);
		
		when(permStatusRepo.save(permStatus2)).thenReturn(permStatus2);
			
		ResponseEntity<Object> saveStatus = permStatusFacadeImpl.savePermStatus(permStatusRequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		PermStatusRequest permStatusRequest = new PermStatusRequest();
		
		permStatusRequest.setTransactionType("delete");
		
		permStatusRequest.setPermStatusList(this.getPermStatusList());
		
		PermStatus permStatus2 = new PermStatus();
		permStatus2.setPermstatusId(null);
		permStatus2.setPermstatus(null);
		permStatus2.setStatus(null);
		
		List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
		permStatusList.add(permStatus2);
		permStatusRequest.setPermStatusList(permStatusList);

		Integer id=permStatusRequest.getPermStatusList().get(0).getPermstatusId();
		
		when(permStatusRepo.getOne(id)).thenReturn(permStatus2);
					
		permStatus2.setStatus(permStatus2.getStatus()!=null);
		
		when(permStatusRepo.save(permStatus2)).thenReturn(permStatus2);
			
		ResponseEntity<Object> saveStatus = permStatusFacadeImpl.savePermStatus(permStatusRequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		PermStatusRequest permStatusReq = new PermStatusRequest();
		
		permStatusReq.setTransactionType("ss");
		
		permStatusReq.setPermStatusList(this.getPermStatusList());
		
		PermStatus permStatus2 = new PermStatus();

		when(permStatusRepo.save(permStatus2)).thenReturn(permStatus2);	
		
		ResponseEntity<Object> saveStatus = permStatusFacadeImpl.savePermStatus(permStatusReq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	//getTestcases
	
		@Test
		public void getAllSuccess() throws SQLException 
		{
			
			PermStatusRequest permStatusRequest = new PermStatusRequest();
			
			permStatusRequest.setPermStatusList(this.getPermStatusList());
			
			permStatusRequest.setTransactionType("getAll");
		
			PermStatus permStatus2 = new PermStatus();
		
			List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
			permStatusList.add(permStatus2);
			permStatusRequest.setPermStatusList(permStatusList);

			when(permStatusRepo.findAll()).thenReturn(permStatusList);	
			
			ResponseEntity<Object> saveStatus = permStatusFacadeImpl.getPermStatus(permStatusRequest);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
		
		@Test
		public void getAllError() throws SQLException 
		{
			
			PermStatusRequest permStutusRequest = new PermStatusRequest();
			
			permStutusRequest.setPermStatusList(this.getPermStatusList());
			
			permStutusRequest.setTransactionType("getAll");
		
			PermStatus permStatusList2 = new PermStatus();
			
		
			List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
			permStatusList.add(permStatusList2);
			permStutusRequest.setPermStatusList(permStatusList);

			when(permStatusRepo.findAll()).thenReturn(permStatusList);
			
			ResponseEntity<Object> saveStatus = permStatusFacadeImpl.getPermStatus(permStutusRequest);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
		
		@Test
     	public void getByIdError() throws SQLException 
		{
			
			PermStatusRequest permStutusRequest = new PermStatusRequest();
			
			permStutusRequest.setPermStatusList(this.getPermStatusList());
		
			PermStatus permStatus2 = new PermStatus();
			permStatus2.setPermstatusId(1);
			
			List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
			permStatusList.add(permStatus2);
			permStutusRequest.setPermStatusList(permStatusList);
	    	
			permStutusRequest.setTransactionType("getById");
	    	Integer id=permStatusList.get(0).getPermstatusId();

			when(permStatusRepo.findAll()).thenReturn(permStatusList);	
			
			ResponseEntity<Object> saveStatus = permStatusFacadeImpl.getPermStatus(permStutusRequest);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.CONFLICT, statusCode);
		}
		@Test
		public void getByIdSuccess() throws SQLException 
		{
			
			PermStatusRequest permStatusRequest = new PermStatusRequest();
			
			permStatusRequest.setPermStatusList(this.getPermStatusList());
			
			PermStatus permStatus2 = new PermStatus();
			permStatus2.setPermstatusId(1);
			
			List<PermStatus>permStatusList  = new ArrayList<PermStatus>();
			permStatusList.add(permStatus2);
			permStatusRequest.setPermStatusList(permStatusList);
	    	
			permStatusRequest.setTransactionType("getById");
	    	Integer id=permStatusList.get(0).getPermstatusId();
	    	
			when(permStatusRepo.findById(id)).thenReturn(Optional.of(permStatus2));
			permStatusList.add(permStatus2);
			
			ResponseEntity<Object> saveStatus = permStatusFacadeImpl.getPermStatus(permStatusRequest);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
		
		
	
	}
