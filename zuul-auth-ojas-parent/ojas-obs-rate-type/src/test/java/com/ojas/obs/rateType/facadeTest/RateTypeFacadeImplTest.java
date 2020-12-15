package com.ojas.obs.rateType.facadeTest;

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

import com.ojas.obs.rateType.facade.RateTypeFacade;
import com.ojas.obs.rateType.facadeImpl.RateTypeFacadeImpl;
import com.ojas.obs.rateType.model.RateType;
import com.ojas.obs.rateType.repository.RateTypeRepository;
import com.ojas.obs.rateType.request.RateTypeRequest;
import com.ojas.obs.rateType.response.ErrorResponse;
import com.ojas.obs.rateType.response.RateTypeResponse;

public class RateTypeFacadeImplTest
{
	@InjectMocks
	RateTypeFacadeImpl ratetypefacadeimpl;
	
	@Mock
	RateTypeRepository rateTypeRepo;
	
	@Mock
	RateTypeFacade rateTypeFacade;
	
	@Spy
	RateTypeRequest reatetypereq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	RateTypeResponse ratetyperesponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(ratetyperesponse, HttpStatus.OK);
	
	@Spy
	RateType  ratetype;
	
	@Before
	public void init() throws Exception 
	{
		ratetypefacadeimpl=new RateTypeFacadeImpl();
		rateTypeRepo = mock(RateTypeRepository.class);
		setCollaborator(ratetypefacadeimpl, "rateTypeRepo", rateTypeRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<RateType> getRateType() {
		List<RateType> ratetypelist = new ArrayList<RateType>();
		RateType ratelist = new RateType();
		ratelist.setId(1);
		ratelist.setRateType("cTA");
		
		
		RateType ratelist1 = new RateType();
		ratelist1.setId(1);
		ratelist1.setRateType("KK");
		
		ratetypelist.add(ratelist);
		ratetypelist.add(ratelist1);
		return ratetypelist;
	}
	
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setTransactionType("save");
		
		req.setRateType(this.getRateType());
		
		RateType ratetype2 = new RateType();

		when(rateTypeRepo.save(ratetype2)).thenReturn(ratetype2);	
		
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testSaveFail() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setTransactionType("save");
		
		req.setRateType(this.getRateType());
		
		RateType ratetype2 = new RateType();
		ratetype2.setId(1);
		ratetype2.setRateType("cat");
		
		List<RateType> ratetype  = new ArrayList<RateType>();
		ratetype.add(ratetype2);
		req.setRateType(ratetype);
		
		when(rateTypeRepo.save(ratetype2)).thenReturn(ratetype2);	
		
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	
	@Test
	public void testSave() throws SQLException {		
		RateTypeRequest req = new RateTypeRequest();		
		req.setTransactionType("save");	
		List<RateType> ratetype  = new ArrayList<RateType>();
		req.setRateType(this.getRateType());		
		RateType ratetype2 = new RateType();
		ratetype2.setId(1);
		ratetype2.setRateType("cat");	
		ratetype2.setStatus(true);
		ratetype.add(ratetype2);
		req.setRateType(ratetype);		
		when(rateTypeRepo.saveAll(ratetype)).thenReturn(ratetype);			
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);		
		HttpStatus statusCode = saveStatus.getStatusCode();		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setTransactionType("update");
		
		req.setRateType(this.getRateType());
		
		RateType ratetype2 = new RateType();
		ratetype2.setId(1);
		ratetype2.setRateType("cat");
		
		List<RateType> ratetype  = new ArrayList<RateType>();
		ratetype.add(ratetype2);
		req.setRateType(ratetype);

		Integer id=req.getRateType().get(0).getId();
		
		when(rateTypeRepo.findById(id)).thenReturn(Optional.of(ratetype2));
			
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setTransactionType("update");
		
		req.setRateType(this.getRateType());
		
		RateType ratetype2 = new RateType();
		ratetype2.setId(null);
		ratetype2.setRateType("cat");
		
		List<RateType> ratetype  = new ArrayList<RateType>();
		ratetype.add(ratetype2);
		req.setRateType(ratetype);

		Integer id=req.getRateType().get(0).getId();
		
		when(rateTypeRepo.findById(id)).thenReturn(Optional.of(ratetype2));
			
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setTransactionType("delete");
		
		req.setRateType(this.getRateType());
		
		RateType ratetype2 = new RateType();
		ratetype2.setId(1);
		ratetype2.setRateType("cat");
		
		
		List<RateType> ratetype  = new ArrayList<RateType>();
		ratetype.add(ratetype2);
		req.setRateType(ratetype);

		Integer id=req.getRateType().get(0).getId();
		
		when(rateTypeRepo.getOne(id)).thenReturn(ratetype2);
					
		ratetype2.setStatus(ratetype2.getStatus()==null);
		
		when(rateTypeRepo.save(ratetype2)).thenReturn(ratetype2);
			
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		req.setTransactionType("delete");
		List<RateType> ratetype  = new ArrayList<RateType>();
		RateType ratetype2 = new RateType();
		ratetype2.setId(null);
		ratetype2.setRateType(null);
		ratetype2.setStatus(false);
		ratetype.add(ratetype2);
		req.setRateType(ratetype);
		Integer id=req.getRateType().get(0).getId();
		when(rateTypeRepo.getOne(id)).thenReturn(ratetype2);			
		when(rateTypeRepo.save(ratetype2)).thenReturn(ratetype2);	
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElse() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setTransactionType("ss");
		
		req.setRateType(this.getRateType());
		
		RateType ratetype2 = new RateType();

		when(rateTypeRepo.save(ratetype2)).thenReturn(ratetype2);	
		
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.saveRateType(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	//getTestcases
	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setRateType(this.getRateType());
		
		req.setTransactionType("getAll");
	
		RateType ratetype2 = new RateType();
	
		List<RateType> ratetype  = new ArrayList<RateType>();
		ratetype.add(ratetype2);
		req.setRateType(ratetype);

		when(rateTypeRepo.findAll()).thenReturn(ratetype);	
		
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.getCustomerDetails(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllTest() throws SQLException {	
		RateTypeRequest req = new RateTypeRequest();	
		req.setRateType(this.getRateType());	
		req.setTransactionType("getAll");		
		List<RateType> ratetype  = new ArrayList<RateType>();
		RateType ratetype2 = new RateType();
		ratetype.add(ratetype2);
		req.setRateType(ratetype);
		req.setRateType(ratetype);
		when(rateTypeRepo.findAll()).thenReturn(ratetype);	
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.getCustomerDetails(req);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void getAllFailTest() throws SQLException {	
		RateTypeRequest req = new RateTypeRequest();		
		req.setTransactionType("getAll");		
		List<RateType> ratetype  = new ArrayList<RateType>();
		ratetype.isEmpty();	
		req.setRateType(ratetype);
		when(rateTypeRepo.findAll()).thenReturn(ratetype);	
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.getCustomerDetails(req);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setRateType(this.getRateType());
	
		RateType ratetype2 = new RateType();
		ratetype2.setId(1);
		
		List<RateType> ratetype  = new ArrayList<RateType>();
		ratetype.add(ratetype2);
		req.setRateType(ratetype);
    	
		req.setTransactionType("getById");
    	Integer id=ratetype.get(0).getId();

		when(rateTypeRepo.findAll()).thenReturn(ratetype);	
		
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.getCustomerDetails(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		RateTypeRequest req = new RateTypeRequest();
		
		req.setRateType(this.getRateType());
		
		RateType ratetype2 = new RateType();
		ratetype2.setId(1);
		
		List<RateType> deliverylocation  = new ArrayList<RateType>();
		deliverylocation.add(ratetype2);
		req.setRateType(deliverylocation);
    	
		req.setTransactionType("getById");
    	Integer id=deliverylocation.get(0).getId();
    	
		when(rateTypeRepo.findById(id)).thenReturn(Optional.of(ratetype2));
		deliverylocation.add(ratetype2);
		
		ResponseEntity<Object> saveStatus = ratetypefacadeimpl.getCustomerDetails(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
}
