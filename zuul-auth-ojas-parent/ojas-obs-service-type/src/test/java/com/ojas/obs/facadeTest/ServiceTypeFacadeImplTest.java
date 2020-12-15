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

import com.ojas.obs.facade.ServiceTypeFacade;
import com.ojas.obs.facadeimpl.ServiceTypeFacadeImpl;
import com.ojas.obs.model.ServiceType;
import com.ojas.obs.repositories.ServiceTypeRepository;
import com.ojas.obs.request.ServiceTypeRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.ServiceTypeResponse;

public class ServiceTypeFacadeImplTest 
{

	@InjectMocks
	ServiceTypeFacadeImpl servicetypefacadeimpl;
	
	@Mock
	ServiceTypeRepository serviceTypeRepo;
	
	@Mock
	ServiceTypeFacade  serfacadeImpl;
	
	@Spy
	ServiceTypeRequest servicetypereq;
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	ServiceTypeResponse servicetyperesponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(servicetyperesponse, HttpStatus.OK);
	
	@Spy
	ServiceType servicetype;
	
	@Before
	public void init() throws Exception 
	{
		servicetypefacadeimpl=new ServiceTypeFacadeImpl();
		serviceTypeRepo = mock(ServiceTypeRepository.class);
		setCollaborator(servicetypefacadeimpl, "serviceTypeRepo", serviceTypeRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<ServiceType> getServicetypeList() {
		List<ServiceType> servicelist = new ArrayList<ServiceType>();
		ServiceType servicedatalist = new ServiceType();
		servicedatalist.setId(1);
		//servicedatalist.setServiceType("clientodc");
		
		
		ServiceType servicedatalist1 = new ServiceType();
		servicedatalist1.setId(2);
		//servicedatalist1.setServiceType("ojasodc");
		
		servicelist.add(servicedatalist);
		servicelist.add(servicedatalist1);
		return servicelist;
	}
	
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setTransactionType("save");
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
		
		ServiceType servicetype2 = new ServiceType();

		when(serviceTypeRepo.save(servicetype2)).thenReturn(servicetype2);	
		
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.saveDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setTransactionType("save");
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
		
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(1);
		//servicetype2.setServiceType("cat");
		
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);
		
		when(serviceTypeRepo.save(servicetype2)).thenReturn(servicetype2);	
		
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.saveDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setTransactionType("update");
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
		
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(1);
		//servicetype2.setServiceType("cat");
		
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);

		Integer id=servicetypereq.getServicetypeList().get(0).getId();
		
		when(serviceTypeRepo.findById(id)).thenReturn(Optional.of(servicetype2));
			
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.saveDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setTransactionType("update");
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
		
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(null);
		//servicetype2.setServiceType("cat");
		
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);

		Integer id=servicetypereq.getServicetypeList().get(0).getId();
		
		when(serviceTypeRepo.findById(id)).thenReturn(Optional.of(servicetype2));
			
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.saveDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setTransactionType("delete");
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
		
		ServiceType servicetype2 = new ServiceType();	
		servicetype2.setId(1);
		//servicetype2.setServiceType("cat");
		
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);

		Integer id=servicetypereq.getServicetypeList().get(0).getId();
		
		when(serviceTypeRepo.getOne(id)).thenReturn(servicetype2);
					
		servicetype2.setStatus(servicetype2.getStatus()==null);
		
		when(serviceTypeRepo.save(servicetype2)).thenReturn(servicetype2);
			
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.saveDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		servicetypereq.setTransactionType("delete");
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(null);
		servicetype2.setStatus(false);
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);
		Integer id=servicetypereq.getServicetypeList().get(0).getId();
		when(serviceTypeRepo.getOne(id)).thenReturn(servicetype2);					
		when(serviceTypeRepo.save(servicetype2)).thenReturn(servicetype2);		
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.saveDetails(servicetypereq);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setTransactionType("ss");
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
		
		ServiceType servicetype2 = new ServiceType();

		when(serviceTypeRepo.save(servicetype2)).thenReturn(servicetype2);	
		
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.saveDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
	
	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		servicetypereq.setTransactionType("delete");
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(null);
		servicetype2.setStatus(false);
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);
		when(serviceTypeRepo.findAll()).thenReturn(servicetype);	
		
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.getDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
	@Test
    public void getAllError() throws SQLException
    {
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(null);
		servicetype2.setStatus(false);
		servicetype2.setServiceTypeName(null);
		servicetype.isEmpty();
		servicetypereq.setTransactionType("getAll");
		servicetypereq.setServicetypeList(servicetype);
        when(serviceTypeRepo.findAll()).thenReturn(servicetype);
        ResponseEntity<Object> saveStatus = servicetypefacadeimpl.getDetails(servicetypereq);	
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.CONFLICT, statusCode);
    }
       
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
	
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(1);
		
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);
    	
		servicetypereq.setTransactionType("getById");
    	Integer id=servicetype.get(0).getId();

		when(serviceTypeRepo.findAll()).thenReturn(servicetype);	
		
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.getDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		ServiceTypeRequest servicetypereq = new ServiceTypeRequest();
		
		servicetypereq.setServicetypeList(this.getServicetypeList());
		
		ServiceType servicetype2 = new ServiceType();
		servicetype2.setId(1);
		
		List<ServiceType> servicetype  = new ArrayList<ServiceType>();
		servicetype.add(servicetype2);
		servicetypereq.setServicetypeList(servicetype);
    	
		servicetypereq.setTransactionType("getById");
    	Integer id=servicetype.get(0).getId();
    	
		when(serviceTypeRepo.findById(id)).thenReturn(Optional.of(servicetype2));
		servicetype.add(servicetype2);
		
		ResponseEntity<Object> saveStatus = servicetypefacadeimpl.getDetails(servicetypereq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	
	
	
	
	}
}
	