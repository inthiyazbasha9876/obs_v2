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

import com.ojas.obs.facade.SezlocationFacade;
import com.ojas.obs.model.SezLocation;
import com.ojas.obs.repositories.SezLocationRepository;
import com.ojas.obs.request.SezlocationRequest;
import com.ojas.obs.response.SezlocationResponse;

@Service
public class SezlocationFacadeImpl implements SezlocationFacade
{
	
	@Autowired
	private SezLocationRepository sezLocationRepo;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveDetails(SezlocationRequest cmsRequestObject) {
	
		SezlocationResponse response=null;
		
		 logger.debug("request coming to the facade");
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(SAVE))
		 {
				response = new SezlocationResponse();
				
				List<SezLocation> details = cmsRequestObject.getSezlocationList();
				
				for (SezLocation location : details) 
				{
					SezLocation save = sezLocationRepo.save(location);
					
					if (save.getSezlocationId()!=null) 
					{
						logger.debug("save method");
						response.setStatusCode("200");
						response.setMessage("sez location saved successfully");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
					else 
					{
						logger.debug("update method");
						response.setStatusCode("409");
						response.setMessage("failed to save");
						return new ResponseEntity<>(response, HttpStatus.CONFLICT);
					}
			
			 }

		 }
		
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
				response = new SezlocationResponse();
				
				for (SezLocation sez : cmsRequestObject.getSezlocationList()) {
				List<SezLocation> details = cmsRequestObject.getSezlocationList();
				Integer id=details.get(0).getSezlocationId();
				Optional<SezLocation>findById=sezLocationRepo.findById(id);
					if (findById.get().getSezlocationId() != null) 
					{
						sezLocationRepo.save(sez);
						response.setStatusCode("200");
						response.setMessage("sez location updated successfully");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
					
						response.setStatusCode("422");
						response.setMessage("failed to update");
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				
		
		 }
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) 
		 {
				response = new SezlocationResponse();
					Integer sezlocationId = cmsRequestObject.getSezlocationList().get(0).getSezlocationId();
					SezLocation ser = sezLocationRepo.getOne(sezlocationId);
		                
					ser.setStatus(!ser.getStatus());
					SezLocation locationdata = sezLocationRepo.save(ser);
					 
					if (locationdata.getSezlocationId() != null) 
					{
						response.setStatusCode("200");
						response.setMessage("service details deleted successfully");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
					
						
						response.setStatusCode("409");
						response.setMessage("failed to delete");
						return new ResponseEntity<>(response, HttpStatus.CONFLICT);
					}
				
			
				
		 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Object> getDetails(SezlocationRequest cmsRequestObject) {
		List<SezLocation> list = cmsRequestObject.getSezlocationList();
        logger.debug(" getAll customer details");
        SezlocationResponse response = null;
        List<SezLocation> getAll = null;
        
        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
            getAll = sezLocationRepo.findAll();
            if (getAll.isEmpty()) {
                response = new SezlocationResponse();
                response.setSezlocationList(new ArrayList<SezLocation>());
                response.setMessage("No records found");
                response.setStatusCode("409");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
                response = new SezlocationResponse();
                response.setSezlocationList(getAll);
                response.setMessage("success");
                response.setStatusCode("200");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        
        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                && list.get(0).getSezlocationId() != null) {
        	
            for (SezLocation details : list) {
            	
                if (details.getSezlocationId() != null) {
                    Integer id = cmsRequestObject.getSezlocationList().get(0).getSezlocationId();
                    
                    ArrayList<SezLocation> listDetails = new ArrayList<>();
                    
                    SezLocation getById = sezLocationRepo.findById(id).orElse(new SezLocation());
                    
                    listDetails.add(getById);
                    
                    if (getById != null && getById.getSezlocationId() != null) {
                        response = new SezlocationResponse();
                        response.setSezlocationList(listDetails);
                        response.setStatusCode("200");
                        response.setMessage("success");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } 
                        response = new SezlocationResponse();
                        response.setStatusCode("422");
                        response.setMessage("please provide valid id");
                        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
         
                }
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
}

}
