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

import com.ojas.obs.facade.SezlocationFacade;
import com.ojas.obs.facadeimpl.SezlocationFacadeImpl;
import com.ojas.obs.model.SezLocation;
import com.ojas.obs.repositories.SezLocationRepository;
import com.ojas.obs.request.SezlocationRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.SezlocationResponse;

public class SezlocationFacadeImplTest
{

	@InjectMocks
	SezlocationFacadeImpl sezlocationfacadeimpl;
	
	@Mock
	SezLocationRepository sezLocationRepo;
	
	@Mock
	SezlocationFacade  cmsfacadeImpl;
	
	@Spy
	SezlocationRequest sezlocationreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	SezlocationResponse sezlocationresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(sezlocationresponse, HttpStatus.OK);
	
	@Spy
	SezLocation sezlocation;
	
	@Before
	public void init() throws Exception 
	{
		sezlocationfacadeimpl=new SezlocationFacadeImpl();
		sezLocationRepo = mock(SezLocationRepository.class);
		setCollaborator(sezlocationfacadeimpl, "sezLocationRepo", sezLocationRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<SezLocation> getSezlocationList() 
	{
		List<SezLocation> sezlist = new ArrayList<SezLocation>();
		SezLocation sezdatalist = new SezLocation();
		sezdatalist.setSezlocationId(1);
		sezdatalist.setSezlocationName("clientodc");
		
		
		SezLocation sezdatalist1 = new SezLocation();
		sezdatalist1.setSezlocationId(2);
		sezdatalist1.setSezlocationName("ojasodc");
		
		sezlist.add(sezdatalist);
		sezlist.add(sezdatalist1);
		return sezlist;
	}
	
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setTransactionType("save");
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();
		
	
		List<SezLocation> list = new ArrayList<SezLocation>();
		list.add(sezlocation2);
		sezlocationreq.setSezlocationList(list);

		when(sezLocationRepo.save(sezlocation2)).thenReturn(sezlocation2);	
	
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.saveDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setTransactionType("save");
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();
		sezlocation2.setSezlocationId(1);
		sezlocation2.setSezlocationName("cat");
		
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);
		
		when(sezLocationRepo.save(sezlocation2)).thenReturn(sezlocation2);	
		
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.saveDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setTransactionType("update");
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();
		sezlocation2.setSezlocationId(1);
		sezlocation2.setSezlocationName("cat");
		
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);

		Integer id=sezlocationreq.getSezlocationList().get(0).getSezlocationId();
		
		when(sezLocationRepo.findById(id)).thenReturn(Optional.of(sezlocation2));
			
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.saveDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setTransactionType("update");
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();
		sezlocation2.setSezlocationId(null);
		sezlocation2.setSezlocationName("cat");
		
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);

		Integer id=sezlocationreq.getSezlocationList().get(0).getSezlocationId();
		
		when(sezLocationRepo.findById(id)).thenReturn(Optional.of(sezlocation2));
			
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.saveDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setTransactionType("delete");
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();	
		sezlocation2.setSezlocationId(1);
		sezlocation2.setSezlocationName("cat");
		
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);

		Integer id=sezlocationreq.getSezlocationList().get(0).getSezlocationId();
		
		when(sezLocationRepo.getOne(id)).thenReturn(sezlocation2);
					
		sezlocation2.setStatus(sezlocation2.getStatus()==null);
		
		when(sezLocationRepo.save(sezlocation2)).thenReturn(sezlocation2);
			
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.saveDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setTransactionType("delete");
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();
		sezlocation2.setSezlocationId(null);
		sezlocation2.setSezlocationName(null);
		sezlocation2.setStatus(null);
		
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);

		Integer id=sezlocationreq.getSezlocationList().get(0).getSezlocationId();
		
		when(sezLocationRepo.getOne(id)).thenReturn(sezlocation2);
					
		sezlocation2.setStatus(sezlocation2.getStatus()!=null);
		
		when(sezLocationRepo.save(sezlocation2)).thenReturn(sezlocation2);
			
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.saveDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setTransactionType("ss");
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();

		when(sezLocationRepo.save(sezlocation2)).thenReturn(sezlocation2);	
		
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.saveDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		sezlocationreq.setTransactionType("getAll");
	
		SezLocation sezlocation2 = new SezLocation();
	
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);

		when(sezLocationRepo.findAll()).thenReturn(sezlocation);	
		
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.getDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllError() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		sezlocationreq.setTransactionType("getAll");
	
		SezLocation sezlocation2 = new SezLocation();
		
	
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);

		when(sezLocationRepo.findAll()).thenReturn(sezlocation);
		
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.getDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
	
		SezLocation sezlocation2 = new SezLocation();
		sezlocation2.setSezlocationId(1);
		
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);
    	
		sezlocationreq.setTransactionType("getById");
    	Integer id=sezlocation.get(0).getSezlocationId();

		when(sezLocationRepo.findAll()).thenReturn(sezlocation);	
		
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.getDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		SezlocationRequest sezlocationreq = new SezlocationRequest();
		
		sezlocationreq.setSezlocationList(this.getSezlocationList());
		
		SezLocation sezlocation2 = new SezLocation();
		sezlocation2.setSezlocationId(1);
		
		List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
		
		sezlocation.add(sezlocation2);
		sezlocationreq.setSezlocationList(sezlocation);
    	
		sezlocationreq.setTransactionType("getById");
    	Integer id=sezlocation.get(0).getSezlocationId();
    	
		when(sezLocationRepo.findById(id)).thenReturn(Optional.of(sezlocation2));
		sezlocation.add(sezlocation2);
		
		ResponseEntity<Object> saveStatus = sezlocationfacadeimpl.getDetails(sezlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
}
