package com.ojas.obs.customerinfo.repositoryTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.repositories.CustomerRepository;
import com.ojas.obs.customerinfo.repositoriesimpl.CustomerRepositoryImpl;
import com.ojas.obs.customerinfo.request.CustomerinfoRequest;
import com.ojas.obs.customerinfo.response.ErrorResponse;



@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerinfoRepositoryImplTest 
{
	@Mock
	private CustomerRepositoryImpl customerrepositoryImpl;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Spy
	CustomerinfoRequest customerinforeq;
	
	@Spy
	ErrorResponse errorresponse;
	
	
	
	@Spy
	List<Customer> customerList = new ArrayList<Customer>();
	
	@Before
	public void init() throws Exception 
	{
		customerrepositoryImpl = new CustomerRepositoryImpl();
		customerRepository = mock(CustomerRepository.class);
		setCollaborator(customerrepositoryImpl, "customerRepository", customerRepository);

	}
	
	public void setCollaborator(Object object, String name, Object collab) throws Exception

	{
		Field field;
		
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collab);
		

	}

	
	public Customer getcustomer() {
		
		
		Customer customer = new Customer();
		
		customer.setCustomerName("fastfood");
		customer.setCustomerId(1);
		
		return customer;
	}
	
	
	@Test
	public void saveTest() throws Exception
	{
		
		CustomerinfoRequest customerinforeq = new CustomerinfoRequest();
		customerinforeq.setCustomerList(this.getcustomer());
		customerinforeq.setTransactionType("save");
        Customer customer = new Customer();
		
		customer.setCustomerName("fastfood");
		customer.setCustomerId(1);
		Boolean saved = false;
		

	    boolean save = customerrepositoryImpl.save(customer);
		assertEquals(false, save);
	}
	
	@Test
	public void saveeTest() throws Exception
	{
		
		CustomerinfoRequest customerinforeq = new CustomerinfoRequest();
		customerinforeq.setCustomerList(this.getcustomer());
		customerinforeq.setTransactionType("save");
        Customer customer = new Customer();
		
		customer.setCustomerName("fastfood");
		customer.setCustomerId(1);
		Boolean saved = false;
		
		when(customerRepository.save(customer)).thenReturn(customer);
	    boolean save = customerrepositoryImpl.save(customer);
		assertEquals(true, save);
	}
	
	
	@Test
	public void getbyidTest() throws Exception
	{
		
		ArrayList<Customer> arrayList = new ArrayList<Customer>();
		Customer value=mock(Customer.class);
		when(customerRepository.getOne(1)).thenReturn(value);
		customerrepositoryImpl.getById(1);

	}
	
	@Test
	public void getAllTest() throws Exception 
	{
		CustomerinfoRequest customerinforeq = new CustomerinfoRequest();
		customerinforeq.setTransactionType("getAll");
		
		 Customer customer = new Customer();
			
			customer.setCustomerName("fastfood");
			customer.setCustomerId(1001);
		
		List<Customer> list=new ArrayList<>();
		list.add(customer);
		
		when(customerRepository.findAll()).thenReturn(list);
		
		customerrepositoryImpl.getCustomerList();
      

	}
	
	@Test
	public void getoneTest() throws Exception
	{
		CustomerinfoRequest customerinforeq = new CustomerinfoRequest();
		Customer customer=new Customer();
		Customer value=mock(Customer.class);
		when(customerRepository.getOne(1)).thenReturn(customer);
		customerrepositoryImpl.getOne(1);
	}
}
