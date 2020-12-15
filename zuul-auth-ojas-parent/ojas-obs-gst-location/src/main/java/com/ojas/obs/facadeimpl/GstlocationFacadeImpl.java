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

import com.ojas.obs.facade.GstlocationFacade;
import com.ojas.obs.model.GstLocation;
import com.ojas.obs.repositories.GstLocationRepository;
import com.ojas.obs.request.GstlocationRequest;
import com.ojas.obs.response.GstlocationResponse;

@Service
public class GstlocationFacadeImpl implements GstlocationFacade
{
	
	@Autowired
	private GstLocationRepository gstLocationRepo;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveDetails(GstlocationRequest cmsRequestObject) {
	
		GstlocationResponse response=null;
		
		 logger.debug("request coming to the facade");
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(SAVE))
		 {
				response = new GstlocationResponse();
				
				List<GstLocation> details = cmsRequestObject.getGstlocationList();
				
				for (GstLocation location : details) 
				{
					GstLocation save = gstLocationRepo.save(location);
					
					if (save != null) 
					{
						logger.debug("save method");
						response.setStatusCode("200");
						response.setMessage("service details has saved successfully");
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
		
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
				response = new GstlocationResponse();
				
				for (GstLocation details : cmsRequestObject.getGstlocationList())
				{
					Integer gstlocationId = cmsRequestObject.getGstlocationList().get(0).getGstlocationId();
					Optional<GstLocation> findById = gstLocationRepo.findById(gstlocationId);
					if (findById != null && findById.get().getGstlocationId() != null) 
					{
						gstLocationRepo.save(details);
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
				response = new GstlocationResponse();
				
				for (GstLocation details : cmsRequestObject.getGstlocationList())
				{
					Integer gstlocationId = cmsRequestObject.getGstlocationList().get(0).getGstlocationId();
					GstLocation ser = gstLocationRepo.getOne(gstlocationId);
		                
					ser.setStatus(!ser.getStatus());
					GstLocation locationdata = gstLocationRepo.save(ser);
					 
					if (locationdata != null) 
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
						response = new GstlocationResponse();
						response.setStatusCode("422");
						response.setMessage("not deleted");
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
		 
		 return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getDetails(GstlocationRequest cmsRequestObject) {
		List<GstLocation> list = cmsRequestObject.getGstlocationList();
        logger.debug(" getAll customer details");
        GstlocationResponse response = null;
        List<GstLocation> getAll = null;
        
        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
            getAll = gstLocationRepo.findAll();
            if (getAll == null) {
                response = new GstlocationResponse();
                response.setGstlocationList(new ArrayList<GstLocation>());
                response.setMessage("No records found");
                response.setStatusCode("409");
                return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
            } else {
                response = new GstlocationResponse();
                response.setGstlocationList(getAll);
                response.setMessage("success");
                response.setStatusCode("200");
                return new ResponseEntity<Object>(response, HttpStatus.OK);
            }
        }
        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                && list.get(0).getGstlocationId() != null) {
        	
            for (GstLocation details : list) {
            	
                if (details.getGstlocationId() != null) {
                    Integer id = cmsRequestObject.getGstlocationList().get(0).getGstlocationId();
                    
                    ArrayList<GstLocation> listDetails = new ArrayList<GstLocation>();
                    
                    GstLocation getById = gstLocationRepo.findById(id).orElse(new GstLocation());
                    
                    listDetails.add(getById);
                    
                    if (getById != null && getById.getGstlocationId() != null) {
                        response = new GstlocationResponse();
                        response.setGstlocationList(listDetails);
                        response.setStatusCode("200");
                        response.setMessage("success");
                        return new ResponseEntity<Object>(response, HttpStatus.OK);
                    } else {
                        response = new GstlocationResponse();
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
