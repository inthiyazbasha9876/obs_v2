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

import com.ojas.obs.controller.ServicecategoryController;
import com.ojas.obs.facade.ServicecategoryFacade;
import com.ojas.obs.facadeimpl.ServicecategoryFacadeImpl;
import com.ojas.obs.model.ServiceCategory;
import com.ojas.obs.repositories.ServiceCategoryRepository;
import com.ojas.obs.request.ServicecategoryRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.ServicecategoryResponse;

public class ServicecategoryFacadeImplTest 
{
	@InjectMocks
	ServicecategoryFacadeImpl servicecategoryfacadeimpl;
	
	@Mock
	ServiceCategoryRepository serviceCategoryrepo;
	
	@Mock
	ServicecategoryFacade cmsfacadeImpl;
	
	@Spy
	ServicecategoryRequest servicecategortrequest;
	
	@Spy
	ErrorResponse errorresponse=new ErrorResponse();
	
	@Spy
	ServicecategoryResponse servicecategoryresponse = new ServicecategoryResponse();
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(servicecategoryresponse, HttpStatus.OK);
	
	@Spy
	ServiceCategory servicecategory;
	
	@Before
	public void init() throws Exception 
	{
		servicecategoryfacadeimpl=new ServicecategoryFacadeImpl();
		serviceCategoryrepo = mock(ServiceCategoryRepository.class);
		setCollaborator(servicecategoryfacadeimpl, "serviceCategoryrepo", serviceCategoryrepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<ServiceCategory> getservicecategoryList() {
		List<ServiceCategory> servicelist = new ArrayList<ServiceCategory>();
		ServiceCategory servicedatalist = new ServiceCategory();
		servicedatalist.setServiceategoryId(1);
		servicedatalist.setServiceategoryName("Dev");
		
		
		ServiceCategory servicedatalist1 = new ServiceCategory();
		servicedatalist1.setServiceategoryId(2);
		servicedatalist1.setServiceategoryName("Testing");
		
		servicelist.add(servicedatalist);
		servicelist.add(servicedatalist1);
		return servicelist;
	}
	
	
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setTransactionType("save");
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		ServiceCategory serviceCategory2 = new ServiceCategory();

		when(serviceCategoryrepo.save(serviceCategory2)).thenReturn(serviceCategory2);	
		
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.saveDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setTransactionType("save");
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		ServiceCategory serviceCategory2 = new ServiceCategory();
		serviceCategory2.setServiceategoryId(1);
		serviceCategory2.setServiceategoryName("cat");
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);
		
		when(serviceCategoryrepo.save(serviceCategory2)).thenReturn(serviceCategory2);	
		
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.saveDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setTransactionType("update");
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		ServiceCategory serviceCategory2 = new ServiceCategory();	
		serviceCategory2.setServiceategoryId(1);
		serviceCategory2.setServiceategoryName("cat");
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);

		Integer id=servicecategoryrequest.getServicecategoryList().get(0).getServiceategoryId();
		
		when(serviceCategoryrepo.findById(id)).thenReturn(Optional.of(serviceCategory2));
			
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.saveDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setTransactionType("update");
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		ServiceCategory serviceCategory2 = new ServiceCategory();	
		serviceCategory2.setServiceategoryId(null);
		serviceCategory2.setServiceategoryName("cat");
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);

		Integer id=servicecategoryrequest.getServicecategoryList().get(0).getServiceategoryId();
		
		when(serviceCategoryrepo.findById(id)).thenReturn(Optional.of(serviceCategory2));
			
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.saveDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setTransactionType("delete");
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		ServiceCategory serviceCategory2 = new ServiceCategory();	
		serviceCategory2.setServiceategoryId(1);
		serviceCategory2.setServiceategoryName("cat");
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);

		Integer id=servicecategoryrequest.getServicecategoryList().get(0).getServiceategoryId();
		
		when(serviceCategoryrepo.getOne(id)).thenReturn(serviceCategory2);
					
		serviceCategory2.setServiceStatus(serviceCategory2.getServiceStatus()==null);
		
		when(serviceCategoryrepo.save(serviceCategory2)).thenReturn(serviceCategory2);
			
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.saveDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setTransactionType("delete");
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		ServiceCategory serviceCategory2 = new ServiceCategory();	
		serviceCategory2.setServiceategoryId(null);
		serviceCategory2.setServiceategoryName(null);
		serviceCategory2.setServiceStatus(null);
		
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);

		Integer id=servicecategoryrequest.getServicecategoryList().get(0).getServiceategoryId();
		
		when(serviceCategoryrepo.getOne(id)).thenReturn(serviceCategory2);
					
		serviceCategory2.setServiceStatus(serviceCategory2.getServiceStatus()!=null);
		
		when(serviceCategoryrepo.save(serviceCategory2)).thenReturn(null);
			
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.saveDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		servicecategoryrequest.setTransactionType("getAll");
	
		ServiceCategory serviceCategory2 = new ServiceCategory();
	
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);

		when(serviceCategoryrepo.findAll()).thenReturn(serviceCategory);	
		
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.getDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllError() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		servicecategoryrequest.setTransactionType("getAll");
	
		ServiceCategory serviceCategory2 = new ServiceCategory();
		
	
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);

		when(serviceCategoryrepo.findAll()).thenReturn(null);
		
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.getDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		
	
		ServiceCategory serviceCategory2 = new ServiceCategory();
		serviceCategory2.setServiceategoryId(1);
		
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);
    	
    	servicecategoryrequest.setTransactionType("getById");
    	Integer id=serviceCategory.get(0).getServiceategoryId();

		when(serviceCategoryrepo.findAll()).thenReturn(serviceCategory);	
		
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.getDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		
	
		ServiceCategory serviceCategory2 = new ServiceCategory();
		serviceCategory2.setServiceategoryId(1);
		
		List<ServiceCategory> serviceCategory  = new ArrayList<ServiceCategory>();
    	serviceCategory.add(serviceCategory2);
    	servicecategoryrequest.setServicecategoryList(serviceCategory);
    	
    	servicecategoryrequest.setTransactionType("getById");
    	Integer id=serviceCategory.get(0).getServiceategoryId();
    	
		when(serviceCategoryrepo.findById(id)).thenReturn(Optional.of(serviceCategory2));
		serviceCategory.add(serviceCategory2);
		
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.getDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		ServicecategoryRequest servicecategoryrequest = new ServicecategoryRequest();
		
		servicecategoryrequest.setTransactionType("ss");
		
		servicecategoryrequest.setServicecategoryList(this.getservicecategoryList());
		
		ServiceCategory serviceCategory2 = new ServiceCategory();

		when(serviceCategoryrepo.save(serviceCategory2)).thenReturn(serviceCategory2);	
		
		ResponseEntity<Object> saveStatus = servicecategoryfacadeimpl.saveDetails(servicecategoryrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
}
