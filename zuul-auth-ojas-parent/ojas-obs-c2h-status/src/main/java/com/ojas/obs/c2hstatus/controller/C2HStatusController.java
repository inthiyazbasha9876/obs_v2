package com.ojas.obs.c2hstatus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ojas.obs.c2hstatus.constants.Constants.SAVE;
import static com.ojas.obs.c2hstatus.constants.Constants.UPDATE;
import static com.ojas.obs.c2hstatus.constants.Constants.DELETE;
import static com.ojas.obs.c2hstatus.constants.Constants.GETBYID;
import static com.ojas.obs.c2hstatus.constants.Constants.NULLVALUE;
import static com.ojas.obs.c2hstatus.constants.Constants.SET;

import static com.ojas.obs.c2hstatus.constants.Constants.GET;

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

import com.ojas.obs.c2hstatus.facadeimpl.C2HStatusFacadeImpl;
import com.ojas.obs.c2hstatus.model.C2HStatus;
import com.ojas.obs.c2hstatus.request.C2HStatusRequest;
import com.ojas.obs.c2hstatus.response.ErrorResponse;

@RestController
//@RequestMapping("obs/c2hstatus")
public class C2HStatusController {
	@Autowired
	private C2HStatusFacadeImpl c2hstatusfacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(SET)
	public ResponseEntity<Object> saveC2HStatus(@RequestBody C2HStatusRequest c2hstatusRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into controller");
		
			List<C2HStatus> c2hList = c2hstatusRequestObject.getC2hstatuslist(); 
			try {
				if (c2hstatusRequestObject == null || (c2hstatusRequestObject.getC2hstatuslist()== null)
						|| c2hstatusRequestObject.getTransactionType() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(NULLVALUE);
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				for (C2HStatus contract : c2hList) {
					if (c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
							&& contract.getC2hstatus().isEmpty()) {
						ErrorResponse response = new ErrorResponse();
						response.setStatusCode("422");
						response.setStatusMessage(NULLVALUE);
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				
				if ((c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						||c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(DELETE))
				&& contract.getC2hstatusId()==null)
								
						{
							ErrorResponse error = new ErrorResponse();
							error.setStatusCode("422");
							error.setStatusMessage(NULLVALUE);
							return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					}
				
				return c2hstatusfacadeImpl.saveC2HStatus(c2hstatusRequestObject);
				
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
	@PostMapping(GET)
    public ResponseEntity<Object> getC2HStatus(@RequestBody C2HStatusRequest c2hstatusRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) 
	{

        try {
            if (c2hstatusRequestObject == null || c2hstatusRequestObject.getC2hstatuslist()== null
                    || c2hstatusRequestObject.getC2hstatuslist().isEmpty()
                    || c2hstatusRequestObject.getTransactionType() == null
                    || c2hstatusRequestObject.getTransactionType().isEmpty()) 
            {
                ErrorResponse error = new ErrorResponse();
                error.setStatusCode("422");
                error.setStatusMessage("object is null");
                return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            if (c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                    && c2hstatusRequestObject.getC2hstatuslist().get(0).getC2hstatusId() == null) {
                logger.error("please provide id");
                ErrorResponse error = new ErrorResponse();
                error.setStatusCode("422");
                error.setStatusMessage("c2hstatus must be getAll");
                return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return c2hstatusfacadeImpl.getC2HStatus(c2hstatusRequestObject);
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