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

import com.ojas.obs.facade.LocationTypeFacade;
import com.ojas.obs.facadeimpl.LocationTypeFacadeImpl;
import com.ojas.obs.model.LocationType;
import com.ojas.obs.repositories.LocationTypeRepository;
import com.ojas.obs.request.LocationTypeRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.LocationTypeResponse;
 
public class LocationTypeFacadeImplTest {    

	@InjectMocks 
	LocationTypeFacadeImpl locationfacadeImpl;

	@Mock
	LocationTypeRepository locationTypeRepo;

	@Mock
	LocationTypeFacade locationTypefacadeImpl;

	@Spy
	LocationTypeRequest locationreq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	LocationTypeResponse locationresponse; 

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(locationresponse, HttpStatus.OK);

	@Spy
	LocationType locationtype;

	@Before
	public void init() throws Exception {
		locationfacadeImpl = new LocationTypeFacadeImpl();
		locationTypeRepo = mock(LocationTypeRepository.class);
		setCollaborator(locationfacadeImpl, "locationTypeRepo", locationTypeRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<LocationType> getLocationTypes() { 
		List<LocationType> locationlist = new ArrayList<LocationType>();
		LocationType locationdatalist = new LocationType();
		locationdatalist.setLocationtypeId(1);
		
		LocationType locationdatalist1 = new LocationType();
		locationdatalist1.setLocationtypeId(2);


		locationlist.add(locationdatalist);
		locationlist.add(locationdatalist1);
		return locationlist;
	}

	@Test
	public void testSaveError() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		
		locationtypereq.setTransactionType("save");
		
		locationtypereq.setLocationTypeList(this.getLocationTypes());
		
		LocationType locationtype2 = new LocationType();

		when(locationTypeRepo.save(locationtype2)).thenReturn(locationtype2);	
		
		ResponseEntity<Object> saveStatus = locationfacadeImpl.saveDetails(locationtypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException  
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		List<LocationType> locationtype = new ArrayList<LocationType>();
		LocationType location = new LocationType();
		location.setLocationtypeId(5);
		location.setLocationType("Hyd");
		location.setStatus(true);
		locationtype.add(location);		
		locationtypereq.setTransactionType("save");
		locationtypereq.setLocationTypeList(locationtype);
		when(locationTypeRepo.saveAll(locationtype)).thenReturn(locationtype);
		ResponseEntity<Object> saveStatus = locationfacadeImpl.saveDetails(locationtypereq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		
		locationtypereq.setTransactionType("update");
		
		locationtypereq.setLocationTypeList(this.getLocationTypes());
		
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationtypeId(1);
	
		
		List<LocationType> locationtype  = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationtypereq.setLocationTypeList(locationtype);

		Integer id=locationtypereq.getLocationTypeList().get(0).getLocationtypeId();
		
		when(locationTypeRepo.findById(id)).thenReturn(Optional.of(locationtype2));
			
		ResponseEntity<Object> saveStatus = locationfacadeImpl.saveDetails(locationtypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		
		locationtypereq.setTransactionType("update");
		
		locationtypereq.setLocationTypeList(this.getLocationTypes());
		
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationtypeId(null);
			
		List<LocationType> locationtype  = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationtypereq.setLocationTypeList(locationtype);

		Integer id=locationtypereq.getLocationTypeList().get(0).getLocationtypeId();
		
		when(locationTypeRepo.findById(id)).thenReturn(Optional.of(locationtype2));
			
		ResponseEntity<Object> saveStatus = locationfacadeImpl.saveDetails(locationtypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		
		locationtypereq.setTransactionType("delete");
		
		locationtypereq.setLocationTypeList(this.getLocationTypes());
		
		LocationType locationtype2 = new LocationType();	
		locationtype2.setLocationtypeId(1);
			
		List<LocationType> locationtype  = new ArrayList<LocationType>();
		locationtype.add(locationtype2); 
		locationtypereq.setLocationTypeList(locationtype);

		Integer id=locationtypereq.getLocationTypeList().get(0).getLocationtypeId();
		
		when(locationTypeRepo.getOne(id)).thenReturn(locationtype2);
					
		locationtype2.setStatus(locationtype2.getStatus()==null);
		
		when(locationTypeRepo.save(locationtype2)).thenReturn(locationtype2);
			
		ResponseEntity<Object> saveStatus = locationfacadeImpl.saveDetails(locationtypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		locationtypereq.setTransactionType("delete");
		List<LocationType> locationtype  = new ArrayList<LocationType>();
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationtypeId(null);
		locationtype2.setStatus(false);
		locationtype.add(locationtype2);
		locationtypereq.setLocationTypeList(locationtype);
		Integer id=locationtypereq.getLocationTypeList().get(0).getLocationtypeId();
		when(locationTypeRepo.getOne(id)).thenReturn(locationtype2);					
		when(locationTypeRepo.save(locationtype2)).thenReturn(locationtype2);		
		ResponseEntity<Object> saveStatus = locationfacadeImpl.saveDetails(locationtypereq);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		
		locationtypereq.setTransactionType("ss");
		
		locationtypereq.setLocationTypeList(this.getLocationTypes());
		
		LocationType locationtype2 = new LocationType();

		when(locationTypeRepo.save(locationtype2)).thenReturn(locationtype2);	
		
		ResponseEntity<Object> saveStatus = locationfacadeImpl.saveDetails(locationtypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
	
	@Test
	public void getAllSuccess() throws SQLException  
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		locationtypereq.setTransactionType("delete");
		List<LocationType> locationtype = new ArrayList<LocationType>();
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationtypeId(null);
		locationtype2.setStatus(false);
		locationtype.add(locationtype2);
		locationtypereq.setLocationTypeList(locationtype);
		when(locationTypeRepo.findAll()).thenReturn(locationtype);

		ResponseEntity<Object> saveStatus = locationfacadeImpl.getDetails(locationtypereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
	@Test
    public void getAllError() throws SQLException
    {
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		List<LocationType> locationtype  = new ArrayList<LocationType>();
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationtypeId(null);
		locationtype2.setStatus(false);
		locationtype2.setLocationType(null);
		locationtype.isEmpty();
		locationtypereq.setTransactionType("getAll");
		locationtypereq.setLocationTypeList(locationtype);
        when(locationTypeRepo.findAll()).thenReturn(locationtype);
        ResponseEntity<Object> saveStatus = locationfacadeImpl.getDetails(locationtypereq);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.CONFLICT, statusCode);
    }
       
	@Test 
	public void getAllTest() throws SQLException {
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		List<LocationType> locationtype = new ArrayList<LocationType>();
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationType(null);
		locationtype2.setStatus(false);
		locationtype2.setLocationType(null);
		locationtype.add(locationtype2);
		locationtypereq.setTransactionType("getAll");
		locationtypereq.setLocationTypeList(locationtype);
		when(locationTypeRepo.findAll()).thenReturn(locationtype);
		ResponseEntity<Object> saveStatus = locationfacadeImpl.getDetails(locationtypereq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void getByIdError() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		
		locationtypereq.setLocationTypeList(this.getLocationTypes());
	
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationtypeId(1);
		
		List<LocationType> locationtype  = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationtypereq.setLocationTypeList(locationtype);
    	
		locationtypereq.setTransactionType("getById");
    	Integer id=locationtype.get(0).getLocationtypeId();

		when(locationTypeRepo.findAll()).thenReturn(locationtype);	
		
		ResponseEntity<Object> saveStatus = locationfacadeImpl.getDetails(locationtypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		LocationTypeRequest locationtypereq = new LocationTypeRequest();
		
		locationtypereq.setLocationTypeList(this.getLocationTypes());
		
		LocationType locationtype2 = new LocationType();
		locationtype2.setLocationtypeId(1);
		
		List<LocationType> locationtype  = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationtypereq.setLocationTypeList(locationtype);
    	
		locationtypereq.setTransactionType("getById");
    	Integer id=locationtype.get(0).getLocationtypeId();
    	
		when(locationTypeRepo.findById(id)).thenReturn(Optional.of(locationtype2));
		locationtype.add(locationtype2);
		
		ResponseEntity<Object> saveStatus = locationfacadeImpl.getDetails(locationtypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	
	
	
	
	}
}
