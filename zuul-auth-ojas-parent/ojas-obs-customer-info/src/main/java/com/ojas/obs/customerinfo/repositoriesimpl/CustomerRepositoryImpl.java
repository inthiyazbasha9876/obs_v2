package com.ojas.obs.customerinfo.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.repositories.CustomerRepository;

public class CustomerRepositoryImpl {

	@Autowired
	private CustomerRepository customerRepository;

	public Boolean save(Customer customer) {
		Boolean saved = false;
		Customer submittedRecord = customerRepository.save(customer);
		if (submittedRecord != null) {
			saved = true;
		} 
		return saved;
	}

	public List<Customer> getById(Integer id) {
		List<Customer> sheet = new ArrayList<Customer>();

		sheet.add(customerRepository.getOne(id));

		return sheet;
	}

	public List<Customer> getCustomerList() {
		List<Customer> ls = customerRepository.getAllCustomers();
		return ls;
	}

	public Customer getOne(Integer customerId) {
		Customer customer = customerRepository.getOne(customerId);

		return customer;
	}


	
}
