package com.obs.psa.customercontactinfofacadeimpltest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import com.obs.psa.customercontactinfo.constants.ErrorResponse;
import com.obs.psa.customercontactinfo.facade.CustomerContactInfoFacade;
import com.obs.psa.customercontactinfo.facadeimpl.CustomerContactInfoFacadeImpl;
import com.obs.psa.customercontactinfo.model.CustomerContactInfo;
import com.obs.psa.customercontactinfo.repositories.CustomerContactInfoRepository;
import com.obs.psa.customercontactinfo.request.CustomerContactInfoRequest;
import com.obs.psa.customercontactinfo.response.CustomerContactInfoResponse;

public class CustomerContactInfoFacadeImplTest {

	@InjectMocks
	CustomerContactInfoFacadeImpl customerContactInfoFacadeImpl;

	@Mock
	CustomerContactInfoRepository customerContactInfoRepository;

	@Mock
	CustomerContactInfoFacade customerContactInfoFacade;

	@Spy
	CustomerContactInfoRequest customerContactInfoRequest;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	CustomerContactInfoResponse customerContactInfoResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(customerContactInfoResponse, HttpStatus.OK);

	@Spy
	CustomerContactInfo customerContactInfo;

	@Before
	public void init() throws Exception {
		customerContactInfoFacadeImpl = new CustomerContactInfoFacadeImpl();
		customerContactInfoRepository = mock(CustomerContactInfoRepository.class);
		setCollaborator(customerContactInfoFacadeImpl, "customerContactInfoRepository", customerContactInfoRepository);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<CustomerContactInfo> getCustomerContactInfoList() {
		List<CustomerContactInfo> contactInfolist = new ArrayList<CustomerContactInfo>();
		CustomerContactInfo customerContactInfolist = new CustomerContactInfo();
		Timestamp timestamp=new Timestamp(new java.util.Date().getTime());
		customerContactInfolist.setContactId(1);
		customerContactInfolist.setContactName("TCS");
		customerContactInfolist.setCustomerId(1001);
		customerContactInfolist.setDepartment("Java");
		customerContactInfolist.setDesignation("Java");
		customerContactInfolist.setDoa("2008-01-11");
		customerContactInfolist.setDob("1993-01-11");
		customerContactInfolist.setPersonalEmail("tcs@gmail.com");
		customerContactInfolist.setOfficialEmail("tataconsultancyservice@gmail.com");
		customerContactInfolist.setPermanentMobileNumber((long) 987654546);
		customerContactInfolist.setAlternateMobileNumber((long) 927253555);
		customerContactInfolist.setAddress1("hyd");
		customerContactInfolist.setAddress2("hyd");
		customerContactInfolist.setAddress3("hyd");
		customerContactInfolist.setBdm("kishore");
		customerContactInfolist.setPincode(515201);
		
		customerContactInfolist.setStatus(true);
		
		
		
		CustomerContactInfo customerContactInfolist1 = new CustomerContactInfo();
		Timestamp timestamp1=new Timestamp(new java.util.Date().getTime());
		customerContactInfolist.setContactId(2);
		customerContactInfolist.setContactName("CTS");
		customerContactInfolist.setCustomerId(1002);
		customerContactInfolist.setDepartment("Java");
		customerContactInfolist.setDesignation("Java");
		customerContactInfolist.setDoa("2008-01-11");
		customerContactInfolist.setDob("1993-01-11");
		customerContactInfolist.setPersonalEmail("cts@gmail.com");
		customerContactInfolist.setOfficialEmail("cognizant@gmail.com");
		customerContactInfolist.setPermanentMobileNumber((long) 980654546);
		customerContactInfolist.setAlternateMobileNumber((long) 927250555);
		customerContactInfolist.setAddress1("hyd");
		customerContactInfolist.setAddress2("hyd");
		customerContactInfolist.setAddress3("hyd");
		customerContactInfolist.setBdm("kishore");
		customerContactInfolist.setPincode(515201);
		customerContactInfolist.setStatus(true);

		contactInfolist.add(customerContactInfolist);
		contactInfolist.add(customerContactInfolist1);
		return contactInfolist;
	}

	@Test
	public void testSaveError() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setTransactionType("save");

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();

		when(customerContactInfoRepository.save(customerContactInfo)).thenReturn(customerContactInfo);

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl
				.setCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setTransactionType("save");

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		Timestamp timestamp=new Timestamp(new java.util.Date().getTime());
		customerContactInfo.setContactId(1);
		customerContactInfo.setContactName("CTS");
		customerContactInfo.setCustomerId(1002);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("cts@gmail.com");
		customerContactInfo.setOfficialEmail("cognizant@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 980654546);
		customerContactInfo.setAlternateMobileNumber((long) 927250555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(true);

		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		when(customerContactInfoRepository.save(customerContactInfo)).thenReturn(customerContactInfo);

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl
				.setCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setTransactionType("update");

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		Timestamp timestamp=new Timestamp(new java.util.Date().getTime());
		customerContactInfo.setContactId(1);
		customerContactInfo.setContactName("CTS");
		customerContactInfo.setCustomerId(1002);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("cts@gmail.com");
		customerContactInfo.setOfficialEmail("cognizant@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 980654546);
		customerContactInfo.setAlternateMobileNumber((long) 927250555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(true);

		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);

		Integer id = customerContactInfoRequest.getCustomerContactInfo().get(0).getCustomerId();

		when(customerContactInfoRepository.findById(id)).thenReturn(Optional.of(customerContactInfo));

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl
				.setCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setTransactionType("update");

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		Timestamp timestamp=new Timestamp(new java.util.Date().getTime());
		customerContactInfo.setContactId(null);
		customerContactInfo.setContactName("CTS");
		customerContactInfo.setCustomerId(1002);
		customerContactInfo.setDepartment("Java");
		customerContactInfo.setDesignation("Java");
		customerContactInfo.setDoa("2008-01-11");
		customerContactInfo.setDob("1993-01-11");
		customerContactInfo.setPersonalEmail("cts@gmail.com");
		customerContactInfo.setOfficialEmail("cognizant@gmail.com");
		customerContactInfo.setPermanentMobileNumber((long) 980654546);
		customerContactInfo.setAlternateMobileNumber((long) 927250555);
		customerContactInfo.setAddress1("hyd");
		customerContactInfo.setAddress2("hyd");
		customerContactInfo.setAddress3("hyd");
		customerContactInfo.setBdm("kishore");
		customerContactInfo.setPincode(515201);
		customerContactInfo.setStatus(true);
		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);

		Integer id = customerContactInfoRequest.getCustomerContactInfo().get(0).getCustomerId();

		when(customerContactInfoRepository.findById(id)).thenReturn(Optional.of(customerContactInfo));

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl
				.setCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		
		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();
        
		customerContactInfoRequest.setTransactionType("delete");
        
		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
        
		CustomerContactInfo customerContactInfo = new CustomerContactInfo();    
		customerContactInfo.setContactId(1);
		customerContactInfo.setContactName("cat");
        
        List<CustomerContactInfo> customerContactInfo1  = new ArrayList<CustomerContactInfo>();
        customerContactInfo1.add(customerContactInfo);
        customerContactInfoRequest.setCustomerContactInfo(customerContactInfo1);

 

        Integer cid=customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId();
        
        when(customerContactInfoRepository.getOne(cid)).thenReturn(customerContactInfo);
                    
        customerContactInfo.setStatus(customerContactInfo.getStatus()==null);
        
        when(customerContactInfoRepository.save(customerContactInfo)).thenReturn(customerContactInfo);
            
        ResponseEntity<Object> saveStatuss = customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest);
        
        HttpStatus statusCo = saveStatuss.getStatusCode();
        
        assertEquals(HttpStatus.OK, statusCo);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();
        
		customerContactInfoRequest.setTransactionType("delete");
        
		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
        
		CustomerContactInfo customerContactInfo = new CustomerContactInfo();    
		customerContactInfo.setContactId(null);
		customerContactInfo.setContactName(null);
		
        
        List<CustomerContactInfo> customerContactInfo1  = new ArrayList<CustomerContactInfo>();
        customerContactInfo1.add(customerContactInfo);
        customerContactInfoRequest.setCustomerContactInfo(customerContactInfo1);

 

        Integer cid=customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId();
        
        when(customerContactInfoRepository.getOne(cid)).thenReturn(customerContactInfo);
                    
        customerContactInfo.setStatus(customerContactInfo.getStatus()==null);
        
        when(customerContactInfoRepository.save(customerContactInfo)).thenReturn(null);
            
        ResponseEntity<Object> saveStatuss = customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest);
        
        HttpStatus statusCo = saveStatuss.getStatusCode();
        
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCo);
	}

	@Test
	public void TestElseError() throws SQLException {

CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();
        
		customerContactInfoRequest.setTransactionType("sss");
        
		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());
        
		CustomerContactInfo customerContactInfo = new CustomerContactInfo();    
		customerContactInfo.setContactId(null);
		customerContactInfo.setContactName(null);
		
        
        List<CustomerContactInfo> customerContactInfo1  = new ArrayList<CustomerContactInfo>();
        customerContactInfo1.add(customerContactInfo);
        customerContactInfoRequest.setCustomerContactInfo(customerContactInfo1);

 

        Integer cid=customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId();
        
        when(customerContactInfoRepository.getOne(cid)).thenReturn(customerContactInfo);
                    
        customerContactInfo.setStatus(customerContactInfo.getStatus()==null);
        
        when(customerContactInfoRepository.save(customerContactInfo)).thenReturn(null);
            
        ResponseEntity<Object> saveStatuss = customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest);
        
        HttpStatus statusCo = saveStatuss.getStatusCode();
        
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCo);
	}

	// getTestcases

	@Test
	public void getAllSuccess() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		customerContactInfoRequest.setTransactionType("getAll");

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();

		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);

		when(customerContactInfoRepository.findAll()).thenReturn(contactInfo);

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl
				.getCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		customerContactInfoRequest.setTransactionType("getAll");

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();

		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);

		when(customerContactInfoRepository.findAll()).thenReturn(null);

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl
				.getCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		customerContactInfo.setContactId(1);
		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);
		customerContactInfoRequest.setTransactionType("getById");
		Integer id = contactInfo.get(0).getContactId();
		when(customerContactInfoRepository.findAll()).thenReturn(contactInfo);

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl
				.getCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		CustomerContactInfoRequest customerContactInfoRequest = new CustomerContactInfoRequest();

		customerContactInfoRequest.setCustomerContactInfo(this.getCustomerContactInfoList());

		CustomerContactInfo customerContactInfo = new CustomerContactInfo();
		customerContactInfo.setContactId(1);

		List<CustomerContactInfo> contactInfo = new ArrayList<CustomerContactInfo>();
		contactInfo.add(customerContactInfo);
		customerContactInfoRequest.setCustomerContactInfo(contactInfo);

		customerContactInfoRequest.setTransactionType("getById");
		Integer id = contactInfo.get(0).getContactId();

		when(customerContactInfoRepository.findById(id)).thenReturn(Optional.of(customerContactInfo));
		contactInfo.add(customerContactInfo);

		ResponseEntity<Object> saveStatus = customerContactInfoFacadeImpl.getCustomerContactInfo(customerContactInfoRequest);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

}
