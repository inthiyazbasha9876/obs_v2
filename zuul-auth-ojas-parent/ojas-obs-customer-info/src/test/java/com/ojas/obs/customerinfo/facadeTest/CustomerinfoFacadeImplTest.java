package com.ojas.obs.customerinfo.facadeTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import com.ojas.obs.customerinfo.facade.CustomerinfoFacade;
import com.ojas.obs.customerinfo.facadeimpl.CustomerinfoFacadeImpl;
import com.ojas.obs.customerinfo.model.BillingAddress;
import com.ojas.obs.customerinfo.model.BillingInfo;
import com.ojas.obs.customerinfo.model.ContactInfo;
import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.model.CustomerGst;
import com.ojas.obs.customerinfo.model.RegisteredAddress;
import com.ojas.obs.customerinfo.model.ServiceType;
import com.ojas.obs.customerinfo.model.ShippingAddress;
import com.ojas.obs.customerinfo.repositories.CustomerRepository;
import com.ojas.obs.customerinfo.repositoriesimpl.CustomerRepositoryImpl;
import com.ojas.obs.customerinfo.request.CustomerinfoRequest;
import com.ojas.obs.customerinfo.response.CustomerinfoResponse;
import com.ojas.obs.customerinfo.response.ErrorResponse;






public class CustomerinfoFacadeImplTest 
{
	@InjectMocks
	CustomerinfoFacadeImpl customerinfofacadeimpl;
	
	@Mock
	CustomerinfoFacade customerinfofacade;
	
	@Mock
	CustomerRepositoryImpl customerRepo;
	
	@Mock
	CustomerRepository customerrepository;
	
	@Spy
	CustomerinfoRequest customerinforeq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	CustomerinfoResponse  customerinforesponse;
	
	@Spy
	 Environment env;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(customerinforesponse, HttpStatus.OK);
	
	@Spy
	Customer customer;
	
	@Spy
	ContactInfo contactinfo;
	
	@Spy
	CustomerGst customergst;
	
	@Spy
	List<Customer> customerList = new ArrayList<Customer>();
	
	@Before
	public void init() throws Exception 
	{
		customerList.add(customer);
		customerinfofacadeimpl = new CustomerinfoFacadeImpl();
		customerRepo = mock(CustomerRepositoryImpl.class);
		setCollaborator(customerinfofacadeimpl, "customerRepo", customerRepo);
	}
	
	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	
	public Customer getCustomerList()
	{
		List<Customer> cuslist = new ArrayList<Customer>();
		
		Customer custdatalist = new Customer();
		custdatalist.setCustomerId(1);
		custdatalist.setCustomerName("clientodc");
		custdatalist.setTds(10.00f);
		custdatalist.setPannumber("123");
		custdatalist.setTannumber("123");
		custdatalist.setCustomerstatus(true);
		custdatalist.setMailstatus("pending");
		custdatalist.setTds(87.6f);
		custdatalist.setCustomerstatus(true);
		custdatalist.setCountries("kk");
		custdatalist.setGeolocations("jj");
		return custdatalist;
	}
	
	public CustomerGst getCustomergstList()
	{
		List<CustomerGst> CustomergstList = new ArrayList<CustomerGst>();
		
		CustomerGst customergstdatalist = new CustomerGst();
		customergstdatalist.setId(1);
		customergstdatalist.setGstnumber("clientodc");
		customergstdatalist.setCustomergststatus(true);
		customergstdatalist.setLocation("hyd");
		customergstdatalist.setGst(true);
		customergstdatalist.setIssez(true);

		return customergstdatalist;
	}
	
	public ContactInfo getContactList()
	{
		List<ContactInfo> contactlist = new ArrayList<ContactInfo>();
		
		ContactInfo contactdatalist = new ContactInfo();
		contactdatalist.setContactId(1);
		contactdatalist.setContactName("clientodc");
		contactdatalist.setContactinfostatus(true);
		return contactdatalist;
	}
	
