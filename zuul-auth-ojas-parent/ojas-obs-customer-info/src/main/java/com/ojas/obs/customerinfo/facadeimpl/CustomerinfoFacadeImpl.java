package com.ojas.obs.customerinfo.facadeimpl;

import static com.ojas.obs.customerinfo.constants.UtilConstants.DELETE;
import static com.ojas.obs.customerinfo.constants.UtilConstants.GETALL;
import static com.ojas.obs.customerinfo.constants.UtilConstants.GETBYID;
import static com.ojas.obs.customerinfo.constants.UtilConstants.SAVE;
import static com.ojas.obs.customerinfo.constants.UtilConstants.UPDATE;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ojas.obs.customerinfo.facade.CustomerinfoFacade;
import com.ojas.obs.customerinfo.model.BillingAddress;
import com.ojas.obs.customerinfo.model.BillingInfo;
import com.ojas.obs.customerinfo.model.ContactInfo;
import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.model.CustomerGst;
import com.ojas.obs.customerinfo.model.RegisteredAddress;
import com.ojas.obs.customerinfo.model.ServiceType;
import com.ojas.obs.customerinfo.model.ShippingAddress;
import com.ojas.obs.customerinfo.repositories.BillingAddressRepository;
import com.ojas.obs.customerinfo.repositories.BillingInfoRepository;
import com.ojas.obs.customerinfo.repositories.ContactInfoRepository;
import com.ojas.obs.customerinfo.repositories.CustomerGstRepository;
import com.ojas.obs.customerinfo.repositories.RegisteredAddressRepository;
import com.ojas.obs.customerinfo.repositories.ServiceTypeRepository;
import com.ojas.obs.customerinfo.repositories.ShippingAddressRepository;
import com.ojas.obs.customerinfo.repositoriesimpl.CustomerRepositoryImpl;
import com.ojas.obs.customerinfo.request.CustomerinfoRequest;
import com.ojas.obs.customerinfo.response.CustomerinfoResponse;
import static com.ojas.obs.customerinfo.constants.UtilConstants.ACCEPTED_MESSAGE;
import static com.ojas.obs.customerinfo.constants.UtilConstants.DECLINE_MESSAGE;
import static com.ojas.obs.customerinfo.constants.UtilConstants.ACCEPTED;
import static com.ojas.obs.customerinfo.constants.UtilConstants.REJECTED;
import static com.ojas.obs.customerinfo.constants.UtilConstants.PENDING;

@Service
public class CustomerinfoFacadeImpl implements CustomerinfoFacade {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;

	@Autowired
	private CustomerRepositoryImpl customerRepo;



	@Override
	public ResponseEntity<Object> setCustomer(CustomerinfoRequest request) throws SQLException, IOException {
		CustomerinfoResponse response = new CustomerinfoResponse();

		if (request.getTransactionType().equalsIgnoreCase(SAVE)) {
			Customer customer = request.getCustomerList();

			Set<ShippingAddress> shippingadress = request.getShippingaddressList();
			customer.setShippingaddress(shippingadress);
			
			Set<BillingAddress> billingadress = request.getBillingaddressList();
			customer.setBillingaddress(billingadress);

			ContactInfo contactInfo = request.getContactinfoList(); 
			contactInfo.setCustomer(customer);
			customer.setContactinfo(contactInfo);

			CustomerGst customergst = request.getCustomergstList();
			customergst.setCustomer(customer);
			customer.setCustomergst(customergst);

			BillingInfo billingInfo = request.getBillinginfoList();
			billingInfo.setCustomer(customer);
			customer.setBillinginfo(billingInfo);

			RegisteredAddress registeredaddress = request.getRegisteredaddress();
			registeredaddress.setCustomer(customer);
			customer.setRegisteredaddress(registeredaddress);

			Set<ServiceType> servicetype = request.getServicetype();
			customer.setServicetype(servicetype);

			System.out.println("request object is :" + request);

			Boolean saved = customerRepo.save(customer);
			
			
			if(request.getCustomerList().getMailstatus().equalsIgnoreCase(PENDING) && request.getCustomerList().getMailstatus()!=null)
			{
				
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			
		
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(env.getProperty("spring.mail.toEmail"));
			mailMessage.setCc(env.getProperty("spring.mail.ccEmail"));
			
			
			mailMessage.setSubject("Welcome to Ojas Family");
			
			mailMessage.setText(" Customer created  successfully."+ '\n'+'\n' +"This is Auto Generated Mail please ignore it");
			
			javaMailSender.send(mailMessage);
			}
			
			
			if (!(saved)) 
			{
				response.setMessage("Failed to save data");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
			
			response.setMessage("Record saved successfully");
			response.setStatusCode("200");
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}

		if (request.getTransactionType().equalsIgnoreCase(UPDATE)) {

			String message = null;
			
			Customer customer = request.getCustomerList();

			Set<ShippingAddress> shippingadress = request.getShippingaddressList();
			customer.setShippingaddress(shippingadress);
			
			Set<BillingAddress> billingadress = request.getBillingaddressList();
			customer.setBillingaddress(billingadress);

			ContactInfo contactInfo = request.getContactinfoList();
			contactInfo.setCustomer(customer);
			customer.setContactinfo(contactInfo);

			CustomerGst customergst = request.getCustomergstList();
			customergst.setCustomer(customer);
			customer.setCustomergst(customergst);

			BillingInfo billingInfo = request.getBillinginfoList();
			billingInfo.setCustomer(customer);
			customer.setBillinginfo(billingInfo);

			RegisteredAddress registeredaddress = request.getRegisteredaddress();
			registeredaddress.setCustomer(customer);
			customer.setRegisteredaddress(registeredaddress);

			Set<ServiceType> servicetype = request.getServicetype();
			customer.setServicetype(servicetype);
			
			Customer customerList = request.getCustomerList();
			
		


			if (customer.getCustomerId() != null) {
				customerRepo.save(customer);
				response.setStatusCode("200");
				response.setMessage("service details has updated successfully");
				return new ResponseEntity<Object>(response, HttpStatus.OK);

			} else {
				response.setStatusCode("422");
				response.setMessage("failed to update");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}

		if (request.getTransactionType().equalsIgnoreCase(DELETE)) {


			Customer customer = request.getCustomerList();


			if (customer.getCustomerId() != null) {

				Customer customer2 = customerRepo.getOne(customer.getCustomerId());
				System.out.println("customer id is" + customer.getCustomerId());

				customer2.setCustomerstatus(!customer2.isCustomerstatus());
				Boolean customer3 = customerRepo.save(customer2);
				response.setMessage("deleted successfully");
				response.setStatusCode("200");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}


			else {
				response.setStatusCode("422");
				response.setMessage("failed to delete");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		response.setMessage("Failed to process your request");
		response.setStatusCode("409");
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);

	}

	@Override
	public ResponseEntity<Object> getCustomerinfo(CustomerinfoRequest request)
			throws SQLException, IOException, URISyntaxException {
		CustomerinfoResponse response = new CustomerinfoResponse();

		List<Customer> customerlist = new ArrayList<>();

		if (request.getTransactionType().equalsIgnoreCase(GETBYID)) {
			customerlist = customerRepo.getById(request.getCustomerList().getCustomerId());


			if (customerlist.size() <= 0) {
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
		}
		if (request.getTransactionType().equalsIgnoreCase(GETALL)) {

			customerlist = customerRepo.getCustomerList();
			

			if (customerlist.size() <= 0) {
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
		}
		response.setCustomerList(customerlist);
		response.setMessage("Records found successfully");
		response.setStatusCode("200");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
