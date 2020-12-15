package com.ojas.obs.c2hstatus.facadetest;

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

import com.ojas.obs.c2hstatus.facade.C2HStatusFacade;
import com.ojas.obs.c2hstatus.facadeimpl.C2HStatusFacadeImpl;
import com.ojas.obs.c2hstatus.model.C2HStatus;
import com.ojas.obs.c2hstatus.repository.C2HStatusRepository;
import com.ojas.obs.c2hstatus.request.C2HStatusRequest;
import com.ojas.obs.c2hstatus.response.C2HStatusResponse;
import com.ojas.obs.c2hstatus.response.ErrorResponse;

public class C2HStatusFacadeImplTest {
	@InjectMocks
	C2HStatusFacadeImpl c2hstatusfacadeimpl;
	
	@Mock
	C2HStatusRepository c2hStatusRepo;
	
	@Mock
	C2HStatusFacade  c2hstatusfacadeImpl;
	
	@Spy
	C2HStatusRequest c2hstatusreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	C2HStatusResponse c2hstatusresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(c2hstatusresponse, HttpStatus.OK);
	
	@Spy
	C2HStatus c2hstatus;
	
	@Before
	public void init() throws Exception 
	{
		c2hstatusfacadeimpl=new C2HStatusFacadeImpl();
		c2hStatusRepo = mock(C2HStatusRepository.class);
		setCollaborator(c2hstatusfacadeimpl, "c2hStatusRepo", c2hStatusRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<C2HStatus> getC2HStatusList() {
		List<C2HStatus> c2hlist = new ArrayList<C2HStatus>();
		C2HStatus c2hstatus1 = new C2HStatus();
		c2hstatus1.setC2hstatusId(1);
		c2hstatus1.setC2hstatus("clientodc");
		
		
		C2HStatus c2hstatus2= new C2HStatus();
		c2hstatus2.setC2hstatusId(2);
		c2hstatus2.setC2hstatus("ojasodc");
		
		c2hlist.add(c2hstatus1);
		c2hlist.add(c2hstatus2);
		return c2hlist;
	}
	
	@Test
	public void testSaveError() throws SQLException 
	
	{
		C2HStatusRequest c2hstatusreq = new C2HStatusRequest();

		c2hstatusreq.setTransactionType("save");

		c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());

		C2HStatus c2hstatus2 = new C2HStatus();
		List<C2HStatus> statusList = new ArrayList<C2HStatus>();
		statusList.add(c2hstatus2);
		c2hstatusreq.setC2hstatuslist(statusList);

		when(c2hStatusRepo.save(c2hstatus2)).thenReturn(c2hstatus2);	
		
		ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.saveC2HStatus(c2hstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
		
		c2hstatusreq.setTransactionType("save");
		
		c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
		
		C2HStatus c2hstatus2 = new C2HStatus();
		c2hstatus2.setC2hstatusId(1);
		c2hstatus2.setC2hstatus("cat");
		
		List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
		c2hlist.add(c2hstatus2);
		c2hstatusreq.setC2hstatuslist(c2hlist);
		
		when(c2hStatusRepo.save(c2hstatus2)).thenReturn(c2hstatus2);	
		
		ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.saveC2HStatus(c2hstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
		
		c2hstatusreq.setTransactionType("update");
		
		c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
		
		C2HStatus c2hstatus2 = new C2HStatus();
		c2hstatus2.setC2hstatusId(1);
		c2hstatus2.setC2hstatus("cat");
		
		List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
		c2hlist.add(c2hstatus2);
		c2hstatusreq.setC2hstatuslist(c2hlist);

		Integer id=c2hstatusreq.getC2hstatuslist().get(0).getC2hstatusId();
		when(c2hStatusRepo.getOne(id)).thenReturn(c2hstatus2);
		
		ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.saveC2HStatus(c2hstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
		
		c2hstatusreq.setTransactionType("update");
		
		c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
		
		C2HStatus c2hstatus2 = new C2HStatus();
		c2hstatus2.setC2hstatusId(null);
		c2hstatus2.setC2hstatus("cat");
		
		List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
		c2hlist.add(c2hstatus2);
		c2hstatusreq.setC2hstatuslist(c2hlist);

		Integer id=c2hstatusreq.getC2hstatuslist().get(0).getC2hstatusId();
		when(c2hStatusRepo.getOne(id)).thenReturn(c2hstatus2);
		ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.saveC2HStatus(c2hstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
		
		c2hstatusreq.setTransactionType("delete");
		
		c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
		
		C2HStatus c2hstatus2 = new C2HStatus();	
		c2hstatus2.setC2hstatusId(1);
		c2hstatus2.setC2hstatus("cat");
		
		List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
		c2hlist.add(c2hstatus2);
		c2hstatusreq.setC2hstatuslist(c2hlist);

		Integer id=c2hstatusreq.getC2hstatuslist().get(0).getC2hstatusId();
		
		when(c2hStatusRepo.getOne(id)).thenReturn(c2hstatus2);
					
		c2hstatus2.setStatus(c2hstatus2.getStatus()==null);
		
		when(c2hStatusRepo.save(c2hstatus2)).thenReturn(c2hstatus2);
			
		ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.saveC2HStatus(c2hstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
		
		c2hstatusreq.setTransactionType("delete");
		
		c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
		
		C2HStatus c2hstatus2 = new C2HStatus();
		c2hstatus2.setC2hstatusId(null);
		c2hstatus2.setC2hstatus(null);
		c2hstatus2.setStatus(null);
		
		List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
		c2hlist.add(c2hstatus2);
		c2hstatusreq.setC2hstatuslist(c2hlist);

		Integer id=c2hstatusreq.getC2hstatuslist().get(0).getC2hstatusId();
		
		when(c2hStatusRepo.getOne(id)).thenReturn(c2hstatus2);
					
		c2hstatus2.setStatus(c2hstatus2.getStatus()!=null);
		
		when(c2hStatusRepo.save(c2hstatus2)).thenReturn(c2hstatus2);
			
		ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.saveC2HStatus(c2hstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
		
		c2hstatusreq.setTransactionType("ss");
		
		c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
		
		C2HStatus c2hstatus2 = new C2HStatus();

		when(c2hStatusRepo.save(c2hstatus2)).thenReturn(c2hstatus2);	
		
		ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.saveC2HStatus(c2hstatusreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	//getTestcases
	
		@Test
		public void getAllSuccess() throws SQLException 
		{
			
			C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
			
			c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
			
			c2hstatusreq.setTransactionType("getAll");
		
			C2HStatus c2hstatus2 = new C2HStatus();
		
			List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
			c2hlist.add(c2hstatus2);
			c2hstatusreq.setC2hstatuslist(c2hlist);

			when(c2hStatusRepo.findAll()).thenReturn(c2hlist);	
			
			ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.getC2HStatus(c2hstatusreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
		
		@Test
		public void getAllError() throws SQLException 
		{
			
			C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
			
			c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
			
			c2hstatusreq.setTransactionType("getAll");
		
			C2HStatus c2hstatus2 = new C2HStatus();
			
		
			List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
			c2hlist.add(c2hstatus2);
			c2hstatusreq.setC2hstatuslist(c2hlist);

			when(c2hStatusRepo.findAll()).thenReturn(c2hlist);
			
			ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.getC2HStatus(c2hstatusreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
		
		@Test
		public void getByIdError() throws SQLException 
		{
			
			C2HStatusRequest c2hstatusreq = new C2HStatusRequest();
			
			c2hstatusreq.setC2hstatuslist(this.getC2HStatusList());
		
			C2HStatus c2hstatus2 = new C2HStatus();
			c2hstatus2.setC2hstatusId(1);
			
			List<C2HStatus> c2hlist = new ArrayList<C2HStatus>();
			c2hlist.add(c2hstatus2);
			c2hstatusreq.setC2hstatuslist(c2hlist);
	    	
			c2hstatusreq.setTransactionType("getById");
	    	Integer id=c2hlist.get(0).getC2hstatusId();

			when(c2hStatusRepo.findAll()).thenReturn(c2hlist);	
			
			ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.getC2HStatus(c2hstatusreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.CONFLICT, statusCode);
		}
	
		@Test
		public void getByIdSuccess() throws SQLException 
		{
		
			C2HStatusRequest deliverylocationreq = new C2HStatusRequest();

			deliverylocationreq.setC2hstatuslist(this.getC2HStatusList());
			
			C2HStatus deliverylocation2 = new C2HStatus();
			deliverylocation2.setC2hstatusId(1);
			
			List<C2HStatus> deliverylocation  = new ArrayList<C2HStatus>();
			deliverylocation.add(deliverylocation2);
			deliverylocationreq.setC2hstatuslist(deliverylocation);
	    	
			deliverylocationreq.setTransactionType("getById");
	    	Integer id=deliverylocation.get(0).getC2hstatusId();
	    	
			when(c2hStatusRepo.findById(id)).thenReturn(Optional.of(deliverylocation2));
			deliverylocation.add(deliverylocation2);
			
			ResponseEntity<Object> saveStatus = c2hstatusfacadeimpl.getC2HStatus(deliverylocationreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
	}

	