	public BillingInfo getBillingList()
	{
		List<BillingInfo> billinglist = new ArrayList<BillingInfo>();
		
		BillingInfo billingdatalist = new BillingInfo();
		billingdatalist.setBillingId(1);
		billingdatalist.setApcontactName("ss");
		billingdatalist.setApEmail("ss@gmail.com");
		billingdatalist.setPhoneNumber(9912345l);
		billingdatalist.setBdmconatctName("kk");
		billingdatalist.setBdmEmail("kk@gmail.com");
		billingdatalist.setAmconatctName("jj");
		billingdatalist.setAmEmail("jj@gmail.com");
		billingdatalist.setBdmconatctName("clientodc");
		billingdatalist.setBillinginfostatus(true);

		return billingdatalist;
	}
	
	public RegisteredAddress getRegisteredAddress()
	{
		List<RegisteredAddress> registeredlist = new ArrayList<RegisteredAddress>();
		
		RegisteredAddress registereddatalist = new RegisteredAddress();
		
		registereddatalist.setId(1);
		registereddatalist.setCitylocation("ss");
		registereddatalist.setStateslocation("ss");
		registereddatalist.setRegisteredpincode(199998);
		registereddatalist.setStateslocation("ss");
		return registereddatalist;
	}
	
	public Set<ServiceType> getServiceType()
	{
		Set<ServiceType> servicetypelist = new HashSet<ServiceType>();
		
		ServiceType servicetypelistdatalist = new ServiceType();
		
		servicetypelistdatalist.setId(1);
		servicetypelistdatalist.setServicetypeid("1");
		servicetypelistdatalist.setServicestatus(true);
		
       ServiceType servicetypelistdatalist1 = new ServiceType();
		
		servicetypelistdatalist1.setId(1);
		servicetypelistdatalist1.setServicetypeid("1");
		servicetypelistdatalist1.setServicestatus(true);
		
		servicetypelist.add(servicetypelistdatalist);
		servicetypelist.add(servicetypelistdatalist1);
		
		
		return servicetypelist;
	}
	
	public Set<ShippingAddress> getShippingAddress()
	{
		Set<ShippingAddress> shiipinglist = new HashSet<ShippingAddress>();
		
		ShippingAddress shiipinglistdatalist = new ShippingAddress();
		
		shiipinglistdatalist.setShippingaddressId(1);
		shiipinglistdatalist.setShippingaddressLine1("hyd");
		shiipinglistdatalist.setShippingaddressLine2("ban");
		shiipinglistdatalist.setPincode(524224);
		shiipinglistdatalist.setCitylocation("hyd");
		shiipinglistdatalist.setStateslocation("ss");
		shiipinglistdatalist.setDefaultaddressstatus(true);
		shiipinglistdatalist.setIssez(true);
		shiipinglistdatalist.setGstlocation("hyd");
		shiipinglistdatalist.setStatus(true);
		
		ShippingAddress shiipinglistdatalist1 = new ShippingAddress();
		
		shiipinglistdatalist1.setShippingaddressId(1);
		shiipinglistdatalist1.setShippingaddressLine1("hyd");
		shiipinglistdatalist1.setShippingaddressLine2("ban");
		shiipinglistdatalist1.setPincode(524224);
		shiipinglistdatalist1.setCitylocation("hyd");
		shiipinglistdatalist1.setStateslocation("ss");
		shiipinglistdatalist1.setDefaultaddressstatus(true);
		shiipinglistdatalist1.setIssez(true);
		shiipinglistdatalist1.setGstlocation("hyd");
		shiipinglistdatalist1.setStatus(true);
		
		shiipinglist.add(shiipinglistdatalist);
		shiipinglist.add(shiipinglistdatalist1);
		
		return shiipinglist;
	}
	
