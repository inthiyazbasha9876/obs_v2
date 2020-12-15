package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;
import static com.ojas.obs.constants.Constants.NULLVALUE;

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
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facadeimpl.SezlocationFacadeImpl;
import com.ojas.obs.model.SezLocation;
import com.ojas.obs.request.SezlocationRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController
//@RequestMapping("/obs/sezlocation")
public class SezlocationController 
{

	@Autowired
	private SezlocationFacadeImpl cmsfacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value = "/set")
	public ResponseEntity<Object> saveDetails(@RequestBody SezlocationRequest cmsRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into controller");
		
			List<SezLocation> sezlocation = cmsRequestObject.getSezlocationList(); 

			try {
					if ((cmsRequestObject == null) || (cmsRequestObject.getSezlocationList() == null)
							|| cmsRequestObject.getTransactionType() == null) 
					{
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage("reqest object is null");
						return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}
					for (SezLocation location : sezlocation) 
					{
					if (cmsRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
							&&location.getSezlocationName().isEmpty()) 
					{
						
								ErrorResponse error = new ErrorResponse();
								error.setStatusCode("422");
								error.setStatusMessage(NULLVALUE);
								return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
							}
						
				
					if ((cmsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
							||cmsRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) 
						&& location.getSezlocationId()==null)
					{
								ErrorResponse error = new ErrorResponse();
								error.setStatusCode("422");
								error.setStatusMessage(NULLVALUE);
								return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
							}
						}
					
				
				return cmsfacadeImpl.saveDetails(cmsRequestObject);
				
			} catch (DuplicateKeyException e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage("duplicate key Exception");
				error.setStatusMessage(e.getCause().getLocalizedMessage());
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			} catch (Exception e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage(e.getMessage());
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}
	  }
	
	
	@PostMapping(value ="/get")
    public ResponseEntity<Object> getDetails(@RequestBody SezlocationRequest cmsRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) 
	{

        logger.debug("received data into controller");
        try {
            if (cmsRequestObject == null || cmsRequestObject.getSezlocationList()== null
                    || cmsRequestObject.getSezlocationList().isEmpty()
                    || cmsRequestObject.getTransactionType() == null
                    || cmsRequestObject.getTransactionType().isEmpty()) 
            {
                ErrorResponse error = new ErrorResponse();
                error.setStatusCode("422");
                error.setStatusMessage("object is null");
                return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            
            if (cmsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                    && cmsRequestObject.getSezlocationList().get(0).getSezlocationId() == null) {
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
