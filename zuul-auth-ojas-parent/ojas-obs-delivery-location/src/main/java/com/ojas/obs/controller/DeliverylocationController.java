package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facadeimpl.DeliverylocationFacadeImpl;
import com.ojas.obs.model.DeliveryLocation;
import com.ojas.obs.request.DeliverylocationRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController
//@RequestMapping("/obs/deliverylocation")
public class DeliverylocationController 
{

	@Autowired
	private DeliverylocationFacadeImpl cmsfacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value = "/set")
	public ResponseEntity<Object> saveDetails(@RequestBody DeliverylocationRequest cmsRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into controller");
		
			List<DeliveryLocation> deliverylocation = cmsRequestObject.getDeliverylocationList(); 

			try {
					if ((cmsRequestObject == null) || (cmsRequestObject.getDeliverylocationList() == null)
							|| cmsRequestObject.getTransactionType() == null) 
					{
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage("reqest object is null");
						return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}

					if (cmsRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) 
					{
						for (DeliveryLocation location : deliverylocation) 
						{
							if ( location.getDeliverylocationName().isEmpty()
									|| location.getStatus()==null)
							{
								ErrorResponse error = new ErrorResponse();
								error.setStatusCode("422");
								error.setStatusMessage(" field is null");
								return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
							}
						}
					}
				
					if (cmsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) 
					{
						for (DeliveryLocation location : deliverylocation) 
						{
							if ( location.getDeliverylocationName().isEmpty()
									|| location.getStatus()==null) 
							{
								ErrorResponse error = new ErrorResponse();
								error.setStatusCode("422");
								error.setStatusMessage(" field is null");
								return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
							}
						}
					}
				if (cmsRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
					for (DeliveryLocation location : deliverylocation) {
						if ( location.getDeliverylocationName() == null || location.getStatus()==null) {
							ErrorResponse error = new ErrorResponse();
							error.setStatusCode("422");
							error.setStatusMessage(" field is null");
							return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					}
				}
				
				return cmsfacadeImpl.saveDetails(cmsRequestObject);
				
			} catch (DuplicateKeyException e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage("duplicate key Exception");
				error.setStatusMessage(e.getCause().getLocalizedMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			} catch (Exception e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage(e.getMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			}
	  }
	
	
	@PostMapping(value ="/get")
    public ResponseEntity<Object> getDetails(@RequestBody DeliverylocationRequest cmsRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) 
	{

        logger.debug("received data into controller" + cmsRequestObject);
        try {
            if (cmsRequestObject == null || cmsRequestObject.getDeliverylocationList()== null
                    || cmsRequestObject.getDeliverylocationList().isEmpty()
                    || cmsRequestObject.getTransactionType() == null
                    || cmsRequestObject.getTransactionType().isEmpty()) 
            {
                ErrorResponse error = new ErrorResponse();
                error.setStatusCode("422");
                error.setStatusMessage("object is null");
                return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            
            if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                    && cmsRequestObject.getDeliverylocationList().get(0).getDeliverylocationId() == null) {
                logger.error("please provide id");
                ErrorResponse error = new ErrorResponse();
                error.setStatusCode("422");
                error.setStatusMessage("Type must be getAll");
                return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return cmsfacadeImpl.getDetails(cmsRequestObject);
        } 
        catch (Exception e) {
            logger.debug("inside catch block.***");
            ErrorResponse er = new ErrorResponse();
            er.setStatusCode("409");
            er.setStatusMessage(e.getMessage());
            return new ResponseEntity<>(er, HttpStatus.CONFLICT);
        }

 

    }
	
}