	public Set<BillingAddress> getBillingAddress()
	{
		Set<BillingAddress> Billingaddresslist = new HashSet<BillingAddress>();
		
		BillingAddress Billingaddressdatalist = new BillingAddress();
		
		Billingaddressdatalist.setBillingaddressId(1);
		Billingaddressdatalist.setBillingaddressLine1("hyd");
		Billingaddressdatalist.setBillingaddressLine2("ban");
		Billingaddressdatalist.setPincode(524334);
		Billingaddressdatalist.setCitylocation("hh");
		Billingaddressdatalist.setStateslocation("ss");
		Billingaddressdatalist.setDefaultaddressstatus(true);
		Billingaddressdatalist.setStatus(true);
		
		BillingAddress Billingaddressdatalist1 = new BillingAddress();
		
		Billingaddressdatalist1.setBillingaddressId(1);
		Billingaddressdatalist1.setBillingaddressLine1("hyd");
		Billingaddressdatalist1.setBillingaddressLine2("ban");
		Billingaddressdatalist1.setPincode(524334);
		Billingaddressdatalist1.setCitylocation("hh");
		Billingaddressdatalist1.setStateslocation("ss");
		Billingaddressdatalist1.setDefaultaddressstatus(true);
		Billingaddressdatalist1.setStatus(true);
		
		Billingaddresslist.add(Billingaddressdatalist);
		Billingaddresslist.add(Billingaddressdatalist1);
		
		return Billingaddresslist;
	}
	
	
	@Test
	public void testErrorSave() throws SQLException, IOException
	{
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		customerrequest.setTransactionType("save");
		
	    customerrequest.setCustomerList(this.getCustomerList());
	    customerrequest.setContactinfoList(this.getContactList());
	    customerrequest.setCustomergstList(this.getCustomergstList());
	    customerrequest.setBillinginfoList(this.getBillingList());
	    customerrequest.setRegisteredaddress(this.getRegisteredAddress());
	    customerrequest.setServicetype(this.getServiceType());
	    customerrequest.setBillingaddressList(this.getBillingAddress());
	    customerrequest.setShippingaddressList(this.getShippingAddress());
		
		when(customerRepo.save(customer)).thenReturn(true);
		
		
		customerrequest.getCustomerList().setMailstatus("sss");

		
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.setCustomer(customerrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	

	
	@Test
	public void testSuccessUpdate() throws SQLException, IOException
	{
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		customerrequest.setTransactionType("update");
		
	    customerrequest.setCustomerList(this.getCustomerList());
	    customerrequest.setContactinfoList(this.getContactList());
	    customerrequest.setCustomergstList(this.getCustomergstList());
	    customerrequest.setBillinginfoList(this.getBillingList());
	    customerrequest.setRegisteredaddress(this.getRegisteredAddress());
	    customerrequest.setServicetype(this.getServiceType());
	    customerrequest.setBillingaddressList(this.getBillingAddress());
	    customerrequest.setShippingaddressList(this.getShippingAddress());
		
		when(customerRepo.save(customer)).thenReturn(true);
		customerrequest.getCustomerList().setMailstatus("sss");
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.setCustomer(customerrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testErrorUpdate() throws SQLException, IOException
	{
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		customerrequest.setTransactionType("update");
		
		Customer custdatalist = new Customer();
		custdatalist.setCustomerId(null);
		
	    customerrequest.setCustomerList(custdatalist);
	    
	    customerrequest.setContactinfoList(this.getContactList());
	    customerrequest.setCustomergstList(this.getCustomergstList());
	    customerrequest.setBillinginfoList(this.getBillingList());
	    customerrequest.setRegisteredaddress(this.getRegisteredAddress());
	    customerrequest.setServicetype(this.getServiceType());
	    customerrequest.setBillingaddressList(this.getBillingAddress());
	    customerrequest.setShippingaddressList(this.getShippingAddress());
		
		when(customerRepo.save(customer)).thenReturn(true);
		customerrequest.getCustomerList().setMailstatus("sss");
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.setCustomer(customerrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testSuccessDelete() throws SQLException, IOException
	{
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		customerrequest.setTransactionType("delete");
		
	    customerrequest.setCustomerList(this.getCustomerList());
	    customerrequest.setContactinfoList(this.getContactList());
	    customerrequest.setCustomergstList(this.getCustomergstList());
	    customerrequest.setBillinginfoList(this.getBillingList());
	    customerrequest.setRegisteredaddress(this.getRegisteredAddress());
	    customerrequest.setServicetype(this.getServiceType());
	    customerrequest.setBillingaddressList(this.getBillingAddress());
	    customerrequest.setShippingaddressList(this.getShippingAddress());
	    
	    Customer customer2 = new Customer();
		when(customerRepo.getOne(customerrequest.getCustomerList().getCustomerId())).thenReturn(customer2);
	   customer2.setCustomerstatus(customer2.isCustomerstatus());
		
		when(customerRepo.save(customer)).thenReturn(true);
		
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.setCustomer(customerrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testErrorDelete() throws SQLException, IOException
	{
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		customerrequest.setTransactionType("delete");
		
		Customer custdatalist = new Customer();
		custdatalist.setCustomerId(null);
		
	    customerrequest.setCustomerList(custdatalist);
	    
	    customerrequest.setContactinfoList(this.getContactList());
	    customerrequest.setCustomergstList(this.getCustomergstList());
	    customerrequest.setBillinginfoList(this.getBillingList());
	    customerrequest.setRegisteredaddress(this.getRegisteredAddress());
	    customerrequest.setServicetype(this.getServiceType());
	    customerrequest.setBillingaddressList(this.getBillingAddress());
	    customerrequest.setShippingaddressList(this.getShippingAddress());
		
		when(customerRepo.save(customer)).thenReturn(true);
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.setCustomer(customerrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	@Test
	public void testError() throws SQLException, IOException
	{
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		customerrequest.setTransactionType("kk");
		
	    customerrequest.setCustomerList(this.getCustomerList());
	    customerrequest.setContactinfoList(this.getContactList());
	    customerrequest.setCustomergstList(this.getCustomergstList());
	    customerrequest.setBillinginfoList(this.getBillingList());
	    customerrequest.setRegisteredaddress(this.getRegisteredAddress());
	    customerrequest.setServicetype(this.getServiceType());
	    customerrequest.setBillingaddressList(this.getBillingAddress());
	    customerrequest.setShippingaddressList(this.getShippingAddress());
		
		when(customerRepo.save(customer)).thenReturn(true);
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.setCustomer(customerrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	
//	@Test
//	public void testSave() throws SQLException, IOException
//	{
//		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
//		customerrequest.setTransactionType("save");
//		
//	    customerrequest.setCustomerList(this.getCustomerList());
//	    customerrequest.setContactinfoList(this.getContactList());
//	    customerrequest.setCustomergstList(this.getCustomergstList());
//	    customerrequest.setBillinginfoList(this.getBillingList());
//	    
//	    Customer customer=new Customer();
//	    customer.setCustomerId(1001);
//	    customer.setCustomerName("ss");
//	
//		when(customerRepo.save(customer)).thenReturn(true);
//		
//		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.setCustomer(customerrequest);
//		HttpStatus statusCode = saveStatus.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
	
	
	
	//getTestcases
	
	
	@Test
	public void getByIdError() throws SQLException, IOException, URISyntaxException 
	{
		
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		
		customerrequest.setCustomerList(this.getCustomerList());
		
		customerrequest.setTransactionType("getById");
	
		Customer deliverylocation2 = new Customer();
		
		int id=customerrequest.getCustomerList().getCustomerId();
	
		List<Customer> customerlist = new ArrayList<Customer>();
		

		when(customerRepo.getById(id)).thenReturn(customerlist);	
		
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.getCustomerinfo(customerrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getError() throws SQLException, IOException, URISyntaxException 
	{
		
		CustomerinfoRequest customerrequest = new CustomerinfoRequest();
		
		customerrequest.setCustomerList(this.getCustomerList());
		
		customerrequest.setTransactionType("getall");
	
		Customer deliverylocation2 = new Customer();
		
		int id=customerrequest.getCustomerList().getCustomerId();
	
		List<Customer> customerlist = new ArrayList<Customer>();
		

		when(customerRepo.getById(id)).thenReturn(customerlist);	
		
		ResponseEntity<Object> saveStatus = customerinfofacadeimpl.getCustomerinfo(customerrequest);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	

	
}
