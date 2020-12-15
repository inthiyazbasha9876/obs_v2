package com.obs.psa.customercontactinfo.facadeimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.obs.psa.customercontactinfo.constants.ErrorResponse;
import com.obs.psa.customercontactinfo.facade.CustomerContactInfoFacade;
import com.obs.psa.customercontactinfo.model.CustomerContactInfo;
import com.obs.psa.customercontactinfo.repositories.CustomerContactInfoRepository;
import com.obs.psa.customercontactinfo.request.CustomerContactInfoRequest;
import com.obs.psa.customercontactinfo.response.CustomerContactInfoResponse;

import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.SAVE;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.UPDATE;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.DELETE;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.GETBYID;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.GETALL;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.SUCCESS;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.FAILED;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.SET;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.GET;

@Service
public class CustomerContactInfoFacadeImpl implements CustomerContactInfoFacade {
	
	@Autowired
	private CustomerContactInfoRepository customerContactInfoRepository;
	
	Logger logger = Logger.getLogger(this.getClass());
	// boolean saveCustomerContactInfo = false;

	@Override
	public ResponseEntity<Object> setCustomerContactInfo(CustomerContactInfoRequest customerContactInfoRequest) {
		

			CustomerContactInfoResponse response=null;
			
			 logger.debug("request coming to the facade");
			 
			 if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(SAVE))
			 {
					response = new CustomerContactInfoResponse();
					
					List<CustomerContactInfo> customerContacts = customerContactInfoRequest.getCustomerContactInfo();
					
					for (CustomerContactInfo contactInfo : customerContacts) 
					{
						CustomerContactInfo save = customerContactInfoRepository.save(contactInfo);
						
						if (save != null) 
						{
							logger.debug("save method");
							response.setStatusCode("200");
							response.setMessage("contact details has been saved successfully");
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						}
						else 
						{
							logger.debug("update method");
							response.setStatusCode("422");
							response.setMessage("failed to save");
							return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
						}
				
				 }

			 }
			 
			 
			 if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
					response = new CustomerContactInfoResponse();
					
					for (CustomerContactInfo details : customerContactInfoRequest.getCustomerContactInfo())
					{
						Integer contactInfoId = customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId();
						Optional<CustomerContactInfo> findById = customerContactInfoRepository.findById(contactInfoId);						
						if (findById != null) 
						{
							customerContactInfoRepository.save(details);
							response.setStatusCode("200");
							response.setMessage("contact details has been updated successfully");
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						} 
						else 
						{
							
							response.setStatusCode("422");
							response.setMessage("failed to update");
							return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					}
				} 
			 
			 
			 
			 if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(DELETE)) 
			 {
					response = new CustomerContactInfoResponse();
					
					for (CustomerContactInfo details : customerContactInfoRequest.getCustomerContactInfo())
					{
						Integer contactInfoId = customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId();
						CustomerContactInfo ser = customerContactInfoRepository.getOne(contactInfoId);
						
						ser.setStatus(!ser.getStatus());
						CustomerContactInfo contactdata = customerContactInfoRepository.save(ser);
						 
						if (contactdata != null) 
						{
							response.setStatusCode("200");
							response.setMessage("contact details has been deleted successfully");
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						} 
						else 
						{
							
							response.setStatusCode("422");
							response.setMessage("failed to delete");
							return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					}
				}
					 else 
					 {
							response = new CustomerContactInfoResponse();
							response.setStatusCode("400");
							response.setMessage("parser error");
							return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
						}
			 
			 
			 
			 

			return new ResponseEntity<Object>(response, HttpStatus.OK);
			
	
		
	}

	@Override
	public ResponseEntity<Object> getCustomerContactInfo(CustomerContactInfoRequest customerContactInfoRequest) {
			List<CustomerContactInfo> list = customerContactInfoRequest.getCustomerContactInfo();
	        logger.debug(" getAll customer details");
	        CustomerContactInfoResponse response = null;
	        List<CustomerContactInfo> getAll = null;
	        
	        if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
	            getAll = customerContactInfoRepository.findAll();
	            if (getAll == null) {
	                response = new CustomerContactInfoResponse();
	                response.setCustomerContactInfoList(new ArrayList<CustomerContactInfo>());
	                response.setMessage("No records found");
	                response.setStatusCode("409");
	                return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	            } else {
	                response = new CustomerContactInfoResponse();
	                response.setCustomerContactInfoList(getAll);
	                response.setMessage("success");
	                response.setStatusCode("200");
	                return new ResponseEntity<Object>(response, HttpStatus.OK);
	            }
	        }
	        if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(GETBYID)
	                && list.get(0).getContactId() != null) {
	        	
	            for (CustomerContactInfo details : list) {
	            	
	                if (details.getContactId() != null) {
	                    Integer id = customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId();
	                    
	                    ArrayList<CustomerContactInfo> listDetails = new ArrayList<CustomerContactInfo>();
	                    
	                    CustomerContactInfo getById = customerContactInfoRepository.findById(id).orElse(new CustomerContactInfo());
	                    
	                    listDetails.add(getById);
	                    
	                    if (getById != null && getById.getContactId() != null) {
	                        response = new CustomerContactInfoResponse();
	                        response.setCustomerContactInfoList(listDetails);
	                        response.setStatusCode("200");
	                        response.setMessage("success");
	                        return new ResponseEntity<Object>(response, HttpStatus.OK);
	                    } else {
	                        response = new CustomerContactInfoResponse();
	                        response.setStatusCode("422");
	                        response.setMessage("please provide valid id");
	                        return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	                    }

	 

	                }
	            }
	        }
	        return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	}
		
	
	
	

	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
