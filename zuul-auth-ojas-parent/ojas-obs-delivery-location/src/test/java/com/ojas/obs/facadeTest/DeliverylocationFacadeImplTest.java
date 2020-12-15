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

import com.ojas.obs.facade.DeliverylocationFacade;
import com.ojas.obs.facadeimpl.DeliverylocationFacadeImpl;
import com.ojas.obs.model.DeliveryLocation;
import com.ojas.obs.repositories.DeliveryLocationRepository;
import com.ojas.obs.request.DeliverylocationRequest;
import com.ojas.obs.response.DeliverylocationResponse;
import com.ojas.obs.response.ErrorResponse;

public class DeliverylocationFacadeImplTest
{

	@InjectMocks
	DeliverylocationFacadeImpl deliverylocationfacadeimpl;
	
	@Mock
	DeliveryLocationRepository deliveryLocationRepo;
	
	@Mock
	DeliverylocationFacade  cmsfacadeImpl;
	
	@Spy
	DeliverylocationRequest deliverylocationreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	DeliverylocationResponse deliverylocationresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(deliverylocationresponse, HttpStatus.OK);
	
	@Spy
	DeliveryLocation deliverylocation;
	
	@Before
	public void init() throws Exception 
	{
		deliverylocationfacadeimpl=new DeliverylocationFacadeImpl();
		deliveryLocationRepo = mock(DeliveryLocationRepository.class);
		setCollaborator(deliverylocationfacadeimpl, "deliveryLocationRepo", deliveryLocationRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<DeliveryLocation> getDeliverylocationList() {
		List<DeliveryLocation> deliverylist = new ArrayList<DeliveryLocation>();
		DeliveryLocation deliverydatalist = new DeliveryLocation();
		deliverydatalist.setDeliverylocationId(1);
		deliverydatalist.setDeliverylocationName("clientodc");
		
		
		DeliveryLocation deliverydatalist1 = new DeliveryLocation();
		deliverydatalist1.setDeliverylocationId(2);
		deliverydatalist1.setDeliverylocationName("ojasodc");
		
		deliverylist.add(deliverydatalist);
		deliverylist.add(deliverydatalist1);
		return deliverylist;
	}
	
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setTransactionType("save");
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();

		when(deliveryLocationRepo.save(deliverylocation2)).thenReturn(deliverylocation2);	
		
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.saveDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setTransactionType("save");
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
		deliverylocation2.setDeliverylocationId(1);
		deliverylocation2.setDeliverylocationName("cat");
		
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);
		
		when(deliveryLocationRepo.save(deliverylocation2)).thenReturn(deliverylocation2);	
		
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.saveDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setTransactionType("update");
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
		deliverylocation2.setDeliverylocationId(1);
		deliverylocation2.setDeliverylocationName("cat");
		
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);

		Integer id=deliverylocationreq.getDeliverylocationList().get(0).getDeliverylocationId();
		
		when(deliveryLocationRepo.findById(id)).thenReturn(Optional.of(deliverylocation2));
			
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.saveDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setTransactionType("update");
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
		deliverylocation2.setDeliverylocationId(null);
		deliverylocation2.setDeliverylocationName("cat");
		
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);

		Integer id=deliverylocationreq.getDeliverylocationList().get(0).getDeliverylocationId();
		
		when(deliveryLocationRepo.findById(id)).thenReturn(Optional.of(deliverylocation2));
			
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.saveDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setTransactionType("delete");
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();	
		deliverylocation2.setDeliverylocationId(1);
		deliverylocation2.setDeliverylocationName("cat");
		
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);

		Integer id=deliverylocationreq.getDeliverylocationList().get(0).getDeliverylocationId();
		
		when(deliveryLocationRepo.getOne(id)).thenReturn(deliverylocation2);
					
		deliverylocation2.setStatus(deliverylocation2.getStatus()==null);
		
		when(deliveryLocationRepo.save(deliverylocation2)).thenReturn(deliverylocation2);
			
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.saveDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setTransactionType("delete");
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
		deliverylocation2.setDeliverylocationId(null);
		deliverylocation2.setDeliverylocationName(null);
		deliverylocation2.setStatus(null);
		
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);

		Integer id=deliverylocationreq.getDeliverylocationList().get(0).getDeliverylocationId();
		
		when(deliveryLocationRepo.getOne(id)).thenReturn(deliverylocation2);
					
		deliverylocation2.setStatus(deliverylocation2.getStatus()!=null);
		
		when(deliveryLocationRepo.save(deliverylocation2)).thenReturn(null);
			
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.saveDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setTransactionType("ss");
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();

		when(deliveryLocationRepo.save(deliverylocation2)).thenReturn(deliverylocation2);	
		
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.saveDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		deliverylocationreq.setTransactionType("getAll");
	
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
	
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);

		when(deliveryLocationRepo.findAll()).thenReturn(deliverylocation);	
		
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.getDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllError() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		deliverylocationreq.setTransactionType("getAll");
	
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
		
	
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);

		when(deliveryLocationRepo.findAll()).thenReturn(null);
		
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.getDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
	
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
		deliverylocation2.setDeliverylocationId(1);
		
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);
    	
		deliverylocationreq.setTransactionType("getById");
    	Integer id=deliverylocation.get(0).getDeliverylocationId();

		when(deliveryLocationRepo.findAll()).thenReturn(deliverylocation);	
		
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.getDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		DeliverylocationRequest deliverylocationreq = new DeliverylocationRequest();
		
		deliverylocationreq.setDeliverylocationList(this.getDeliverylocationList());
		
		DeliveryLocation deliverylocation2 = new DeliveryLocation();
		deliverylocation2.setDeliverylocationId(1);
		
		List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
		deliverylocation.add(deliverylocation2);
		deliverylocationreq.setDeliverylocationList(deliverylocation);
    	
		deliverylocationreq.setTransactionType("getById");
    	Integer id=deliverylocation.get(0).getDeliverylocationId();
    	
		when(deliveryLocationRepo.findById(id)).thenReturn(Optional.of(deliverylocation2));
		deliverylocation.add(deliverylocation2);
		
		ResponseEntity<Object> saveStatus = deliverylocationfacadeimpl.getDetails(deliverylocationreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
}
