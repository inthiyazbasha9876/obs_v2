package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.facade.ServicecategoryFacade;
import com.ojas.obs.model.ServiceCategory;
import com.ojas.obs.repositories.ServiceCategoryRepository;
import com.ojas.obs.request.ServicecategoryRequest;
import com.ojas.obs.response.ServicecategoryResponse;


@Service
public class ServicecategoryFacadeImpl implements ServicecategoryFacade{



	//private static final String UPDATE = "update";

	@Autowired
	private ServiceCategoryRepository serviceCategoryrepo;
	

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	CmsResponse response=null;
//	ErrorResponse errorResponse = null;
	
	@Override
	public   ResponseEntity<Object> saveDetails(ServicecategoryRequest cmsRequestObject) 
	{
		
	
		ServicecategoryResponse response=null;
		
		 logger.debug("request coming to the facade");
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(SAVE))
		 {
				response = new ServicecategoryResponse();
				
				List<ServiceCategory> servicedetails = cmsRequestObject.getServicecategoryList();
				
				for (ServiceCategory service : servicedetails) 
				{
					ServiceCategory save = serviceCategoryrepo.save(service);
					
					if (save != null) 
					{
						logger.debug("save method");
						response.setStatusCode("200");
						response.setMessage("service details has saved successfully");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
					else 
					{
						logger.debug("save method");
						response.setStatusCode("422");
						response.setMessage("failed to save");
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
			
			 }

		 }
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
				response = new ServicecategoryResponse();
				
				for (ServiceCategory service : cmsRequestObject.getServicecategoryList())
				{
					Integer servicecategoryId = cmsRequestObject.getServicecategoryList().get(0).getServiceategoryId();
					Optional<ServiceCategory> findById = serviceCategoryrepo.findById(servicecategoryId);
					if (findById != null && findById.get().getServiceategoryId() != null) 
					{
						serviceCategoryrepo.save(service);
						response.setStatusCode("200");
						response.setMessage("service details has updated successfully");
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

			
					
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) 
		 {
				response = new ServicecategoryResponse();
				
				for (ServiceCategory service : cmsRequestObject.getServicecategoryList())
				{
					Integer servicecategoryId = cmsRequestObject.getServicecategoryList().get(0).getServiceategoryId();
					ServiceCategory ser = serviceCategoryrepo.getOne(servicecategoryId);
		                
					ser.setServiceStatus(!ser.getServiceStatus());
		                ServiceCategory servicedata = serviceCategoryrepo.save(ser);
					 
					if (servicedata != null) 
					{
						response.setStatusCode("200");
						response.setMessage("service details has deleted successfully");
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
						response = new ServicecategoryResponse();
						response.setStatusCode("422");
						response.setMessage("not deleted");
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
		 
		 return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getDetails(ServicecategoryRequest cmsRequestObject) {
		   List<ServiceCategory> list = cmsRequestObject.getServicecategoryList();
	        logger.debug(" getAll customer details");
	        ServicecategoryResponse response = null;
	        List<ServiceCategory> getAll = null;
	        
	        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
	            getAll = serviceCategoryrepo.findAll();
	            if (getAll == null) {
	                response = new ServicecategoryResponse();
	                response.setServicecategoryList(new ArrayList<ServiceCategory>());
	                response.setMessage("No records found");
	                response.setStatusCode("409");
	                return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	            } else {
	                response = new ServicecategoryResponse();
	                response.setServicecategoryList(getAll);
	                response.setMessage("success");
	                response.setStatusCode("200");
	                return new ResponseEntity<Object>(response, HttpStatus.OK);
	            }
	        }
	        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
	                && list.get(0).getServiceategoryId() != null) {
	        	
	            for (ServiceCategory servicedetails : list) {
	            	
	                if (servicedetails.getServiceategoryId() != null) {
	                    Integer id = cmsRequestObject.getServicecategoryList().get(0).getServiceategoryId();
	                    
	                    ArrayList<ServiceCategory> listDetails = new ArrayList<ServiceCategory>();
	                    
	                    ServiceCategory getById = serviceCategoryrepo.findById(id).orElse(new ServiceCategory());
	                    
	                    listDetails.add(getById);
	                    
	                    if (getById != null && getById.getServiceategoryId() != null) {
	                        response = new ServicecategoryResponse();
	                        response.setServicecategoryList(listDetails);
	                        response.setStatusCode("200");
	                        response.setMessage("success");
	                        return new ResponseEntity<Object>(response, HttpStatus.OK);
	                    } else {
	                        response = new ServicecategoryResponse();
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
