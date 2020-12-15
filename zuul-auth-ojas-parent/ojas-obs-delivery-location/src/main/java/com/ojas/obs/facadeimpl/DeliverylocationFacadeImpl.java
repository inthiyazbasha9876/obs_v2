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

import com.ojas.obs.facade.DeliverylocationFacade;
import com.ojas.obs.model.DeliveryLocation;
import com.ojas.obs.repositories.DeliveryLocationRepository;
import com.ojas.obs.request.DeliverylocationRequest;
import com.ojas.obs.response.DeliverylocationResponse;

@Service
public class DeliverylocationFacadeImpl implements DeliverylocationFacade
{
	
	@Autowired
	private DeliveryLocationRepository deliveryLocationRepo;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveDetails(DeliverylocationRequest cmsRequestObject) {
	
		DeliverylocationResponse response=null;
		
		 logger.debug("request coming to the facade");
		 
		 if (cmsRequestObject.getTransactionType().equalsIgnoreCase(SAVE))
		 {
				response = new DeliverylocationResponse();
				
				List<DeliveryLocation> details = cmsRequestObject.getDeliverylocationList();
				
				for (DeliveryLocation location : details) 
				{
					DeliveryLocation save = deliveryLocationRepo.save(location);
					
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
				response = new DeliverylocationResponse();
				
				for (DeliveryLocation details : cmsRequestObject.getDeliverylocationList())
				{
					Integer deliverylocationId = cmsRequestObject.getDeliverylocationList().get(0).getDeliverylocationId();
					Optional<DeliveryLocation> findById = deliveryLocationRepo.findById(deliverylocationId);
					if (findById != null && findById.get().getDeliverylocationId() != null) 
					{
						deliveryLocationRepo.save(details);
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
				response = new DeliverylocationResponse();
				
				for (DeliveryLocation details : cmsRequestObject.getDeliverylocationList())
				{
					Integer deliverylocationId = cmsRequestObject.getDeliverylocationList().get(0).getDeliverylocationId();
					DeliveryLocation ser = deliveryLocationRepo.getOne(deliverylocationId);
		                
					ser.setStatus(!ser.getStatus());
					DeliveryLocation locationdata = deliveryLocationRepo.save(ser);
					 
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
						response = new DeliverylocationResponse();
						response.setStatusCode("422");
						response.setMessage("not deleted");
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
		 
		 return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getDetails(DeliverylocationRequest cmsRequestObject) {
		List<DeliveryLocation> list = cmsRequestObject.getDeliverylocationList();
        logger.debug(" getAll customer details");
        DeliverylocationResponse response = null;
        List<DeliveryLocation> getAll = null;
        
        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
            getAll = deliveryLocationRepo.findAll();
            if (getAll == null) {
                response = new DeliverylocationResponse();
                response.setDeliverylocationList(new ArrayList<DeliveryLocation>());
                response.setMessage("No records found");
                response.setStatusCode("409");
                return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
            } else {
                response = new DeliverylocationResponse();
                response.setDeliverylocationList(getAll);
                response.setMessage("success");
                response.setStatusCode("200");
                return new ResponseEntity<Object>(response, HttpStatus.OK);
            }
        }
        if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                && list.get(0).getDeliverylocationId() != null) {
        	
            for (DeliveryLocation details : list) {
            	
                if (details.getDeliverylocationId() != null) {
                    Integer id = cmsRequestObject.getDeliverylocationList().get(0).getDeliverylocationId();
                    
                    ArrayList<DeliveryLocation> listDetails = new ArrayList<DeliveryLocation>();
                    
                    DeliveryLocation getById = deliveryLocationRepo.findById(id).orElse(new DeliveryLocation());
                    
                    listDetails.add(getById);
                    
                    if (getById != null && getById.getDeliverylocationId() != null) {
                        response = new DeliverylocationResponse();
                        response.setDeliverylocationList(listDetails);
                        response.setStatusCode("200");
                        response.setMessage("success");
                        return new ResponseEntity<Object>(response, HttpStatus.OK);
                    } else {
                        response = new DeliverylocationResponse();
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
