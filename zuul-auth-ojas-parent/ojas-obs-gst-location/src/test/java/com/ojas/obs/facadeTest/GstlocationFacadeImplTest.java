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

import com.ojas.obs.facade.GstlocationFacade;
import com.ojas.obs.facadeimpl.GstlocationFacadeImpl;
import com.ojas.obs.model.GstLocation;
import com.ojas.obs.repositories.GstLocationRepository;
import com.ojas.obs.request.GstlocationRequest;
import com.ojas.obs.response.GstlocationResponse;
import com.ojas.obs.response.ErrorResponse;

public class GstlocationFacadeImplTest
{

	@InjectMocks
	GstlocationFacadeImpl gstlocationfacadeimpl;
	
	@Mock
	GstLocationRepository gstLocationRepo;
	
	@Mock
	GstlocationFacade  cmsfacadeImpl;
	
	@Spy
	GstlocationRequest gstlocationreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	GstlocationResponse gstlocationresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(gstlocationresponse, HttpStatus.OK);
	
	@Spy
	GstLocation sezlocation;
	
	@Before
	public void init() throws Exception 
	{
		gstlocationfacadeimpl=new GstlocationFacadeImpl();
		gstLocationRepo = mock(GstLocationRepository.class);
		setCollaborator(gstlocationfacadeimpl, "gstLocationRepo", gstLocationRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<GstLocation> getGStlocationList() 
	{
		List<GstLocation> gstlist = new ArrayList<GstLocation>();
		GstLocation gstdatalist = new GstLocation();
		gstdatalist.setGstlocationId(1);
		gstdatalist.setGstlocationName("clientodc");
		
		
		GstLocation gstdatalist1 = new GstLocation();
		gstdatalist1.setGstlocationId(2);
		gstdatalist1.setGstlocationName("ojasodc");
		
		gstlist.add(gstdatalist);
		gstlist.add(gstdatalist1);
		return gstlist;
	}
	
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setTransactionType("save");
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();

		when(gstLocationRepo.save(gstlocation2)).thenReturn(gstlocation2);	
		
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.saveDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setTransactionType("save");
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();
		gstlocation2.setGstlocationId(1);
		gstlocation2.setGstlocationName("cat");
		
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);
		
		when(gstLocationRepo.save(gstlocation2)).thenReturn(gstlocation2);	
		
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.saveDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setTransactionType("update");
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();
		gstlocation2.setGstlocationId(1);
		gstlocation2.setGstlocationName("cat");
		
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);

		Integer id=gstlocationreq.getGstlocationList().get(0).getGstlocationId();
		
		when(gstLocationRepo.findById(id)).thenReturn(Optional.of(gstlocation2));
			
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.saveDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setTransactionType("update");
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();
		gstlocation2.setGstlocationId(null);
		gstlocation2.setGstlocationName("cat");
		
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		
		gstlocationreq.setGstlocationList(gstlocation);

		Integer id=gstlocationreq.getGstlocationList().get(0).getGstlocationId();
		
		when(gstLocationRepo.findById(id)).thenReturn(Optional.of(gstlocation2));
			
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.saveDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setTransactionType("delete");
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();	
		gstlocation2.setGstlocationId(1);
		gstlocation2.setGstlocationName("cat");
		
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);

		Integer id=gstlocationreq.getGstlocationList().get(0).getGstlocationId();
		
		when(gstLocationRepo.getOne(id)).thenReturn(gstlocation2);
					
		gstlocation2.setStatus(gstlocation2.getStatus()==null);
		
		when(gstLocationRepo.save(gstlocation2)).thenReturn(gstlocation2);
			
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.saveDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setTransactionType("delete");
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();
		gstlocation2.setGstlocationId(null);
		gstlocation2.setGstlocationName(null);
		gstlocation2.setStatus(null);
		
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);

		Integer id=gstlocationreq.getGstlocationList().get(0).getGstlocationId();
		
		when(gstLocationRepo.getOne(id)).thenReturn(gstlocation2);
					
		gstlocation2.setStatus(gstlocation2.getStatus()!=null);
		
		when(gstLocationRepo.save(gstlocation2)).thenReturn(null);
			
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.saveDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setTransactionType("ss");
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();

		when(gstLocationRepo.save(gstlocation2)).thenReturn(gstlocation2);	
		
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.saveDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		gstlocationreq.setTransactionType("getAll");
	
		GstLocation gstlocation2 = new GstLocation();
	
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);

		when(gstLocationRepo.findAll()).thenReturn(gstlocation);	
		
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.getDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllError() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		gstlocationreq.setTransactionType("getAll");
	
		GstLocation gstlocation2 = new GstLocation();
		
	
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);

		when(gstLocationRepo.findAll()).thenReturn(null);
		
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.getDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
	
		GstLocation gstlocation2 = new GstLocation();
		gstlocation2.setGstlocationId(1);
		
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);
    	
		gstlocationreq.setTransactionType("getById");
    	Integer id=gstlocation.get(0).getGstlocationId();

		when(gstLocationRepo.findAll()).thenReturn(gstlocation);	
		
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.getDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		GstlocationRequest gstlocationreq = new GstlocationRequest();
		
		gstlocationreq.setGstlocationList(this.getGStlocationList());
		
		GstLocation gstlocation2 = new GstLocation();
		gstlocation2.setGstlocationId(1);
		
		List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
		
		gstlocation.add(gstlocation2);
		gstlocationreq.setGstlocationList(gstlocation);
    	
		gstlocationreq.setTransactionType("getById");
    	Integer id=gstlocation.get(0).getGstlocationId();
    	
		when(gstLocationRepo.findById(id)).thenReturn(Optional.of(gstlocation2));
		gstlocation.add(gstlocation2);
		
		ResponseEntity<Object> saveStatus = gstlocationfacadeimpl.getDetails(gstlocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
}
